package org.path.amr.services.repository;

import org.path.amr.services.domain.WhonetResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WhonetResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WhonetResourceRepository extends JpaRepository<WhonetResource, Long>, JpaSpecificationExecutor<WhonetResource> {}
