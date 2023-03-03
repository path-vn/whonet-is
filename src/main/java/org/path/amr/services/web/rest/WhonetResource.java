package org.path.amr.services.web.rest;

import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.path.amr.services.config.WhonetConfiguration;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.MailService;
import org.path.amr.services.service.dto.IsolateDTO;
import org.path.amr.services.service.dto.IsolateRequestDTO;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
import org.path.amr.services.service.mapper.IsolateRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Api(value = "Whonet Resource", tags = { "1. Phiên giải/ Interpretation" })
public class WhonetResource {

    InterpretationService interpretationService;
    WhonetConfiguration whonetConfiguration;
    private final IsolateRequestMapper isolateRequestMapper;
    MailService mailService;
    int thread = 10;
    private final Logger log = LoggerFactory.getLogger(WhonetResource.class);

    public WhonetResource(
        InterpretationService interpretationService,
        WhonetConfiguration whonetConfiguration,
        IsolateRequestMapper isolateRequestMapper,
        MailService mailService
    ) {
        this.interpretationService = interpretationService;
        this.whonetConfiguration = whonetConfiguration;
        this.isolateRequestMapper = isolateRequestMapper;
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
     * {@code POST /whonet/interpretation} : Thực hiện phiên giải.
     */
    @PostMapping("/whonet/interpretation")
    @ApiOperation(value = "Thực hiện phiên giải", notes = "Thực hiện phiên giải cho 1 cặp vi khuẩn - kháng sinh")
    public IsolateDTO getInterpretation(@RequestBody IsolateRequestDTO request) {
        IsolateDTO isolateDTO = isolateRequestMapper.toEntity(request);
        this.interpretationService.execute(isolateDTO);
        return isolateDTO;
    }

    /**
     * {@code POST /whonet/interpretation-file} : Thực hiện phiên giải / input file.
     */
    @PostMapping("/whonet/interpretation-file")
    @ApiOperation(value = "Thực hiện phiên giải", notes = "Thực hiện phiên giải / input file")
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
     * {@code POST /whonet/interpretation-bulk} : Thực hiện phiên giải nhiều cặp vi khuẩn kháng sinh .
     */
    @ApiOperation(value = "Thực hiện phiên giải", notes = "Thực hiện phiên giải nhiều cặp vi khuẩn kháng sinh")
    @PostMapping("/whonet/interpretation-bulk")
    public List<IsolateDTO> getInterpretations(@RequestBody List<IsolateRequestDTO> requests)
        throws ExecutionException, InterruptedException {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());

        log.info("Start getInterpretations " + timestamp2.toString() + " thread:" + this.thread);
        List<IsolateDTO> isolateDTO = requests.stream().map(isolateRequestMapper::toEntity).collect(Collectors.toList());
        List<IsolateDTO> result = interpretationService.execIsolateDTOS(isolateDTO, this.thread);
        log.info("End getInterpretations" + timestamp2.toString());
        return result;
    }

    /**
     * {@code GET /whonet/intrinsic_resistance} : Lấy thông tin kháng tự nhiên theo orgCode.
     */
    @GetMapping("/whonet/intrinsic_resistance")
    @ApiOperation(value = "Lấy thông tin kháng tự nhiên theo orgCode", notes = "Lấy thông tin kháng tự nhiên theo orgCode")
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
