package org.path.amr.services.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class OrganismDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganismDTO.class);
        OrganismDTO organismDTO1 = new OrganismDTO();
        organismDTO1.setId(1L);
        OrganismDTO organismDTO2 = new OrganismDTO();
        assertThat(organismDTO1).isNotEqualTo(organismDTO2);
        organismDTO2.setId(organismDTO1.getId());
        assertThat(organismDTO1).isEqualTo(organismDTO2);
        organismDTO2.setId(2L);
        assertThat(organismDTO1).isNotEqualTo(organismDTO2);
        organismDTO1.setId(null);
        assertThat(organismDTO1).isNotEqualTo(organismDTO2);
    }
}
