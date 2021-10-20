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
 * Criteria class for the {@link org.path.amr.services.domain.ExpertInterpretationRules} entity. This class is used
 * in {@link org.path.amr.services.web.rest.ExpertInterpretationRulesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /expert-interpretation-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExpertInterpretationRulesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ruleCode;

    private StringFilter description;

    private StringFilter organismCode;

    private StringFilter organismCodeType;

    private StringFilter ruleCriteria;

    private StringFilter affectedAntibiotics;

    private StringFilter antibioticExceptions;

    public ExpertInterpretationRulesCriteria() {}

    public ExpertInterpretationRulesCriteria(ExpertInterpretationRulesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ruleCode = other.ruleCode == null ? null : other.ruleCode.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.organismCode = other.organismCode == null ? null : other.organismCode.copy();
        this.organismCodeType = other.organismCodeType == null ? null : other.organismCodeType.copy();
        this.ruleCriteria = other.ruleCriteria == null ? null : other.ruleCriteria.copy();
        this.affectedAntibiotics = other.affectedAntibiotics == null ? null : other.affectedAntibiotics.copy();
        this.antibioticExceptions = other.antibioticExceptions == null ? null : other.antibioticExceptions.copy();
    }

    @Override
    public ExpertInterpretationRulesCriteria copy() {
        return new ExpertInterpretationRulesCriteria(this);
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

    public StringFilter getRuleCode() {
        return ruleCode;
    }

    public StringFilter ruleCode() {
        if (ruleCode == null) {
            ruleCode = new StringFilter();
        }
        return ruleCode;
    }

    public void setRuleCode(StringFilter ruleCode) {
        this.ruleCode = ruleCode;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getOrganismCode() {
        return organismCode;
    }

    public StringFilter organismCode() {
        if (organismCode == null) {
            organismCode = new StringFilter();
        }
        return organismCode;
    }

    public void setOrganismCode(StringFilter organismCode) {
        this.organismCode = organismCode;
    }

    public StringFilter getOrganismCodeType() {
        return organismCodeType;
    }

    public StringFilter organismCodeType() {
        if (organismCodeType == null) {
            organismCodeType = new StringFilter();
        }
        return organismCodeType;
    }

    public void setOrganismCodeType(StringFilter organismCodeType) {
        this.organismCodeType = organismCodeType;
    }

    public StringFilter getRuleCriteria() {
        return ruleCriteria;
    }

    public StringFilter ruleCriteria() {
        if (ruleCriteria == null) {
            ruleCriteria = new StringFilter();
        }
        return ruleCriteria;
    }

    public void setRuleCriteria(StringFilter ruleCriteria) {
        this.ruleCriteria = ruleCriteria;
    }

    public StringFilter getAffectedAntibiotics() {
        return affectedAntibiotics;
    }

    public StringFilter affectedAntibiotics() {
        if (affectedAntibiotics == null) {
            affectedAntibiotics = new StringFilter();
        }
        return affectedAntibiotics;
    }

    public void setAffectedAntibiotics(StringFilter affectedAntibiotics) {
        this.affectedAntibiotics = affectedAntibiotics;
    }

    public StringFilter getAntibioticExceptions() {
        return antibioticExceptions;
    }

    public StringFilter antibioticExceptions() {
        if (antibioticExceptions == null) {
            antibioticExceptions = new StringFilter();
        }
        return antibioticExceptions;
    }

    public void setAntibioticExceptions(StringFilter antibioticExceptions) {
        this.antibioticExceptions = antibioticExceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpertInterpretationRulesCriteria that = (ExpertInterpretationRulesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ruleCode, that.ruleCode) &&
            Objects.equals(description, that.description) &&
            Objects.equals(organismCode, that.organismCode) &&
            Objects.equals(organismCodeType, that.organismCodeType) &&
            Objects.equals(ruleCriteria, that.ruleCriteria) &&
            Objects.equals(affectedAntibiotics, that.affectedAntibiotics) &&
            Objects.equals(antibioticExceptions, that.antibioticExceptions)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            ruleCode,
            description,
            organismCode,
            organismCodeType,
            ruleCriteria,
            affectedAntibiotics,
            antibioticExceptions
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpertInterpretationRulesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ruleCode != null ? "ruleCode=" + ruleCode + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (organismCode != null ? "organismCode=" + organismCode + ", " : "") +
            (organismCodeType != null ? "organismCodeType=" + organismCodeType + ", " : "") +
            (ruleCriteria != null ? "ruleCriteria=" + ruleCriteria + ", " : "") +
            (affectedAntibiotics != null ? "affectedAntibiotics=" + affectedAntibiotics + ", " : "") +
            (antibioticExceptions != null ? "antibioticExceptions=" + antibioticExceptions + ", " : "") +
            "}";
    }
}
