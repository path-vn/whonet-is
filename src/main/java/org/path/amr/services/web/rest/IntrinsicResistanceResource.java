package org.path.amr.services.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.path.amr.services.repository.IntrinsicResistanceRepository;
import org.path.amr.services.service.IntrinsicResistanceQueryService;
import org.path.amr.services.service.IntrinsicResistanceService;
import org.path.amr.services.service.criteria.IntrinsicResistanceCriteria;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;
import org.path.amr.services.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.path.amr.services.domain.IntrinsicResistance}.
 */
@RestController
@RequestMapping("/api")
public class IntrinsicResistanceResource {

    private final Logger log = LoggerFactory.getLogger(IntrinsicResistanceResource.class);

    private static final String ENTITY_NAME = "intrinsicResistance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntrinsicResistanceService intrinsicResistanceService;

    private final IntrinsicResistanceRepository intrinsicResistanceRepository;

    private final IntrinsicResistanceQueryService intrinsicResistanceQueryService;

    public IntrinsicResistanceResource(
        IntrinsicResistanceService intrinsicResistanceService,
        IntrinsicResistanceRepository intrinsicResistanceRepository,
        IntrinsicResistanceQueryService intrinsicResistanceQueryService
    ) {
        this.intrinsicResistanceService = intrinsicResistanceService;
        this.intrinsicResistanceRepository = intrinsicResistanceRepository;
        this.intrinsicResistanceQueryService = intrinsicResistanceQueryService;
    }

    /**
     * {@code POST  /intrinsic-resistances} : Create a new intrinsicResistance.
     *
     * @param intrinsicResistanceDTO the intrinsicResistanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intrinsicResistanceDTO, or with status {@code 400 (Bad Request)} if the intrinsicResistance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intrinsic-resistances")
    public ResponseEntity<IntrinsicResistanceDTO> createIntrinsicResistance(@RequestBody IntrinsicResistanceDTO intrinsicResistanceDTO)
        throws URISyntaxException {
        log.debug("REST request to save IntrinsicResistance : {}", intrinsicResistanceDTO);
        if (intrinsicResistanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new intrinsicResistance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntrinsicResistanceDTO result = intrinsicResistanceService.save(intrinsicResistanceDTO);
        return ResponseEntity
            .created(new URI("/api/intrinsic-resistances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intrinsic-resistances/:id} : Updates an existing intrinsicResistance.
     *
     * @param id the id of the intrinsicResistanceDTO to save.
     * @param intrinsicResistanceDTO the intrinsicResistanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrinsicResistanceDTO,
     * or with status {@code 400 (Bad Request)} if the intrinsicResistanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intrinsicResistanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intrinsic-resistances/{id}")
    public ResponseEntity<IntrinsicResistanceDTO> updateIntrinsicResistance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrinsicResistanceDTO intrinsicResistanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IntrinsicResistance : {}, {}", id, intrinsicResistanceDTO);
        if (intrinsicResistanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrinsicResistanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrinsicResistanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntrinsicResistanceDTO result = intrinsicResistanceService.save(intrinsicResistanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intrinsicResistanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /intrinsic-resistances/:id} : Partial updates given fields of an existing intrinsicResistance, field will ignore if it is null
     *
     * @param id the id of the intrinsicResistanceDTO to save.
     * @param intrinsicResistanceDTO the intrinsicResistanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrinsicResistanceDTO,
     * or with status {@code 400 (Bad Request)} if the intrinsicResistanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the intrinsicResistanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the intrinsicResistanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/intrinsic-resistances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IntrinsicResistanceDTO> partialUpdateIntrinsicResistance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrinsicResistanceDTO intrinsicResistanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntrinsicResistance partially : {}, {}", id, intrinsicResistanceDTO);
        if (intrinsicResistanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrinsicResistanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrinsicResistanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntrinsicResistanceDTO> result = intrinsicResistanceService.partialUpdate(intrinsicResistanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intrinsicResistanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /intrinsic-resistances} : get all the intrinsicResistances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intrinsicResistances in body.
     */
    @GetMapping("/intrinsic-resistances")
    public ResponseEntity<List<IntrinsicResistanceDTO>> getAllIntrinsicResistances(
        IntrinsicResistanceCriteria criteria,
        Pageable pageable
    ) {
        log.debug("REST request to get IntrinsicResistances by criteria: {}", criteria);
        Page<IntrinsicResistanceDTO> page = intrinsicResistanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intrinsic-resistances/count} : count all the intrinsicResistances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/intrinsic-resistances/count")
    public ResponseEntity<Long> countIntrinsicResistances(IntrinsicResistanceCriteria criteria) {
        log.debug("REST request to count IntrinsicResistances by criteria: {}", criteria);
        return ResponseEntity.ok().body(intrinsicResistanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /intrinsic-resistances/:id} : get the "id" intrinsicResistance.
     *
     * @param id the id of the intrinsicResistanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intrinsicResistanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intrinsic-resistances/{id}")
    public ResponseEntity<IntrinsicResistanceDTO> getIntrinsicResistance(@PathVariable Long id) {
        log.debug("REST request to get IntrinsicResistance : {}", id);
        Optional<IntrinsicResistanceDTO> intrinsicResistanceDTO = intrinsicResistanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intrinsicResistanceDTO);
    }

    /**
     * {@code DELETE  /intrinsic-resistances/:id} : delete the "id" intrinsicResistance.
     *
     * @param id the id of the intrinsicResistanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intrinsic-resistances/{id}")
    public ResponseEntity<Void> deleteIntrinsicResistance(@PathVariable Long id) {
        log.debug("REST request to delete IntrinsicResistance : {}", id);
        intrinsicResistanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
