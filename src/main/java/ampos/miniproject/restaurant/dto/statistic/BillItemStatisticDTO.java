package ampos.miniproject.restaurant.dto.statistic;

import java.math.BigDecimal;

import ampos.miniproject.restaurant.dto.MenuDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Bill item statistic DTO
 */
@Getter
@Setter
public class BillItemStatisticDTO {
    private MenuDTO menu;
    private long quantity;
    private BigDecimal subTotal;
}
