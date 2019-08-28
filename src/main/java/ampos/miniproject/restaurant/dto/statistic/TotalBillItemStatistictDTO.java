package ampos.miniproject.restaurant.dto.statistic;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Total bill item statistic DTO
 */
@Getter
@Setter
public class TotalBillItemStatistictDTO {
    private List<BillItemStatisticDTO> billItems;
}
