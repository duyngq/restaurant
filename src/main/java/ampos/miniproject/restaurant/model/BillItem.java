package ampos.miniproject.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BillItem entity
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = BillItem.TABLE_NAME)
public class BillItem implements DomainEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    protected static final String TABLE_NAME = "bill_item";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "ordered_time", nullable = false)
    private Instant orderedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public BillItem(long id, int quantity, Menu menuItem, Instant orderedTime) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.menu = menuItem;
        this.orderedTime = orderedTime;
    }

    /**
     *
     * @return sub total of a bill item which is equal menu price multiply quantity
     */
    public BigDecimal getSubTotal() {
        return BigDecimal.valueOf(quantity).multiply(menu.getPrice());
    }

	
}
