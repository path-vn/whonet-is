package org.path.amr.services.web.rest;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
import javax.validation.Valid;
import org.path.amr.services.config.WhonetConfiguration;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.IntrinsicResistanceQueryService;
import org.path.amr.services.service.dto.IsolateDTO;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
import org.path.amr.services.service.impl.InterpretationWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class WhonetResource {

    InterpretationService interpretationService;
    WhonetConfiguration whonetConfiguration;
    int thread = 10;
    private final Logger log = LoggerFactory.getLogger(WhonetResource.class);

    public WhonetResource(InterpretationService interpretationService, WhonetConfiguration whonetConfiguration) {
        this.interpretationService = interpretationService;
        this.whonetConfiguration = whonetConfiguration;
        if (this.whonetConfiguration.getThread() != null) {
            this.thread = this.whonetConfiguration.getThread();
        }
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @PostMapping("/whonet/interpretation")
    public IsolateDTO getInterpretation(@RequestBody IsolateDTO isolateDTO) {
        this.interpretationService.execute(isolateDTO);
        return isolateDTO;
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @PostMapping("/whonet/interpretation-file")
    public ResponseEntity<Resource> getInterpretation_file(@RequestParam(value = "file") MultipartFile file) throws IOException {
        Resource resource = new InputStreamResource(file.getInputStream());

        MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set("x-filename", "path-" + file.getOriginalFilename());
        // 3
        ContentDisposition disposition = ContentDisposition
            // 3.2
            .inline() // or .attachment()
            // 3.1
            .filename(file.getOriginalFilename() == null ? "output" : file.getOriginalFilename())
            .build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @PostMapping("/whonet/interpretation-bulk")
    public List<IsolateDTO> getInterpretations(@RequestBody List<IsolateDTO> isolateDTO) throws ExecutionException, InterruptedException {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());

        log.info("Start getInterpretations " + timestamp2.toString() + " thread:" + this.thread);

        ExecutorService executorService = Executors.newFixedThreadPool(this.thread);
        Set<Callable<IsolateDTO>> callables = new HashSet<Callable<IsolateDTO>>();
        for (int i = 0; i < isolateDTO.size(); i++) {
            callables.add(new InterpretationWorker(interpretationService, isolateDTO.get(i)));
        }
        List<Future<IsolateDTO>> futures = executorService.invokeAll(callables);
        List<IsolateDTO> result = new ArrayList<>();
        for (Future<IsolateDTO> future : futures) {
            result.add(future.get());
        }
        executorService.shutdown();
        log.info("End getInterpretations" + timestamp2.toString());
        return result;
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @GetMapping("/whonet/intrinsic_resistance")
    public List<OrganismIntrinsicResistanceAntibioticDTO> getIntrinsicResistance(
        @RequestParam String abxCode,
        @RequestParam String orgCode
    ) {
        return this.interpretationService.intrinsicResistance(orgCode, abxCode);
    }
}
