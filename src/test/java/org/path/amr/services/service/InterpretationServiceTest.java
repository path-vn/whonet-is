package org.path.amr.services.service;

import static org.path.amr.services.service.InterpretationService.GENUS_CODE;
import static org.path.amr.services.service.InterpretationService.PATTERN_1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.amr.services.AmrInterpreationApp;
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.service.dto.*;
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
        List<OrganismBreakPointDTO> organismBreakPointDTOList = interpretationService.getBreakpoints(
            "spn",
            "MFX_NM",
            "Human",
            "",
            "",
            null,
            null,
            null
        );
        organismBreakPointDTOList.forEach(f -> System.out.println("KKK => " + f.getBreakPointID()));
        assert organismBreakPointDTOList.size() > 0;
    }

    @Test
    void execute() {
        IsolateDTO isolateDTO = new IsolateDTO();
        isolateDTO.setOrgCode("pma");

        TestDTO test = new TestDTO();
        test.setRawValue(".50.");
        test.setWhonet5Code("TCC_NM");

        //        TestDTO test2 = new TestDTO();
        //        test2.setRawValue("28");
        //        test2.setWhonet5Code("FOX_ND30");
        //        isolateDTO.addTest(test2);

        isolateDTO.addTest(test);
        interpretationService.execute(isolateDTO);
        System.out.println("SIZE: " + isolateDTO.getTest().size());
        System.out.println("SIZE: " + isolateDTO.getTest().get(0).getResult().size());
        assert isolateDTO.getTest().get(0).getResult().size() == 1;
        assert isolateDTO.getTest().get(0).getResult().get(0).getResult() == "R";
    }

    @Test
    void executeSmaTZPNM32() {
        IsolateDTO isolateDTO = new IsolateDTO();
        isolateDTO.setOrgCode("sma");
        isolateDTO.setBreakpointType("Human");

        TestDTO test = new TestDTO();
        test.setRawValue(">32");
        test.setWhonet5Code("TZP_NM");

        isolateDTO.addTest(test);
        interpretationService.execute(isolateDTO);
        System.out.println("SIZE: " + isolateDTO.getTest().size());
        System.out.println("SIZE: " + isolateDTO.getTest().get(0).getResult().size());
        assert isolateDTO.getTest().get(0).getResult().size() == 1;
        assert isolateDTO.getTest().get(0).getResult().get(0).getResult() == "R";
    }

    @Test
    void execute2() {
        IsolateDTO isolateDTO = new IsolateDTO();
        isolateDTO.setOrgCode("sau");
        isolateDTO.setBreakpointType("Human");

        TestDTO test = new TestDTO();
        test.setRawValue("0.25");
        test.setWhonet5Code("CLI_NM");
        isolateDTO.addTest(test);

        System.out.println("SIZE: " + isolateDTO.getTest().size());
        interpretationService.execute(isolateDTO);
        isolateDTO
            .getTest()
            .forEach(
                r -> {
                    r
                        .getResult()
                        .forEach(
                            ri -> {
                                System.out.println("Result: " + ri.getResult() + " , breaking: " + ri.getBreaking());
                            }
                        );
                }
            );

        assert isolateDTO.getTest().get(0).getResult().size() == 1;
        assert isolateDTO.getTest().get(0).getResult().get(0).getResult() == "S";
    }

    @Test
    void parseRules() {
        //        'ESBL=+ OR X_BLEE=p'
        //        'CEPH3=R'
        //        'BETA_LACT=- AND AMP=NS'
        String testRule = "MECA_PCR=+ OR MRSA=+ OR OXA_SCREEN=+ OR MRSA_SCRN=+ OR PBP2A_AGGL=+";
        //        'OXA=NS OR FOX=NS'
        //        'INDUC_CLI=+ OR MLS_DTEST=+ OR X_MLS=p OR X_MLS_IND=p'
        //        'INDUC_CLI=+ OR MLS_DTEST=+ OR X_MLS=p OR X_MLS_IND=p'

        List<RuleDTO> ruleDTOList = interpretationService.parseRules(testRule);
        assert ruleDTOList.size() == 5;
        System.out.println(ruleDTOList.get(3).getName() + ":" + ruleDTOList.get(3).getValue());
        assert ruleDTOList.get(3).getName().equals("MRSA_SCRN");

        System.out.println(ruleDTOList.get(3).getRaw() + ":" + ruleDTOList.get(3).getValue());
    }

    @Test
    void parseRules2() {
        //        'ESBL=+ OR X_BLEE=p'
        //        'CEPH3=R'
        String testRule = "BETA_LACT=- AND AMP=NS";
        //        'OXA=NS OR FOX=NS'
        //        'INDUC_CLI=+ OR MLS_DTEST=+ OR X_MLS=p OR X_MLS_IND=p'
        //        'INDUC_CLI=+ OR MLS_DTEST=+ OR X_MLS=p OR X_MLS_IND=p'

        List<RuleDTO> ruleDTOList = interpretationService.parseRules(testRule);
        assert ruleDTOList.size() == 2;
        System.out.println(ruleDTOList.get(1).getName() + ":" + ruleDTOList.get(1).getValue());
        assert ruleDTOList.get(1).getName().equals("AMP");
        assert ruleDTOList.get(1).getValue().equals("NS");

        System.out.println(ruleDTOList.get(1).getRaw() + ":" + ruleDTOList.get(1).getValue());
    }

    @Test
    void processRuleCase1() {
        RuleDTO ruleDTO = new RuleDTO("OXA=NS", "OXA", "NS");
        IsolateDTO isolateDTO = new IsolateDTO();
        AntibioticDTO antibioticDTO = new AntibioticDTO();
        antibioticDTO.setWhonetAbxCode("OXA");

        TestDTO test = new TestDTO();
        test.setAntibiotic(antibioticDTO);

        Map<String, String> testFields = new HashMap<>();
        isolateDTO.setDataFields(testFields);
        test.addResult("S");
        isolateDTO.addTest(test);
        interpretationService.processRule(ruleDTO, isolateDTO);
        assert !ruleDTO.getResult();

        test.addResult("NS");
        isolateDTO.addTest(test);
        interpretationService.processRule(ruleDTO, isolateDTO);
        assert ruleDTO.getResult();
    }

    @Test
    void processRuleCase2() {
        RuleDTO ruleDTO = new RuleDTO("MRSA=+", "RMSA", "+");
        IsolateDTO isolateDTO = new IsolateDTO();
        Map<String, String> testFields = new HashMap<>();
        testFields.put("RMSA", "-");
        isolateDTO.setDataFields(testFields);
        interpretationService.processRule(ruleDTO, isolateDTO);
        assert !ruleDTO.getResult();

        testFields.put("RMSA", "+");
        isolateDTO.setDataFields(testFields);
        interpretationService.processRule(ruleDTO, isolateDTO);
        assert ruleDTO.getResult();
    }

    @Test
    void applyExpertRule() {
        IsolateDTO isolateDTO = new IsolateDTO();
        isolateDTO.setOrgCode("sau");
        isolateDTO.setBreakpointType("Human");

        TestDTO test = new TestDTO();
        test.setRawValue("0.25");
        test.setWhonet5Code("CLI_NM");
        isolateDTO.addTest(test);

        System.out.println("SIZE: " + isolateDTO.getTest().size());
        interpretationService.execute(isolateDTO);

        ExpertInterpretationRules expertRule = new ExpertInterpretationRules();
        expertRule.setRuleCriteria("MECA_PCR=+ OR MRSA=+ OR OXA_SCREEN=+ OR MRSA_SCRN=+ OR PBP2A_AGGL=+");
        expertRule.setOrganismCodeType(GENUS_CODE);
        expertRule.setOrganismCode("sau");

        assert interpretationService.notMatchRuleCriteria(expertRule, isolateDTO);
    }

    @Test
    void testECOvsFOS() throws JsonProcessingException {
        String data =
            "{\n" +
            "    \"requestID\": \"5988568\",\n" +
            "    \"method\": null,\n" +
            "    \"orgCode\": \"eco\",\n" +
            "    \"breakpointType\": null,\n" +
            "    \"specType\": null,\n" +
            "    \"guidelines\": null,\n" +
            "    \"year\": null,\n" +
            "    \"organism\": null,\n" +
            "    \"test\": [\n" +
            "      {\n" +
            "        \"value\": null,\n" +
            "        \"rawValue\": \"26\",\n" +
            "        \"oper\": 0,\n" +
            "        \"antibiotic\": null,\n" +
            "        \"whonet5Code\": \"FOS_ND200\",\n" +
            "        \"expertRuleCode\": null,\n" +
            "        \"testID\": \"21355003\",\n" +
            "        \"result\": null,\n" +
            "        \"intrinsicResistance\": null\n" +
            "      }\n" +
            "    ],\n" +
            "    \"dataFields\": {\n" +
            "      \"INDUC_CLI\": \"\",\n" +
            "      \"VRE\": \"\",\n" +
            "      \"X_MLS\": null,\n" +
            "      \"MECA_PCR\": null,\n" +
            "      \"PBP2A_AGGL\": null,\n" +
            "      \"X_MLS_IND\": null,\n" +
            "      \"X_BLEE\": null,\n" +
            "      \"OXA_SCREEN\": null,\n" +
            "      \"MRSA\": \"\",\n" +
            "      \"ESBL\": \"\",\n" +
            "      \"MLS_DTEST\": null,\n" +
            "      \"BETA_LACT\": \"\",\n" +
            "      \"MRSA_SCRN\": null\n" +
            "    },\n" +
            "    \"organismCodeTypeOrder\": null\n" +
            "  }";

        ObjectMapper objectMapper = new ObjectMapper();
        IsolateDTO isolateDTO = objectMapper.readValue(data, IsolateDTO.class);
        assert isolateDTO != null;
        interpretationService.execute(isolateDTO);
        assert isolateDTO.getTest().size() > 0;
    }

    @Test
    void testConvertNumber() {
        String testValue = ".19".replaceAll(PATTERN_1, "");
        assert Double.parseDouble(testValue) == 0.19;
        testValue = ",19".replaceAll(PATTERN_1, "").replaceAll(",", ".");
        assert Double.parseDouble(testValue) == 0.19;
    }
}
