package ampos.miniproject.restaurant.exception;

/**
 * Application application exception
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message
     * @param cause
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
