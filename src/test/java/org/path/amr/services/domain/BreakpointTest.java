package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class BreakpointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Breakpoint.class);
        Breakpoint breakpoint1 = new Breakpoint();
        breakpoint1.setId(1L);
        Breakpoint breakpoint2 = new Breakpoint();
        breakpoint2.setId(breakpoint1.getId());
        assertThat(breakpoint1).isEqualTo(breakpoint2);
        breakpoint2.setId(2L);
        assertThat(breakpoint1).isNotEqualTo(breakpoint2);
        breakpoint1.setId(null);
        assertThat(breakpoint1).isNotEqualTo(breakpoint2);
    }
}
