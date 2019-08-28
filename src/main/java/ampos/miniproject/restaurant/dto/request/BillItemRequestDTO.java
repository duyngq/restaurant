package ampos.miniproject.restaurant.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ampos.miniproject.restaurant.util.BillItemRequestView;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * DTO for request of a bill item
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillItemRequestDTO {

    @JsonView(BillItemRequestView.Edit.class)
    private Long id;

    @JsonView(BillItemRequestView.Add.class)
    private int quantity;

    @JsonView(BillItemRequestView.Add.class)
    @NotNull
    private Long menuId;

}
