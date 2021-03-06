package org.path.amr.services.web.rest;

import io.minio.errors.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.path.amr.services.config.WhonetConfiguration;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.MailService;
import org.path.amr.services.service.dto.IsolateDTO;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class WhonetResource {

    InterpretationService interpretationService;
    WhonetConfiguration whonetConfiguration;
    MailService mailService;
    int thread = 10;
    private final Logger log = LoggerFactory.getLogger(WhonetResource.class);

    public WhonetResource(InterpretationService interpretationService, WhonetConfiguration whonetConfiguration, MailService mailService) {
        this.interpretationService = interpretationService;
        this.whonetConfiguration = whonetConfiguration;
        this.mailService = mailService;
        if (this.whonetConfiguration == null) {
            this.thread = 1;
            return;
        }
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
    public ResponseEntity<Void> getInterpretation_file(
        @RequestParam(value = "files") MultipartFile[] files,
        @RequestParam(value = "email") String email,
        @RequestParam(value = "action") String action,
        @RequestParam(value = "breakpoint") String breakpoint,
        @RequestParam(value = "intrinsic") String intrinsic,
        @RequestParam(value = "no-empty") String noEmpty,
        @RequestParam(value = "equal") String filterEqual,
        @RequestParam(value = "organismGroupTypeOrder") String organismCodeTypeOrder
    )
        throws IOException, ExecutionException, InterruptedException, InsufficientDataException, NoSuchAlgorithmException, InternalException, InvalidResponseException, ErrorResponseException, XmlParserException, ServerException, InvalidKeyException {
        if (files.length == 0) {
            throw new RuntimeException("Missing file");
        }
        interpretationService.processFile(
            this.mailService,
            interpretationService.mergeInputStreams(files),
            files[0].getOriginalFilename(),
            email,
            action,
            breakpoint,
            intrinsic,
            noEmpty,
            filterEqual,
            organismCodeTypeOrder,
            this.thread
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @PostMapping("/whonet/interpretation-bulk")
    public List<IsolateDTO> getInterpretations(@RequestBody List<IsolateDTO> isolateDTO) throws ExecutionException, InterruptedException {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());

        log.info("Start getInterpretations " + timestamp2.toString() + " thread:" + this.thread);

        List<IsolateDTO> result = interpretationService.execIsolateDTOS(isolateDTO, this.thread);
        log.info("End getInterpretations" + timestamp2.toString());
        return result;
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @GetMapping("/whonet/intrinsic_resistance")
    public List<OrganismIntrinsicResistanceAntibioticDTO> getIntrinsicResistance(
        @RequestParam String abxCode,
        @RequestParam String orgCode,
        @RequestParam List<String> guidelines,
        @RequestParam Optional<String> organismCodeTypeOrderParam
    ) {
        String organismCodeTypeOrder = "";
        if (organismCodeTypeOrderParam.isPresent()) {
            organismCodeTypeOrder = organismCodeTypeOrderParam.get();
        }
        return this.interpretationService.getIntrinsicResistance(orgCode, abxCode, guidelines, organismCodeTypeOrder);
    }
}
