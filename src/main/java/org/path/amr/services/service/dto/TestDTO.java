package org.path.amr.services.service.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.path.amr.services.service.InterpretationResult;

public class TestDTO {

    String rawValue;
    String whonet5Code;

    Double value;
    short oper;
    AntibioticDTO antibiotic;
    String expertRuleCode;
    String testID;

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

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
        if (interpretationResult == null || interpretationResult.getResult() == null) {
            return;
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

    public void sort(Map<String, Integer> priority) {
        if (this.result != null && this.result.size() > 1 && priority != null) {
            this.result.sort(
                    new Comparator<InterpretationResult>() {
                        @Override
                        public int compare(InterpretationResult o1, InterpretationResult o2) {
                            if (!o1.getOrganismCodeType().equals(o2.getOrganismCodeType())) {
                                return o1.getPriority() - o2.getPriority();
                            }
                            if (!priority.containsKey(o1.getSpecType()) || !priority.containsKey(o2.getSpecType())) {
                                return 0;
                            }
                            if (priority.get(o1.getSpecType()).equals(priority.get(o2.getSpecType()))) {
                                return (
                                    (o1.getBreaking() == null || o1.getBreaking().equals("") ? 0 : 1) -
                                    (o2.getBreaking() == null || o2.getBreaking().equals("") ? 0 : 1)
                                );
                            }
                            return priority.get(o1.getSpecType()) < priority.get(o2.getSpecType()) ? -1 : 1;
                        }
                    }
                );
        }
    }
}
