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

    private StringFilter path;

    private StringFilter query;

    private StringFilter antibioticQuery;

    private StringFilter organismQuery;

    private StringFilter intrinsicResistanceQuery;

    public BreakpointCriteria() {}

    public BreakpointCriteria(BreakpointCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.path = other.path == null ? null : other.path.copy();
        this.query = other.query == null ? null : other.query.copy();
        this.antibioticQuery = other.antibioticQuery == null ? null : other.antibioticQuery.copy();
        this.organismQuery = other.organismQuery == null ? null : other.organismQuery.copy();
        this.intrinsicResistanceQuery = other.intrinsicResistanceQuery == null ? null : other.intrinsicResistanceQuery.copy();
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

    public StringFilter getPath() {
        return path;
    }

    public StringFilter path() {
        if (path == null) {
            path = new StringFilter();
        }
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public StringFilter getQuery() {
        return query;
    }

    public StringFilter query() {
        if (query == null) {
            query = new StringFilter();
        }
        return query;
    }

    public void setQuery(StringFilter query) {
        this.query = query;
    }

    public StringFilter getAntibioticQuery() {
        return antibioticQuery;
    }

    public StringFilter antibioticQuery() {
        if (antibioticQuery == null) {
            antibioticQuery = new StringFilter();
        }
        return antibioticQuery;
    }

    public void setAntibioticQuery(StringFilter antibioticQuery) {
        this.antibioticQuery = antibioticQuery;
    }

    public StringFilter getOrganismQuery() {
        return organismQuery;
    }

    public StringFilter organismQuery() {
        if (organismQuery == null) {
            organismQuery = new StringFilter();
        }
        return organismQuery;
    }

    public void setOrganismQuery(StringFilter organismQuery) {
        this.organismQuery = organismQuery;
    }

    public StringFilter getIntrinsicResistanceQuery() {
        return intrinsicResistanceQuery;
    }

    public StringFilter intrinsicResistanceQuery() {
        if (intrinsicResistanceQuery == null) {
            intrinsicResistanceQuery = new StringFilter();
        }
        return intrinsicResistanceQuery;
    }

    public void setIntrinsicResistanceQuery(StringFilter intrinsicResistanceQuery) {
        this.intrinsicResistanceQuery = intrinsicResistanceQuery;
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
            Objects.equals(path, that.path) &&
            Objects.equals(query, that.query) &&
            Objects.equals(antibioticQuery, that.antibioticQuery) &&
            Objects.equals(organismQuery, that.organismQuery) &&
            Objects.equals(intrinsicResistanceQuery, that.intrinsicResistanceQuery)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, query, antibioticQuery, organismQuery, intrinsicResistanceQuery);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreakpointCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (path != null ? "path=" + path + ", " : "") +
            (query != null ? "query=" + query + ", " : "") +
            (antibioticQuery != null ? "antibioticQuery=" + antibioticQuery + ", " : "") +
            (organismQuery != null ? "organismQuery=" + organismQuery + ", " : "") +
            (intrinsicResistanceQuery != null ? "intrinsicResistanceQuery=" + intrinsicResistanceQuery + ", " : "") +
            "}";
    }
}
