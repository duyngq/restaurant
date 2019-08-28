package ampos.miniproject.restaurant.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Bill item DTO
 */
@Getter
@Setter
public class BillItemDTO implements Serializable {
    private Long id;
    private int quantity;
    private MenuDTO menu;
    private Date orderedTime;
    private Long billId;
    private BigDecimal subTotal;
}