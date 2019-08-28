package ampos.miniproject.restaurant.model.mapper;

import java.util.List;

/**
 * Generic mapper to help convert request, entity and dto
 *
 */
public interface GenericMapper<DTO, ENTITY, REQUEST> {

    /**
     * Map dto type to entity type
     *
     * @param request
     * @return entity
     */
    public ENTITY requestToEntity(REQUEST request);

    /**
     * Map entity type to dto type
     *
     * @param entity
     * @return dto
     */
    public DTO entityToDto(ENTITY entity);

    /**
     * Map list of dto type to list of entity type
     *
     * @param requestList
     * @return entityList
     */
    public List<ENTITY> requestToEntity(List<REQUEST> requestList);

    /**
     * Map list of entity type to list of dto type
     *
     * @param entityList
     * @return dtoList
     */
    public List<DTO> entityToDto(List<ENTITY> entityList);
}