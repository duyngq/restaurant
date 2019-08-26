package ampos.miniproject.restaurant.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ampos.miniproject.restaurant.dto.BillDTO;
import ampos.miniproject.restaurant.dto.request.BillItemRequestDTO;
import ampos.miniproject.restaurant.dto.request.BillRequestDTO;
import ampos.miniproject.restaurant.dto.statistic.TotalBillItemStatistictDTO;
import ampos.miniproject.restaurant.dto.statistic.TotalBillStatisticDTO;
import ampos.miniproject.restaurant.dto.statistic.TotalStatisticDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.model.Bill;
import ampos.miniproject.restaurant.model.BillItem;
import ampos.miniproject.restaurant.model.Menu;
import ampos.miniproject.restaurant.model.mapper.BillItemStatisticMapper;
import ampos.miniproject.restaurant.model.mapper.BillMapper;
import ampos.miniproject.restaurant.repository.BillItemRepository;
import ampos.miniproject.restaurant.repository.BillRepository;
import ampos.miniproject.restaurant.repository.MenuRepository;
import ampos.miniproject.restaurant.service.BillService;
import ampos.miniproject.restaurant.util.RestaurantConstants;

/**
 * Bill item service implementation
 *
 */
@Service
public class BillServiceImpl extends GenericServiceImpl<BillRequestDTO, BillDTO, Long, Bill, BillRepository, BillMapper>
        implements BillService {

    private BillItemRepository billItemRepository;
    private BillItemStatisticMapper billItemReportMapper;
    private MenuRepository menuRepository;

    public BillServiceImpl(BillRepository billRepository, BillMapper billMapper, BillItemRepository billItemRepository,
            BillItemStatisticMapper billItemReportMapper, MenuRepository menuItemRepository) {
        super(billRepository, billMapper);
        this.billItemRepository = billItemRepository;
        this.billItemReportMapper = billItemReportMapper;
        this.menuRepository = menuItemRepository;
    }

    @Override
    void processExistingEntity(Bill bill) {
        bill.getBillItems().clear();
    }

    @Override
    void processBeforeSaving(BillRequestDTO billRequestDTO, Bill bill) {
        if (billRequestDTO.getBillItems().size() <= 0) {
            return;
        }

        // Because request only contains Menu ID, need to get Menu entity from
        // data store to return in the response body
        List<Long> menuIds = billRequestDTO.getBillItems().stream().map(BillItemRequestDTO::getMenuId)
                .collect(Collectors.toList());
        Map<Long, Menu> menus = menuRepository.findAllById(menuIds).stream()
                .collect(Collectors.toMap(Menu::getId, menu -> menu));

        for (BillItem billItem : bill.getBillItems()) {
            billItem.setOrderedTime(Instant.now());
            billItem.setBill(bill);
            billItem.setMenu(menus.get(billItem.getMenu().getId()));
        }
    }

    /**
     * Delete bill
     *
     * @param id : of the bill to be deleted
     * @throws ApplicationException
     */
    @Override
    public void delete(Long id) throws ApplicationException {
        throw new ApplicationException(RestaurantConstants.ACTION_NOT_SUPPORTED);
    }

    /**
     * Get bill and bill item statistic
     *
     * @return
     * @throws ApplicationException
     */
    @Override
    public TotalStatisticDTO getBillAndBillItemStatistic() throws ApplicationException {
        TotalBillItemStatistictDTO totalBillItemReportDTO = new TotalBillItemStatistictDTO();
        totalBillItemReportDTO
                .setBillItems(this.billItemReportMapper.entityToDto(billItemRepository.getAllBillReport()));

        TotalBillStatisticDTO totalBillReportDTO = new TotalBillStatisticDTO();
        totalBillReportDTO.setBills(this.mapper.entityToDto(this.repository.findAll()));
        totalBillReportDTO.setNoOfBills(totalBillReportDTO.getBills().size());
        totalBillReportDTO.setGrandTotal(
                totalBillReportDTO.getBills().stream().map(BillDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        return new TotalStatisticDTO(totalBillItemReportDTO, totalBillReportDTO);
    }
}