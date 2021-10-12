package org.path.amr.services.repository;

import org.path.amr.services.domain.IntrinsicResistance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IntrinsicResistance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntrinsicResistanceRepository
    extends JpaRepository<IntrinsicResistance, Long>, JpaSpecificationExecutor<IntrinsicResistance> {}
