package com.finastra.apps.finhealth.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Employee WorkDayNo
     */
    @NotNull
    @ApiModelProperty(value = "Employee WorkDayNo", required = true)
    @Column(name = "work_day_no", nullable = false, unique = true)
    private String workDayNo;

    /**
     * Employee username
     */
    @NotNull
    @ApiModelProperty(value = "Employee username", required = true)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Employee designation
     */
    @ApiModelProperty(value = "Employee designation")
    @Column(name = "designation")
    private String designation;

    /**
     * Last name
     */
    @NotNull
    @ApiModelProperty(value = "Last name", required = true)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * First name
     */
    @NotNull
    @ApiModelProperty(value = "First name", required = true)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @OneToOne
    @JoinColumn(unique = true)
    private Entitlement entitlement;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Dependent> dependents = new HashSet<>();

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

    public Employee workDayNo(String workDayNo) {
        this.workDayNo = workDayNo;
        return this;
    }

    public void setWorkDayNo(String workDayNo) {
        this.workDayNo = workDayNo;
    }

    public String getUsername() {
        return username;
    }

    public Employee username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public Employee designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Entitlement getEntitlement() {
        return entitlement;
    }

    public Employee entitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
        return this;
    }

    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }

    public Set<Dependent> getDependents() {
        return dependents;
    }

    public Employee dependents(Set<Dependent> dependents) {
        this.dependents = dependents;
        return this;
    }

    public Employee addDependents(Dependent dependent) {
        this.dependents.add(dependent);
        dependent.setEmployee(this);
        return this;
    }

    public Employee removeDependents(Dependent dependent) {
        this.dependents.remove(dependent);
        dependent.setEmployee(null);
        return this;
    }

    public void setDependents(Set<Dependent> dependents) {
        this.dependents = dependents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", workDayNo='" + getWorkDayNo() + "'" +
            ", username='" + getUsername() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            "}";
    }
}
