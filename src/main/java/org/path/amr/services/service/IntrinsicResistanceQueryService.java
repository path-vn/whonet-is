package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.IntrinsicResistance;
import org.path.amr.services.repository.IntrinsicResistanceRepository;
import org.path.amr.services.service.criteria.IntrinsicResistanceCriteria;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;
import org.path.amr.services.service.mapper.IntrinsicResistanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link IntrinsicResistance} entities in the database.
 * The main input is a {@link IntrinsicResistanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntrinsicResistanceDTO} or a {@link Page} of {@link IntrinsicResistanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntrinsicResistanceQueryService extends QueryService<IntrinsicResistance> {

    private final Logger log = LoggerFactory.getLogger(IntrinsicResistanceQueryService.class);

    private final IntrinsicResistanceRepository intrinsicResistanceRepository;

    private final IntrinsicResistanceMapper intrinsicResistanceMapper;

    public IntrinsicResistanceQueryService(
        IntrinsicResistanceRepository intrinsicResistanceRepository,
        IntrinsicResistanceMapper intrinsicResistanceMapper
    ) {
        this.intrinsicResistanceRepository = intrinsicResistanceRepository;
        this.intrinsicResistanceMapper = intrinsicResistanceMapper;
    }

    /**
     * Return a {@link List} of {@link IntrinsicResistanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntrinsicResistanceDTO> findByCriteria(IntrinsicResistanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IntrinsicResistance> specification = createSpecification(criteria);
        return intrinsicResistanceMapper.toDto(intrinsicResistanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IntrinsicResistanceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntrinsicResistanceDTO> findByCriteria(IntrinsicResistanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IntrinsicResistance> specification = createSpecification(criteria);
        return intrinsicResistanceRepository.findAll(specification, page).map(intrinsicResistanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntrinsicResistanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IntrinsicResistance> specification = createSpecification(criteria);
        return intrinsicResistanceRepository.count(specification);
    }

    /**
     * Function to convert {@link IntrinsicResistanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IntrinsicResistance> createSpecification(IntrinsicResistanceCriteria criteria) {
        Specification<IntrinsicResistance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IntrinsicResistance_.id));
            }
            if (criteria.getGuideline() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuideline(), IntrinsicResistance_.guideline));
            }
            if (criteria.getReferenceTable() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReferenceTable(), IntrinsicResistance_.referenceTable));
            }
            if (criteria.getOrganismCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismCode(), IntrinsicResistance_.organismCode));
            }
            if (criteria.getOrganismCodeType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOrganismCodeType(), IntrinsicResistance_.organismCodeType));
            }
            if (criteria.getExceptionOrganismCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getExceptionOrganismCode(), IntrinsicResistance_.exceptionOrganismCode)
                    );
            }
            if (criteria.getExceptionOrganismCodeType() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getExceptionOrganismCodeType(), IntrinsicResistance_.exceptionOrganismCodeType)
                    );
            }
            if (criteria.getAbxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbxCode(), IntrinsicResistance_.abxCode));
            }
            if (criteria.getAbxCodeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbxCodeType(), IntrinsicResistance_.abxCodeType));
            }
            if (criteria.getDateEntered() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateEntered(), IntrinsicResistance_.dateEntered));
            }
            if (criteria.getDateModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateModified(), IntrinsicResistance_.dateModified));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), IntrinsicResistance_.comments));
            }
        }
        return specification;
    }
}
