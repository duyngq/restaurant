package ampos.miniproject.restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ampos.miniproject.restaurant.dto.BillDTO;
import ampos.miniproject.restaurant.dto.request.BillRequestDTO;
import ampos.miniproject.restaurant.model.Bill;

/**
 * Mapper for the entity Bill and BillDTO.
 */
@Mapper(componentModel = "spring", uses = { BillItemMapper.class })
public interface BillMapper extends GenericMapper<BillDTO, Bill, BillRequestDTO> {

    /**
     *
     * @param bill entity
     * @return billDTO
     */
    @Mapping(expression = "java(bill.getTotal())", target = "total")
    BillDTO entityToDto(Bill bill);

    /**
     *
     * @param billRequestDTO
     * @return bill
     */
    Bill requestToEntity(BillRequestDTO billRequestDTO);

    /**
     * Create a dummy Bill object from id to prevent infinite loop in bidirectional
     * relationship
     *
     * @param id
     * @return
     */
    default Bill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill Bill = new Bill();
        Bill.setId(id);
        return Bill;
    }
}
