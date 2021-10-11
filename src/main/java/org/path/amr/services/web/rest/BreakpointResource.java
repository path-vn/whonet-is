package org.path.amr.services.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.path.amr.services.repository.BreakpointRepository;
import org.path.amr.services.service.BreakpointQueryService;
import org.path.amr.services.service.BreakpointService;
import org.path.amr.services.service.criteria.BreakpointCriteria;
import org.path.amr.services.service.dto.BreakpointDTO;
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
 * REST controller for managing {@link org.path.amr.services.domain.Breakpoint}.
 */
@RestController
@RequestMapping("/api")
public class BreakpointResource {

    private final Logger log = LoggerFactory.getLogger(BreakpointResource.class);

    private static final String ENTITY_NAME = "breakpoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BreakpointService breakpointService;

    private final BreakpointRepository breakpointRepository;

    private final BreakpointQueryService breakpointQueryService;

    public BreakpointResource(
        BreakpointService breakpointService,
        BreakpointRepository breakpointRepository,
        BreakpointQueryService breakpointQueryService
    ) {
        this.breakpointService = breakpointService;
        this.breakpointRepository = breakpointRepository;
        this.breakpointQueryService = breakpointQueryService;
    }

    /**
     * {@code POST  /breakpoints} : Create a new breakpoint.
     *
     * @param breakpointDTO the breakpointDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new breakpointDTO, or with status {@code 400 (Bad Request)} if the breakpoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/breakpoints")
    public ResponseEntity<BreakpointDTO> createBreakpoint(@RequestBody BreakpointDTO breakpointDTO) throws URISyntaxException {
        log.debug("REST request to save Breakpoint : {}", breakpointDTO);
        if (breakpointDTO.getId() != null) {
            throw new BadRequestAlertException("A new breakpoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BreakpointDTO result = breakpointService.save(breakpointDTO);
        return ResponseEntity
            .created(new URI("/api/breakpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /breakpoints/:id} : Updates an existing breakpoint.
     *
     * @param id the id of the breakpointDTO to save.
     * @param breakpointDTO the breakpointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breakpointDTO,
     * or with status {@code 400 (Bad Request)} if the breakpointDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the breakpointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/breakpoints/{id}")
    public ResponseEntity<BreakpointDTO> updateBreakpoint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BreakpointDTO breakpointDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Breakpoint : {}, {}", id, breakpointDTO);
        if (breakpointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, breakpointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!breakpointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BreakpointDTO result = breakpointService.save(breakpointDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, breakpointDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /breakpoints/:id} : Partial updates given fields of an existing breakpoint, field will ignore if it is null
     *
     * @param id the id of the breakpointDTO to save.
     * @param breakpointDTO the breakpointDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breakpointDTO,
     * or with status {@code 400 (Bad Request)} if the breakpointDTO is not valid,
     * or with status {@code 404 (Not Found)} if the breakpointDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the breakpointDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/breakpoints/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BreakpointDTO> partialUpdateBreakpoint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BreakpointDTO breakpointDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Breakpoint partially : {}, {}", id, breakpointDTO);
        if (breakpointDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, breakpointDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!breakpointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BreakpointDTO> result = breakpointService.partialUpdate(breakpointDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, breakpointDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /breakpoints} : get all the breakpoints.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of breakpoints in body.
     */
    @GetMapping("/breakpoints")
    public ResponseEntity<List<BreakpointDTO>> getAllBreakpoints(BreakpointCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Breakpoints by criteria: {}", criteria);
        Page<BreakpointDTO> page = breakpointQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /breakpoints/count} : count all the breakpoints.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/breakpoints/count")
    public ResponseEntity<Long> countBreakpoints(BreakpointCriteria criteria) {
        log.debug("REST request to count Breakpoints by criteria: {}", criteria);
        return ResponseEntity.ok().body(breakpointQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /breakpoints/:id} : get the "id" breakpoint.
     *
     * @param id the id of the breakpointDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the breakpointDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/breakpoints/{id}")
    public ResponseEntity<BreakpointDTO> getBreakpoint(@PathVariable Long id) {
        log.debug("REST request to get Breakpoint : {}", id);
        Optional<BreakpointDTO> breakpointDTO = breakpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(breakpointDTO);
    }

    /**
     * {@code DELETE  /breakpoints/:id} : delete the "id" breakpoint.
     *
     * @param id the id of the breakpointDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/breakpoints/{id}")
    public ResponseEntity<Void> deleteBreakpoint(@PathVariable Long id) {
        log.debug("REST request to delete Breakpoint : {}", id);
        breakpointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
