package ampos.miniproject.restaurant.dto.statistic;

import java.math.BigDecimal;
import java.util.List;

import ampos.miniproject.restaurant.dto.BillDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Total bill statistic DTO
 */
@Setter
@Getter
public class TotalBillStatisticDTO {
    private int noOfBills;
    private BigDecimal grandTotal;
    private List<BillDTO> bills;
}
