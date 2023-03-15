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
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link org.path.amr.services.domain.WhonetResource} entity. This class is used
 * in {@link org.path.amr.services.web.rest.WhonetResourceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /whonet-resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WhonetResourceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter uploadDate;

    private StringFilter antibiotic;

    private StringFilter organism;

    private StringFilter intrinsicResistance;

    private StringFilter expertRule;

    private StringFilter breakPoint;

    private StringFilter status;

    private ZonedDateTimeFilter importedDate;

    public WhonetResourceCriteria() {}

    public WhonetResourceCriteria(WhonetResourceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uploadDate = other.uploadDate == null ? null : other.uploadDate.copy();
        this.antibiotic = other.antibiotic == null ? null : other.antibiotic.copy();
        this.organism = other.organism == null ? null : other.organism.copy();
        this.intrinsicResistance = other.intrinsicResistance == null ? null : other.intrinsicResistance.copy();
        this.expertRule = other.expertRule == null ? null : other.expertRule.copy();
        this.breakPoint = other.breakPoint == null ? null : other.breakPoint.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.importedDate = other.importedDate == null ? null : other.importedDate.copy();
    }

    @Override
    public WhonetResourceCriteria copy() {
        return new WhonetResourceCriteria(this);
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

    public ZonedDateTimeFilter getUploadDate() {
        return uploadDate;
    }

    public ZonedDateTimeFilter uploadDate() {
        if (uploadDate == null) {
            uploadDate = new ZonedDateTimeFilter();
        }
        return uploadDate;
    }

    public void setUploadDate(ZonedDateTimeFilter uploadDate) {
        this.uploadDate = uploadDate;
    }

    public StringFilter getAntibiotic() {
        return antibiotic;
    }

    public StringFilter antibiotic() {
        if (antibiotic == null) {
            antibiotic = new StringFilter();
        }
        return antibiotic;
    }

    public void setAntibiotic(StringFilter antibiotic) {
        this.antibiotic = antibiotic;
    }

    public StringFilter getOrganism() {
        return organism;
    }

    public StringFilter organism() {
        if (organism == null) {
            organism = new StringFilter();
        }
        return organism;
    }

    public void setOrganism(StringFilter organism) {
        this.organism = organism;
    }

    public StringFilter getIntrinsicResistance() {
        return intrinsicResistance;
    }

    public StringFilter intrinsicResistance() {
        if (intrinsicResistance == null) {
            intrinsicResistance = new StringFilter();
        }
        return intrinsicResistance;
    }

    public void setIntrinsicResistance(StringFilter intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
    }

    public StringFilter getExpertRule() {
        return expertRule;
    }

    public StringFilter expertRule() {
        if (expertRule == null) {
            expertRule = new StringFilter();
        }
        return expertRule;
    }

    public void setExpertRule(StringFilter expertRule) {
        this.expertRule = expertRule;
    }

    public StringFilter getBreakPoint() {
        return breakPoint;
    }

    public StringFilter breakPoint() {
        if (breakPoint == null) {
            breakPoint = new StringFilter();
        }
        return breakPoint;
    }

    public void setBreakPoint(StringFilter breakPoint) {
        this.breakPoint = breakPoint;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public ZonedDateTimeFilter getImportedDate() {
        return importedDate;
    }

    public ZonedDateTimeFilter importedDate() {
        if (importedDate == null) {
            importedDate = new ZonedDateTimeFilter();
        }
        return importedDate;
    }

    public void setImportedDate(ZonedDateTimeFilter importedDate) {
        this.importedDate = importedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WhonetResourceCriteria that = (WhonetResourceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uploadDate, that.uploadDate) &&
            Objects.equals(antibiotic, that.antibiotic) &&
            Objects.equals(organism, that.organism) &&
            Objects.equals(intrinsicResistance, that.intrinsicResistance) &&
            Objects.equals(expertRule, that.expertRule) &&
            Objects.equals(breakPoint, that.breakPoint) &&
            Objects.equals(status, that.status) &&
            Objects.equals(importedDate, that.importedDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uploadDate, antibiotic, organism, intrinsicResistance, expertRule, breakPoint, status, importedDate);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WhonetResourceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uploadDate != null ? "uploadDate=" + uploadDate + ", " : "") +
            (antibiotic != null ? "antibiotic=" + antibiotic + ", " : "") +
            (organism != null ? "organism=" + organism + ", " : "") +
            (intrinsicResistance != null ? "intrinsicResistance=" + intrinsicResistance + ", " : "") +
            (expertRule != null ? "expertRule=" + expertRule + ", " : "") +
            (breakPoint != null ? "breakPoint=" + breakPoint + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (importedDate != null ? "importedDate=" + importedDate + ", " : "") +
            "}";
    }
}
