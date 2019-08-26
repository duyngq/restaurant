package ampos.miniproject.restaurant.models;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private List<String> details;
}
