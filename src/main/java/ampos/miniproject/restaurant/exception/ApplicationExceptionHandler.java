package ampos.miniproject.restaurant.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Application application handler
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	  private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ssXXX";
	  
    @ExceptionHandler( ApplicationException.class )
    public ResponseEntity<Object> handleApplicationException( ApplicationException ex ) {
    	return buildResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }
    
    
    /**
     * Wrap the response of exception with RestErrorResponse
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return buildResponseEntity(ex, status);
    }
    
    
     private ResponseEntity<Object> buildResponseEntity(Exception ex,HttpStatus status) {
    	RestErrorResponse errorRespone = new RestErrorResponse();
    	errorRespone.setTimestamp(new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date()));
        errorRespone.setError(status.name());
        errorRespone.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorRespone,status);
    }
}
