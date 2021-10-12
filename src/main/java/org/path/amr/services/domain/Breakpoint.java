package org.path.amr.services.domain;

import io.swagger.models.auth.In;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Breakpoint.
 */
@Entity
@Table(name = "breakpoints")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Breakpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guidelines", columnDefinition = "TEXT")
    private String guidelines;

    @Column(name = "year")
    private Integer year;

    @Column(name = "test_method", columnDefinition = "TEXT")
    private String testMethod;

    @Column(name = "potency", columnDefinition = "TEXT")
    private String potency;

    @Column(name = "organism_code", columnDefinition = "TEXT")
    private String organismCode;

    @Column(name = "organism_code_type", columnDefinition = "TEXT")
    private String organismCodeType;

    @Column(name = "breakpoint_type", columnDefinition = "TEXT")
    private String breakpointType;

    @Column(name = "host", columnDefinition = "TEXT")
    private String host;

    @Column(name = "site_of_infection", columnDefinition = "TEXT")
    private String siteOfInfection;

    @Column(name = "reference_table", columnDefinition = "TEXT")
    private String referenceTable;

    @Column(name = "reference_sequence", columnDefinition = "TEXT")
    private String referenceSequence;

    @Column(name = "whonet_abx_code", columnDefinition = "TEXT")
    private String whonetAbxCode;

    @Column(name = "whonet_test", columnDefinition = "TEXT")
    private String whonetTest;

    @Column(name = "r", columnDefinition = "TEXT")
    private String r;

    @Column(name = "i", columnDefinition = "TEXT")
    private String i;

    @Column(name = "sdd", columnDefinition = "TEXT")
    private String sdd;

    @Column(name = "s", columnDefinition = "TEXT")
    private String s;

    @Column(name = "ecv_ecoff", columnDefinition = "TEXT")
    private String ecvEcoff;

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

    public Breakpoint id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuidelines() {
        return this.guidelines;
    }

    public Breakpoint guidelines(String guidelines) {
        this.guidelines = guidelines;
        return this;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public Integer getYear() {
        return this.year;
    }

    public Breakpoint year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTestMethod() {
        return this.testMethod;
    }

    public Breakpoint testMethod(String testMethod) {
        this.testMethod = testMethod;
        return this;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String getPotency() {
        return this.potency;
    }

    public Breakpoint potency(String potency) {
        this.potency = potency;
        return this;
    }

    public void setPotency(String potency) {
        this.potency = potency;
    }

    public String getOrganismCode() {
        return this.organismCode;
    }

    public Breakpoint organismCode(String organismCode) {
        this.organismCode = organismCode;
        return this;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getOrganismCodeType() {
        return this.organismCodeType;
    }

    public Breakpoint organismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
        return this;
    }

    public void setOrganismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public String getBreakpointType() {
        return this.breakpointType;
    }

    public Breakpoint breakpointType(String breakpointType) {
        this.breakpointType = breakpointType;
        return this;
    }

    public void setBreakpointType(String breakpointType) {
        this.breakpointType = breakpointType;
    }

    public String getHost() {
        return this.host;
    }

    public Breakpoint host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSiteOfInfection() {
        return this.siteOfInfection;
    }

    public Breakpoint siteOfInfection(String siteOfInfection) {
        this.siteOfInfection = siteOfInfection;
        return this;
    }

    public void setSiteOfInfection(String siteOfInfection) {
        this.siteOfInfection = siteOfInfection;
    }

    public String getReferenceTable() {
        return this.referenceTable;
    }

    public Breakpoint referenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
        return this;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getReferenceSequence() {
        return this.referenceSequence;
    }

    public Breakpoint referenceSequence(String referenceSequence) {
        this.referenceSequence = referenceSequence;
        return this;
    }

    public void setReferenceSequence(String referenceSequence) {
        this.referenceSequence = referenceSequence;
    }

    public String getWhonetAbxCode() {
        return this.whonetAbxCode;
    }

    public Breakpoint whonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
        return this;
    }

    public void setWhonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public String getWhonetTest() {
        return this.whonetTest;
    }

    public Breakpoint whonetTest(String whonetTest) {
        this.whonetTest = whonetTest;
        return this;
    }

    public void setWhonetTest(String whonetTest) {
        this.whonetTest = whonetTest;
    }

    public String getR() {
        return this.r;
    }

    public Breakpoint r(String r) {
        this.r = r;
        return this;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getI() {
        return this.i;
    }

    public Breakpoint i(String i) {
        this.i = i;
        return this;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getSdd() {
        return this.sdd;
    }

    public Breakpoint sdd(String sdd) {
        this.sdd = sdd;
        return this;
    }

    public void setSdd(String sdd) {
        this.sdd = sdd;
    }

    public String getS() {
        return this.s;
    }

    public Breakpoint s(String s) {
        this.s = s;
        return this;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getEcvEcoff() {
        return this.ecvEcoff;
    }

    public Breakpoint ecvEcoff(String ecvEcoff) {
        this.ecvEcoff = ecvEcoff;
        return this;
    }

    public void setEcvEcoff(String ecvEcoff) {
        this.ecvEcoff = ecvEcoff;
    }

    public String getDateEntered() {
        return this.dateEntered;
    }

    public Breakpoint dateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
        return this;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return this.dateModified;
    }

    public Breakpoint dateModified(String dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getComments() {
        return this.comments;
    }

    public Breakpoint comments(String comments) {
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
        if (!(o instanceof Breakpoint)) {
            return false;
        }
        return id != null && id.equals(((Breakpoint) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Breakpoint{" +
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
