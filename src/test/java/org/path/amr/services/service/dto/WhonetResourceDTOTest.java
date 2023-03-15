package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class WhonetResourceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhonetResourceDTO.class);
        WhonetResourceDTO whonetResourceDTO1 = new WhonetResourceDTO();
        whonetResourceDTO1.setId(1L);
        WhonetResourceDTO whonetResourceDTO2 = new WhonetResourceDTO();
        assertThat(whonetResourceDTO1).isNotEqualTo(whonetResourceDTO2);
        whonetResourceDTO2.setId(whonetResourceDTO1.getId());
        assertThat(whonetResourceDTO1).isEqualTo(whonetResourceDTO2);
        whonetResourceDTO2.setId(2L);
        assertThat(whonetResourceDTO1).isNotEqualTo(whonetResourceDTO2);
        whonetResourceDTO1.setId(null);
        assertThat(whonetResourceDTO1).isNotEqualTo(whonetResourceDTO2);
    }
}
