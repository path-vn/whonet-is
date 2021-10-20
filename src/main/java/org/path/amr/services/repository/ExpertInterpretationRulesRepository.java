package org.path.amr.services.repository;

import org.path.amr.services.domain.ExpertInterpretationRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExpertInterpretationRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpertInterpretationRulesRepository
    extends JpaRepository<ExpertInterpretationRules, Long>, JpaSpecificationExecutor<ExpertInterpretationRules> {}
