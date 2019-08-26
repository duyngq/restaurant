package ampos.miniproject.restaurant.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Statistic of all menus in a bill
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillItemStatistic {
    private Menu menu;
    private long quantity;
    private BigDecimal subTotal;
}
