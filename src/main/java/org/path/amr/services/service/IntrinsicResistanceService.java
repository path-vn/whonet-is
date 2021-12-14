package org.path.amr.services.service;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.IntrinsicResistance}.
 */
public interface IntrinsicResistanceService {
    /**
     * Save a intrinsicResistance.
     *
     * @param intrinsicResistanceDTO the entity to save.
     * @return the persisted entity.
     */
    IntrinsicResistanceDTO save(IntrinsicResistanceDTO intrinsicResistanceDTO);

    /**
     * Partially updates a intrinsicResistance.
     *
     * @param intrinsicResistanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntrinsicResistanceDTO> partialUpdate(IntrinsicResistanceDTO intrinsicResistanceDTO);

    /**
     * Get all the intrinsicResistances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntrinsicResistanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" intrinsicResistance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntrinsicResistanceDTO> findOne(Long id);

    /**
     * Delete the "id" intrinsicResistance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<String> findGroups(String key);
}
