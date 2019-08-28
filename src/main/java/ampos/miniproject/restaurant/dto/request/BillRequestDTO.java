package ampos.miniproject.restaurant.dto.request;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import ampos.miniproject.restaurant.util.BillItemRequestView;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * DTO for request of a bill
 */
@Setter
@Getter
public class BillRequestDTO {
    @JsonView(BillItemRequestView.Add.class)
    private Set<BillItemRequestDTO> billItems = new HashSet<>();
}
