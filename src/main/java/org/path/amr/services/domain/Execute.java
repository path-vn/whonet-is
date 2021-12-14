package org.path.amr.services.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Execute.
 */
@Entity
@Table(name = "execute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Execute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request")
    private String request;

    @Column(name = "response")
    private String response;

    @Column(name = "started_at")
    private ZonedDateTime startedAt;

    @Column(name = "time")
    private Long time;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Execute id(Long id) {
        this.id = id;
        return this;
    }

    public String getRequest() {
        return this.request;
    }

    public Execute request(String request) {
        this.request = request;
        return this;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return this.response;
    }

    public Execute response(String response) {
        this.response = response;
        return this;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ZonedDateTime getStartedAt() {
        return this.startedAt;
    }

    public Execute startedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Long getTime() {
        return this.time;
    }

    public Execute time(Long time) {
        this.time = time;
        return this;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Execute)) {
            return false;
        }
        return id != null && id.equals(((Execute) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Execute{" +
            "id=" + getId() +
            ", request='" + getRequest() + "'" +
            ", response='" + getResponse() + "'" +
            ", startedAt='" + getStartedAt() + "'" +
            ", time=" + getTime() +
            "}";
    }
}
