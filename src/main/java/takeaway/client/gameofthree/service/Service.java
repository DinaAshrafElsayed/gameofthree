package takeaway.client.gameofthree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import takeaway.client.gameofthree.dto.Player;

@org.springframework.stereotype.Service
public class Service {
	@Autowired
	private RestTemplate restTemplate;
	public void registerUser(Player player) {
		
	}
}
