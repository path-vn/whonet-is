package org.path.amr.services.web.rest;

import io.swagger.annotations.Api;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.path.amr.services.repository.AntibioticRepository;
import org.path.amr.services.service.AntibioticQueryService;
import org.path.amr.services.service.AntibioticService;
import org.path.amr.services.service.criteria.AntibioticCriteria;
import org.path.amr.services.service.dto.AntibioticDTO;
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
 * REST controller for managing {@link org.path.amr.services.domain.Antibiotic}.
 */
@RestController
@RequestMapping("/api")
@Api(value = "Whonet Resource", tags = { "2. Kháng sinh/ Antibiotic " })
public class AntibioticResource {

    private final Logger log = LoggerFactory.getLogger(AntibioticResource.class);

    private static final String ENTITY_NAME = "antibiotic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntibioticService antibioticService;

    private final AntibioticRepository antibioticRepository;

    private final AntibioticQueryService antibioticQueryService;

    public AntibioticResource(
        AntibioticService antibioticService,
        AntibioticRepository antibioticRepository,
        AntibioticQueryService antibioticQueryService
    ) {
        this.antibioticService = antibioticService;
        this.antibioticRepository = antibioticRepository;
        this.antibioticQueryService = antibioticQueryService;
    }

    /**
     * {@code POST  /antibiotics} : Create a new antibiotic.
     *
     * @param antibioticDTO the antibioticDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antibioticDTO, or with status {@code 400 (Bad Request)} if the antibiotic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antibiotics")
    public ResponseEntity<AntibioticDTO> createAntibiotic(@RequestBody AntibioticDTO antibioticDTO) throws URISyntaxException {
        log.debug("REST request to save Antibiotic : {}", antibioticDTO);
        if (antibioticDTO.getId() != null) {
            throw new BadRequestAlertException("A new antibiotic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntibioticDTO result = antibioticService.save(antibioticDTO);
        return ResponseEntity
            .created(new URI("/api/antibiotics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antibiotics/:id} : Updates an existing antibiotic.
     *
     * @param id the id of the antibioticDTO to save.
     * @param antibioticDTO the antibioticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibioticDTO,
     * or with status {@code 400 (Bad Request)} if the antibioticDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antibioticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antibiotics/{id}")
    public ResponseEntity<AntibioticDTO> updateAntibiotic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AntibioticDTO antibioticDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Antibiotic : {}, {}", id, antibioticDTO);
        if (antibioticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibioticDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AntibioticDTO result = antibioticService.save(antibioticDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibioticDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /antibiotics/:id} : Partial updates given fields of an existing antibiotic, field will ignore if it is null
     *
     * @param id the id of the antibioticDTO to save.
     * @param antibioticDTO the antibioticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antibioticDTO,
     * or with status {@code 400 (Bad Request)} if the antibioticDTO is not valid,
     * or with status {@code 404 (Not Found)} if the antibioticDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the antibioticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/antibiotics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AntibioticDTO> partialUpdateAntibiotic(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AntibioticDTO antibioticDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Antibiotic partially : {}, {}", id, antibioticDTO);
        if (antibioticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, antibioticDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!antibioticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AntibioticDTO> result = antibioticService.partialUpdate(antibioticDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antibioticDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /antibiotics} : get all the antibiotics.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antibiotics in body.
     */
    @GetMapping("/antibiotics")
    public ResponseEntity<List<AntibioticDTO>> getAllAntibiotics(AntibioticCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Antibiotics by criteria: {}", criteria);
        Page<AntibioticDTO> page = antibioticQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /antibiotics/count} : count all the antibiotics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/antibiotics/count")
    public ResponseEntity<Long> countAntibiotics(AntibioticCriteria criteria) {
        log.debug("REST request to count Antibiotics by criteria: {}", criteria);
        return ResponseEntity.ok().body(antibioticQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /antibiotics/:id} : get the "id" antibiotic.
     *
     * @param id the id of the antibioticDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antibioticDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antibiotics/{id}")
    public ResponseEntity<AntibioticDTO> getAntibiotic(@PathVariable Long id) {
        log.debug("REST request to get Antibiotic : {}", id);
        Optional<AntibioticDTO> antibioticDTO = antibioticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(antibioticDTO);
    }

    /**
     * {@code DELETE  /antibiotics/:id} : delete the "id" antibiotic.
     *
     * @param id the id of the antibioticDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antibiotics/{id}")
    public ResponseEntity<Void> deleteAntibiotic(@PathVariable Long id) {
        log.debug("REST request to delete Antibiotic : {}", id);
        antibioticService.delete(id);
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
    @GetMapping("/antibiotics/groups/{key}")
    public ResponseEntity<Map<String, List<String>>> getGroupsByField(@PathVariable String key) {
        log.debug("REST request to get Breakpoint group: {}", key);
        List<String> breakpointDTO = antibioticService.findGroups(key);
        Map<String, List<String>> result = new HashMap<>();
        result.put(key, breakpointDTO);
        return ResponseUtil.wrapOrNotFound(Optional.of(result));
    }
}
