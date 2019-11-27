package takeaway.client.gameofthree.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlayRequestAndResponse implements Serializable {
	private static final long serialVersionUID = 6860603557450916164L;
	private int value;
	private String inputChoice;
	private PlayerStatusEnum playerStatusEnum;

	public PlayRequestAndResponse() {

	}

	public PlayRequestAndResponse(int value) {
		super();
		this.value = value;
	}
}
