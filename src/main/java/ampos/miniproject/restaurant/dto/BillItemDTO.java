package ampos.miniproject.restaurant.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Bill item DTO
 */
public class BillItemDTO implements Serializable {
    private Long id;
    private int quantity;
    private MenuDTO menu;
    private Instant orderedTime;
    private Long billId;
    private BigDecimal subTotal;
}