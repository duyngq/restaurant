package ampos.miniproject.restaurant.dto.statistic;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import ampos.miniproject.restaurant.dto.BillDTO;

@Setter
@Getter
/**
 * Total bill report DTO
 */
public class TotalBillStatisticDTO {
    private int noOfBills;
    private BigDecimal grandTotal;
    private List<BillDTO> bills;
}
