package com.finastra.apps.finhealth.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Entitlement.
 */
@Entity
@Table(name = "entitlement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entitlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "work_day_no", nullable = false)
    private String workDayNo;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "medical_reimbursement", precision = 21, scale = 2, nullable = false)
    private BigDecimal medicalReimbursement;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "vaccine_reimbursement", precision = 21, scale = 2, nullable = false)
    private BigDecimal vaccineReimbursement;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "vitamins_reimbursement", precision = 21, scale = 2, nullable = false)
    private BigDecimal vitaminsReimbursement;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "optical_reimbursement", precision = 21, scale = 2, nullable = false)
    private BigDecimal opticalReimbursement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkDayNo() {
        return workDayNo;
    }

    public Entitlement workDayNo(String workDayNo) {
        this.workDayNo = workDayNo;
        return this;
    }

    public void setWorkDayNo(String workDayNo) {
        this.workDayNo = workDayNo;
    }

    public BigDecimal getMedicalReimbursement() {
        return medicalReimbursement;
    }

    public Entitlement medicalReimbursement(BigDecimal medicalReimbursement) {
        this.medicalReimbursement = medicalReimbursement;
        return this;
    }

    public void setMedicalReimbursement(BigDecimal medicalReimbursement) {
        this.medicalReimbursement = medicalReimbursement;
    }

    public BigDecimal getVaccineReimbursement() {
        return vaccineReimbursement;
    }

    public Entitlement vaccineReimbursement(BigDecimal vaccineReimbursement) {
        this.vaccineReimbursement = vaccineReimbursement;
        return this;
    }

    public void setVaccineReimbursement(BigDecimal vaccineReimbursement) {
        this.vaccineReimbursement = vaccineReimbursement;
    }

    public BigDecimal getVitaminsReimbursement() {
        return vitaminsReimbursement;
    }

    public Entitlement vitaminsReimbursement(BigDecimal vitaminsReimbursement) {
        this.vitaminsReimbursement = vitaminsReimbursement;
        return this;
    }

    public void setVitaminsReimbursement(BigDecimal vitaminsReimbursement) {
        this.vitaminsReimbursement = vitaminsReimbursement;
    }

    public BigDecimal getOpticalReimbursement() {
        return opticalReimbursement;
    }

    public Entitlement opticalReimbursement(BigDecimal opticalReimbursement) {
        this.opticalReimbursement = opticalReimbursement;
        return this;
    }

    public void setOpticalReimbursement(BigDecimal opticalReimbursement) {
        this.opticalReimbursement = opticalReimbursement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entitlement)) {
            return false;
        }
        return id != null && id.equals(((Entitlement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entitlement{" +
            "id=" + getId() +
            ", workDayNo='" + getWorkDayNo() + "'" +
            ", medicalReimbursement=" + getMedicalReimbursement() +
            ", vaccineReimbursement=" + getVaccineReimbursement() +
            ", vitaminsReimbursement=" + getVitaminsReimbursement() +
            ", opticalReimbursement=" + getOpticalReimbursement() +
            "}";
    }
}
