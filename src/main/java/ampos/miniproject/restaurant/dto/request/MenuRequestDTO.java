package ampos.miniproject.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for request of a menu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String imageUrl;

    @NotNull
    private BigDecimal price;

    private List<String> details;
}
