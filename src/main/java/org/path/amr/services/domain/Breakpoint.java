package org.path.amr.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Breakpoint.
 */
@Entity
@Table(name = "breakpoint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Breakpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "query")
    private String query;

    @Column(name = "antibiotic_query")
    private String antibioticQuery;

    @Column(name = "organism_query")
    private String organismQuery;

    @Column(name = "intrinsic_resistance_query")
    private String intrinsicResistanceQuery;

    @Lob
    @Column(name = "binary_data")
    private byte[] binaryData;

    @Column(name = "binary_data_content_type")
    private String binaryDataContentType;

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

    public String getPath() {
        return this.path;
    }

    public Breakpoint path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return this.query;
    }

    public Breakpoint query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAntibioticQuery() {
        return this.antibioticQuery;
    }

    public Breakpoint antibioticQuery(String antibioticQuery) {
        this.antibioticQuery = antibioticQuery;
        return this;
    }

    public void setAntibioticQuery(String antibioticQuery) {
        this.antibioticQuery = antibioticQuery;
    }

    public String getOrganismQuery() {
        return this.organismQuery;
    }

    public Breakpoint organismQuery(String organismQuery) {
        this.organismQuery = organismQuery;
        return this;
    }

    public void setOrganismQuery(String organismQuery) {
        this.organismQuery = organismQuery;
    }

    public String getIntrinsicResistanceQuery() {
        return this.intrinsicResistanceQuery;
    }

    public Breakpoint intrinsicResistanceQuery(String intrinsicResistanceQuery) {
        this.intrinsicResistanceQuery = intrinsicResistanceQuery;
        return this;
    }

    public void setIntrinsicResistanceQuery(String intrinsicResistanceQuery) {
        this.intrinsicResistanceQuery = intrinsicResistanceQuery;
    }

    public byte[] getBinaryData() {
        return this.binaryData;
    }

    public Breakpoint binaryData(byte[] binaryData) {
        this.binaryData = binaryData;
        return this;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public String getBinaryDataContentType() {
        return this.binaryDataContentType;
    }

    public Breakpoint binaryDataContentType(String binaryDataContentType) {
        this.binaryDataContentType = binaryDataContentType;
        return this;
    }

    public void setBinaryDataContentType(String binaryDataContentType) {
        this.binaryDataContentType = binaryDataContentType;
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
            ", path='" + getPath() + "'" +
            ", query='" + getQuery() + "'" +
            ", antibioticQuery='" + getAntibioticQuery() + "'" +
            ", organismQuery='" + getOrganismQuery() + "'" +
            ", intrinsicResistanceQuery='" + getIntrinsicResistanceQuery() + "'" +
            ", binaryData='" + getBinaryData() + "'" +
            ", binaryDataContentType='" + getBinaryDataContentType() + "'" +
            "}";
    }
}
