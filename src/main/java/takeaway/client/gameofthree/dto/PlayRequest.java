package takeaway.client.gameofthree.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlayRequest implements Serializable{
	private static final long serialVersionUID = 6860603557450916164L;
	private int value;
}
