package ampos.miniproject.restaurant.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.dto.request.MenuRequestDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;

/**
 * Menu Service
 */
public interface MenuService extends GenericService<MenuRequestDTO, MenuDTO, Long> {

    /**
     * Search menu by keyword
     *
     * @param keyword  : the keyword of menu title or description or additional
     *                 details.
     * @param pageable
     * @return
     * @throws ApplicationException
     */
    Page<MenuDTO> search(String keyword, Pageable pageable) throws ApplicationException;
}
