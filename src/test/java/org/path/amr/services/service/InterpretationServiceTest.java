package org.path.amr.services.service;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.amr.services.AmrInterpreationApp;
import org.path.amr.services.service.dto.OrganismBreakPointDTO;
import org.path.amr.services.service.dto.TestResultDTO;
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
        List<OrganismBreakPointDTO> organismBreakPointDTOList = interpretationService.getBreakpoints("aba", "SAM_ND10", "Human");
        organismBreakPointDTOList.forEach(f -> System.out.println("KKK => " + f.getBreakPointID()));
        assert organismBreakPointDTOList.size() > 0;
    }

    @Test
    void execute() {
        TestResultDTO result = interpretationService.execute("6", "aba", "SAM_ND10", "", "Human");
        System.out.println("SIZE: " + result.getResult().size());
        result
            .getResult()
            .forEach(
                r -> {
                    System.out.println("Result: " + r.getResult() + " , breaking: " + r.getBreaking());
                }
            );
    }

    @Test
    void execute2() {
        TestResultDTO result = interpretationService.execute("0.25", "sau", "CLI_NM", "", "Human");
        System.out.println("SIZE: " + result.getResult().size());
        result
            .getResult()
            .forEach(
                r -> {
                    System.out.println("Result: " + r.getResult() + " , breaking: " + r.getBreaking());
                }
            );
    }
}
