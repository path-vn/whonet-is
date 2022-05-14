package org.path.amr.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExpertInterpretationRules.
 */
@Entity
@Table(name = "expert_interpretation_rules")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExpertInterpretationRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_code", columnDefinition = "TEXT")
    private String ruleCode;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "organism_code", columnDefinition = "TEXT")
    private String organismCode;

    @Column(name = "organism_code_type", columnDefinition = "TEXT")
    private String organismCodeType;

    @Column(name = "rule_criteria", columnDefinition = "TEXT")
    private String ruleCriteria;

    @Column(name = "affected_antibiotics", columnDefinition = "TEXT")
    private String affectedAntibiotics;

    @Column(name = "antibiotic_exceptions", columnDefinition = "TEXT")
    private String antibioticExceptions;

    @Column(name = "result")
    private String result;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpertInterpretationRules id(Long id) {
        this.id = id;
        return this;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public ExpertInterpretationRules ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDescription() {
        return this.description;
    }

    public ExpertInterpretationRules description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganismCode() {
        return this.organismCode;
    }

    public ExpertInterpretationRules organismCode(String organismCode) {
        this.organismCode = organismCode;
        return this;
    }

    public void setOrganismCode(String organismCode) {
        this.organismCode = organismCode;
    }

    public String getOrganismCodeType() {
        return this.organismCodeType;
    }

    public ExpertInterpretationRules organismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
        return this;
    }

    public void setOrganismCodeType(String organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public String getRuleCriteria() {
        return this.ruleCriteria;
    }

    public ExpertInterpretationRules ruleCriteria(String ruleCriteria) {
        this.ruleCriteria = ruleCriteria;
        return this;
    }

    public void setRuleCriteria(String ruleCriteria) {
        this.ruleCriteria = ruleCriteria;
    }

    public String getAffectedAntibiotics() {
        return this.affectedAntibiotics;
    }

    public ExpertInterpretationRules affectedAntibiotics(String affectedAntibiotics) {
        this.affectedAntibiotics = affectedAntibiotics;
        return this;
    }

    public void setAffectedAntibiotics(String affectedAntibiotics) {
        this.affectedAntibiotics = affectedAntibiotics;
    }

    public String getAntibioticExceptions() {
        return this.antibioticExceptions;
    }

    public ExpertInterpretationRules antibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
        return this;
    }

    public void setAntibioticExceptions(String antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
    }

    public String getResult() {
        return this.result;
    }

    public ExpertInterpretationRules result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpertInterpretationRules)) {
            return false;
        }
        return id != null && id.equals(((ExpertInterpretationRules) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpertInterpretationRules{" +
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
