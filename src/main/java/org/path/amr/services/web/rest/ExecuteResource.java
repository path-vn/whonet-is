package org.path.amr.services.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.path.amr.services.repository.ExecuteRepository;
import org.path.amr.services.service.ExecuteQueryService;
import org.path.amr.services.service.ExecuteService;
import org.path.amr.services.service.criteria.ExecuteCriteria;
import org.path.amr.services.service.dto.ExecuteDTO;
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
 * REST controller for managing {@link org.path.amr.services.domain.Execute}.
 */
@RestController
@RequestMapping("/api")
public class ExecuteResource {

    private final Logger log = LoggerFactory.getLogger(ExecuteResource.class);

    private static final String ENTITY_NAME = "execute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExecuteService executeService;

    private final ExecuteRepository executeRepository;

    private final ExecuteQueryService executeQueryService;

    public ExecuteResource(ExecuteService executeService, ExecuteRepository executeRepository, ExecuteQueryService executeQueryService) {
        this.executeService = executeService;
        this.executeRepository = executeRepository;
        this.executeQueryService = executeQueryService;
    }

    /**
     * {@code POST  /executes} : Create a new execute.
     *
     * @param executeDTO the executeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new executeDTO, or with status {@code 400 (Bad Request)} if the execute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/executes")
    public ResponseEntity<ExecuteDTO> createExecute(@RequestBody ExecuteDTO executeDTO) throws URISyntaxException {
        log.debug("REST request to save Execute : {}", executeDTO);
        if (executeDTO.getId() != null) {
            throw new BadRequestAlertException("A new execute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExecuteDTO result = executeService.save(executeDTO);
        return ResponseEntity
            .created(new URI("/api/executes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /executes/:id} : Updates an existing execute.
     *
     * @param id the id of the executeDTO to save.
     * @param executeDTO the executeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated executeDTO,
     * or with status {@code 400 (Bad Request)} if the executeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the executeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/executes/{id}")
    public ResponseEntity<ExecuteDTO> updateExecute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExecuteDTO executeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Execute : {}, {}", id, executeDTO);
        if (executeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, executeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!executeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExecuteDTO result = executeService.save(executeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, executeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /executes/:id} : Partial updates given fields of an existing execute, field will ignore if it is null
     *
     * @param id the id of the executeDTO to save.
     * @param executeDTO the executeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated executeDTO,
     * or with status {@code 400 (Bad Request)} if the executeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the executeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the executeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/executes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExecuteDTO> partialUpdateExecute(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExecuteDTO executeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Execute partially : {}, {}", id, executeDTO);
        if (executeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, executeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!executeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExecuteDTO> result = executeService.partialUpdate(executeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, executeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /executes} : get all the executes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of executes in body.
     */
    @GetMapping("/executes")
    public ResponseEntity<List<ExecuteDTO>> getAllExecutes(ExecuteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Executes by criteria: {}", criteria);
        Page<ExecuteDTO> page = executeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /executes/count} : count all the executes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/executes/count")
    public ResponseEntity<Long> countExecutes(ExecuteCriteria criteria) {
        log.debug("REST request to count Executes by criteria: {}", criteria);
        return ResponseEntity.ok().body(executeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /executes/:id} : get the "id" execute.
     *
     * @param id the id of the executeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the executeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/executes/{id}")
    public ResponseEntity<ExecuteDTO> getExecute(@PathVariable Long id) {
        log.debug("REST request to get Execute : {}", id);
        Optional<ExecuteDTO> executeDTO = executeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(executeDTO);
    }

    /**
     * {@code DELETE  /executes/:id} : delete the "id" execute.
     *
     * @param id the id of the executeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/executes/{id}")
    public ResponseEntity<Void> deleteExecute(@PathVariable Long id) {
        log.debug("REST request to delete Execute : {}", id);
        executeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
