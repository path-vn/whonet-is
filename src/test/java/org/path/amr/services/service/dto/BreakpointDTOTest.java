package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class BreakpointDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BreakpointDTO.class);
        BreakpointDTO breakpointDTO1 = new BreakpointDTO();
        breakpointDTO1.setId(1L);
        BreakpointDTO breakpointDTO2 = new BreakpointDTO();
        assertThat(breakpointDTO1).isNotEqualTo(breakpointDTO2);
        breakpointDTO2.setId(breakpointDTO1.getId());
        assertThat(breakpointDTO1).isEqualTo(breakpointDTO2);
        breakpointDTO2.setId(2L);
        assertThat(breakpointDTO1).isNotEqualTo(breakpointDTO2);
        breakpointDTO1.setId(null);
        assertThat(breakpointDTO1).isNotEqualTo(breakpointDTO2);
    }
}
