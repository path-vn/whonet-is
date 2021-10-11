package org.path.amr.services.repository;

import org.path.amr.services.domain.Breakpoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Breakpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreakpointRepository extends JpaRepository<Breakpoint, Long>, JpaSpecificationExecutor<Breakpoint> {}
