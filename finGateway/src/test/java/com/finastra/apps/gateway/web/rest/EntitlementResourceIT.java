package com.finastra.apps.gateway.web.rest;

import com.finastra.apps.gateway.FinGatewayApp;
import com.finastra.apps.gateway.domain.Entitlement;
import com.finastra.apps.gateway.repository.EntitlementRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EntitlementResource} REST controller.
 */
@SpringBootTest(classes = FinGatewayApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EntitlementResourceIT {

    private static final String DEFAULT_WORK_DAY_NO = "AAAAAAAAAA";
    private static final String UPDATED_WORK_DAY_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MEDICAL_REIMBURSEMENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_MEDICAL_REIMBURSEMENT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_VACCINE_REIMBURSEMENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_VACCINE_REIMBURSEMENT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_VITAMINS_REIMBURSEMENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_VITAMINS_REIMBURSEMENT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OPTICAL_REIMBURSEMENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_OPTICAL_REIMBURSEMENT = new BigDecimal(1);

    @Autowired
    private EntitlementRepository entitlementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntitlementMockMvc;

    private Entitlement entitlement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entitlement createEntity(EntityManager em) {
        Entitlement entitlement = new Entitlement()
            .workDayNo(DEFAULT_WORK_DAY_NO)
            .medicalReimbursement(DEFAULT_MEDICAL_REIMBURSEMENT)
            .vaccineReimbursement(DEFAULT_VACCINE_REIMBURSEMENT)
            .vitaminsReimbursement(DEFAULT_VITAMINS_REIMBURSEMENT)
            .opticalReimbursement(DEFAULT_OPTICAL_REIMBURSEMENT);
        return entitlement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entitlement createUpdatedEntity(EntityManager em) {
        Entitlement entitlement = new Entitlement()
            .workDayNo(UPDATED_WORK_DAY_NO)
            .medicalReimbursement(UPDATED_MEDICAL_REIMBURSEMENT)
            .vaccineReimbursement(UPDATED_VACCINE_REIMBURSEMENT)
            .vitaminsReimbursement(UPDATED_VITAMINS_REIMBURSEMENT)
            .opticalReimbursement(UPDATED_OPTICAL_REIMBURSEMENT);
        return entitlement;
    }

    @BeforeEach
    public void initTest() {
        entitlement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntitlement() throws Exception {
        int databaseSizeBeforeCreate = entitlementRepository.findAll().size();
        // Create the Entitlement
        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isCreated());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeCreate + 1);
        Entitlement testEntitlement = entitlementList.get(entitlementList.size() - 1);
        assertThat(testEntitlement.getWorkDayNo()).isEqualTo(DEFAULT_WORK_DAY_NO);
        assertThat(testEntitlement.getMedicalReimbursement()).isEqualTo(DEFAULT_MEDICAL_REIMBURSEMENT);
        assertThat(testEntitlement.getVaccineReimbursement()).isEqualTo(DEFAULT_VACCINE_REIMBURSEMENT);
        assertThat(testEntitlement.getVitaminsReimbursement()).isEqualTo(DEFAULT_VITAMINS_REIMBURSEMENT);
        assertThat(testEntitlement.getOpticalReimbursement()).isEqualTo(DEFAULT_OPTICAL_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void createEntitlementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entitlementRepository.findAll().size();

        // Create the Entitlement with an existing ID
        entitlement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWorkDayNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setWorkDayNo(null);

        // Create the Entitlement, which fails.


        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMedicalReimbursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setMedicalReimbursement(null);

        // Create the Entitlement, which fails.


        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVaccineReimbursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setVaccineReimbursement(null);

        // Create the Entitlement, which fails.


        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVitaminsReimbursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setVitaminsReimbursement(null);

        // Create the Entitlement, which fails.


        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpticalReimbursementIsRequired() throws Exception {
        int databaseSizeBeforeTest = entitlementRepository.findAll().size();
        // set the field null
        entitlement.setOpticalReimbursement(null);

        // Create the Entitlement, which fails.


        restEntitlementMockMvc.perform(post("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntitlements() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        // Get all the entitlementList
        restEntitlementMockMvc.perform(get("/api/entitlements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entitlement.getId().intValue())))
            .andExpect(jsonPath("$.[*].workDayNo").value(hasItem(DEFAULT_WORK_DAY_NO)))
            .andExpect(jsonPath("$.[*].medicalReimbursement").value(hasItem(DEFAULT_MEDICAL_REIMBURSEMENT.intValue())))
            .andExpect(jsonPath("$.[*].vaccineReimbursement").value(hasItem(DEFAULT_VACCINE_REIMBURSEMENT.intValue())))
            .andExpect(jsonPath("$.[*].vitaminsReimbursement").value(hasItem(DEFAULT_VITAMINS_REIMBURSEMENT.intValue())))
            .andExpect(jsonPath("$.[*].opticalReimbursement").value(hasItem(DEFAULT_OPTICAL_REIMBURSEMENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        // Get the entitlement
        restEntitlementMockMvc.perform(get("/api/entitlements/{id}", entitlement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entitlement.getId().intValue()))
            .andExpect(jsonPath("$.workDayNo").value(DEFAULT_WORK_DAY_NO))
            .andExpect(jsonPath("$.medicalReimbursement").value(DEFAULT_MEDICAL_REIMBURSEMENT.intValue()))
            .andExpect(jsonPath("$.vaccineReimbursement").value(DEFAULT_VACCINE_REIMBURSEMENT.intValue()))
            .andExpect(jsonPath("$.vitaminsReimbursement").value(DEFAULT_VITAMINS_REIMBURSEMENT.intValue()))
            .andExpect(jsonPath("$.opticalReimbursement").value(DEFAULT_OPTICAL_REIMBURSEMENT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEntitlement() throws Exception {
        // Get the entitlement
        restEntitlementMockMvc.perform(get("/api/entitlements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        int databaseSizeBeforeUpdate = entitlementRepository.findAll().size();

        // Update the entitlement
        Entitlement updatedEntitlement = entitlementRepository.findById(entitlement.getId()).get();
        // Disconnect from session so that the updates on updatedEntitlement are not directly saved in db
        em.detach(updatedEntitlement);
        updatedEntitlement
            .workDayNo(UPDATED_WORK_DAY_NO)
            .medicalReimbursement(UPDATED_MEDICAL_REIMBURSEMENT)
            .vaccineReimbursement(UPDATED_VACCINE_REIMBURSEMENT)
            .vitaminsReimbursement(UPDATED_VITAMINS_REIMBURSEMENT)
            .opticalReimbursement(UPDATED_OPTICAL_REIMBURSEMENT);

        restEntitlementMockMvc.perform(put("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntitlement)))
            .andExpect(status().isOk());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeUpdate);
        Entitlement testEntitlement = entitlementList.get(entitlementList.size() - 1);
        assertThat(testEntitlement.getWorkDayNo()).isEqualTo(UPDATED_WORK_DAY_NO);
        assertThat(testEntitlement.getMedicalReimbursement()).isEqualTo(UPDATED_MEDICAL_REIMBURSEMENT);
        assertThat(testEntitlement.getVaccineReimbursement()).isEqualTo(UPDATED_VACCINE_REIMBURSEMENT);
        assertThat(testEntitlement.getVitaminsReimbursement()).isEqualTo(UPDATED_VITAMINS_REIMBURSEMENT);
        assertThat(testEntitlement.getOpticalReimbursement()).isEqualTo(UPDATED_OPTICAL_REIMBURSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEntitlement() throws Exception {
        int databaseSizeBeforeUpdate = entitlementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntitlementMockMvc.perform(put("/api/entitlements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entitlement)))
            .andExpect(status().isBadRequest());

        // Validate the Entitlement in the database
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntitlement() throws Exception {
        // Initialize the database
        entitlementRepository.saveAndFlush(entitlement);

        int databaseSizeBeforeDelete = entitlementRepository.findAll().size();

        // Delete the entitlement
        restEntitlementMockMvc.perform(delete("/api/entitlements/{id}", entitlement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entitlement> entitlementList = entitlementRepository.findAll();
        assertThat(entitlementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
