package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class ExpertInterpretationRulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpertInterpretationRules.class);
        ExpertInterpretationRules expertInterpretationRules1 = new ExpertInterpretationRules();
        expertInterpretationRules1.setId(1L);
        ExpertInterpretationRules expertInterpretationRules2 = new ExpertInterpretationRules();
        expertInterpretationRules2.setId(expertInterpretationRules1.getId());
        assertThat(expertInterpretationRules1).isEqualTo(expertInterpretationRules2);
        expertInterpretationRules2.setId(2L);
        assertThat(expertInterpretationRules1).isNotEqualTo(expertInterpretationRules2);
        expertInterpretationRules1.setId(null);
        assertThat(expertInterpretationRules1).isNotEqualTo(expertInterpretationRules2);
    }
}
