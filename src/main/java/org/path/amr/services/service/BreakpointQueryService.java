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
            if (criteria.getGuidelines() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuidelines(), Breakpoint_.guidelines));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), Breakpoint_.year));
            }
            if (criteria.getTestMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTestMethod(), Breakpoint_.testMethod));
            }
            if (criteria.getPotency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPotency(), Breakpoint_.potency));
            }
            if (criteria.getOrganismCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismCode(), Breakpoint_.organismCode));
            }
            if (criteria.getOrganismCodeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismCodeType(), Breakpoint_.organismCodeType));
            }
            if (criteria.getBreakpointType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBreakpointType(), Breakpoint_.breakpointType));
            }
            if (criteria.getHost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHost(), Breakpoint_.host));
            }
            if (criteria.getSiteOfInfection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteOfInfection(), Breakpoint_.siteOfInfection));
            }
            if (criteria.getReferenceTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceTable(), Breakpoint_.referenceTable));
            }
            if (criteria.getReferenceSequence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceSequence(), Breakpoint_.referenceSequence));
            }
            if (criteria.getWhonetAbxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhonetAbxCode(), Breakpoint_.whonetAbxCode));
            }
            if (criteria.getWhonetTest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhonetTest(), Breakpoint_.whonetTest));
            }
            if (criteria.getR() != null) {
                specification = specification.and(buildStringSpecification(criteria.getR(), Breakpoint_.r));
            }
            if (criteria.getI() != null) {
                specification = specification.and(buildStringSpecification(criteria.getI(), Breakpoint_.i));
            }
            if (criteria.getSdd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSdd(), Breakpoint_.sdd));
            }
            if (criteria.getS() != null) {
                specification = specification.and(buildStringSpecification(criteria.getS(), Breakpoint_.s));
            }
            if (criteria.getEcvEcoff() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEcvEcoff(), Breakpoint_.ecvEcoff));
            }
            if (criteria.getDateEntered() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateEntered(), Breakpoint_.dateEntered));
            }
            if (criteria.getDateModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateModified(), Breakpoint_.dateModified));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Breakpoint_.comments));
            }
        }
        return specification;
    }
}
