package com.finastra.apps.gateway.web.rest;

import com.finastra.apps.gateway.domain.Dependent;
import com.finastra.apps.gateway.repository.DependentRepository;
import com.finastra.apps.gateway.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.finastra.apps.gateway.domain.Dependent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DependentResource {

    private final Logger log = LoggerFactory.getLogger(DependentResource.class);

    private static final String ENTITY_NAME = "dependent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependentRepository dependentRepository;

    public DependentResource(DependentRepository dependentRepository) {
        this.dependentRepository = dependentRepository;
    }

    /**
     * {@code POST  /dependents} : Create a new dependent.
     *
     * @param dependent the dependent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependent, or with status {@code 400 (Bad Request)} if the dependent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dependents")
    public ResponseEntity<Dependent> createDependent(@Valid @RequestBody Dependent dependent) throws URISyntaxException {
        log.debug("REST request to save Dependent : {}", dependent);
        if (dependent.getId() != null) {
            throw new BadRequestAlertException("A new dependent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependent result = dependentRepository.save(dependent);
        return ResponseEntity.created(new URI("/api/dependents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dependents} : Updates an existing dependent.
     *
     * @param dependent the dependent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependent,
     * or with status {@code 400 (Bad Request)} if the dependent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dependents")
    public ResponseEntity<Dependent> updateDependent(@Valid @RequestBody Dependent dependent) throws URISyntaxException {
        log.debug("REST request to update Dependent : {}", dependent);
        if (dependent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dependent result = dependentRepository.save(dependent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dependent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dependents} : get all the dependents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependents in body.
     */
    @GetMapping("/dependents")
    public List<Dependent> getAllDependents() {
        log.debug("REST request to get all Dependents");
        return dependentRepository.findAll();
    }

    /**
     * {@code GET  /dependents/:id} : get the "id" dependent.
     *
     * @param id the id of the dependent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dependents/{id}")
    public ResponseEntity<Dependent> getDependent(@PathVariable Long id) {
        log.debug("REST request to get Dependent : {}", id);
        Optional<Dependent> dependent = dependentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dependent);
    }

    /**
     * {@code DELETE  /dependents/:id} : delete the "id" dependent.
     *
     * @param id the id of the dependent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dependents/{id}")
    public ResponseEntity<Void> deleteDependent(@PathVariable Long id) {
        log.debug("REST request to delete Dependent : {}", id);
        dependentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
