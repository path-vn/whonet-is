package org.path.amr.services.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntrinsicResistanceMapperTest {

    private IntrinsicResistanceMapper intrinsicResistanceMapper;

    @BeforeEach
    public void setUp() {
        intrinsicResistanceMapper = new IntrinsicResistanceMapperImpl();
    }
}
