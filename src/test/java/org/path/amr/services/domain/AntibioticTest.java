package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class AntibioticTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Antibiotic.class);
        Antibiotic antibiotic1 = new Antibiotic();
        antibiotic1.setId(1L);
        Antibiotic antibiotic2 = new Antibiotic();
        antibiotic2.setId(antibiotic1.getId());
        assertThat(antibiotic1).isEqualTo(antibiotic2);
        antibiotic2.setId(2L);
        assertThat(antibiotic1).isNotEqualTo(antibiotic2);
        antibiotic1.setId(null);
        assertThat(antibiotic1).isNotEqualTo(antibiotic2);
    }
}
