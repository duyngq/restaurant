package ampos.miniproject.restaurant.models;

import java.math.BigDecimal;

import ampos.miniproject.restaurant.dto.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillItemsResponse {
    private Long id;
    private int quantity;
    private MenuDTO menu;
    private String orderedTime;
    private Long billId;
    private BigDecimal subTotal;
}
