package ampos.miniproject.restaurant.model.mapper;

import org.mapstruct.Mapper;

import ampos.miniproject.restaurant.dto.request.BillItemRequestDTO;
import ampos.miniproject.restaurant.dto.statistic.BillItemStatisticDTO;
import ampos.miniproject.restaurant.model.BillItemStatistic;

/**
 * Bill item statistic mapper
 */
@Mapper( componentModel = "spring", uses = { MenuMapper.class })
public interface BillItemStatisticMapper extends GenericMapper<BillItemStatisticDTO, BillItemStatistic, BillItemRequestDTO> {

    /**
     *
     * @param billItemReport
     * @return billItemReportDTO which is DTO of billItemReport
     */
    BillItemStatisticDTO entityToDto( BillItemStatistic billItemReport );
}
