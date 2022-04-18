package org.path.amr.services.service;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.dto.BreakpointDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.path.amr.services.domain.Breakpoint}.
 */
public interface BreakpointService {
    /**
     * Save a breakpoint.
     *
     * @param breakpointDTO the entity to save.
     * @return the persisted entity.
     */
    BreakpointDTO save(BreakpointDTO breakpointDTO);

    /**
     * Partially updates a breakpoint.
     *
     * @param breakpointDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BreakpointDTO> partialUpdate(BreakpointDTO breakpointDTO);

    /**
     * Get all the breakpoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BreakpointDTO> findAll(Pageable pageable);

    /**
     * Get the "id" breakpoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BreakpointDTO> findOne(Long id);

    /**
     * Delete the "id" breakpoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<String> findGroups(String key);

    void flushAllAndSaveAll(List<BreakpointDTO> newDTO);
}
