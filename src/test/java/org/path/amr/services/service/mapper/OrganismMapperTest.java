package org.path.amr.services.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganismMapperTest {

    private OrganismMapper organismMapper;

    @BeforeEach
    public void setUp() {
        organismMapper = new OrganismMapperImpl();
    }
}
