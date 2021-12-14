package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class ExecuteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExecuteDTO.class);
        ExecuteDTO executeDTO1 = new ExecuteDTO();
        executeDTO1.setId(1L);
        ExecuteDTO executeDTO2 = new ExecuteDTO();
        assertThat(executeDTO1).isNotEqualTo(executeDTO2);
        executeDTO2.setId(executeDTO1.getId());
        assertThat(executeDTO1).isEqualTo(executeDTO2);
        executeDTO2.setId(2L);
        assertThat(executeDTO1).isNotEqualTo(executeDTO2);
        executeDTO1.setId(null);
        assertThat(executeDTO1).isNotEqualTo(executeDTO2);
    }
}
