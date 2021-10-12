package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;

/**
 * Mapper for the entity {@link IntrinsicResistance} and its DTO {@link IntrinsicResistanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntrinsicResistanceMapper extends EntityMapper<IntrinsicResistanceDTO, IntrinsicResistance> {}
