package org.path.amr.services.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WhonetResourceMapperTest {

    private WhonetResourceMapper whonetResourceMapper;

    @BeforeEach
    public void setUp() {
        whonetResourceMapper = new WhonetResourceMapperImpl();
    }
}
