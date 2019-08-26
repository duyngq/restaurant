package ampos.miniproject.restaurant.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalStatisticDTO {
    private TotalBillItemStatistictDTO billItemReport;
    private TotalBillStatisticDTO billReport;
}
