package org.path.amr.services.repository;

import java.util.Optional;
import org.path.amr.services.domain.Organism;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Organism entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganismRepository extends JpaRepository<Organism, Long>, JpaSpecificationExecutor<Organism> {
    Optional<Organism> findFirstByWhonetOrgCode(String code);
}
