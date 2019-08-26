package ampos.miniproject.restaurant.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestErrorResponse {
	private String timestamp;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	private String error;
	private String message;
}
