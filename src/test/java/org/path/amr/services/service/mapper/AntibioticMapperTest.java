package org.path.amr.services.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AntibioticMapperTest {

    private AntibioticMapper antibioticMapper;

    @BeforeEach
    public void setUp() {
        antibioticMapper = new AntibioticMapperImpl();
    }
}
