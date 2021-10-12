package org.path.amr.services.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.path.amr.services.domain.*; // for static metamodels
import org.path.amr.services.domain.Antibiotic;
import org.path.amr.services.repository.AntibioticRepository;
import org.path.amr.services.service.criteria.AntibioticCriteria;
import org.path.amr.services.service.dto.AntibioticDTO;
import org.path.amr.services.service.mapper.AntibioticMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Antibiotic} entities in the database.
 * The main input is a {@link AntibioticCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AntibioticDTO} or a {@link Page} of {@link AntibioticDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AntibioticQueryService extends QueryService<Antibiotic> {

    private final Logger log = LoggerFactory.getLogger(AntibioticQueryService.class);

    private final AntibioticRepository antibioticRepository;

    private final AntibioticMapper antibioticMapper;

    public AntibioticQueryService(AntibioticRepository antibioticRepository, AntibioticMapper antibioticMapper) {
        this.antibioticRepository = antibioticRepository;
        this.antibioticMapper = antibioticMapper;
    }

    /**
     * Return a {@link List} of {@link AntibioticDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AntibioticDTO> findByCriteria(AntibioticCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Antibiotic> specification = createSpecification(criteria);
        return antibioticMapper.toDto(antibioticRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AntibioticDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AntibioticDTO> findByCriteria(AntibioticCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Antibiotic> specification = createSpecification(criteria);
        return antibioticRepository.findAll(specification, page).map(antibioticMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AntibioticCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Antibiotic> specification = createSpecification(criteria);
        return antibioticRepository.count(specification);
    }

    /**
     * Function to convert {@link AntibioticCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Antibiotic> createSpecification(AntibioticCriteria criteria) {
        Specification<Antibiotic> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Antibiotic_.id));
            }
            if (criteria.getWhonetAbxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhonetAbxCode(), Antibiotic_.whonetAbxCode));
            }
            if (criteria.getWhoCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhoCode(), Antibiotic_.whoCode));
            }
            if (criteria.getDinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDinCode(), Antibiotic_.dinCode));
            }
            if (criteria.getJacCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJacCode(), Antibiotic_.jacCode));
            }
            if (criteria.getEucastCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEucastCode(), Antibiotic_.eucastCode));
            }
            if (criteria.getUserCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserCode(), Antibiotic_.userCode));
            }
            if (criteria.getAntibiotic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAntibiotic(), Antibiotic_.antibiotic));
            }
            if (criteria.getGuidelines() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGuidelines(), Antibiotic_.guidelines));
            }
            if (criteria.getClsi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClsi(), Antibiotic_.clsi));
            }
            if (criteria.getEucast() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEucast(), Antibiotic_.eucast));
            }
            if (criteria.getSfm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSfm(), Antibiotic_.sfm));
            }
            if (criteria.getSrga() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSrga(), Antibiotic_.srga));
            }
            if (criteria.getBsac() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBsac(), Antibiotic_.bsac));
            }
            if (criteria.getDin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDin(), Antibiotic_.din));
            }
            if (criteria.getNeo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNeo(), Antibiotic_.neo));
            }
            if (criteria.getAfa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAfa(), Antibiotic_.afa));
            }
            if (criteria.getAbxNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbxNumber(), Antibiotic_.abxNumber));
            }
            if (criteria.getPotency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPotency(), Antibiotic_.potency));
            }
            if (criteria.getAtcCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAtcCode(), Antibiotic_.atcCode));
            }
            if (criteria.getProfClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfClass(), Antibiotic_.profClass));
            }
            if (criteria.getCiaCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCiaCategory(), Antibiotic_.ciaCategory));
            }
            if (criteria.getClsiOrder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClsiOrder(), Antibiotic_.clsiOrder));
            }
            if (criteria.getEucastOrder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEucastOrder(), Antibiotic_.eucastOrder));
            }
            if (criteria.getHuman() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHuman(), Antibiotic_.human));
            }
            if (criteria.getVeterinary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVeterinary(), Antibiotic_.veterinary));
            }
            if (criteria.getAnimalGp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnimalGp(), Antibiotic_.animalGp));
            }
            if (criteria.getLoinccomp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoinccomp(), Antibiotic_.loinccomp));
            }
            if (criteria.getLoincgen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincgen(), Antibiotic_.loincgen));
            }
            if (criteria.getLoincdisk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincdisk(), Antibiotic_.loincdisk));
            }
            if (criteria.getLoincmic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincmic(), Antibiotic_.loincmic));
            }
            if (criteria.getLoincetest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincetest(), Antibiotic_.loincetest));
            }
            if (criteria.getLoincslow() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincslow(), Antibiotic_.loincslow));
            }
            if (criteria.getLoincafb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincafb(), Antibiotic_.loincafb));
            }
            if (criteria.getLoincsbt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincsbt(), Antibiotic_.loincsbt));
            }
            if (criteria.getLoincmlc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoincmlc(), Antibiotic_.loincmlc));
            }
            if (criteria.getDateEntered() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateEntered(), Antibiotic_.dateEntered));
            }
            if (criteria.getDateModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateModified(), Antibiotic_.dateModified));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Antibiotic_.comments));
            }
        }
        return specification;
    }
}
