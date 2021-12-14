package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.Execute} entity.
 */
public class ExecuteDTO implements Serializable {

    private Long id;

    private String request;

    private String response;

    private ZonedDateTime startedAt;

    private Long time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExecuteDTO)) {
            return false;
        }

        ExecuteDTO executeDTO = (ExecuteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, executeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExecuteDTO{" +
            "id=" + getId() +
            ", request='" + getRequest() + "'" +
            ", response='" + getResponse() + "'" +
            ", startedAt='" + getStartedAt() + "'" +
            ", time=" + getTime() +
            "}";
    }
}
