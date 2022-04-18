package org.path.amr.services.service;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.ExpertInterpretationRules}.
 */
public interface ExpertInterpretationRulesService {
    /**
     * Save a expertInterpretationRules.
     *
     * @param expertInterpretationRulesDTO the entity to save.
     * @return the persisted entity.
     */
    ExpertInterpretationRulesDTO save(ExpertInterpretationRulesDTO expertInterpretationRulesDTO);

    /**
     * Partially updates a expertInterpretationRules.
     *
     * @param expertInterpretationRulesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExpertInterpretationRulesDTO> partialUpdate(ExpertInterpretationRulesDTO expertInterpretationRulesDTO);

    /**
     * Get all the expertInterpretationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExpertInterpretationRulesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" expertInterpretationRules.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpertInterpretationRulesDTO> findOne(Long id);

    /**
     * Delete the "id" expertInterpretationRules.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void flushAllAndSaveAll(List<ExpertInterpretationRulesDTO> newDTO);
}
