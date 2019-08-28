package ampos.miniproject.restaurant.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Menu DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class MenuDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String imageUrl;

    @NotNull
    private BigDecimal price;

    private List<String> details;

    public MenuDTO(Long id, @NotNull String name, @NotNull String description, String imageUrl,
            @NotNull BigDecimal price, List<String> details) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.details = details;
    }
}
