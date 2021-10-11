package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.BreakpointDTO;

/**
 * Mapper for the entity {@link Breakpoint} and its DTO {@link BreakpointDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BreakpointMapper extends EntityMapper<BreakpointDTO, Breakpoint> {}
