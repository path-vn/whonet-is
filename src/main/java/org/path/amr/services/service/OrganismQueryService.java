package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.Organism;
import org.path.amr.services.repository.OrganismRepository;
import org.path.amr.services.service.criteria.OrganismCriteria;
import org.path.amr.services.service.dto.OrganismDTO;
import org.path.amr.services.service.mapper.OrganismMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Organism} entities in the database.
 * The main input is a {@link OrganismCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganismDTO} or a {@link Page} of {@link OrganismDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganismQueryService extends QueryService<Organism> {

    private final Logger log = LoggerFactory.getLogger(OrganismQueryService.class);

    private final OrganismRepository organismRepository;

    private final OrganismMapper organismMapper;

    public OrganismQueryService(OrganismRepository organismRepository, OrganismMapper organismMapper) {
        this.organismRepository = organismRepository;
        this.organismMapper = organismMapper;
    }

    /**
     * Return a {@link List} of {@link OrganismDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganismDTO> findByCriteria(OrganismCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organism> specification = createSpecification(criteria);
        return organismMapper.toDto(organismRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganismDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganismDTO> findByCriteria(OrganismCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organism> specification = createSpecification(criteria);
        return organismRepository.findAll(specification, page).map(organismMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganismCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organism> specification = createSpecification(criteria);
        return organismRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganismCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organism> createSpecification(OrganismCriteria criteria) {
        Specification<Organism> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Organism_.id));
            }
            if (criteria.getWhonetOrgCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhonetOrgCode(), Organism_.whonetOrgCode));
            }
            if (criteria.getOrganism() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganism(), Organism_.organism));
            }
            if (criteria.getTaxonomicStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxonomicStatus(), Organism_.taxonomicStatus));
            }
            if (criteria.getCommon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommon(), Organism_.common));
            }
            if (criteria.getOrganismType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismType(), Organism_.organismType));
            }
            if (criteria.getAnaerobe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnaerobe(), Organism_.anaerobe));
            }
            if (criteria.getMorphology() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMorphology(), Organism_.morphology));
            }
            if (criteria.getSubkingdomCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubkingdomCode(), Organism_.subkingdomCode));
            }
            if (criteria.getFamilyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFamilyCode(), Organism_.familyCode));
            }
            if (criteria.getGenusGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenusGroup(), Organism_.genusGroup));
            }
            if (criteria.getGenusCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenusCode(), Organism_.genusCode));
            }
            if (criteria.getSpeciesGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpeciesGroup(), Organism_.speciesGroup));
            }
            if (criteria.getSerovarGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerovarGroup(), Organism_.serovarGroup));
            }
            if (criteria.getMsfGrpClin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMsfGrpClin(), Organism_.msfGrpClin));
            }
            if (criteria.getSctCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSctCode(), Organism_.sctCode));
            }
            if (criteria.getSctText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSctText(), Organism_.sctText));
            }
            if (criteria.getDwcTaxonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDwcTaxonId(), Organism_.dwcTaxonId));
            }
            if (criteria.getDwcTaxonomicStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDwcTaxonomicStatus(), Organism_.dwcTaxonomicStatus));
            }
            if (criteria.getGbifTaxonId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGbifTaxonId(), Organism_.gbifTaxonId));
            }
            if (criteria.getGbifDatasetId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGbifDatasetId(), Organism_.gbifDatasetId));
            }
            if (criteria.getGbifTaxonomicStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getGbifTaxonomicStatus(), Organism_.gbifTaxonomicStatus));
            }
            if (criteria.getKingdom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKingdom(), Organism_.kingdom));
            }
            if (criteria.getPhylum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhylum(), Organism_.phylum));
            }
            if (criteria.getOrganismClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganismClass(), Organism_.organismClass));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrder(), Organism_.order));
            }
            if (criteria.getFamily() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFamily(), Organism_.family));
            }
            if (criteria.getGenus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGenus(), Organism_.genus));
            }
        }
        return specification;
    }
}
