package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class AntibioticDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AntibioticDTO.class);
        AntibioticDTO antibioticDTO1 = new AntibioticDTO();
        antibioticDTO1.setId(1L);
        AntibioticDTO antibioticDTO2 = new AntibioticDTO();
        assertThat(antibioticDTO1).isNotEqualTo(antibioticDTO2);
        antibioticDTO2.setId(antibioticDTO1.getId());
        assertThat(antibioticDTO1).isEqualTo(antibioticDTO2);
        antibioticDTO2.setId(2L);
        assertThat(antibioticDTO1).isNotEqualTo(antibioticDTO2);
        antibioticDTO1.setId(null);
        assertThat(antibioticDTO1).isNotEqualTo(antibioticDTO2);
    }
}
