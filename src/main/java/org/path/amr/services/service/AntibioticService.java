package org.path.amr.services.service;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.dto.AntibioticDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.Antibiotic}.
 */
public interface AntibioticService {
    /**
     * Save a antibiotic.
     *
     * @param antibioticDTO the entity to save.
     * @return the persisted entity.
     */
    AntibioticDTO save(AntibioticDTO antibioticDTO);

    /**
     * Partially updates a antibiotic.
     *
     * @param antibioticDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AntibioticDTO> partialUpdate(AntibioticDTO antibioticDTO);

    /**
     * Get all the antibiotics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AntibioticDTO> findAll(Pageable pageable);

    /**
     * Get the "id" antibiotic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AntibioticDTO> findOne(Long id);

    /**
     * Delete the "id" antibiotic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<String> findGroups(String key);
}
