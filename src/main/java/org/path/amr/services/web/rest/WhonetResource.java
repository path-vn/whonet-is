package org.path.amr.services.web.rest;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
import org.path.amr.services.service.dto.TestResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/whonet/interpretation")
    public TestResultDTO getInterpretation(
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
