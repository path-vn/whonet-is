package org.path.amr.services.service.impl;

import java.util.concurrent.Callable;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.dto.IsolateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterpretationWorker implements Callable<IsolateDTO> {

    private final Logger log = LoggerFactory.getLogger(InterpretationWorker.class);
    private InterpretationService interpretationService;
    private IsolateDTO isolateDTO;

    public InterpretationWorker(InterpretationService interpretationService, IsolateDTO isolateDTO) {
        this.interpretationService = interpretationService;
        this.isolateDTO = isolateDTO;
    }

    @Override
    public IsolateDTO call() throws Exception {
        try {
            interpretationService.execute(isolateDTO);
        } catch (Exception e) {
            if (isolateDTO.getTest() != null && isolateDTO.getTest().size() > 0) {
                for (int i = 0; i < isolateDTO.getTest().size(); i++) {
                    log.error(
                        "{} {} {}",
                        isolateDTO.getOrgCode(),
                        isolateDTO.getTest().get(i).getWhonet5Code(),
                        isolateDTO.getTest().get(i).getRawValue()
                    );
                }
            }
            e.printStackTrace();
        }
        return isolateDTO;
    }
}
