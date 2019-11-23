package takeaway.client.gameofthree.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import takeaway.client.gameofthree.dto.Player;
import takeaway.client.gameofthree.service.CommunicationService;

@Controller
@Validated
public class GameOfThreeController {
	@Value("${server.port}")
	private String serverPort;
	
	
	@Autowired
	private CommunicationService communicationService;

	@GetMapping({"/"})
    public String showLoginPage() throws UnknownHostException{
		InetAddress inetAddress = InetAddress.getLocalHost();
		Player player = new Player();
		player.setEmail("dina@gmail.com");
		player.setIp("127.127.127.1");
		player.setPort(serverPort);
		communicationService.registerUser(player);
        return "welcome";
    }
}