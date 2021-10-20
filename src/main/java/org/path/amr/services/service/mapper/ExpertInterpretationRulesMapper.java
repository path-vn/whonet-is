package org.path.amr.services.service.mapper;

import org.mapstruct.*;
import org.path.amr.services.domain.*;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;

/**
 * Mapper for the entity {@link ExpertInterpretationRules} and its DTO {@link ExpertInterpretationRulesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExpertInterpretationRulesMapper extends EntityMapper<ExpertInterpretationRulesDTO, ExpertInterpretationRules> {}
