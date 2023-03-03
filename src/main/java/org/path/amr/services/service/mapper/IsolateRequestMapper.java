package org.path.amr.services.service.mapper;

import org.mapstruct.Mapper;
import org.path.amr.services.service.dto.IsolateDTO;
import org.path.amr.services.service.dto.IsolateRequestDTO;

@Mapper(componentModel = "spring", uses = {})
public interface IsolateRequestMapper extends EntityMapper<IsolateRequestDTO, IsolateDTO> {}
