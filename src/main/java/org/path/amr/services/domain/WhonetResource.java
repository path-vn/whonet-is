package org.path.amr.services.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WhonetResource.
 */
@Entity
@Table(name = "whonet_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WhonetResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "upload_date")
    private ZonedDateTime uploadDate;

    @Column(name = "antibiotic")
    private String antibiotic;

    @Column(name = "organism")
    private String organism;

    @Column(name = "intrinsic_resistance")
    private String intrinsicResistance;

    @Column(name = "expert_rule")
    private String expertRule;

    @Column(name = "break_point")
    private String breakPoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WhonetResource id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getUploadDate() {
        return this.uploadDate;
    }

    public WhonetResource uploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAntibiotic() {
        return this.antibiotic;
    }

    public WhonetResource antibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
        return this;
    }

    public void setAntibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
    }

    public String getOrganism() {
        return this.organism;
    }

    public WhonetResource organism(String organism) {
        this.organism = organism;
        return this;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getIntrinsicResistance() {
        return this.intrinsicResistance;
    }

    public WhonetResource intrinsicResistance(String intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
        return this;
    }

    public void setIntrinsicResistance(String intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
    }

    public String getExpertRule() {
        return this.expertRule;
    }

    public WhonetResource expertRule(String expertRule) {
        this.expertRule = expertRule;
        return this;
    }

    public void setExpertRule(String expertRule) {
        this.expertRule = expertRule;
    }

    public String getBreakPoint() {
        return this.breakPoint;
    }

    public WhonetResource breakPoint(String breakPoint) {
        this.breakPoint = breakPoint;
        return this;
    }

    public void setBreakPoint(String breakPoint) {
        this.breakPoint = breakPoint;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhonetResource)) {
            return false;
        }
        return id != null && id.equals(((WhonetResource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WhonetResource{" +
            "id=" + getId() +
            ", uploadDate='" + getUploadDate() + "'" +
            ", antibiotic='" + getAntibiotic() + "'" +
            ", organism='" + getOrganism() + "'" +
            ", intrinsicResistance='" + getIntrinsicResistance() + "'" +
            ", expertRule='" + getExpertRule() + "'" +
            ", breakPoint='" + getBreakPoint() + "'" +
            "}";
    }
}
