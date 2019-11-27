package takeaway.client.gameofthree.service;

import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import takeaway.client.gameofthree.dto.ChoicesEnum;
import takeaway.client.gameofthree.dto.PlayRequestAndResponse;
import takeaway.client.gameofthree.dto.PlayerStatusEnum;

@Service
public class GameOfThreeService {
	@Autowired
	private CommunicationService communicationService;

	public ModelAndView play(Integer value, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		String choice = (String) request.getSession().getAttribute("choice");
		int previousVal = (int) request.getSession().getAttribute("currValue");
		ModelAndView model = new ModelAndView("play");
		if (ChoicesEnum.AUTOMATIC.name().equalsIgnoreCase(choice)) {
			model = handleAutomaticCase(request, token, previousVal, model);
		} else {
			model = handleManualCase(value, request, token, previousVal, model);
		}
		return model;
	}

	private ModelAndView handleManualCase(Integer value, HttpServletRequest request, String token, int previousVal,
			ModelAndView model) {
		int tempValue = previousVal + value;
		if (tempValue % 3 != 0 || (value != 1 && value != 0 && value != -1)) {
			model.addObject("message", "your input was incorrect please enter");
			model.addObject("value", previousVal);
		} else {
			value = previousVal + value;
			PlayRequestAndResponse response = communicationService.play(token, value);
			if (PlayerStatusEnum.WON == response.getPlayerStatusEnum()) {
				model = getEndModel(PlayerStatusEnum.WON.getMessage());
			} else if (PlayerStatusEnum.LOST == response.getPlayerStatusEnum()) {
				model = getEndModel(PlayerStatusEnum.LOST.getMessage());
			} else {
				request.getSession().setAttribute("currValue", response.getValue());
				int newValue = response.getValue();
				model.addObject("value", newValue);
			}
		}
		return model;
	}

	private ModelAndView handleAutomaticCase(HttpServletRequest request, String token, int previousVal,
			ModelAndView model) {
		Integer value;
		value = getAutomaticValue(previousVal);
		PlayRequestAndResponse response = communicationService.play(token, value);
		int newValue = response.getValue();
		if (PlayerStatusEnum.WON == response.getPlayerStatusEnum()) {
			model = getEndModel(PlayerStatusEnum.WON.getMessage());
		} else if (PlayerStatusEnum.LOST == response.getPlayerStatusEnum()) {
			model = getEndModel(PlayerStatusEnum.LOST.getMessage());
		} else {
			request.getSession().setAttribute("currValue", newValue);
			int nextValue = getAutomaticValue(newValue);
			model.addObject("message", "the server generate your next input as " + nextValue);
			model.addObject("value", newValue);
		}
		return model;
	}

	private ModelAndView getEndModel(String message) {
		ModelAndView model = new ModelAndView("end");
		model.addObject("message", message);
		return model;
	}

	private Integer getAutomaticValue(int previousVal) {
		Integer value;
		if (previousVal % 3 == 0) {
			value = previousVal;
		} else if ((previousVal + 1) % 3 == 0) {
			value = previousVal + 1;
		} else {
			value = previousVal - 1;
		}
		return value;
	}

	public ModelAndView handleFirstPlay(String choice, int initalValue, HttpServletRequest request) {
		if (ChoicesEnum.MANUAL.name().equalsIgnoreCase(choice)) {
			request.getSession().setAttribute("choice", ChoicesEnum.MANUAL.name());
		} else {
			request.getSession().setAttribute("choice", ChoicesEnum.AUTOMATIC.name());
		}
		String token = (String) request.getSession().getAttribute("token");
		ModelAndView model;
		PlayRequestAndResponse response = communicationService.play(token, initalValue);
		if (PlayerStatusEnum.WON == response.getPlayerStatusEnum()) {
			model = getEndModel(PlayerStatusEnum.WON.getMessage());
		} else if (PlayerStatusEnum.LOST == response.getPlayerStatusEnum()) {
			model = getEndModel(PlayerStatusEnum.LOST.getMessage());
		} else {
			model = new ModelAndView("play");
			int newValue = response.getValue();
			request.getSession().setAttribute("currValue", newValue);
			model.addObject("value", newValue);
		}
		return model;
	}

	public PlayRequestAndResponse handlePlayForPlayerTwo(int value, boolean firstRound, String inputChoice,
			Scanner scanner) {
		String inputType;
		PlayRequestAndResponse response = new PlayRequestAndResponse();
		if (firstRound) {
			int choice = getInputChoice(scanner);
			inputType = choice == 1 ? ChoicesEnum.MANUAL.name() : ChoicesEnum.AUTOMATIC.name();
			response.setInputChoice(inputType);
		} else {
			inputType = inputChoice;
		}
		if (ChoicesEnum.AUTOMATIC.name().equalsIgnoreCase(inputType)) {
			value = getAutomaticValue(value);
			System.out.println("automatic generated out is " + value);
		} else {
			value = handleManualInput(value, scanner);
		}
		response.setValue(value);
		return response;
	}

	private int getInputChoice(Scanner scanner) {
		System.out.println("please press 1 for manual input or 2 for automatic input");
		int choice = scanner.nextInt();
		while (choice != 1 && choice != 2) {
			System.out.println("please press 1 for manual input or 2 for automatic input");
			choice = scanner.nextInt();
		}
		return choice;
	}

	private int handleManualInput(int value, Scanner scanner) {
		System.out.println("value is : " + value + " please enter one of the following values");
		System.out.println("{-1,0,1} to make the new number divisble by three");
		int userInput = scanner.nextInt();
		int tempValue = userInput + value;
		while (tempValue % 3 != 0 || (userInput != 1 && userInput != 0 && userInput != -1)) {
			System.out.println(" wrong input");
			System.out.println(
					"please make sure to enter on of the following {-1,0,1} to make the new number divisble by three");
			userInput = scanner.nextInt();
			tempValue = userInput + value;
		}
		value = value + userInput;
		return value;
	}
}
