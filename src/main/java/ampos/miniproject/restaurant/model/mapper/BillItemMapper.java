package ampos.miniproject.restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ampos.miniproject.restaurant.dto.BillItemDTO;
import ampos.miniproject.restaurant.dto.request.BillItemRequestDTO;
import ampos.miniproject.restaurant.model.BillItem;
import ampos.miniproject.restaurant.repository.MenuRepository;

@Mapper(componentModel = "spring", uses = { BillMapper.class, MenuMapper.class, MenuRepository.class })
public interface BillItemMapper extends GenericMapper<BillItemDTO, BillItem, BillItemRequestDTO> {

    /**
     *
     * @param billItem
     * @return billItemDTO
     */
    @Mapping(source = "bill.id", target = "billId")
    @Mapping(expression = "java(billItem.getSubTotal())", target = "subTotal")
    BillItemDTO entityToDto(BillItem billItem);

    /**
     *
     * @param billItemRequestDTO
     * @return billItem
     */
    @Mapping(source = "menuId", target = "menu", qualifiedByName = "fromIdToMenu")
    @Mapping(target = "bill", ignore = true)
    BillItem requestToEntity(BillItemRequestDTO billItemRequestDTO);

    /**
     * Create a dummy billItem from id to prevent infinite loop in
     * bidirectional relationship
     *
     * @param id
     * @return
     */
    default BillItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillItem Bill = new BillItem();
        Bill.setId(id);
        return Bill;
    }
}