package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.ExpertInterpretationRules} entity.
 */
public class ExpertInterpretationRulesDTO implements Serializable {

    private Long id;

    private String ruleCode;

    private String description;

    private String organismCode;

    private String organismCodeType;

    private String ruleCriteria;

    private String affectedAntibiotics;

    private String antibioticExceptions;

    private String result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganismCode() {
        return organismCode;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getOrganismCodeType() {
        return organismCodeType;
    }

    public void setOrganismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public String getRuleCriteria() {
        return ruleCriteria;
    }

    public void setRuleCriteria(String ruleCriteria) {
        this.ruleCriteria = ruleCriteria;
    }

    public String getAffectedAntibiotics() {
        return affectedAntibiotics;
    }

    public void setAffectedAntibiotics(String affectedAntibiotics) {
        this.affectedAntibiotics = affectedAntibiotics;
    }

    public String getAntibioticExceptions() {
        return antibioticExceptions;
    }

    public void setAntibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpertInterpretationRulesDTO)) {
            return false;
        }

        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = (ExpertInterpretationRulesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, expertInterpretationRulesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpertInterpretationRulesDTO{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", organismCode='" + getOrganismCode() + "'" +
            ", organismCodeType='" + getOrganismCodeType() + "'" +
            ", ruleCriteria='" + getRuleCriteria() + "'" +
            ", affectedAntibiotics='" + getAffectedAntibiotics() + "'" +
            ", antibioticExceptions='" + getAntibioticExceptions() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }
}
