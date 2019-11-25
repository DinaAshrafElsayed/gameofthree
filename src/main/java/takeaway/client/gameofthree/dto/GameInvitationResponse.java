package takeaway.client.gameofthree.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GameInvitationResponse implements Serializable{
	private static final long serialVersionUID = 6976580149032288969L;
	String acceptInvitationResponse;
	public GameInvitationResponse() {

	}

	public GameInvitationResponse(String acceptInvitationResponse) {
		this.acceptInvitationResponse = acceptInvitationResponse;
	}

}
