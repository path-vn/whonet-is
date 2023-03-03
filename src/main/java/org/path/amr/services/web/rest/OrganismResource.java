package org.path.amr.services.web.rest;

import io.swagger.annotations.Api;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.path.amr.services.repository.OrganismRepository;
import org.path.amr.services.service.OrganismQueryService;
import org.path.amr.services.service.OrganismService;
import org.path.amr.services.service.criteria.OrganismCriteria;
import org.path.amr.services.service.dto.OrganismDTO;
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
 * REST controller for managing {@link org.path.amr.services.domain.Organism}.
 */
@RestController
@RequestMapping("/api")
@Api(value = "Whonet Resource", tags = { "3. Vi khuẩn/ Organism" })
public class OrganismResource {

    private final Logger log = LoggerFactory.getLogger(OrganismResource.class);

    private static final String ENTITY_NAME = "organism";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganismService organismService;

    private final OrganismRepository organismRepository;

    private final OrganismQueryService organismQueryService;

    public OrganismResource(
        OrganismService organismService,
        OrganismRepository organismRepository,
        OrganismQueryService organismQueryService
    ) {
        this.organismService = organismService;
        this.organismRepository = organismRepository;
        this.organismQueryService = organismQueryService;
    }

    /**
     * {@code POST  /organisms} : Create a new organism.
     *
     * @param organismDTO the organismDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organismDTO, or with status {@code 400 (Bad Request)} if the organism has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisms")
    public ResponseEntity<OrganismDTO> createOrganism(@RequestBody OrganismDTO organismDTO) throws URISyntaxException {
        log.debug("REST request to save Organism : {}", organismDTO);
        if (organismDTO.getId() != null) {
            throw new BadRequestAlertException("A new organism cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganismDTO result = organismService.save(organismDTO);
        return ResponseEntity
            .created(new URI("/api/organisms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisms/:id} : Updates an existing organism.
     *
     * @param id the id of the organismDTO to save.
     * @param organismDTO the organismDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organismDTO,
     * or with status {@code 400 (Bad Request)} if the organismDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organismDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisms/{id}")
    public ResponseEntity<OrganismDTO> updateOrganism(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganismDTO organismDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Organism : {}, {}", id, organismDTO);
        if (organismDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organismDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganismDTO result = organismService.save(organismDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organismDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organisms/:id} : Partial updates given fields of an existing organism, field will ignore if it is null
     *
     * @param id the id of the organismDTO to save.
     * @param organismDTO the organismDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organismDTO,
     * or with status {@code 400 (Bad Request)} if the organismDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organismDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organismDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organisms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrganismDTO> partialUpdateOrganism(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganismDTO organismDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organism partially : {}, {}", id, organismDTO);
        if (organismDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organismDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organismRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganismDTO> result = organismService.partialUpdate(organismDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organismDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organisms} : get all the organisms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisms in body.
     */
    @GetMapping("/organisms")
    public ResponseEntity<List<OrganismDTO>> getAllOrganisms(OrganismCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Organisms by criteria: {}", criteria);
        Page<OrganismDTO> page = organismQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organisms/count} : count all the organisms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/organisms/count")
    public ResponseEntity<Long> countOrganisms(OrganismCriteria criteria) {
        log.debug("REST request to count Organisms by criteria: {}", criteria);
        return ResponseEntity.ok().body(organismQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /organisms/:id} : get the "id" organism.
     *
     * @param id the id of the organismDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organismDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisms/{id}")
    public ResponseEntity<OrganismDTO> getOrganism(@PathVariable Long id) {
        log.debug("REST request to get Organism : {}", id);
        Optional<OrganismDTO> organismDTO = organismService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organismDTO);
    }

    /**
     * {@code DELETE  /organisms/:id} : delete the "id" organism.
     *
     * @param id the id of the organismDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisms/{id}")
    public ResponseEntity<Void> deleteOrganism(@PathVariable Long id) {
        log.debug("REST request to delete Organism : {}", id);
        organismService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /breakpoints/group/:id} : get the "id" breakpoint.
     *
     * @param key the id of the breakpointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the breakpointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisms/groups/{key}")
    public ResponseEntity<Map<String, List<String>>> getGroupsByField(@PathVariable String key) {
        log.debug("REST request to get Breakpoint group: {}", key);
        List<String> breakpointDTO = organismService.findGroups(key);
        Map<String, List<String>> result = new HashMap<>();
        result.put(key, breakpointDTO);
        return ResponseUtil.wrapOrNotFound(Optional.of(result));
    }
}
