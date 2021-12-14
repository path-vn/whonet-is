package org.path.amr.services.service;

import java.util.Optional;
import org.path.amr.services.service.dto.ExecuteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.Execute}.
 */
public interface ExecuteService {
    /**
     * Save a execute.
     *
     * @param executeDTO the entity to save.
     * @return the persisted entity.
     */
    ExecuteDTO save(ExecuteDTO executeDTO);

    /**
     * Partially updates a execute.
     *
     * @param executeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExecuteDTO> partialUpdate(ExecuteDTO executeDTO);

    /**
     * Get all the executes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExecuteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" execute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExecuteDTO> findOne(Long id);

    /**
     * Delete the "id" execute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
