package org.path.amr.services.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.path.amr.services.web.rest.TestUtil;

class OrganismTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organism.class);
        Organism organism1 = new Organism();
        organism1.setId(1L);
        Organism organism2 = new Organism();
        organism2.setId(organism1.getId());
        assertThat(organism1).isEqualTo(organism2);
        organism2.setId(2L);
        assertThat(organism1).isNotEqualTo(organism2);
        organism1.setId(null);
        assertThat(organism1).isNotEqualTo(organism2);
    }
}
