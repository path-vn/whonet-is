package org.path.amr.services.repository;

import org.path.amr.services.domain.ExpertInterpretationRules;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data SQL repository for the ExpertInterpretationRules entity.
 */
@SuppressWarnings("unused")
@Repository
@Transactional
public interface ExpertInterpretationRulesRepository
    extends JpaRepository<ExpertInterpretationRules, Long>, JpaSpecificationExecutor<ExpertInterpretationRules> {
    @Query("delete from ExpertInterpretationRules e where e.ruleCode not like '%PATH%'")
    @Modifying
    void deleteNotPATH();
}
