package org.path.amr.services.service;

import java.util.Optional;
import org.path.amr.services.service.dto.WhonetResourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.WhonetResource}.
 */
public interface WhonetResourceService {
    /**
     * Save a whonetResource.
     *
     * @param whonetResourceDTO the entity to save.
     * @return the persisted entity.
     */
    WhonetResourceDTO save(WhonetResourceDTO whonetResourceDTO);

    /**
     * Partially updates a whonetResource.
     *
     * @param whonetResourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WhonetResourceDTO> partialUpdate(WhonetResourceDTO whonetResourceDTO);

    /**
     * Get all the whonetResources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WhonetResourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" whonetResource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WhonetResourceDTO> findOne(Long id);

    /**
     * Delete the "id" whonetResource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
