package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class ExecuteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Execute.class);
        Execute execute1 = new Execute();
        execute1.setId(1L);
        Execute execute2 = new Execute();
        execute2.setId(execute1.getId());
        assertThat(execute1).isEqualTo(execute2);
        execute2.setId(2L);
        assertThat(execute1).isNotEqualTo(execute2);
        execute1.setId(null);
        assertThat(execute1).isNotEqualTo(execute2);
    }
}
