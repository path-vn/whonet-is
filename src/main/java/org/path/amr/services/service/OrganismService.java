package org.path.amr.services.service;

import java.util.Optional;
import org.path.amr.services.service.dto.OrganismDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.Organism}.
 */
public interface OrganismService {
    /**
     * Save a organism.
     *
     * @param organismDTO the entity to save.
     * @return the persisted entity.
     */
    OrganismDTO save(OrganismDTO organismDTO);

    /**
     * Partially updates a organism.
     *
     * @param organismDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganismDTO> partialUpdate(OrganismDTO organismDTO);

    /**
     * Get all the organisms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganismDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organism.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganismDTO> findOne(Long id);

    /**
     * Delete the "id" organism.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
