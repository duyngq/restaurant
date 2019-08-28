package ampos.miniproject.restaurant.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ampos.miniproject.restaurant.exception.ApplicationException;

public interface GenericService<REQUEST, RESPONSE, ID> {
    /**
     * Create or update resource from request
     *
     * @param id
     * @param request
     * @return
     * @throws ApplicationException
     */
    RESPONSE save(ID id, REQUEST request) throws ApplicationException;

    /**
     * Delete resource
     *
     * @param id
     * @throws ApplicationException
     */
    void delete(ID id) throws ApplicationException;

    /**
     * Get resource by Id
     *
     * @param id
     * @return
     * @throws ApplicationException
     */
    RESPONSE findById(ID id) throws ApplicationException;

    /**
     * Get list resources
     *
     * @param pageable
     * @return
     * @throws ApplicationException
     */
    Page<RESPONSE> findWithPageable(Pageable pageable) throws ApplicationException;
}
