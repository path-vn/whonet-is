package org.path.amr.services.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.path.amr.services.repository.WhonetResourceRepository;
import org.path.amr.services.service.WhonetResourceQueryService;
import org.path.amr.services.service.WhonetResourceService;
import org.path.amr.services.service.criteria.WhonetResourceCriteria;
import org.path.amr.services.service.dto.WhonetResourceDTO;
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
 * REST controller for managing {@link org.path.amr.services.domain.WhonetResource}.
 */
@RestController
@RequestMapping("/api")
public class WhonetResourceResource {

    private final Logger log = LoggerFactory.getLogger(WhonetResourceResource.class);

    private static final String ENTITY_NAME = "whonetResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WhonetResourceService whonetResourceService;

    private final WhonetResourceRepository whonetResourceRepository;

    private final WhonetResourceQueryService whonetResourceQueryService;

    public WhonetResourceResource(
        WhonetResourceService whonetResourceService,
        WhonetResourceRepository whonetResourceRepository,
        WhonetResourceQueryService whonetResourceQueryService
    ) {
        this.whonetResourceService = whonetResourceService;
        this.whonetResourceRepository = whonetResourceRepository;
        this.whonetResourceQueryService = whonetResourceQueryService;
    }

    /**
     * {@code POST  /whonet-resources} : Create a new whonetResource.
     *
     * @param whonetResourceDTO the whonetResourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new whonetResourceDTO, or with status {@code 400 (Bad Request)} if the whonetResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/whonet-resources")
    public ResponseEntity<WhonetResourceDTO> createWhonetResource(@RequestBody WhonetResourceDTO whonetResourceDTO)
        throws URISyntaxException {
        log.debug("REST request to save WhonetResource : {}", whonetResourceDTO);
        if (whonetResourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new whonetResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WhonetResourceDTO result = whonetResourceService.save(whonetResourceDTO);
        return ResponseEntity
            .created(new URI("/api/whonet-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /whonet-resources/:id} : Updates an existing whonetResource.
     *
     * @param id the id of the whonetResourceDTO to save.
     * @param whonetResourceDTO the whonetResourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated whonetResourceDTO,
     * or with status {@code 400 (Bad Request)} if the whonetResourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the whonetResourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/whonet-resources/{id}")
    public ResponseEntity<WhonetResourceDTO> updateWhonetResource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WhonetResourceDTO whonetResourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WhonetResource : {}, {}", id, whonetResourceDTO);
        if (whonetResourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, whonetResourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!whonetResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WhonetResourceDTO result = whonetResourceService.save(whonetResourceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, whonetResourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /whonet-resources/:id} : Partial updates given fields of an existing whonetResource, field will ignore if it is null
     *
     * @param id the id of the whonetResourceDTO to save.
     * @param whonetResourceDTO the whonetResourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated whonetResourceDTO,
     * or with status {@code 400 (Bad Request)} if the whonetResourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the whonetResourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the whonetResourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/whonet-resources/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WhonetResourceDTO> partialUpdateWhonetResource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WhonetResourceDTO whonetResourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WhonetResource partially : {}, {}", id, whonetResourceDTO);
        if (whonetResourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, whonetResourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!whonetResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WhonetResourceDTO> result = whonetResourceService.partialUpdate(whonetResourceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, whonetResourceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /whonet-resources} : get all the whonetResources.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of whonetResources in body.
     */
    @GetMapping("/whonet-resources")
    public ResponseEntity<List<WhonetResourceDTO>> getAllWhonetResources(WhonetResourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WhonetResources by criteria: {}", criteria);
        Page<WhonetResourceDTO> page = whonetResourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /whonet-resources/count} : count all the whonetResources.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/whonet-resources/count")
    public ResponseEntity<Long> countWhonetResources(WhonetResourceCriteria criteria) {
        log.debug("REST request to count WhonetResources by criteria: {}", criteria);
        return ResponseEntity.ok().body(whonetResourceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /whonet-resources/:id} : get the "id" whonetResource.
     *
     * @param id the id of the whonetResourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the whonetResourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/whonet-resources/{id}")
    public ResponseEntity<WhonetResourceDTO> getWhonetResource(@PathVariable Long id) {
        log.debug("REST request to get WhonetResource : {}", id);
        Optional<WhonetResourceDTO> whonetResourceDTO = whonetResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(whonetResourceDTO);
    }

    /**
     * {@code DELETE  /whonet-resources/:id} : delete the "id" whonetResource.
     *
     * @param id the id of the whonetResourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/whonet-resources/{id}")
    public ResponseEntity<Void> deleteWhonetResource(@PathVariable Long id) {
        log.debug("REST request to delete WhonetResource : {}", id);
        whonetResourceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
