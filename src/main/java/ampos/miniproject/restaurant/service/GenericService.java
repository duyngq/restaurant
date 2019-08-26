package ampos.miniproject.restaurant.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ampos.miniproject.restaurant.exception.ApplicationException;

public interface GenericService <REQUEST, RESPONSE, ID> {
    /**
     * Create or update resource from request
     *
     * @param id
     *            : of request resource to be updated ( in case of updating )
     * @param request
     *            : the resource to be persisted
     * @return
     * @throws ApplicationException
     */
    RESPONSE save( ID id, REQUEST request ) throws ApplicationException;

    /**
     * Delete resource
     *
     * @param id
     *            : of the request resource to be deleted
     */
    void delete( ID id ) throws ApplicationException;

    /**
     * Get resource by Id
     *
     * @param id
     *            : of the resource to be retrieved
     * @return
     * @throws ApplicationException
     */
    RESPONSE findById( ID id ) throws ApplicationException;

    /**
     * Get list resources
     *
     * @param pageable
     *            : the pagination information
     * @return
     * @throws ApplicationException
     */
    Page<RESPONSE> findWithPageable(Pageable pageable ) throws ApplicationException;
}
