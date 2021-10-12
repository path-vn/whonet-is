package org.path.amr.services.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.amr.services.AmrInterpreationApp;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = AmrInterpreationApp.class)
@ActiveProfiles("testcontainers")
class InterpretationServiceTest {

    @Autowired
    InterpretationService interpretationService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    void getBreakpoints() {
        List<OrganismBreakPointDTO> organismBreakPointDTOList = interpretationService.getBreakpoints();
        organismBreakPointDTOList.forEach(f -> System.out.println("KKK => " + f.getBreakPointID()));
        assert organismBreakPointDTOList.size() > 0;
    }
}
