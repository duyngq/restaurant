package ampos.miniproject.restaurant.model.mapper;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.dto.request.MenuRequestDTO;
import ampos.miniproject.restaurant.model.Menu;

/**
 * Mapper for the entity MenuItem and its DTO MenuItemDTO.
 */
@Mapper( componentModel = "spring" )
public interface MenuMapper extends GenericMapper<MenuDTO, Menu, MenuRequestDTO> {

    /**
     *
     * @param menuItem
     * @return menuItemDTO which is DTO of menuItem
     */
    @Mapping( source = "menu.details", target = "details", qualifiedByName = "toDtoDetails" )
    MenuDTO entityToDto( Menu menu);

    /**
     *
     * @param menuItemRequestDTO
     * @return menuItem which is entity of menuItemDTO
     */
    @Mapping( source = "menuRequestDTO.details", target = "details", qualifiedByName = "toEntityDetails" )
    Menu requestToEntity( MenuRequestDTO menuRequestDTO );

    /**
     * Helper method to map "details" field from entity (which is of type String) to "details" field of dto (which is of type List<String>)
     * @param details comma separated string of details
     * @return return list of details
     */
    @Named( "toDtoDetails" )
    default List<String> toDtoDetails( String details ) {
        return Arrays.asList( details.split( "\\s*,\\s*" ) );
    }

    /**
     * Helper method to map "details" field from dto (which is of type List<String>) to "details" field of dto (which is of type String)
     * @param listDetails list of details
     * @return return a comma-separated string
     */
    @Named( "toEntityDetails" )
    default String toEntityDetails( List<String> listDetails ) {
        String details = String.join( ",", listDetails );
        return details;
    }

    /**
     * Create a dummy MenuItem object from id to prevent infinite loop in bidirectional relationship when mapping
     * @param id
     * @return
     */
    @Named( "fromIdToMenu" )
    default Menu fromId( Long id ) {
        if ( id == null ) {
            return null;
        }
        Menu menuItem = new Menu();
        menuItem.setId( id );
        return menuItem;
    }
}