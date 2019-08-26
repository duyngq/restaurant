package ampos.miniproject.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * RestaurantApplication is used to initialize application
 *
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan( { "ampos.miniproject.restaurant" } )
public class RestaurantApplication {
    public static void main( String[] args ) {
        SpringApplication.run( RestaurantApplication.class, args );
    }
}
