package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.IntrinsicResistance} entity.
 */
public class IntrinsicResistanceDTO implements Serializable {

    private Long id;

    private String guideline;

    private String referenceTable;

    private String organismCode;

    private String organismCodeType;

    private String exceptionOrganismCode;

    private String exceptionOrganismCodeType;

    private String abxCode;

    private String abxCodeType;

    private String antibioticExceptions;

    private String dateEntered;

    private String dateModified;

    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getOrganismCode() {
        return organismCode;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getOrganismCodeType() {
        return organismCodeType;
    }

    public void setOrganismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public String getExceptionOrganismCode() {
        return exceptionOrganismCode;
    }

    public void setExceptionOrganismCode(String exceptionOrganismCode) {
        this.exceptionOrganismCode = exceptionOrganismCode;
    }

    public String getExceptionOrganismCodeType() {
        return exceptionOrganismCodeType;
    }

    public void setExceptionOrganismCodeType(String exceptionOrganismCodeType) {
        this.exceptionOrganismCodeType = exceptionOrganismCodeType;
    }

    public String getAbxCode() {
        return abxCode;
    }

    public void setAbxCode(String abxCode) {
        this.abxCode = abxCode;
    }

    public String getAbxCodeType() {
        return abxCodeType;
    }

    public void setAbxCodeType(String abxCodeType) {
        this.abxCodeType = abxCodeType;
    }

    public String getAntibioticExceptions() {
        return antibioticExceptions;
    }

    public void setAntibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
    }

    public String getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntrinsicResistanceDTO)) {
            return false;
        }

        IntrinsicResistanceDTO intrinsicResistanceDTO = (IntrinsicResistanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, intrinsicResistanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntrinsicResistanceDTO{" +
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
