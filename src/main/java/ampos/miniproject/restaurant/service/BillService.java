package ampos.miniproject.restaurant.service;

import ampos.miniproject.restaurant.dto.BillDTO;
import ampos.miniproject.restaurant.dto.request.BillRequestDTO;
import ampos.miniproject.restaurant.dto.statistic.TotalStatisticDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;

/**
 * Bill and Bill Item Service
 *
 */
public interface BillService extends GenericService<BillRequestDTO, BillDTO, Long> {
    /**
     * Get bill and bill item report
     *
     * @return
     * @throws ApplicationException
     */
    TotalStatisticDTO getBillAndBillItemStatistic() throws ApplicationException;
}