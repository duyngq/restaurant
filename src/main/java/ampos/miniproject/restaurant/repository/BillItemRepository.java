package ampos.miniproject.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ampos.miniproject.restaurant.model.BillItem;
import ampos.miniproject.restaurant.model.BillItemStatistic;

/**
 * Repository to manage bill item
 */
@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {
    // @formatter:off
    @Query("SELECT new ampos.miniproject.restaurant.model.BillItemStatistic( menu, sum(billItem.quantity), sum(billItem.quantity)*menu.price ) "
            + " FROM BillItem billItem LEFT JOIN Menu menu ON billItem.menu.id = menu.id"
            + " GROUP BY menu.id")
    // @formatter:on
    List<BillItemStatistic> getAllBillReport();

    @Modifying
    @Query("DELETE" + " FROM BillItem billItem" + " WHERE billItem.bill.id = :billId")
    void deleteBillItemsByBillId(@Param("billId") Long billId);
}
