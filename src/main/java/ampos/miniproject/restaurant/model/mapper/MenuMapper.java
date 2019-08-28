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
@Mapper(componentModel = "spring")
public interface MenuMapper extends GenericMapper<MenuDTO, Menu, MenuRequestDTO> {

    /**
     *
     * @param menuItem
     * @return menuItemDTO
     */
    @Mapping(source = "menu.details", target = "details", qualifiedByName = "toDtoDetails")
    MenuDTO entityToDto(Menu menu);

    /**
     *
     * @param menuItemRequestDTO
     * @return menuItem
     */
    @Mapping(source = "menuRequestDTO.details", target = "details", qualifiedByName = "toEntityDetails")
    Menu requestToEntity(MenuRequestDTO menuRequestDTO);

    /**
     * Map "details" field from entity to "details" field of dto (String with
     * delimiter to List<String>)
     *
     * @param details comma separated string of details
     * @return return list of details
     */
    @Named("toDtoDetails")
    default List<String> toDtoDetails(String details) {
        return Arrays.asList(details.split("\\s*,\\s*"));
    }

    /**
     * Map "details" field from dto to "details" field of dto (List<String> to
     * String with delimiter)
     *
     * @param listDetails list of details
     * @return return a comma-separated string
     */
    @Named("toEntityDetails")
    default String toEntityDetails(List<String> listDetails) {
        return String.join(",", listDetails);
    }

    /**
     * A dummy MenuItem object from id to prevent infinite loop in bidirectional
     * relationship
     *
     * @param id
     * @return
     */
    @Named("fromIdToMenu")
    default Menu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menuItem = new Menu();
        menuItem.setId(id);
        return menuItem;
    }
}