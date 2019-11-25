package takeaway.client.gameofthree.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import takeaway.client.gameofthree.dto.GameInvitationResponse;
import takeaway.client.gameofthree.dto.GameInvitationStatusEnum;

@Service
public class InvitationService {

	@Autowired
	private CommunicationService communicationService;

	public ModelAndView inviteAnotherPlayer(String playerEmail, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		GameInvitationResponse response = communicationService.invitePlayer(playerEmail, token);
		ModelAndView model;
		if (GameInvitationStatusEnum.ACCEPTED.name().equals(response.getAcceptInvitationResponse())) {
			model = new ModelAndView("game");
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt((100 - 2) + 1) + 2;
			model.addObject("initalValue", randomInt);
			return model;
		} else {
			Set<String> players = communicationService.getAvaliablePlayers(token);
			model = new ModelAndView("retry");
			model.addObject("players", players);
		}
		return model;
	}


}
