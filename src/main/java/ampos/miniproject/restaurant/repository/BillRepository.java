package ampos.miniproject.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ampos.miniproject.restaurant.model.Bill;

/**
 * Repository to manage bill
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}