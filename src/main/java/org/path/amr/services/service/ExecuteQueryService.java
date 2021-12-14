package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.Execute;
import org.path.amr.services.repository.ExecuteRepository;
import org.path.amr.services.service.criteria.ExecuteCriteria;
import org.path.amr.services.service.dto.ExecuteDTO;
import org.path.amr.services.service.mapper.ExecuteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Execute} entities in the database.
 * The main input is a {@link ExecuteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExecuteDTO} or a {@link Page} of {@link ExecuteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExecuteQueryService extends QueryService<Execute> {

    private final Logger log = LoggerFactory.getLogger(ExecuteQueryService.class);

    private final ExecuteRepository executeRepository;

    private final ExecuteMapper executeMapper;

    public ExecuteQueryService(ExecuteRepository executeRepository, ExecuteMapper executeMapper) {
        this.executeRepository = executeRepository;
        this.executeMapper = executeMapper;
    }

    /**
     * Return a {@link List} of {@link ExecuteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExecuteDTO> findByCriteria(ExecuteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Execute> specification = createSpecification(criteria);
        return executeMapper.toDto(executeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExecuteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExecuteDTO> findByCriteria(ExecuteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Execute> specification = createSpecification(criteria);
        return executeRepository.findAll(specification, page).map(executeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExecuteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Execute> specification = createSpecification(criteria);
        return executeRepository.count(specification);
    }

    /**
     * Function to convert {@link ExecuteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Execute> createSpecification(ExecuteCriteria criteria) {
        Specification<Execute> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Execute_.id));
            }
            if (criteria.getRequest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequest(), Execute_.request));
            }
            if (criteria.getResponse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponse(), Execute_.response));
            }
            if (criteria.getStartedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedAt(), Execute_.startedAt));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTime(), Execute_.time));
            }
        }
        return specification;
    }
}
