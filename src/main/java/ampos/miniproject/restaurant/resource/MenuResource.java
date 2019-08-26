package ampos.miniproject.restaurant.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.dto.request.MenuRequestDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.service.MenuService;
import io.swagger.annotations.ApiOperation;

/**
 * Controller handles requests to menus with CRUD operations
 *
 */
@RestController
@RequestMapping( MenuResource.MENUS_MAPPING )
public class MenuResource extends GenericResource<MenuRequestDTO, MenuDTO, Long, MenuService> {

    public static final String MENUS_MAPPING = "/menus";

    public MenuResource( MenuService menuService ) {
        super(menuService);
    }

    @Override
    String getMapping() {
        return MENUS_MAPPING;
    }

    /**
     * GET /{mapping}/search?keyword=<keyword> : Search menus by keyword.
     *
     * @param keyword:
     *            the keyword to search
     * @param pageable:
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @ApiOperation( value = "Search menus by title or description or additional details", response = ResponseEntity.class )
    @GetMapping( "/search" )
    public ResponseEntity<Page<MenuDTO>> search( @RequestParam( value = "keyword" ) String keyword, Pageable pageable ) throws ApplicationException {
        Page<MenuDTO> page = this.service.search( keyword, pageable );
        return new ResponseEntity<>( page, null, HttpStatus.OK );
    }
}
