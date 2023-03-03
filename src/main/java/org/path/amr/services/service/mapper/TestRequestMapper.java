package org.path.amr.services.service.mapper;

import org.mapstruct.Mapper;
import org.path.amr.services.service.dto.TestDTO;
import org.path.amr.services.service.dto.TestRequestDTO;

@Mapper(componentModel = "spring", uses = {})
public interface TestRequestMapper extends EntityMapper<TestRequestDTO, TestDTO> {}
