package ampos.miniproject.restaurant.resource;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.service.GenericService;
import io.swagger.annotations.ApiOperation;

/**
 * Generic controller to handle requests with CRUD operation
 *
 */

public abstract class GenericResource<REQUEST, RESPONSE, ID, SERVICE extends GenericService<REQUEST, RESPONSE, ID>> {
    private static final Logger LOGGER = LogManager.getLogger(GenericResource.class);
    protected SERVICE service;

    public GenericResource(SERVICE service) {
        this.service = service;
    }

    abstract String getMapping();

    /**
     * POST /{mapping}: Create new resource
     *
     * @param request
     * @return
     * @throws ApplicationException
     * @throws URISyntaxException
     */
    @ApiOperation(value = "Create new resource", response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<RESPONSE> create(@RequestBody REQUEST request)
            throws ApplicationException, URISyntaxException {
        LOGGER.info("Adding a new resource");
        RESPONSE response = this.service.save(null, request);
        return ResponseEntity.created(new URI(getMapping() + "/")).body(response);
    }

    /**
     * PUT /{mapping} : Updates an existing resource.
     *
     * @param id
     * @param request
     * @return
     * @throws ApplicationException
     */
    @ApiOperation(value = "Update an existing resource", response = ResponseEntity.class)
    @PutMapping("/{id}")
    public ResponseEntity<RESPONSE> update(@PathVariable ID id, @RequestBody REQUEST request)
            throws ApplicationException {
        LOGGER.info("Updating an existing resource");
        RESPONSE response = this.service.save(id, request);
        return ResponseEntity.ok().body(response);
    }

    /**
     * GET /{mapping}/:id : Get the "id" resource.
     *
     * @param id
     * @return
     * @throws ApplicationException
     */
    @ApiOperation(value = "Get resource by id", response = ResponseEntity.class)
    @GetMapping("/{id}")
    public ResponseEntity<RESPONSE> findById(@PathVariable ID id) throws ApplicationException {
        LOGGER.info("Getting a resource by ID");
        RESPONSE response = this.service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    /**
     * GET /{mapping} : Get all the resources.
     *
     * @param pageable
     * @return
     * @throws ApplicationException
     */
    @ApiOperation(value = "Get all resources with paging", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<Page<RESPONSE>> findWithPageable(Pageable pageable) throws ApplicationException {
        Page<RESPONSE> page = this.service.findWithPageable(pageable);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * DELETE /{mapping}/:id : Delete the "id" item.
     *
     * @param id
     * @return
     * @throws ApplicationException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) throws ApplicationException {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
