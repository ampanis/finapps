package com.finastra.apps.finhealth.web.rest;

import com.finastra.apps.finhealth.domain.Entitlement;
import com.finastra.apps.finhealth.repository.EntitlementRepository;
import com.finastra.apps.finhealth.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.finastra.apps.finhealth.domain.Entitlement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntitlementResource {

    private final Logger log = LoggerFactory.getLogger(EntitlementResource.class);

    private static final String ENTITY_NAME = "finHealthEntitlement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntitlementRepository entitlementRepository;

    public EntitlementResource(EntitlementRepository entitlementRepository) {
        this.entitlementRepository = entitlementRepository;
    }

    /**
     * {@code POST  /entitlements} : Create a new entitlement.
     *
     * @param entitlement the entitlement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entitlement, or with status {@code 400 (Bad Request)} if the entitlement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entitlements")
    public ResponseEntity<Entitlement> createEntitlement(@Valid @RequestBody Entitlement entitlement) throws URISyntaxException {
        log.debug("REST request to save Entitlement : {}", entitlement);
        if (entitlement.getId() != null) {
            throw new BadRequestAlertException("A new entitlement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entitlement result = entitlementRepository.save(entitlement);
        return ResponseEntity.created(new URI("/api/entitlements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entitlements} : Updates an existing entitlement.
     *
     * @param entitlement the entitlement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entitlement,
     * or with status {@code 400 (Bad Request)} if the entitlement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entitlement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entitlements")
    public ResponseEntity<Entitlement> updateEntitlement(@Valid @RequestBody Entitlement entitlement) throws URISyntaxException {
        log.debug("REST request to update Entitlement : {}", entitlement);
        if (entitlement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entitlement result = entitlementRepository.save(entitlement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entitlement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entitlements} : get all the entitlements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entitlements in body.
     */
    @GetMapping("/entitlements")
    public List<Entitlement> getAllEntitlements() {
        log.debug("REST request to get all Entitlements");
        return entitlementRepository.findAll();
    }

    /**
     * {@code GET  /entitlements/:id} : get the "id" entitlement.
     *
     * @param id the id of the entitlement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entitlement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entitlements/{id}")
    public ResponseEntity<Entitlement> getEntitlement(@PathVariable Long id) {
        log.debug("REST request to get Entitlement : {}", id);
        Optional<Entitlement> entitlement = entitlementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entitlement);
    }

    /**
     * {@code DELETE  /entitlements/:id} : delete the "id" entitlement.
     *
     * @param id the id of the entitlement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entitlements/{id}")
    public ResponseEntity<Void> deleteEntitlement(@PathVariable Long id) {
        log.debug("REST request to delete Entitlement : {}", id);
        entitlementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
