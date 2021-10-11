package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.Breakpoint;
import org.path.amr.services.repository.BreakpointRepository;
import org.path.amr.services.service.criteria.BreakpointCriteria;
import org.path.amr.services.service.dto.BreakpointDTO;
import org.path.amr.services.service.mapper.BreakpointMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Breakpoint} entities in the database.
 * The main input is a {@link BreakpointCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BreakpointDTO} or a {@link Page} of {@link BreakpointDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BreakpointQueryService extends QueryService<Breakpoint> {

    private final Logger log = LoggerFactory.getLogger(BreakpointQueryService.class);

    private final BreakpointRepository breakpointRepository;

    private final BreakpointMapper breakpointMapper;

    public BreakpointQueryService(BreakpointRepository breakpointRepository, BreakpointMapper breakpointMapper) {
        this.breakpointRepository = breakpointRepository;
        this.breakpointMapper = breakpointMapper;
    }

    /**
     * Return a {@link List} of {@link BreakpointDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BreakpointDTO> findByCriteria(BreakpointCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Breakpoint> specification = createSpecification(criteria);
        return breakpointMapper.toDto(breakpointRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BreakpointDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BreakpointDTO> findByCriteria(BreakpointCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Breakpoint> specification = createSpecification(criteria);
        return breakpointRepository.findAll(specification, page).map(breakpointMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BreakpointCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Breakpoint> specification = createSpecification(criteria);
        return breakpointRepository.count(specification);
    }

    /**
     * Function to convert {@link BreakpointCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Breakpoint> createSpecification(BreakpointCriteria criteria) {
        Specification<Breakpoint> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Breakpoint_.id));
            }
            if (criteria.getPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPath(), Breakpoint_.path));
            }
            if (criteria.getQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuery(), Breakpoint_.query));
            }
            if (criteria.getAntibioticQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAntibioticQuery(), Breakpoint_.antibioticQuery));
            }
            if (criteria.getOrganismQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismQuery(), Breakpoint_.organismQuery));
            }
            if (criteria.getIntrinsicResistanceQuery() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIntrinsicResistanceQuery(), Breakpoint_.intrinsicResistanceQuery)
                    );
            }
        }
        return specification;
    }
}
