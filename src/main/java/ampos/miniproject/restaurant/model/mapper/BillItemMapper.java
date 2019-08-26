package ampos.miniproject.restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ampos.miniproject.restaurant.dto.BillItemDTO;
import ampos.miniproject.restaurant.dto.request.BillItemRequestDTO;
import ampos.miniproject.restaurant.model.BillItem;
import ampos.miniproject.restaurant.repository.MenuRepository;

@Mapper( componentModel = "spring", uses = { BillMapper.class, MenuMapper.class, MenuRepository.class } )
public interface BillItemMapper extends GenericMapper<BillItemDTO, BillItem, BillItemRequestDTO> {

    /**
     *
     * @param billItem
     * @return billItemDTO which is DTO of billItem
     */
    @Mapping( source = "bill.id", target = "billId" )
    @Mapping( expression = "java(billItem.getSubTotal())", target = "subTotal" )
    BillItemDTO entityToDto( BillItem billItem );

    /**
     *
     * @param billItemRequestDTO
     * @return billItem which is entity of billItemDTO
     */
    @Mapping( source = "billId", target = "bill" )
    @Mapping( source = "menuId", target = "menu", qualifiedByName = "fromIdToMenu" )
    BillItem requestToEntity( BillItemRequestDTO billItemRequestDTO);

    /**
     * Create dummy billItem object from id to prevent infinite loop in bidirectional relationship when mapping
     * @param id
     * @return
     */
    default BillItem fromId( Long id ) {
        if ( id == null ) {
            return null;
        }
        BillItem Bill = new BillItem();
        Bill.setId( id );
        return Bill;
    }
}