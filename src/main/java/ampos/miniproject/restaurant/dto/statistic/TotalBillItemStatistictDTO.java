package ampos.miniproject.restaurant.dto.statistic;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Total bill item report DTO
 */
public class TotalBillItemStatistictDTO {
    private List<BillItemStatisticDTO> billItems;
}
