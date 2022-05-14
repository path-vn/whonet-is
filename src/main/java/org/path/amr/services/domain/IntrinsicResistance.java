package org.path.amr.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IntrinsicResistance.
 */
@Entity
@Table(name = "intrinsic_resistance")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IntrinsicResistance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guideline", columnDefinition = "TEXT")
    private String guideline;

    @Column(name = "reference_table", columnDefinition = "TEXT")
    private String referenceTable;

    @Column(name = "organism_code", columnDefinition = "TEXT")
    private String organismCode;

    @Column(name = "organism_code_type", columnDefinition = "TEXT")
    private String organismCodeType;

    @Column(name = "exception_organism_code", columnDefinition = "TEXT")
    private String exceptionOrganismCode;

    @Column(name = "exception_organism_code_type", columnDefinition = "TEXT")
    private String exceptionOrganismCodeType;

    @Column(name = "abx_code", columnDefinition = "TEXT")
    private String abxCode;

    @Column(name = "abx_code_type", columnDefinition = "TEXT")
    private String abxCodeType;

    @Column(name = "antibiotic_exceptions", columnDefinition = "TEXT")
    private String antibioticExceptions;

    @Column(name = "date_entered", columnDefinition = "TEXT")
    private String dateEntered;

    @Column(name = "date_modified", columnDefinition = "TEXT")
    private String dateModified;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntrinsicResistance id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuideline() {
        return this.guideline;
    }

    public IntrinsicResistance guideline(String guideline) {
        this.guideline = guideline;
        return this;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getReferenceTable() {
        return this.referenceTable;
    }

    public IntrinsicResistance referenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
        return this;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getOrganismCode() {
        return this.organismCode;
    }

    public IntrinsicResistance organismCode(String organismCode) {
        this.organismCode = organismCode;
        return this;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getOrganismCodeType() {
        return this.organismCodeType;
    }

    public IntrinsicResistance organismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
        return this;
    }

    public void setOrganismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public String getExceptionOrganismCode() {
        return this.exceptionOrganismCode;
    }

    public IntrinsicResistance exceptionOrganismCode(String exceptionOrganismCode) {
        this.exceptionOrganismCode = exceptionOrganismCode;
        return this;
    }

    public void setExceptionOrganismCode(String exceptionOrganismCode) {
        this.exceptionOrganismCode = exceptionOrganismCode;
    }

    public String getExceptionOrganismCodeType() {
        return this.exceptionOrganismCodeType;
    }

    public IntrinsicResistance exceptionOrganismCodeType(String exceptionOrganismCodeType) {
        this.exceptionOrganismCodeType = exceptionOrganismCodeType;
        return this;
    }

    public void setExceptionOrganismCodeType(String exceptionOrganismCodeType) {
        this.exceptionOrganismCodeType = exceptionOrganismCodeType;
    }

    public String getAbxCode() {
        return this.abxCode;
    }

    public IntrinsicResistance abxCode(String abxCode) {
        this.abxCode = abxCode;
        return this;
    }

    public void setAbxCode(String abxCode) {
        this.abxCode = abxCode;
    }

    public String getAbxCodeType() {
        return this.abxCodeType;
    }

    public IntrinsicResistance abxCodeType(String abxCodeType) {
        this.abxCodeType = abxCodeType;
        return this;
    }

    public void setAbxCodeType(String abxCodeType) {
        this.abxCodeType = abxCodeType;
    }

    public String getAntibioticExceptions() {
        return this.antibioticExceptions;
    }

    public IntrinsicResistance antibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
        return this;
    }

    public void setAntibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
    }

    public String getDateEntered() {
        return this.dateEntered;
    }

    public IntrinsicResistance dateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
        return this;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return this.dateModified;
    }

    public IntrinsicResistance dateModified(String dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getComments() {
        return this.comments;
    }

    public IntrinsicResistance comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntrinsicResistance)) {
            return false;
        }
        return id != null && id.equals(((IntrinsicResistance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntrinsicResistance{" +
            "id=" + getId() +
            ", guideline='" + getGuideline() + "'" +
            ", referenceTable='" + getReferenceTable() + "'" +
            ", organismCode='" + getOrganismCode() + "'" +
            ", organismCodeType='" + getOrganismCodeType() + "'" +
            ", exceptionOrganismCode='" + getExceptionOrganismCode() + "'" +
            ", exceptionOrganismCodeType='" + getExceptionOrganismCodeType() + "'" +
            ", abxCode='" + getAbxCode() + "'" +
            ", abxCodeType='" + getAbxCodeType() + "'" +
            ", antibioticExceptions='" + getAntibioticExceptions() + "'" +
            ", dateEntered='" + getDateEntered() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
