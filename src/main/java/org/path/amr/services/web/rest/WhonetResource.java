package org.path.amr.services.web.rest;

import java.util.Optional;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.dto.TestResultDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WhonetResource {

    InterpretationService interpretationService;

    public WhonetResource(InterpretationService interpretationService) {
        this.interpretationService = interpretationService;
    }

    /**
     * {@code GET /antibiotics/getAntibiotics} : get all antibiotics.
     */
    @GetMapping("/whonet/antibiotics")
    public TestResultDTO getAntibiotics(
        @RequestParam String test,
        @RequestParam String orgCode,
        @RequestParam String result,
        @RequestParam Optional<String> breakpointType
    ) {
        String _breakpointType = "Human";
        if (breakpointType.isPresent()) {
            _breakpointType = breakpointType.get();
        }
        return this.interpretationService.execute(result, orgCode, test, "", _breakpointType);
    }
}
