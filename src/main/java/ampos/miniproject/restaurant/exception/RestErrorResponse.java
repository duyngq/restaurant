package ampos.miniproject.restaurant.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Represent the error with message for a REST call
 */
@Getter
@Setter
public class RestErrorResponse {
    private String timestamp;
    private String error;
    private String message;
}
