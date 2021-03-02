package com.finastra.apps.finhealth.repository;

import com.finastra.apps.finhealth.domain.Entitlement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Entitlement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, Long> {
}
