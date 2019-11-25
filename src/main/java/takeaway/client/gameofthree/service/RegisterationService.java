package takeaway.client.gameofthree.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import takeaway.client.gameofthree.dto.JwtResponse;
import takeaway.client.gameofthree.dto.Player;

@Service
public class RegisterationService {
	@Value("${server.port}")
	private String serverPort;
	@Autowired
	private CommunicationService communicationService;

	public JwtResponse registerPlayer(String email) throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getLocalHost();
		String address = inetAddress.toString();
		int index = address.lastIndexOf("/");
		String ip = address.substring(index + 1);
		Player player = new Player();
		player.setEmail(email);
		player.setIp(ip);
		player.setPort(serverPort);
		return communicationService.registerUser(player);
	}

}
