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
 * Criteria class for the {@link org.path.amr.services.domain.Execute} entity. This class is used
 * in {@link org.path.amr.services.web.rest.ExecuteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /executes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExecuteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter request;

    private StringFilter response;

    private ZonedDateTimeFilter startedAt;

    private LongFilter time;

    public ExecuteCriteria() {}

    public ExecuteCriteria(ExecuteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.request = other.request == null ? null : other.request.copy();
        this.response = other.response == null ? null : other.response.copy();
        this.startedAt = other.startedAt == null ? null : other.startedAt.copy();
        this.time = other.time == null ? null : other.time.copy();
    }

    @Override
    public ExecuteCriteria copy() {
        return new ExecuteCriteria(this);
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

    public StringFilter getRequest() {
        return request;
    }

    public StringFilter request() {
        if (request == null) {
            request = new StringFilter();
        }
        return request;
    }

    public void setRequest(StringFilter request) {
        this.request = request;
    }

    public StringFilter getResponse() {
        return response;
    }

    public StringFilter response() {
        if (response == null) {
            response = new StringFilter();
        }
        return response;
    }

    public void setResponse(StringFilter response) {
        this.response = response;
    }

    public ZonedDateTimeFilter getStartedAt() {
        return startedAt;
    }

    public ZonedDateTimeFilter startedAt() {
        if (startedAt == null) {
            startedAt = new ZonedDateTimeFilter();
        }
        return startedAt;
    }

    public void setStartedAt(ZonedDateTimeFilter startedAt) {
        this.startedAt = startedAt;
    }

    public LongFilter getTime() {
        return time;
    }

    public LongFilter time() {
        if (time == null) {
            time = new LongFilter();
        }
        return time;
    }

    public void setTime(LongFilter time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExecuteCriteria that = (ExecuteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(request, that.request) &&
            Objects.equals(response, that.response) &&
            Objects.equals(startedAt, that.startedAt) &&
            Objects.equals(time, that.time)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, request, response, startedAt, time);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExecuteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (request != null ? "request=" + request + ", " : "") +
            (response != null ? "response=" + response + ", " : "") +
            (startedAt != null ? "startedAt=" + startedAt + ", " : "") +
            (time != null ? "time=" + time + ", " : "") +
            "}";
    }
}
