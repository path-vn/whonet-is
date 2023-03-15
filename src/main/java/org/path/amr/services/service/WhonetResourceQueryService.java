package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.WhonetResource;
import org.path.amr.services.repository.WhonetResourceRepository;
import org.path.amr.services.service.criteria.WhonetResourceCriteria;
import org.path.amr.services.service.dto.WhonetResourceDTO;
import org.path.amr.services.service.mapper.WhonetResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link WhonetResource} entities in the database.
 * The main input is a {@link WhonetResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WhonetResourceDTO} or a {@link Page} of {@link WhonetResourceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WhonetResourceQueryService extends QueryService<WhonetResource> {

    private final Logger log = LoggerFactory.getLogger(WhonetResourceQueryService.class);

    private final WhonetResourceRepository whonetResourceRepository;

    private final WhonetResourceMapper whonetResourceMapper;

    public WhonetResourceQueryService(WhonetResourceRepository whonetResourceRepository, WhonetResourceMapper whonetResourceMapper) {
        this.whonetResourceRepository = whonetResourceRepository;
        this.whonetResourceMapper = whonetResourceMapper;
    }

    /**
     * Return a {@link List} of {@link WhonetResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WhonetResourceDTO> findByCriteria(WhonetResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WhonetResource> specification = createSpecification(criteria);
        return whonetResourceMapper.toDto(whonetResourceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WhonetResourceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WhonetResourceDTO> findByCriteria(WhonetResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WhonetResource> specification = createSpecification(criteria);
        return whonetResourceRepository.findAll(specification, page).map(whonetResourceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WhonetResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WhonetResource> specification = createSpecification(criteria);
        return whonetResourceRepository.count(specification);
    }

    /**
     * Function to convert {@link WhonetResourceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WhonetResource> createSpecification(WhonetResourceCriteria criteria) {
        Specification<WhonetResource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WhonetResource_.id));
            }
            if (criteria.getUploadDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadDate(), WhonetResource_.uploadDate));
            }
            if (criteria.getAntibiotic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAntibiotic(), WhonetResource_.antibiotic));
            }
            if (criteria.getOrganism() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganism(), WhonetResource_.organism));
            }
            if (criteria.getIntrinsicResistance() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIntrinsicResistance(), WhonetResource_.intrinsicResistance));
            }
            if (criteria.getExpertRule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpertRule(), WhonetResource_.expertRule));
            }
            if (criteria.getBreakPoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBreakPoint(), WhonetResource_.breakPoint));
            }
        }
        return specification;
    }
}
