package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class IntrinsicResistanceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntrinsicResistance.class);
        IntrinsicResistance intrinsicResistance1 = new IntrinsicResistance();
        intrinsicResistance1.setId(1L);
        IntrinsicResistance intrinsicResistance2 = new IntrinsicResistance();
        intrinsicResistance2.setId(intrinsicResistance1.getId());
        assertThat(intrinsicResistance1).isEqualTo(intrinsicResistance2);
        intrinsicResistance2.setId(2L);
        assertThat(intrinsicResistance1).isNotEqualTo(intrinsicResistance2);
        intrinsicResistance1.setId(null);
        assertThat(intrinsicResistance1).isNotEqualTo(intrinsicResistance2);
    }
}
