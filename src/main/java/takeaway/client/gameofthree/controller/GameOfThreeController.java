package takeaway.client.gameofthree.controller;

import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import takeaway.client.gameofthree.dto.ChoicesEnum;
import takeaway.client.gameofthree.dto.GameInvitationResponse;
import takeaway.client.gameofthree.dto.GameInvitationStatusEnum;
import takeaway.client.gameofthree.dto.JwtResponse;
import takeaway.client.gameofthree.service.CommunicationService;
import takeaway.client.gameofthree.service.InvitationService;
import takeaway.client.gameofthree.service.RegisterationService;

@Controller
@Validated
public class GameOfThreeController {

	@Autowired
	private CommunicationService communicationService;

	@Autowired
	private RegisterationService registerationService;

	@Autowired
	private InvitationService invitationService;

	@GetMapping({ "/" })
	public String showRegisterPage() {
		return "register";
	}

	@PostMapping({ "/register" })
	public String showStartGamePage(@Email @RequestParam String email, HttpServletRequest request)
			throws UnknownHostException {
		JwtResponse jwtResponse = registerationService.registerPlayer(email);
		request.getSession().setAttribute("token", jwtResponse.getJwttoken());
		return "start";
	}

	@GetMapping({ "/start" })
	public ModelAndView showAvalaiblePlayers(HttpServletRequest request) throws UnknownHostException {
		String token = (String) request.getSession().getAttribute("token");
		Set<String> players = communicationService.getAvaliablePlayers(token);
		ModelAndView model = new ModelAndView("avaliablePlayers");
		model.addObject("players", players);
		return model;
	}

	@PostMapping({ "/invitePlayer" })
	public ModelAndView invitePlayer(@Email @RequestParam String email, HttpServletRequest request) {
		return invitationService.inviteAnotherPlayer(email, request);
	}

	@PostMapping({ "/startPlaying" })
	public String initGame(@RequestParam(required = true) String choice, @RequestParam int initalValue,
			HttpServletRequest request) throws UnknownHostException {
		if (ChoicesEnum.MANUAL.name().equalsIgnoreCase(choice)) {
			request.getSession().setAttribute("choice", ChoicesEnum.MANUAL.name());
		} else {
			request.getSession().setAttribute("choice", ChoicesEnum.AUTOMATIC.name());
		}
		request.getSession().setAttribute("currValue", initalValue);
		String token = (String) request.getSession().getAttribute("token");
		communicationService.play(token, initalValue);
		return "wait";
	}

	@PostMapping({ "/play" })
	public String play(@RequestParam(required = false) int value, HttpServletRequest request)
			throws UnknownHostException {
		String token = (String) request.getSession().getAttribute("token");
		communicationService.play(token, value);
		// handle mid game or start
		// if start
		// takes manual or automatic input
		// start game (random number)
		// if mid
		// valid input or generate number (game automatic/manual)
		// handle game or lose
		return "wait till other player send new value";
	}

	@GetMapping({ "/accept" })
	public ResponseEntity<?> acceptInvitation(@RequestParam @Email String email) {
		// FIXME it shouldn't be console input
		System.out.println("please enter 1 to accept any other number to decline=");
		Scanner scanner = new Scanner(System.in);
		int userResponse = scanner.nextInt();
		GameInvitationResponse response = new GameInvitationResponse();
		if (userResponse == 1) {
			response.setAcceptInvitationResponse(GameInvitationStatusEnum.ACCEPTED.name());
		} else {
			response.setAcceptInvitationResponse(GameInvitationStatusEnum.DECLINED.name());
		}
		scanner.close();
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/playRediect", method = RequestMethod.GET)
	public ModelAndView finalPage(@RequestParam int value) {
		ModelAndView model = new ModelAndView("play");
		model.addObject("initalValue", value);
		return model;
	}

	@GetMapping({ "/receive" })
	public RedirectView  recieveAValue(@RequestParam int value) {
		//TODO make it async and call play with new value no redirection and use console input
		String redirectURL = "forward:/playRediect?value=" + value;
		return new RedirectView(redirectURL);
	}

}