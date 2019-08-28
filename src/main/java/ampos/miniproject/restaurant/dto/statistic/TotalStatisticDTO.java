package ampos.miniproject.restaurant.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Total statistic for bill and menu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalStatisticDTO {
    private TotalBillItemStatistictDTO billItemReport;
    private TotalBillStatisticDTO billReport;
}
