package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class WhonetResourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WhonetResource.class);
        WhonetResource whonetResource1 = new WhonetResource();
        whonetResource1.setId(1L);
        WhonetResource whonetResource2 = new WhonetResource();
        whonetResource2.setId(whonetResource1.getId());
        assertThat(whonetResource1).isEqualTo(whonetResource2);
        whonetResource2.setId(2L);
        assertThat(whonetResource1).isNotEqualTo(whonetResource2);
        whonetResource1.setId(null);
        assertThat(whonetResource1).isNotEqualTo(whonetResource2);
    }
}
