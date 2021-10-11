package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link org.path.amr.services.domain.Breakpoint} entity.
 */
public class BreakpointDTO implements Serializable {

    private Long id;

    private String path;

    private String query;

    private String antibioticQuery;

    private String organismQuery;

    private String intrinsicResistanceQuery;

    @Lob
    private byte[] binaryData;

    private String binaryDataContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAntibioticQuery() {
        return antibioticQuery;
    }

    public void setAntibioticQuery(String antibioticQuery) {
        this.antibioticQuery = antibioticQuery;
    }

    public String getOrganismQuery() {
        return organismQuery;
    }

    public void setOrganismQuery(String organismQuery) {
        this.organismQuery = organismQuery;
    }

    public String getIntrinsicResistanceQuery() {
        return intrinsicResistanceQuery;
    }

    public void setIntrinsicResistanceQuery(String intrinsicResistanceQuery) {
        this.intrinsicResistanceQuery = intrinsicResistanceQuery;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public String getBinaryDataContentType() {
        return binaryDataContentType;
    }

    public void setBinaryDataContentType(String binaryDataContentType) {
        this.binaryDataContentType = binaryDataContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BreakpointDTO)) {
            return false;
        }

        BreakpointDTO breakpointDTO = (BreakpointDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, breakpointDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreakpointDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", query='" + getQuery() + "'" +
            ", antibioticQuery='" + getAntibioticQuery() + "'" +
            ", organismQuery='" + getOrganismQuery() + "'" +
            ", intrinsicResistanceQuery='" + getIntrinsicResistanceQuery() + "'" +
            ", binaryData='" + getBinaryData() + "'" +
            "}";
    }
}
