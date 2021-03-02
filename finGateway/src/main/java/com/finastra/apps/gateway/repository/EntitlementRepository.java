package com.finastra.apps.gateway.repository;

import com.finastra.apps.gateway.domain.Entitlement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Entitlement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntitlementRepository extends JpaRepository<Entitlement, Long> {
}
