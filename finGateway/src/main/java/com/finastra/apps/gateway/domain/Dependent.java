package com.finastra.apps.gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.finastra.apps.gateway.domain.enumeration.RelationshipType;

/**
 * A Dependent.
 */
@Entity
@Table(name = "dependent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dependent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relationshipx", nullable = false)
    private RelationshipType relationshipx;

    @Column(name = "relationship_others")
    private String relationshipOthers;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "dependents", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Dependent firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Dependent lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RelationshipType getRelationshipx() {
        return relationshipx;
    }

    public Dependent relationshipx(RelationshipType relationshipx) {
        this.relationshipx = relationshipx;
        return this;
    }

    public void setRelationshipx(RelationshipType relationshipx) {
        this.relationshipx = relationshipx;
    }

    public String getRelationshipOthers() {
        return relationshipOthers;
    }

    public Dependent relationshipOthers(String relationshipOthers) {
        this.relationshipOthers = relationshipOthers;
        return this;
    }

    public void setRelationshipOthers(String relationshipOthers) {
        this.relationshipOthers = relationshipOthers;
    }

    public byte[] getImage() {
        return image;
    }

    public Dependent image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Dependent imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Dependent employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dependent)) {
            return false;
        }
        return id != null && id.equals(((Dependent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dependent{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", relationshipx='" + getRelationshipx() + "'" +
            ", relationshipOthers='" + getRelationshipOthers() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
