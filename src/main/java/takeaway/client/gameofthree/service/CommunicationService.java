package takeaway.client.gameofthree.service;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import takeaway.client.gameofthree.dto.GameInvitationResponse;
import takeaway.client.gameofthree.dto.JwtResponse;
import takeaway.client.gameofthree.dto.PlayRequestAndResponse;
import takeaway.client.gameofthree.dto.Player;

@Configuration
public class CommunicationService {

	@Value("${gameofthree.server.ip}")
	private String serverIp;
	@Value("${gameofthree.server.port}")
	private String serverPort;
	@Value("${gameofthree.server.register.path}")
	private String serverRegisterPath;
	@Value("${gameofthree.server.availablePlayers.path}")
	private String serverAvailablePlayersrPath;
	@Value("${gameofthree.server.invite.Startpath}")
	private String inviteStartPath;
	@Value("${gameofthree.server.invite.endpath}")
	private String inviteEndPath;
	@Value("${gameofthree.server.play.path}")
	private String playPath;
	@Value("${gameofthree.server.unregister.path}")
	private String unregisterPath;

	private final String CONTENT_TYPE = "application/json;charset=UTF-8";
	private final String BEAERER = "Bearer ";
	private final String HTTP = "http";

	@Autowired
	private RestTemplate restTemplate;

	public JwtResponse registerUser(Player player) {
		String URI = buildURI(serverRegisterPath);
		JwtResponse jwtResponse = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Player> entity = new HttpEntity<>(player, headers);
			ResponseEntity<JwtResponse> responseEntity = restTemplate.exchange(URI, HttpMethod.POST, entity,
					JwtResponse.class);
			jwtResponse = responseEntity.getBody();
			HttpStatus status = responseEntity.getStatusCode();
			// TODO use it to handle specific client errors
		} catch (RestClientException e) {
			// TODO handle communicationExecption
			System.out.println("error " + e.getMessage());
		}
		return jwtResponse;
	}

	public GameInvitationResponse invitePlayer(String player, String jwt) {
		String URI = buildURI(inviteStartPath, player, inviteEndPath);
		HttpEntity<GameInvitationResponse> responseEntity = null;
		try {
			HttpHeaders headers = createHeaders(jwt);
			headers.set(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			responseEntity = restTemplate.exchange(URI, HttpMethod.GET, entity, GameInvitationResponse.class);
			// TODO use it to handle specific client errors
		} catch (RestClientException e) {
			// TODO handle communicationExecption
			System.out.println("error " + e.getMessage());
		}

		return responseEntity.getBody();
	}
	
	
	public void unregister(String jwt) {
		String URI = buildURI(unregisterPath);
		try {
			HttpHeaders headers = createHeaders(jwt);
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			restTemplate.exchange(URI, HttpMethod.DELETE, entity, ResponseEntity.class);
		} catch (RestClientException e) {
			System.out.println("error " + e.getMessage());
		}
	}

	public void sendNewValueToOtherPlayer(int value) {

	}

	public PlayRequestAndResponse play(String jwt, int value) {
		ResponseEntity<PlayRequestAndResponse> response = null ;
		try {
			String URI = buildURI(playPath);
			HttpHeaders headers = createHeaders(jwt);
			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
			PlayRequestAndResponse request = new PlayRequestAndResponse();
			request.setValue(value);
			HttpEntity<PlayRequestAndResponse> entity = new HttpEntity<>(request, headers);
			response  = restTemplate.postForEntity(URI, entity,PlayRequestAndResponse.class);
		} catch (RestClientException e) {
			// TODO handle communicationExecption
			System.out.println("error " + e.getMessage());
		}
		return response.getBody();
	}

	public Set<String> getAvaliablePlayers(String jwt) {
		String URI = buildURI(serverAvailablePlayersrPath);
		HttpHeaders headers = createHeaders(jwt);
		headers.set(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<Set<String>> PlayersResponses = restTemplate.exchange(URI, HttpMethod.GET, entity,
				new ParameterizedTypeReference<Set<String>>() {
				});
		return PlayersResponses.getBody();
	}

	private HttpHeaders createHeaders(String jwt) {
		return new HttpHeaders() {
			private static final long serialVersionUID = -3993880285131909883L;

			{
				String authHeader = BEAERER + jwt;
				set(HttpHeaders.AUTHORIZATION, authHeader);
			}
		};
	}

	private String buildURI(String... paths) {
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		builder = builder.scheme(HTTP).host(serverIp).port(serverPort);
		for (String path : paths) {
			builder = builder.path(path);
		}
		return builder.toUriString();
	}
}
