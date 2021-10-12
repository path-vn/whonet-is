package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.AntibioticDTO;

/**
 * Mapper for the entity {@link Antibiotic} and its DTO {@link AntibioticDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AntibioticMapper extends EntityMapper<AntibioticDTO, Antibiotic> {}
