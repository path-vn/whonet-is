package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link org.path.amr.services.domain.WhonetResource} entity.
 */
public class WhonetResourceDTO implements Serializable {

    private Long id;

    private ZonedDateTime uploadDate;

    private String antibiotic;

    private String organism;

    private String intrinsicResistance;

    private String expertRule;

    private String breakPoint;

    private String status;

    private ZonedDateTime importedDate;

    @Lob
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAntibiotic() {
        return antibiotic;
    }

    public void setAntibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getIntrinsicResistance() {
        return intrinsicResistance;
    }

    public void setIntrinsicResistance(String intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
    }

    public String getExpertRule() {
        return expertRule;
    }

    public void setExpertRule(String expertRule) {
        this.expertRule = expertRule;
    }

    public String getBreakPoint() {
        return breakPoint;
    }

    public void setBreakPoint(String breakPoint) {
        this.breakPoint = breakPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(ZonedDateTime importedDate) {
        this.importedDate = importedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhonetResourceDTO)) {
            return false;
        }

        WhonetResourceDTO whonetResourceDTO = (WhonetResourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, whonetResourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WhonetResourceDTO{" +
            "id=" + getId() +
            ", uploadDate='" + getUploadDate() + "'" +
            ", antibiotic='" + getAntibiotic() + "'" +
            ", organism='" + getOrganism() + "'" +
            ", intrinsicResistance='" + getIntrinsicResistance() + "'" +
            ", expertRule='" + getExpertRule() + "'" +
            ", breakPoint='" + getBreakPoint() + "'" +
            ", status='" + getStatus() + "'" +
            ", importedDate='" + getImportedDate() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
