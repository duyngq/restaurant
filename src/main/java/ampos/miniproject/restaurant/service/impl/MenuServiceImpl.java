package ampos.miniproject.restaurant.service.impl;

import java.util.Arrays;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.dto.request.MenuRequestDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.model.Menu;
import ampos.miniproject.restaurant.model.mapper.MenuMapper;
import ampos.miniproject.restaurant.repository.MenuRepository;
import ampos.miniproject.restaurant.service.MenuService;
import ampos.miniproject.restaurant.util.RestaurantConstants;

/**
 *
 * Menu service implementation
 *
 */
@Service
public class MenuServiceImpl extends GenericServiceImpl<MenuRequestDTO, MenuDTO, Long, Menu, MenuRepository, MenuMapper> implements MenuService {

    MenuServiceImpl(MenuRepository menuItemRepository, MenuMapper menuItemMapper) {
        super(menuItemRepository, menuItemMapper);
    }

    @Override
    public void delete( Long id ) throws  ApplicationException{
    	try{
    		this.repository.deleteAllMenuWithIds( Arrays.asList(id) );
    	 } catch ( DataIntegrityViolationException e ) {
    		if ( e.getCause() instanceof ConstraintViolationException ) {
    			throw new ApplicationException( RestaurantConstants.FOREIGN_KEY_CONSTRAINT );
    		}
    	 }
    }

    @Override
    @Transactional( readOnly = true )
    public Page<MenuDTO> search( String keyword, Pageable pageable ) {
        return this.repository.search( keyword.toLowerCase(), pageable ).map( this.mapper::entityToDto );
    }
}
