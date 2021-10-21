package org.path.amr.services.repository;

import java.util.List;
import java.util.Optional;
import org.path.amr.services.domain.Antibiotic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Antibiotic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntibioticRepository extends JpaRepository<Antibiotic, Long>, JpaSpecificationExecutor<Antibiotic> {
    List<Antibiotic> findAllByProfClass(String profClass);
    Optional<Antibiotic> findFirstByWhonetAbxCode(String abxCode);
}
