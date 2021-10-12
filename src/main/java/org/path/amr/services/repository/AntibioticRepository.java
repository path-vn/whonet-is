package org.path.amr.services.repository;

import org.path.amr.services.domain.Antibiotic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Antibiotic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntibioticRepository extends JpaRepository<Antibiotic, Long>, JpaSpecificationExecutor<Antibiotic> {}
