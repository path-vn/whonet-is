package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.ExecuteDTO;

/**
 * Mapper for the entity {@link Execute} and its DTO {@link ExecuteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExecuteMapper extends EntityMapper<ExecuteDTO, Execute> {}
