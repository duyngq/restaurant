package ampos.miniproject.restaurant.rest.util;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    private ResponseUtil() {
    }

    public static <X> ResponseEntity<X> wrapOrNotFound( Optional<X> maybeResponse ) {
        return wrapOrNotFound( maybeResponse, (HttpHeaders) null );
    }

    public static <X> ResponseEntity<X> wrapOrNotFound( Optional<X> maybeResponse, HttpHeaders header ) {
        return maybeResponse.map( ( response ) -> {
            return ResponseEntity.ok().headers( header ).body( response );
        } ).orElse( new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    }
}
