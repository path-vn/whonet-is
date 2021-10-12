package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.Breakpoint} entity.
 */
public class BreakpointDTO implements Serializable {

    private Long id;

    private String guidelines;

    private String year;

    private String testMethod;

    private String potency;

    private String organismCode;

    private String organismCodeType;

    private String breakpointType;

    private String host;

    private String siteOfInfection;

    private String referenceTable;

    private String referenceSequence;

    private String whonetAbxCode;

    private String whonetTest;

    private String r;

    private String i;

    private String sdd;

    private String s;

    private String ecvEcoff;

    private String dateEntered;

    private String dateModified;

    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getPotency() {
        return potency;
    }

    public void setPotency(String potency) {
        this.potency = potency;
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

    public String getBreakpointType() {
        return breakpointType;
    }

    public void setBreakpointType(String breakpointType) {
        this.breakpointType = breakpointType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSiteOfInfection() {
        return siteOfInfection;
    }

    public void setSiteOfInfection(String siteOfInfection) {
        this.siteOfInfection = siteOfInfection;
    }

    public String getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getReferenceSequence() {
        return referenceSequence;
    }

    public void setReferenceSequence(String referenceSequence) {
        this.referenceSequence = referenceSequence;
    }

    public String getWhonetAbxCode() {
        return whonetAbxCode;
    }

    public void setWhonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public String getWhonetTest() {
        return whonetTest;
    }

    public void setWhonetTest(String whonetTest) {
        this.whonetTest = whonetTest;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getSdd() {
        return sdd;
    }

    public void setSdd(String sdd) {
        this.sdd = sdd;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getEcvEcoff() {
        return ecvEcoff;
    }

    public void setEcvEcoff(String ecvEcoff) {
        this.ecvEcoff = ecvEcoff;
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
        if (!(o instanceof BreakpointDTO)) {
            return false;
        }

        BreakpointDTO breakpointDTO = (BreakpointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, breakpointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreakpointDTO{" +
            "id=" + getId() +
            ", guidelines='" + getGuidelines() + "'" +
            ", year='" + getYear() + "'" +
            ", testMethod='" + getTestMethod() + "'" +
            ", potency='" + getPotency() + "'" +
            ", organismCode='" + getOrganismCode() + "'" +
            ", organismCodeType='" + getOrganismCodeType() + "'" +
            ", breakpointType='" + getBreakpointType() + "'" +
            ", host='" + getHost() + "'" +
            ", siteOfInfection='" + getSiteOfInfection() + "'" +
            ", referenceTable='" + getReferenceTable() + "'" +
            ", referenceSequence='" + getReferenceSequence() + "'" +
            ", whonetAbxCode='" + getWhonetAbxCode() + "'" +
            ", whonetTest='" + getWhonetTest() + "'" +
            ", r='" + getR() + "'" +
            ", i='" + getI() + "'" +
            ", sdd='" + getSdd() + "'" +
            ", s='" + getS() + "'" +
            ", ecvEcoff='" + getEcvEcoff() + "'" +
            ", dateEntered='" + getDateEntered() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
