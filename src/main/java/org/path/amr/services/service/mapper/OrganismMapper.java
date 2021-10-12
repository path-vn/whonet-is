package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.OrganismDTO;

/**
 * Mapper for the entity {@link Organism} and its DTO {@link OrganismDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganismMapper extends EntityMapper<OrganismDTO, Organism> {}
