package org.path.amr.services.web.rest;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.dto.IsolateDTO;
import org.path.amr.services.service.dto.OrganismIntrinsicResistanceAntibioticDTO;
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
    @PostMapping("/whonet/interpretation")
    public IsolateDTO getInterpretation(@RequestBody IsolateDTO isolateDTO) {
        this.interpretationService.execute(isolateDTO);
        return isolateDTO;
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
