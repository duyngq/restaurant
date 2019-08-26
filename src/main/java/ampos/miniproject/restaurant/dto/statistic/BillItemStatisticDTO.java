package ampos.miniproject.restaurant.dto.statistic;

import java.math.BigDecimal;

import ampos.miniproject.restaurant.dto.MenuDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Bill item report DTO
 */
public class BillItemStatisticDTO {
    private MenuDTO menu;
    private long quantity;
    private BigDecimal subTotal;
}
