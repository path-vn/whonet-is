package org.path.amr.services.service;

import java.util.List;
import org.path.amr.services.repository.CustomRepository;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InterpretationService {

    CustomRepository customRepository;

    public InterpretationService(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    public List<OrganismBreakPointDTO> getBreakpoints() {
        return customRepository.getBreakPoints();
    }
}
