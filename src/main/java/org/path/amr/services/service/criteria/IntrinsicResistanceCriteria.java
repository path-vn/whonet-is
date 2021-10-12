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
 * Criteria class for the {@link org.path.amr.services.domain.IntrinsicResistance} entity. This class is used
 * in {@link org.path.amr.services.web.rest.IntrinsicResistanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /intrinsic-resistances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IntrinsicResistanceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter guideline;

    private StringFilter referenceTable;

    private StringFilter organismCode;

    private StringFilter organismCodeType;

    private StringFilter exceptionOrganismCode;

    private StringFilter exceptionOrganismCodeType;

    private StringFilter abxCode;

    private StringFilter abxCodeType;

    private StringFilter dateEntered;

    private StringFilter dateModified;

    private StringFilter comments;

    public IntrinsicResistanceCriteria() {}

    public IntrinsicResistanceCriteria(IntrinsicResistanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.guideline = other.guideline == null ? null : other.guideline.copy();
        this.referenceTable = other.referenceTable == null ? null : other.referenceTable.copy();
        this.organismCode = other.organismCode == null ? null : other.organismCode.copy();
        this.organismCodeType = other.organismCodeType == null ? null : other.organismCodeType.copy();
        this.exceptionOrganismCode = other.exceptionOrganismCode == null ? null : other.exceptionOrganismCode.copy();
        this.exceptionOrganismCodeType = other.exceptionOrganismCodeType == null ? null : other.exceptionOrganismCodeType.copy();
        this.abxCode = other.abxCode == null ? null : other.abxCode.copy();
        this.abxCodeType = other.abxCodeType == null ? null : other.abxCodeType.copy();
        this.dateEntered = other.dateEntered == null ? null : other.dateEntered.copy();
        this.dateModified = other.dateModified == null ? null : other.dateModified.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
    }

    @Override
    public IntrinsicResistanceCriteria copy() {
        return new IntrinsicResistanceCriteria(this);
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

    public StringFilter getGuideline() {
        return guideline;
    }

    public StringFilter guideline() {
        if (guideline == null) {
            guideline = new StringFilter();
        }
        return guideline;
    }

    public void setGuideline(StringFilter guideline) {
        this.guideline = guideline;
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

    public StringFilter getExceptionOrganismCode() {
        return exceptionOrganismCode;
    }

    public StringFilter exceptionOrganismCode() {
        if (exceptionOrganismCode == null) {
            exceptionOrganismCode = new StringFilter();
        }
        return exceptionOrganismCode;
    }

    public void setExceptionOrganismCode(StringFilter exceptionOrganismCode) {
        this.exceptionOrganismCode = exceptionOrganismCode;
    }

    public StringFilter getExceptionOrganismCodeType() {
        return exceptionOrganismCodeType;
    }

    public StringFilter exceptionOrganismCodeType() {
        if (exceptionOrganismCodeType == null) {
            exceptionOrganismCodeType = new StringFilter();
        }
        return exceptionOrganismCodeType;
    }

    public void setExceptionOrganismCodeType(StringFilter exceptionOrganismCodeType) {
        this.exceptionOrganismCodeType = exceptionOrganismCodeType;
    }

    public StringFilter getAbxCode() {
        return abxCode;
    }

    public StringFilter abxCode() {
        if (abxCode == null) {
            abxCode = new StringFilter();
        }
        return abxCode;
    }

    public void setAbxCode(StringFilter abxCode) {
        this.abxCode = abxCode;
    }

    public StringFilter getAbxCodeType() {
        return abxCodeType;
    }

    public StringFilter abxCodeType() {
        if (abxCodeType == null) {
            abxCodeType = new StringFilter();
        }
        return abxCodeType;
    }

    public void setAbxCodeType(StringFilter abxCodeType) {
        this.abxCodeType = abxCodeType;
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
        final IntrinsicResistanceCriteria that = (IntrinsicResistanceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(guideline, that.guideline) &&
            Objects.equals(referenceTable, that.referenceTable) &&
            Objects.equals(organismCode, that.organismCode) &&
            Objects.equals(organismCodeType, that.organismCodeType) &&
            Objects.equals(exceptionOrganismCode, that.exceptionOrganismCode) &&
            Objects.equals(exceptionOrganismCodeType, that.exceptionOrganismCodeType) &&
            Objects.equals(abxCode, that.abxCode) &&
            Objects.equals(abxCodeType, that.abxCodeType) &&
            Objects.equals(dateEntered, that.dateEntered) &&
            Objects.equals(dateModified, that.dateModified) &&
            Objects.equals(comments, that.comments)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            guideline,
            referenceTable,
            organismCode,
            organismCodeType,
            exceptionOrganismCode,
            exceptionOrganismCodeType,
            abxCode,
            abxCodeType,
            dateEntered,
            dateModified,
            comments
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntrinsicResistanceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (guideline != null ? "guideline=" + guideline + ", " : "") +
            (referenceTable != null ? "referenceTable=" + referenceTable + ", " : "") +
            (organismCode != null ? "organismCode=" + organismCode + ", " : "") +
            (organismCodeType != null ? "organismCodeType=" + organismCodeType + ", " : "") +
            (exceptionOrganismCode != null ? "exceptionOrganismCode=" + exceptionOrganismCode + ", " : "") +
            (exceptionOrganismCodeType != null ? "exceptionOrganismCodeType=" + exceptionOrganismCodeType + ", " : "") +
            (abxCode != null ? "abxCode=" + abxCode + ", " : "") +
            (abxCodeType != null ? "abxCodeType=" + abxCodeType + ", " : "") +
            (dateEntered != null ? "dateEntered=" + dateEntered + ", " : "") +
            (dateModified != null ? "dateModified=" + dateModified + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            "}";
    }
}
