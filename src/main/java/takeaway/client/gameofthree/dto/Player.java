package takeaway.client.gameofthree.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

/**
 * 
 * @author El-sayedD
 *
 */
@JsonDeserialize(as = Player.class)
@Data
public class Player implements Serializable {

	private static final long serialVersionUID = -7199112174977103043L;
	@NotBlank
	@Email
	private String email;
	private String ip;
	private String port;
	private String token;
}
