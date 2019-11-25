package takeaway.client.gameofthree.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@JsonDeserialize
@Data
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonProperty("jwttoken")
	private String jwttoken;

}