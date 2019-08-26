package ampos.miniproject.restaurant.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BillItemRequestDTO {
    private int quantity;

    @NotNull
    private Long menuId;

    @NotNull
    private Long billId;
}
