package ampos.miniproject.restaurant.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ampos.miniproject.restaurant.dto.MenuDTO;
import ampos.miniproject.restaurant.exception.ApplicationException;
import ampos.miniproject.restaurant.rest.util.ResponseUtil;
import ampos.miniproject.restaurant.service.GenericService;
import ampos.miniproject.restaurant.service.MenuService;

/**
 * Generic controller to handle requests with CRUD operation
 *
 */

public abstract class GenericResource<REQUEST, RESPONSE, ID, SERVICE extends GenericService<REQUEST, RESPONSE, ID>> {

    protected SERVICE service;

    public GenericResource(SERVICE service) {
        this.service = service;
    }

    abstract String getMapping();

    /**
     * POST /{mapping}: Create new resource
     *
     * @param request: contain data about the resource to be created
     * @return the ResponseEntity with status 200 (OK)
     * @throws ApplicationException
     * @throws URISyntaxException
     */
    @ApiOperation(value = "Create new resource", response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<RESPONSE> create(@RequestBody REQUEST request)
            throws ApplicationException, URISyntaxException {
        RESPONSE response = this.service.save(null, request);
        return ResponseEntity.created(new URI(getMapping() + "/")).body(response);
    }

    /**
     * PUT /{mapping} : Updates an existing resource.
     *
     * @param id: if of the resource to update
     * @param request: resource to update
     * @return the ResponseEntity with status code and body the updated resource
     */
    @ApiOperation(value = "Update an existing resource", response = ResponseEntity.class)
    @PutMapping("/{id}")
    public ResponseEntity<RESPONSE> update(@PathVariable ID id, @RequestBody REQUEST request)
            throws ApplicationException {
        RESPONSE response = this.service.save(id, request);
        return ResponseEntity.ok().body(response);
    }

    /**
     * GET /{mapping}/:id : Get the "id" resource.
     *
     * @param id: the id of the resource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resource,
     *         or with status 404 (Not Found)
     */
    @ApiOperation(value = "Get resource by id", response = ResponseEntity.class)
    @GetMapping("/{id}")
    public ResponseEntity<RESPONSE> findById(@PathVariable ID id) throws ApplicationException {
        RESPONSE response = this.service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    /**
     * GET /{mapping} : Get all the resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status code and the list of resources in body
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
     * @param id: the id of the item to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) throws ApplicationException {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}
