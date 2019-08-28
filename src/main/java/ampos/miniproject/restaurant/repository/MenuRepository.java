package ampos.miniproject.restaurant.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ampos.miniproject.restaurant.model.Menu;

/**
 * Repository to manage menu
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // @formatter:off
    @Query("SELECT distinct menu FROM Menu AS menu WHERE (:keyword is NULL) "
            + " OR (LOWER(menu.name) LIKE CONCAT('%',:keyword,'%'))"
            + " OR (LOWER(menu.description) LIKE CONCAT('%',:keyword,'%'))"
            + " OR (LOWER(menu.details) LIKE CONCAT('%',:keyword,'%'))")
    // @formatter:on
    Page<Menu> search(@Param("keyword") String keyword, Pageable pageable);

    List<Menu> findMenusByIdIn(Collection<Long> ids);

    @Modifying
    @Query("DELETE FROM Menu WHERE id in :ids")
    void deleteAllMenuWithIds(@Param("ids") Collection<Long> ids);
}
