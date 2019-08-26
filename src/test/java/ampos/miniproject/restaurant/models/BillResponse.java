package ampos.miniproject.restaurant.models;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private Long id;
    private Set<BillItemsResponse> billItems = new HashSet<>();
    private BigDecimal total;
}
