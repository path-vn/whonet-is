package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class IntrinsicResistanceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntrinsicResistanceDTO.class);
        IntrinsicResistanceDTO intrinsicResistanceDTO1 = new IntrinsicResistanceDTO();
        intrinsicResistanceDTO1.setId(1L);
        IntrinsicResistanceDTO intrinsicResistanceDTO2 = new IntrinsicResistanceDTO();
        assertThat(intrinsicResistanceDTO1).isNotEqualTo(intrinsicResistanceDTO2);
        intrinsicResistanceDTO2.setId(intrinsicResistanceDTO1.getId());
        assertThat(intrinsicResistanceDTO1).isEqualTo(intrinsicResistanceDTO2);
        intrinsicResistanceDTO2.setId(2L);
        assertThat(intrinsicResistanceDTO1).isNotEqualTo(intrinsicResistanceDTO2);
        intrinsicResistanceDTO1.setId(null);
        assertThat(intrinsicResistanceDTO1).isNotEqualTo(intrinsicResistanceDTO2);
    }
}
