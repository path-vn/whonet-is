package org.path.amr.services.service.impl;

import java.util.concurrent.Callable;
import org.path.amr.services.service.InterpretationService;
import org.path.amr.services.service.dto.IsolateDTO;

public class InterpretationWorker implements Callable<IsolateDTO> {

    private InterpretationService interpretationService;
    private IsolateDTO isolateDTO;

    public InterpretationWorker(InterpretationService interpretationService, IsolateDTO isolateDTO) {
        this.interpretationService = interpretationService;
        this.isolateDTO = isolateDTO;
    }

    @Override
    public IsolateDTO call() throws Exception {
        interpretationService.execute(isolateDTO);
        return isolateDTO;
    }
}
