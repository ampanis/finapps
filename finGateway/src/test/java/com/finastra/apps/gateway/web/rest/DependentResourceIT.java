package com.finastra.apps.gateway.web.rest;

import com.finastra.apps.gateway.FinGatewayApp;
import com.finastra.apps.gateway.domain.Dependent;
import com.finastra.apps.gateway.repository.DependentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.finastra.apps.gateway.domain.enumeration.RelationshipType;
/**
 * Integration tests for the {@link DependentResource} REST controller.
 */
@SpringBootTest(classes = FinGatewayApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DependentResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final RelationshipType DEFAULT_RELATIONSHIPX = RelationshipType.CHILD;
    private static final RelationshipType UPDATED_RELATIONSHIPX = RelationshipType.GRAND_PARENT;

    private static final String DEFAULT_RELATIONSHIP_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_OTHERS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependentMockMvc;

    private Dependent dependent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependent createEntity(EntityManager em) {
        Dependent dependent = new Dependent()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .relationshipx(DEFAULT_RELATIONSHIPX)
            .relationshipOthers(DEFAULT_RELATIONSHIP_OTHERS)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return dependent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependent createUpdatedEntity(EntityManager em) {
        Dependent dependent = new Dependent()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .relationshipx(UPDATED_RELATIONSHIPX)
            .relationshipOthers(UPDATED_RELATIONSHIP_OTHERS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return dependent;
    }

    @BeforeEach
    public void initTest() {
        dependent = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependent() throws Exception {
        int databaseSizeBeforeCreate = dependentRepository.findAll().size();
        // Create the Dependent
        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isCreated());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeCreate + 1);
        Dependent testDependent = dependentList.get(dependentList.size() - 1);
        assertThat(testDependent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDependent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDependent.getRelationshipx()).isEqualTo(DEFAULT_RELATIONSHIPX);
        assertThat(testDependent.getRelationshipOthers()).isEqualTo(DEFAULT_RELATIONSHIP_OTHERS);
        assertThat(testDependent.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDependent.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDependentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependentRepository.findAll().size();

        // Create the Dependent with an existing ID
        dependent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isBadRequest());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependentRepository.findAll().size();
        // set the field null
        dependent.setFirstName(null);

        // Create the Dependent, which fails.


        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isBadRequest());

        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependentRepository.findAll().size();
        // set the field null
        dependent.setLastName(null);

        // Create the Dependent, which fails.


        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isBadRequest());

        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationshipxIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependentRepository.findAll().size();
        // set the field null
        dependent.setRelationshipx(null);

        // Create the Dependent, which fails.


        restDependentMockMvc.perform(post("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isBadRequest());

        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDependents() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        // Get all the dependentList
        restDependentMockMvc.perform(get("/api/dependents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependent.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].relationshipx").value(hasItem(DEFAULT_RELATIONSHIPX.toString())))
            .andExpect(jsonPath("$.[*].relationshipOthers").value(hasItem(DEFAULT_RELATIONSHIP_OTHERS)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        // Get the dependent
        restDependentMockMvc.perform(get("/api/dependents/{id}", dependent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependent.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.relationshipx").value(DEFAULT_RELATIONSHIPX.toString()))
            .andExpect(jsonPath("$.relationshipOthers").value(DEFAULT_RELATIONSHIP_OTHERS))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingDependent() throws Exception {
        // Get the dependent
        restDependentMockMvc.perform(get("/api/dependents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        int databaseSizeBeforeUpdate = dependentRepository.findAll().size();

        // Update the dependent
        Dependent updatedDependent = dependentRepository.findById(dependent.getId()).get();
        // Disconnect from session so that the updates on updatedDependent are not directly saved in db
        em.detach(updatedDependent);
        updatedDependent
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .relationshipx(UPDATED_RELATIONSHIPX)
            .relationshipOthers(UPDATED_RELATIONSHIP_OTHERS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDependentMockMvc.perform(put("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDependent)))
            .andExpect(status().isOk());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeUpdate);
        Dependent testDependent = dependentList.get(dependentList.size() - 1);
        assertThat(testDependent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDependent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDependent.getRelationshipx()).isEqualTo(UPDATED_RELATIONSHIPX);
        assertThat(testDependent.getRelationshipOthers()).isEqualTo(UPDATED_RELATIONSHIP_OTHERS);
        assertThat(testDependent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDependent.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDependent() throws Exception {
        int databaseSizeBeforeUpdate = dependentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependentMockMvc.perform(put("/api/dependents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependent)))
            .andExpect(status().isBadRequest());

        // Validate the Dependent in the database
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDependent() throws Exception {
        // Initialize the database
        dependentRepository.saveAndFlush(dependent);

        int databaseSizeBeforeDelete = dependentRepository.findAll().size();

        // Delete the dependent
        restDependentMockMvc.perform(delete("/api/dependents/{id}", dependent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dependent> dependentList = dependentRepository.findAll();
        assertThat(dependentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
