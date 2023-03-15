package org.path.amr.services.web.rest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @Value("${application.baseDirectory:#{'/tmp'}}")
    private String baseDirectory;

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

    @GetMapping("/whonet-resources/{id}/{file}")
    public ResponseEntity<Resource> downloadFile(
        @PathVariable(value = "id", required = true) final Long id,
        @PathVariable(value = "file", required = true) final String fileType
    ) {
        String filePath = "";
        switch (fileType) {
            case "antibiotic":
                filePath = baseDirectory + "/" + id + "/antibiotic.txt";
                break;
            case "organism":
                filePath = baseDirectory + "/" + id + "/organism.txt";
                break;
            case "intrinsicResistance":
                filePath = baseDirectory + "/" + id + "/intrinsicResistance.txt";
                break;
            case "expertRule":
                filePath = baseDirectory + "/" + id + "/expertRule.txt";
                break;
            case "breakPoint":
                filePath = baseDirectory + "/" + id + "/breakPoint.txt";
                break;
        }
        if (filePath.equals("")) {
            throw new BadRequestAlertException("Type not supported", "", "");
        }
        // Construct the file object from the file path
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set the content type and disposition headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

        // Create a Resource object from the file and return it in a ResponseEntity
        Resource resource = new FileSystemResource(file);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * {@code POST  /whonet-resources} : Create a new whonetResource.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new whonetResourceDTO, or with status {@code 400 (Bad Request)} if the whonetResource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/whonet-resources")
    public ResponseEntity<WhonetResourceDTO> createWhonetResource(
        @RequestParam("antibiotic") Optional<MultipartFile> antibiotic,
        @RequestParam("organism") Optional<MultipartFile> organism,
        @RequestParam("intrinsicResistance") Optional<MultipartFile> intrinsicResistance,
        @RequestParam("expertRule") Optional<MultipartFile> expertRule,
        @RequestParam("breakPoint") Optional<MultipartFile> breakpoint
    ) throws URISyntaxException, IllegalAccessException, IOException, InvocationTargetException {
        if (baseDirectory.isEmpty()) {
            baseDirectory = "/tmp/";
        }
        WhonetResourceDTO whonetResourceDTO = new WhonetResourceDTO();
        whonetResourceDTO.setUploadDate(ZonedDateTime.now());

        log.debug("REST request to save WhonetResource : {}", whonetResourceDTO);
        if (whonetResourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new whonetResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WhonetResourceDTO result = whonetResourceService.save(whonetResourceDTO);
        String dir = String.format("%s/%d/", baseDirectory, result.getId());

        if (antibiotic.isPresent()) {
            result.setAntibiotic(dir + "antibiotic.txt");
            saveMultipartFile(antibiotic.get(), result.getAntibiotic());
        }

        if (organism.isPresent()) {
            result.setOrganism(dir + "organism.txt");
            saveMultipartFile(organism.get(), result.getOrganism());
        }
        if (intrinsicResistance.isPresent()) {
            result.setIntrinsicResistance(dir + "intrinsicResistance.txt");
            saveMultipartFile(intrinsicResistance.get(), result.getIntrinsicResistance());
        }

        if (breakpoint.isPresent()) {
            result.setBreakPoint(dir + "breakpoint.txt");
            saveMultipartFile(breakpoint.get(), result.getBreakPoint());
        }
        if (expertRule.isPresent()) {
            result.setExpertRule(dir + "expertRule.txt");
            saveMultipartFile(expertRule.get(), result.getExpertRule());
        }

        result = whonetResourceService.save(result);

        whonetResourceService.doImport(result);
        return ResponseEntity
            .created(new URI("/api/whonet-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private void saveMultipartFile(MultipartFile file, String filePath) {
        try {
            // Create a new directory if it does not exist
            File directory = new File(filePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File oldFile = new File(filePath);
            if (oldFile.exists()) {
                oldFile.delete();
            }
            // Save the file to disk
            file.transferTo(oldFile);
        } catch (IOException e) {
            log.error("{} {} ", filePath, e);
        }
    }

    /**
     * {@code PUT  /whonet-resources/:id} : Updates an existing whonetResource.
     *
     * @param id the id of the whonetResourceDTO to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated whonetResourceDTO,
     * or with status {@code 400 (Bad Request)} if the whonetResourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the whonetResourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/whonet-resources/{id}")
    public ResponseEntity<WhonetResourceDTO> updateWhonetResource(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestParam("antibiotic") Optional<MultipartFile> antibiotic,
        @RequestParam("organism") Optional<MultipartFile> organism,
        @RequestParam("intrinsicResistance") Optional<MultipartFile> intrinsicResistance,
        @RequestParam("expertRule") Optional<MultipartFile> expertRule,
        @RequestParam("breakPoint") Optional<MultipartFile> breakpoint
    ) throws URISyntaxException, IllegalAccessException, IOException, InvocationTargetException {
        log.debug("REST request to update WhonetResource : {}", id);
        String dir = String.format("%s/%d/", baseDirectory, id);

        if (!whonetResourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WhonetResourceDTO result = whonetResourceService.findOne(id).get();
        result.setUploadDate(ZonedDateTime.now());

        if (antibiotic.isPresent()) {
            result.setAntibiotic(dir + "antibiotic.txt");
            saveMultipartFile(antibiotic.get(), result.getAntibiotic());
        }

        if (organism.isPresent()) {
            result.setOrganism(dir + "organism.txt");
            saveMultipartFile(organism.get(), result.getOrganism());
        }
        if (intrinsicResistance.isPresent()) {
            result.setIntrinsicResistance(dir + "intrinsicResistance.txt");
            saveMultipartFile(intrinsicResistance.get(), result.getIntrinsicResistance());
        }

        if (breakpoint.isPresent()) {
            result.setBreakPoint(dir + "breakpoint.txt");
            saveMultipartFile(breakpoint.get(), result.getBreakPoint());
        }
        if (expertRule.isPresent()) {
            result.setExpertRule(dir + "expertRule.txt");
            saveMultipartFile(expertRule.get(), result.getExpertRule());
        }

        result = whonetResourceService.save(result);
        whonetResourceService.doImport(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /whonet-resources/:id} : Partial updates given fields of an existing whonetResource, field will ignore if it is null
     *
     * @param id                the id of the whonetResourceDTO to save.
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
