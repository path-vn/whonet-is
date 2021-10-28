package org.path.amr.services.service.dto;

import java.util.ArrayList;
import java.util.List;
import org.path.amr.services.service.InterpretationResult;

public class TestDTO {

    Double value;
    String rawValue;
    short oper;
    AntibioticDTO antibiotic;
    String whonet5Code;
    String expertRuleCode;

    List<InterpretationResult> result;
    List<OrganismIntrinsicResistanceAntibioticDTO> intrinsicResistance;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public short getOper() {
        return oper;
    }

    public void setOper(short oper) {
        this.oper = oper;
    }

    public AntibioticDTO getAntibiotic() {
        return antibiotic;
    }

    public void setAntibiotic(AntibioticDTO antibiotic) {
        this.antibiotic = antibiotic;
    }

    public List<InterpretationResult> getResult() {
        return result;
    }

    public void setResult(List<InterpretationResult> result) {
        this.result = result;
    }

    public List<OrganismIntrinsicResistanceAntibioticDTO> getIntrinsicResistance() {
        return intrinsicResistance;
    }

    public void setIntrinsicResistance(List<OrganismIntrinsicResistanceAntibioticDTO> intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
    }

    public void addResult(String result) {
        if (this.result == null) {
            this.result = new ArrayList<>();
        }
        this.result.add(new InterpretationResult(result));
    }

    public String getWhonet5Code() {
        return whonet5Code;
    }

    public void setWhonet5Code(String whonet5Code) {
        this.whonet5Code = whonet5Code;
    }

    public void addResult(InterpretationResult interpretationResult) {
        if (this.result == null) {
            this.result = new ArrayList<>();
        }
        for (int i = 0; i < result.size(); i++) {
            if (
                result.get(i).getResult().equals(interpretationResult.getResult()) &&
                result.get(i).getBreaking().equals(interpretationResult.getBreaking())
            ) {
                return;
            }
        }
        this.result.add(interpretationResult);
    }

    public String getExpertRuleCode() {
        return expertRuleCode;
    }

    public void setExpertRuleCode(String expertRuleCode) {
        this.expertRuleCode = expertRuleCode;
    }
}
