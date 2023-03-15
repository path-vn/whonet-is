package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.WhonetResourceDTO;

/**
 * Mapper for the entity {@link WhonetResource} and its DTO {@link WhonetResourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WhonetResourceMapper extends EntityMapper<WhonetResourceDTO, WhonetResource> {}
