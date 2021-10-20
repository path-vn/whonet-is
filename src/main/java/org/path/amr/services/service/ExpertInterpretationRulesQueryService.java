package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.repository.ExpertInterpretationRulesRepository;
import org.path.amr.services.service.criteria.ExpertInterpretationRulesCriteria;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;
import org.path.amr.services.service.mapper.ExpertInterpretationRulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ExpertInterpretationRules} entities in the database.
 * The main input is a {@link ExpertInterpretationRulesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExpertInterpretationRulesDTO} or a {@link Page} of {@link ExpertInterpretationRulesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpertInterpretationRulesQueryService extends QueryService<ExpertInterpretationRules> {

    private final Logger log = LoggerFactory.getLogger(ExpertInterpretationRulesQueryService.class);

    private final ExpertInterpretationRulesRepository expertInterpretationRulesRepository;

    private final ExpertInterpretationRulesMapper expertInterpretationRulesMapper;

    public ExpertInterpretationRulesQueryService(
        ExpertInterpretationRulesRepository expertInterpretationRulesRepository,
        ExpertInterpretationRulesMapper expertInterpretationRulesMapper
    ) {
        this.expertInterpretationRulesRepository = expertInterpretationRulesRepository;
        this.expertInterpretationRulesMapper = expertInterpretationRulesMapper;
    }

    /**
     * Return a {@link List} of {@link ExpertInterpretationRulesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExpertInterpretationRulesDTO> findByCriteria(ExpertInterpretationRulesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExpertInterpretationRules> specification = createSpecification(criteria);
        return expertInterpretationRulesMapper.toDto(expertInterpretationRulesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExpertInterpretationRulesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpertInterpretationRulesDTO> findByCriteria(ExpertInterpretationRulesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExpertInterpretationRules> specification = createSpecification(criteria);
        return expertInterpretationRulesRepository.findAll(specification, page).map(expertInterpretationRulesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpertInterpretationRulesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExpertInterpretationRules> specification = createSpecification(criteria);
        return expertInterpretationRulesRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpertInterpretationRulesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExpertInterpretationRules> createSpecification(ExpertInterpretationRulesCriteria criteria) {
        Specification<ExpertInterpretationRules> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExpertInterpretationRules_.id));
            }
            if (criteria.getRuleCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRuleCode(), ExpertInterpretationRules_.ruleCode));
            }
            if (criteria.getDescription() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescription(), ExpertInterpretationRules_.description));
            }
            if (criteria.getOrganismCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOrganismCode(), ExpertInterpretationRules_.organismCode));
            }
            if (criteria.getOrganismCodeType() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getOrganismCodeType(), ExpertInterpretationRules_.organismCodeType)
                    );
            }
            if (criteria.getRuleCriteria() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRuleCriteria(), ExpertInterpretationRules_.ruleCriteria));
            }
            if (criteria.getAffectedAntibiotics() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAffectedAntibiotics(), ExpertInterpretationRules_.affectedAntibiotics)
                    );
            }
            if (criteria.getAntibioticExceptions() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getAntibioticExceptions(), ExpertInterpretationRules_.antibioticExceptions)
                    );
            }
        }
        return specification;
    }
}
