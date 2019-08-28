package ampos.miniproject.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bill entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Bill.TABLE_NAME)
public class Bill implements DomainEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    protected static final String TABLE_NAME = "bill";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BillItem> billItems = new HashSet<>();

    /**
     * get total price of a bill which is total of all bill items
     *
     */
    public BigDecimal getTotal() {
        return billItems.stream().map(BillItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
