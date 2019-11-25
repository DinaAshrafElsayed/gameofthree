package takeaway.client.gameofthree.service;

import java.net.UnknownHostException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import takeaway.client.gameofthree.dto.JwtResponse;

@Service
public class InitiationOfGameService {
	
	@Autowired
	private CommunicationService communicationService;
	@Autowired
	private RegisterationService registerationService;
	
	public Set<String> startGame(HttpServletRequest request,String email) throws UnknownHostException{
		//TODO handle error cases
		JwtResponse jwtResponse = registerationService.registerPlayer(email);
		request.getSession().setAttribute("token", jwtResponse.getJwttoken());
		Set<String> players =  communicationService.getAvaliablePlayers(jwtResponse.getJwttoken());
		return players;
	}

}
