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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import takeaway.client.gameofthree.dto.ChoicesEnum;
import takeaway.client.gameofthree.dto.GameInvitationResponse;
import takeaway.client.gameofthree.dto.GameInvitationStatusEnum;
import takeaway.client.gameofthree.dto.JwtResponse;
import takeaway.client.gameofthree.dto.PlayRequestAndResponse;
import takeaway.client.gameofthree.service.CommunicationService;
import takeaway.client.gameofthree.service.GameOfThreeService;
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

	@Autowired
	private GameOfThreeService gameOfThreeService;

	private Scanner scanner = new Scanner(System.in);

	@GetMapping({ "/" })
	public String showRegisterPage() {
		return "register";
	}

	@PostMapping({ "/register" })
	public String showStartGamePage(@Email @RequestParam String email, HttpServletRequest request)
			throws UnknownHostException {
		JwtResponse jwtResponse = registerationService.registerPlayer(email);
		request.getSession().setAttribute("token", jwtResponse.getJwttoken());
		request.getServletContext().setAttribute("token", jwtResponse.getJwttoken());
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
	public ModelAndView initGame(@RequestParam(required = true) String choice, @RequestParam int initalValue,
			HttpServletRequest request) {
		return gameOfThreeService.handleFirstPlay(choice, initalValue, request);
	}

	@PostMapping({ "/play" })
	public ModelAndView play(@RequestParam(required = false) Integer value, HttpServletRequest request) {
		return gameOfThreeService.play(value, request);
	}

	@GetMapping({ "/accept" })
	public ResponseEntity<?> acceptInvitation(@RequestParam @Email String email) {
		System.out.println("please enter 1 to accept any other number to decline=");
		int userResponse = scanner.nextInt();
		GameInvitationResponse response = new GameInvitationResponse();
		if (userResponse == 1) {
			response.setAcceptInvitationResponse(GameInvitationStatusEnum.ACCEPTED.name());
		} else {
			response.setAcceptInvitationResponse(GameInvitationStatusEnum.DECLINED.name());
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping({ "/receive" })
	public ResponseEntity<?> recieveAValue(@RequestParam int value, @RequestParam boolean firstRound,
			@RequestParam(required = false) String inputChoice, @RequestParam boolean playerOneWon) {
		PlayRequestAndResponse response = new PlayRequestAndResponse();
		if (playerOneWon) {
			System.out.println("you LOST");
			return ResponseEntity.ok(response);
		} else {
			response = gameOfThreeService.handlePlayForPlayerTwo(value, firstRound, inputChoice, scanner);
			if (response.getValue() == 3)
				System.out.println("you won");
			return ResponseEntity.ok(response);
		}
	}

}