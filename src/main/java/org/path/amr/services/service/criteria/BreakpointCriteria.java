package org.path.amr.services.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.path.amr.services.domain.Breakpoint} entity. This class is used
 * in {@link org.path.amr.services.web.rest.BreakpointResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /breakpoints?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BreakpointCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter guidelines;

    private StringFilter year;

    private StringFilter testMethod;

    private StringFilter potency;

    private StringFilter organismCode;

    private StringFilter organismCodeType;

    private StringFilter breakpointType;

    private StringFilter host;

    private StringFilter siteOfInfection;

    private StringFilter referenceTable;

    private StringFilter referenceSequence;

    private StringFilter whonetAbxCode;

    private StringFilter whonetTest;

    private StringFilter r;

    private StringFilter i;

    private StringFilter sdd;

    private StringFilter s;

    private StringFilter ecvEcoff;

    private StringFilter dateEntered;

    private StringFilter dateModified;

    private StringFilter comments;

    public BreakpointCriteria() {}

    public BreakpointCriteria(BreakpointCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.guidelines = other.guidelines == null ? null : other.guidelines.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.testMethod = other.testMethod == null ? null : other.testMethod.copy();
        this.potency = other.potency == null ? null : other.potency.copy();
        this.organismCode = other.organismCode == null ? null : other.organismCode.copy();
        this.organismCodeType = other.organismCodeType == null ? null : other.organismCodeType.copy();
        this.breakpointType = other.breakpointType == null ? null : other.breakpointType.copy();
        this.host = other.host == null ? null : other.host.copy();
        this.siteOfInfection = other.siteOfInfection == null ? null : other.siteOfInfection.copy();
        this.referenceTable = other.referenceTable == null ? null : other.referenceTable.copy();
        this.referenceSequence = other.referenceSequence == null ? null : other.referenceSequence.copy();
        this.whonetAbxCode = other.whonetAbxCode == null ? null : other.whonetAbxCode.copy();
        this.whonetTest = other.whonetTest == null ? null : other.whonetTest.copy();
        this.r = other.r == null ? null : other.r.copy();
        this.i = other.i == null ? null : other.i.copy();
        this.sdd = other.sdd == null ? null : other.sdd.copy();
        this.s = other.s == null ? null : other.s.copy();
        this.ecvEcoff = other.ecvEcoff == null ? null : other.ecvEcoff.copy();
        this.dateEntered = other.dateEntered == null ? null : other.dateEntered.copy();
        this.dateModified = other.dateModified == null ? null : other.dateModified.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
    }

    @Override
    public BreakpointCriteria copy() {
        return new BreakpointCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGuidelines() {
        return guidelines;
    }

    public StringFilter guidelines() {
        if (guidelines == null) {
            guidelines = new StringFilter();
        }
        return guidelines;
    }

    public void setGuidelines(StringFilter guidelines) {
        this.guidelines = guidelines;
    }

    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
            year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getTestMethod() {
        return testMethod;
    }

    public StringFilter testMethod() {
        if (testMethod == null) {
            testMethod = new StringFilter();
        }
        return testMethod;
    }

    public void setTestMethod(StringFilter testMethod) {
        this.testMethod = testMethod;
    }

    public StringFilter getPotency() {
        return potency;
    }

    public StringFilter potency() {
        if (potency == null) {
            potency = new StringFilter();
        }
        return potency;
    }

    public void setPotency(StringFilter potency) {
        this.potency = potency;
    }

    public StringFilter getOrganismCode() {
        return organismCode;
    }

    public StringFilter organismCode() {
        if (organismCode == null) {
            organismCode = new StringFilter();
        }
        return organismCode;
    }

    public void setOrganismCode(StringFilter organismCode) {
        this.organismCode = organismCode;
    }

    public StringFilter getOrganismCodeType() {
        return organismCodeType;
    }

    public StringFilter organismCodeType() {
        if (organismCodeType == null) {
            organismCodeType = new StringFilter();
        }
        return organismCodeType;
    }

    public void setOrganismCodeType(StringFilter organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public StringFilter getBreakpointType() {
        return breakpointType;
    }

    public StringFilter breakpointType() {
        if (breakpointType == null) {
            breakpointType = new StringFilter();
        }
        return breakpointType;
    }

    public void setBreakpointType(StringFilter breakpointType) {
        this.breakpointType = breakpointType;
    }

    public StringFilter getHost() {
        return host;
    }

    public StringFilter host() {
        if (host == null) {
            host = new StringFilter();
        }
        return host;
    }

    public void setHost(StringFilter host) {
        this.host = host;
    }

    public StringFilter getSiteOfInfection() {
        return siteOfInfection;
    }

    public StringFilter siteOfInfection() {
        if (siteOfInfection == null) {
            siteOfInfection = new StringFilter();
        }
        return siteOfInfection;
    }

    public void setSiteOfInfection(StringFilter siteOfInfection) {
        this.siteOfInfection = siteOfInfection;
    }

    public StringFilter getReferenceTable() {
        return referenceTable;
    }

    public StringFilter referenceTable() {
        if (referenceTable == null) {
            referenceTable = new StringFilter();
        }
        return referenceTable;
    }

    public void setReferenceTable(StringFilter referenceTable) {
        this.referenceTable = referenceTable;
    }

    public StringFilter getReferenceSequence() {
        return referenceSequence;
    }

    public StringFilter referenceSequence() {
        if (referenceSequence == null) {
            referenceSequence = new StringFilter();
        }
        return referenceSequence;
    }

    public void setReferenceSequence(StringFilter referenceSequence) {
        this.referenceSequence = referenceSequence;
    }

    public StringFilter getWhonetAbxCode() {
        return whonetAbxCode;
    }

    public StringFilter whonetAbxCode() {
        if (whonetAbxCode == null) {
            whonetAbxCode = new StringFilter();
        }
        return whonetAbxCode;
    }

    public void setWhonetAbxCode(StringFilter whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public StringFilter getWhonetTest() {
        return whonetTest;
    }

    public StringFilter whonetTest() {
        if (whonetTest == null) {
            whonetTest = new StringFilter();
        }
        return whonetTest;
    }

    public void setWhonetTest(StringFilter whonetTest) {
        this.whonetTest = whonetTest;
    }

    public StringFilter getR() {
        return r;
    }

    public StringFilter r() {
        if (r == null) {
            r = new StringFilter();
        }
        return r;
    }

    public void setR(StringFilter r) {
        this.r = r;
    }

    public StringFilter getI() {
        return i;
    }

    public StringFilter i() {
        if (i == null) {
            i = new StringFilter();
        }
        return i;
    }

    public void setI(StringFilter i) {
        this.i = i;
    }

    public StringFilter getSdd() {
        return sdd;
    }

    public StringFilter sdd() {
        if (sdd == null) {
            sdd = new StringFilter();
        }
        return sdd;
    }

    public void setSdd(StringFilter sdd) {
        this.sdd = sdd;
    }

    public StringFilter getS() {
        return s;
    }

    public StringFilter s() {
        if (s == null) {
            s = new StringFilter();
        }
        return s;
    }

    public void setS(StringFilter s) {
        this.s = s;
    }

    public StringFilter getEcvEcoff() {
        return ecvEcoff;
    }

    public StringFilter ecvEcoff() {
        if (ecvEcoff == null) {
            ecvEcoff = new StringFilter();
        }
        return ecvEcoff;
    }

    public void setEcvEcoff(StringFilter ecvEcoff) {
        this.ecvEcoff = ecvEcoff;
    }

    public StringFilter getDateEntered() {
        return dateEntered;
    }

    public StringFilter dateEntered() {
        if (dateEntered == null) {
            dateEntered = new StringFilter();
        }
        return dateEntered;
    }

    public void setDateEntered(StringFilter dateEntered) {
        this.dateEntered = dateEntered;
    }

    public StringFilter getDateModified() {
        return dateModified;
    }

    public StringFilter dateModified() {
        if (dateModified == null) {
            dateModified = new StringFilter();
        }
        return dateModified;
    }

    public void setDateModified(StringFilter dateModified) {
        this.dateModified = dateModified;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BreakpointCriteria that = (BreakpointCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(guidelines, that.guidelines) &&
            Objects.equals(year, that.year) &&
            Objects.equals(testMethod, that.testMethod) &&
            Objects.equals(potency, that.potency) &&
            Objects.equals(organismCode, that.organismCode) &&
            Objects.equals(organismCodeType, that.organismCodeType) &&
            Objects.equals(breakpointType, that.breakpointType) &&
            Objects.equals(host, that.host) &&
            Objects.equals(siteOfInfection, that.siteOfInfection) &&
            Objects.equals(referenceTable, that.referenceTable) &&
            Objects.equals(referenceSequence, that.referenceSequence) &&
            Objects.equals(whonetAbxCode, that.whonetAbxCode) &&
            Objects.equals(whonetTest, that.whonetTest) &&
            Objects.equals(r, that.r) &&
            Objects.equals(i, that.i) &&
            Objects.equals(sdd, that.sdd) &&
            Objects.equals(s, that.s) &&
            Objects.equals(ecvEcoff, that.ecvEcoff) &&
            Objects.equals(dateEntered, that.dateEntered) &&
            Objects.equals(dateModified, that.dateModified) &&
            Objects.equals(comments, that.comments)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            guidelines,
            year,
            testMethod,
            potency,
            organismCode,
            organismCodeType,
            breakpointType,
            host,
            siteOfInfection,
            referenceTable,
            referenceSequence,
            whonetAbxCode,
            whonetTest,
            r,
            i,
            sdd,
            s,
            ecvEcoff,
            dateEntered,
            dateModified,
            comments
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreakpointCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (guidelines != null ? "guidelines=" + guidelines + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (testMethod != null ? "testMethod=" + testMethod + ", " : "") +
            (potency != null ? "potency=" + potency + ", " : "") +
            (organismCode != null ? "organismCode=" + organismCode + ", " : "") +
            (organismCodeType != null ? "organismCodeType=" + organismCodeType + ", " : "") +
            (breakpointType != null ? "breakpointType=" + breakpointType + ", " : "") +
            (host != null ? "host=" + host + ", " : "") +
            (siteOfInfection != null ? "siteOfInfection=" + siteOfInfection + ", " : "") +
            (referenceTable != null ? "referenceTable=" + referenceTable + ", " : "") +
            (referenceSequence != null ? "referenceSequence=" + referenceSequence + ", " : "") +
            (whonetAbxCode != null ? "whonetAbxCode=" + whonetAbxCode + ", " : "") +
            (whonetTest != null ? "whonetTest=" + whonetTest + ", " : "") +
            (r != null ? "r=" + r + ", " : "") +
            (i != null ? "i=" + i + ", " : "") +
            (sdd != null ? "sdd=" + sdd + ", " : "") +
            (s != null ? "s=" + s + ", " : "") +
            (ecvEcoff != null ? "ecvEcoff=" + ecvEcoff + ", " : "") +
            (dateEntered != null ? "dateEntered=" + dateEntered + ", " : "") +
            (dateModified != null ? "dateModified=" + dateModified + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            "}";
    }
}
