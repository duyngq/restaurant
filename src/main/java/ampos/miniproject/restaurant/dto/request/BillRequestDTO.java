package ampos.miniproject.restaurant.dto.request;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillRequestDTO {
    private Set<BillItemRequestDTO> billItems = new HashSet<>();
}
