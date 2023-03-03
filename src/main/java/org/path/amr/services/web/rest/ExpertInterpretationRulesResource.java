package org.path.amr.services.web.rest;

import io.swagger.annotations.Api;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.path.amr.services.repository.ExpertInterpretationRulesRepository;
import org.path.amr.services.service.ExpertInterpretationRulesQueryService;
import org.path.amr.services.service.ExpertInterpretationRulesService;
import org.path.amr.services.service.criteria.ExpertInterpretationRulesCriteria;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;
import org.path.amr.services.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.path.amr.services.domain.ExpertInterpretationRules}.
 */
@RestController
@RequestMapping("/api")
@Api(value = "Whonet Resource", tags = { "6. ExpertInterpretationRules" })
public class ExpertInterpretationRulesResource {

    private final Logger log = LoggerFactory.getLogger(ExpertInterpretationRulesResource.class);

    private static final String ENTITY_NAME = "expertInterpretationRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpertInterpretationRulesService expertInterpretationRulesService;

    private final ExpertInterpretationRulesRepository expertInterpretationRulesRepository;

    private final ExpertInterpretationRulesQueryService expertInterpretationRulesQueryService;

    public ExpertInterpretationRulesResource(
        ExpertInterpretationRulesService expertInterpretationRulesService,
        ExpertInterpretationRulesRepository expertInterpretationRulesRepository,
        ExpertInterpretationRulesQueryService expertInterpretationRulesQueryService
    ) {
        this.expertInterpretationRulesService = expertInterpretationRulesService;
        this.expertInterpretationRulesRepository = expertInterpretationRulesRepository;
        this.expertInterpretationRulesQueryService = expertInterpretationRulesQueryService;
    }

    /**
     * {@code POST  /expert-interpretation-rules} : Create a new expertInterpretationRules.
     *
     * @param expertInterpretationRulesDTO the expertInterpretationRulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expertInterpretationRulesDTO, or with status {@code 400 (Bad Request)} if the expertInterpretationRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expert-interpretation-rules")
    public ResponseEntity<ExpertInterpretationRulesDTO> createExpertInterpretationRules(
        @RequestBody ExpertInterpretationRulesDTO expertInterpretationRulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ExpertInterpretationRules : {}", expertInterpretationRulesDTO);
        if (expertInterpretationRulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new expertInterpretationRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpertInterpretationRulesDTO result = expertInterpretationRulesService.save(expertInterpretationRulesDTO);
        return ResponseEntity
            .created(new URI("/api/expert-interpretation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expert-interpretation-rules/:id} : Updates an existing expertInterpretationRules.
     *
     * @param id the id of the expertInterpretationRulesDTO to save.
     * @param expertInterpretationRulesDTO the expertInterpretationRulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expertInterpretationRulesDTO,
     * or with status {@code 400 (Bad Request)} if the expertInterpretationRulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expertInterpretationRulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expert-interpretation-rules/{id}")
    public ResponseEntity<ExpertInterpretationRulesDTO> updateExpertInterpretationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpertInterpretationRulesDTO expertInterpretationRulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExpertInterpretationRules : {}, {}", id, expertInterpretationRulesDTO);
        if (expertInterpretationRulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expertInterpretationRulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expertInterpretationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExpertInterpretationRulesDTO result = expertInterpretationRulesService.save(expertInterpretationRulesDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expertInterpretationRulesDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /expert-interpretation-rules/:id} : Partial updates given fields of an existing expertInterpretationRules, field will ignore if it is null
     *
     * @param id the id of the expertInterpretationRulesDTO to save.
     * @param expertInterpretationRulesDTO the expertInterpretationRulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expertInterpretationRulesDTO,
     * or with status {@code 400 (Bad Request)} if the expertInterpretationRulesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the expertInterpretationRulesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the expertInterpretationRulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/expert-interpretation-rules/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExpertInterpretationRulesDTO> partialUpdateExpertInterpretationRules(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpertInterpretationRulesDTO expertInterpretationRulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExpertInterpretationRules partially : {}, {}", id, expertInterpretationRulesDTO);
        if (expertInterpretationRulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expertInterpretationRulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expertInterpretationRulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExpertInterpretationRulesDTO> result = expertInterpretationRulesService.partialUpdate(expertInterpretationRulesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expertInterpretationRulesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /expert-interpretation-rules} : get all the expertInterpretationRules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expertInterpretationRules in body.
     */
    @GetMapping("/expert-interpretation-rules")
    public ResponseEntity<List<ExpertInterpretationRulesDTO>> getAllExpertInterpretationRules(
        ExpertInterpretationRulesCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get ExpertInterpretationRules by criteria: {}", criteria);
        Page<ExpertInterpretationRulesDTO> page = expertInterpretationRulesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expert-interpretation-rules/count} : count all the expertInterpretationRules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expert-interpretation-rules/count")
    public ResponseEntity<Long> countExpertInterpretationRules(ExpertInterpretationRulesCriteria criteria) {
        log.debug("REST request to count ExpertInterpretationRules by criteria: {}", criteria);
        return ResponseEntity.ok().body(expertInterpretationRulesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expert-interpretation-rules/:id} : get the "id" expertInterpretationRules.
     *
     * @param id the id of the expertInterpretationRulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expertInterpretationRulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expert-interpretation-rules/{id}")
    public ResponseEntity<ExpertInterpretationRulesDTO> getExpertInterpretationRules(@PathVariable Long id) {
        log.debug("REST request to get ExpertInterpretationRules : {}", id);
        Optional<ExpertInterpretationRulesDTO> expertInterpretationRulesDTO = expertInterpretationRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expertInterpretationRulesDTO);
    }

    /**
     * {@code DELETE  /expert-interpretation-rules/:id} : delete the "id" expertInterpretationRules.
     *
     * @param id the id of the expertInterpretationRulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expert-interpretation-rules/{id}")
    public ResponseEntity<Void> deleteExpertInterpretationRules(@PathVariable Long id) {
        log.debug("REST request to delete ExpertInterpretationRules : {}", id);
        expertInterpretationRulesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
