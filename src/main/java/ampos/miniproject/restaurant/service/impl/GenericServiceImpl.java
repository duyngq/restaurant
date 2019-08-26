package ampos.miniproject.restaurant.service.impl;

import java.io.Serializable;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.model.DomainEntity;
import ampos.miniproject.restaurant.model.mapper.GenericMapper;
import ampos.miniproject.restaurant.service.GenericService;
import ampos.miniproject.restaurant.util.RestaurantConstants;



@Transactional
public abstract class GenericServiceImpl<REQUEST, RESPONSE extends Serializable, ID, ENTITY extends DomainEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID>, MAPPER extends GenericMapper<RESPONSE, ENTITY, REQUEST>>
        implements GenericService<REQUEST, RESPONSE, ID> {
    protected REPOSITORY repository;
    protected MAPPER mapper;

    public GenericServiceImpl(REPOSITORY repository, MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Create or update resource
     *
     * @param id      : of request resource to be updated ( in case of updating)
     * @param request : the resource to be persisted
     * @return
     * @throws ApplicationException
     */
    @Override
    public RESPONSE save( ID id, REQUEST request ) throws ApplicationException {
        ENTITY entity = null;
        if( id != null ){
            entity = repository.findById(id).orElseThrow( () -> new ApplicationException( RestaurantConstants.ITEM_NOT_FOUND ));
            processExistingEntity(entity);
        }
        entity = mapper.requestToEntity(request);
        entity.setId(id);
        processBeforeSaving(request, entity);
        try {
        	entity = repository.save(entity);
        } catch ( DataIntegrityViolationException e ) { 
        	if ( e.getCause() instanceof ConstraintViolationException ) {
        		new ApplicationException( RestaurantConstants.DUPLICATE_ENTRY );
        	}
        }
        return mapper.entityToDto(entity);
    }

    void processExistingEntity(ENTITY entity) {}

    void processBeforeSaving(REQUEST request, ENTITY entity) {}

    /**
     * Delete item
     *
     * @param id
     *            : of the item to be deleted
     */
    @Override
    public void delete( ID id ) throws ApplicationException { 
    	repository.deleteById(id);
    }

    /**
     * Get resource by Id
     *
     * @param id : of the resource to be retrieved
     * @return
     * @throws ApplicationException
     */
    @Override
    @Transactional(readOnly = true)
    public RESPONSE findById( ID id ) throws ApplicationException {
        ENTITY entity = repository.findById(id).orElseThrow( () -> new ApplicationException( RestaurantConstants.ITEM_NOT_FOUND ));
        return mapper.entityToDto(entity);
    }

    /**
     * Get list resources
     *
     * @param pageable : the pagination information
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RESPONSE> findWithPageable(Pageable pageable ) {
        Page<ENTITY> page = repository.findAll(pageable);
        return page.map(mapper::entityToDto);
    }
}
