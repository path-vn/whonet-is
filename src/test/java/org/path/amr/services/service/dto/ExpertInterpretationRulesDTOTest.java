package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class ExpertInterpretationRulesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpertInterpretationRulesDTO.class);
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO1 = new ExpertInterpretationRulesDTO();
        expertInterpretationRulesDTO1.setId(1L);
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO2 = new ExpertInterpretationRulesDTO();
        assertThat(expertInterpretationRulesDTO1).isNotEqualTo(expertInterpretationRulesDTO2);
        expertInterpretationRulesDTO2.setId(expertInterpretationRulesDTO1.getId());
        assertThat(expertInterpretationRulesDTO1).isEqualTo(expertInterpretationRulesDTO2);
        expertInterpretationRulesDTO2.setId(2L);
        assertThat(expertInterpretationRulesDTO1).isNotEqualTo(expertInterpretationRulesDTO2);
        expertInterpretationRulesDTO1.setId(null);
        assertThat(expertInterpretationRulesDTO1).isNotEqualTo(expertInterpretationRulesDTO2);
    }
}
