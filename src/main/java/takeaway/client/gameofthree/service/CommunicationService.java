package takeaway.client.gameofthree.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import takeaway.client.gameofthree.dto.JwtResponse;
import takeaway.client.gameofthree.dto.Player;

@Service
public class CommunicationService {

	@Value("${gameofthree.server.ip}")
	private String serverIp;
	@Value("${gameofthree.server.port}")
	private String serverPort;
	@Value("${gameofthree.server.register.path}")
	private String serverRegisterPath;

	private final String BEAERER = "Bearer ";
	private final String HTTP ="http";

	@Autowired
	private RestTemplate restTemplate;

	public JwtResponse registerUser(Player player) {
		String URI = buildURI(serverRegisterPath);
		System.out.println("URI"+URI);
		JwtResponse response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-type", "application/json");
			HttpEntity<Player> entity = new HttpEntity<>(player, headers);
			response = restTemplate.postForObject(URI, player, JwtResponse.class);
			System.out.println(response.getJwttoken());

		} catch (RestClientException e) {
			// TODO handle communicationExecption
			System.out.println("error "+ e.getMessage());
		}

		return response;
	}

	/*
	 * HttpHeaders headers = createHeaders(jwt); HttpEntity<String> entity = new
	 * HttpEntity<>("body", headers);
	 */
	private HttpHeaders createHeaders(String jwt) {
		return new HttpHeaders() {
			{
				String authHeader = BEAERER + jwt;
				set(HttpHeaders.AUTHORIZATION, authHeader);
			}
		};
	}

	private String buildURI(String path) {
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		String URI = builder.scheme(HTTP).host(serverIp).port(serverPort).path(path).toUriString();
		return URI;
	}
}
