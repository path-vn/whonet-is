package org.path.amr.services.repository;

import org.path.amr.services.domain.Execute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Execute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExecuteRepository extends JpaRepository<Execute, Long>, JpaSpecificationExecutor<Execute> {}
