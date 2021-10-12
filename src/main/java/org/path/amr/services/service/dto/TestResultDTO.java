package org.path.amr.services.service.dto;

import java.util.ArrayList;
import java.util.List;
import org.path.amr.services.service.InterpretationResult;

public class TestResultDTO {

    String method;
    Double value;
    String rawValue;
    short oper;
    List<InterpretationResult> result;
    String orgCode;

    public TestResultDTO() {
        result = new ArrayList<>();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public short getOper() {
        return oper;
    }

    public void setOper(short oper) {
        this.oper = oper;
    }

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

    public List<InterpretationResult> getResult() {
        return result;
    }

    public void setResult(List<InterpretationResult> result) {
        this.result = result;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addResult(String result) {
        this.result.add(new InterpretationResult(result));
    }

    public void addResult(InterpretationResult interpretation) {
        this.result.add(interpretation);
    }
}
