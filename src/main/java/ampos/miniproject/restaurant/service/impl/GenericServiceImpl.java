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
     * @param id
     * @param request
     * @return
     * @throws ApplicationException
     */
    @Override
    public RESPONSE save(ID id, REQUEST request) throws ApplicationException {
        ENTITY entityToBePersisted = mapper.requestToEntity(request);

        if (id != null) {
            ENTITY existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ApplicationException(RestaurantConstants.ITEM_NOT_FOUND));
            entityToBePersisted = mergeExistingAndNewEntity(existingEntity, entityToBePersisted);
        }
        processBeforeSaving(entityToBePersisted);
        try {
            entityToBePersisted = repository.save(entityToBePersisted);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                new ApplicationException(RestaurantConstants.DUPLICATE_ENTRY);
            }
        }
        return mapper.entityToDto(entityToBePersisted);
    }

    abstract ENTITY mergeExistingAndNewEntity(ENTITY existingEntity, ENTITY newEntity);

    public void processBeforeSaving(ENTITY entity) {
    }

    /**
     * Delete item
     *
     * @param id
     * @throws ApplicationException
     */
    @Override
    public void delete(ID id) throws ApplicationException {
        repository.deleteById(id);
    }

    /**
     * Get resource by Id
     *
     * @param id
     * @return
     * @throws ApplicationException
     */
    @Override
    @Transactional(readOnly = true)
    public RESPONSE findById(ID id) throws ApplicationException {
        ENTITY entity = repository.findById(id)
                .orElseThrow(() -> new ApplicationException(RestaurantConstants.ITEM_NOT_FOUND));
        return mapper.entityToDto(entity);
    }

    /**
     * Get list resources
     *
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RESPONSE> findWithPageable(Pageable pageable) {
        Page<ENTITY> page = repository.findAll(pageable);
        return page.map(mapper::entityToDto);
    }
}
