package ampos.miniproject.restaurant.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequestDTO implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String imageUrl;

    @NotNull
    private BigDecimal price;

    private List<String> details;
}
