package ampos.miniproject.restaurant.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Bill DTO
 */
@Getter
@Setter
public class BillDTO implements Serializable {
    private Long id;
    private Set<BillItemDTO> billItems = new HashSet<>();
    private BigDecimal total;
}
