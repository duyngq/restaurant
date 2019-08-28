package ampos.miniproject.restaurant.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ampos.miniproject.restaurant.dto.BillDTO;
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
            BillItemStatisticMapper billItemReportMapper, MenuRepository menuRepository) {
        super(billRepository, billMapper);
        this.billItemRepository = billItemRepository;
        this.billItemReportMapper = billItemReportMapper;
        this.menuRepository = menuRepository;
    }

    @Override
    Bill mergeExistingAndNewEntity(Bill existingBill, Bill newBill) {
        Map<Long, BillItem> commonBillItems = newBill.getBillItems().stream().filter(item -> item.getId() != null)
                .collect(Collectors.toMap(BillItem::getId, item -> item));

        List<BillItem> newBillItems = newBill.getBillItems().stream().filter(item -> item.getId() == null)
                .collect(Collectors.toList());

        if (commonBillItems.size() > 0) {
            existingBill.getBillItems().removeIf(item -> !commonBillItems.containsKey(item.getId()));
        } else {
            existingBill.getBillItems().clear();
        }

        existingBill.getBillItems().forEach(item -> {
            if (commonBillItems.containsKey(item.getId())) {
                BillItem source = commonBillItems.get(item.getId());
                if (item.getQuantity() != source.getQuantity()) {
                    item.setQuantity(source.getQuantity());
                }

                if (!item.getMenu().getId().equals(source.getMenu().getId())) {
                    item.setMenu(source.getMenu());
                    item.setOrderedTime(null);
                }
            }
        });

        // Add all new Bill Items to create in database
        existingBill.getBillItems().addAll(newBillItems);
        return existingBill;
    }

    @Override
    public void processBeforeSaving(Bill bill) {
        // Because request only contains MenuItem ID, need to get MenuItem entity from
        // data store to return in the response body
        Set<Long> menuIds = bill.getBillItems().stream().map(BillItem::getMenu).map(Menu::getId)
                .collect(Collectors.toSet());
        List<Menu> items = menuRepository.findMenusByIdIn(menuIds);
        Map<Long, Menu> menus = items.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));

        for (BillItem billItem : bill.getBillItems()) {
            if (billItem.getId() == null || billItem.getOrderedTime() == null) {
                billItem.setOrderedTime(new Date());
                billItem.setBill(bill);
                billItem.setMenu(menus.get(billItem.getMenu().getId()));
            }
        }
    }

    /**
     * Delete bill
     *
     * @param id
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
        totalBillItemReportDTO.setBillItems(billItemReportMapper.entityToDto(billItemRepository.getAllBillStatistic()));

        TotalBillStatisticDTO totalBillReportDTO = new TotalBillStatisticDTO();
        totalBillReportDTO.setBills(mapper.entityToDto(repository.findAll()));
        totalBillReportDTO.setNoOfBills(totalBillReportDTO.getBills().size());
        totalBillReportDTO.setGrandTotal(
                totalBillReportDTO.getBills().stream().map(BillDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        return new TotalStatisticDTO(totalBillItemReportDTO, totalBillReportDTO);

    }
}