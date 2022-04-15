package org.path.amr.services.service;

public class InterpretationResult {

    Short isQuestionMark;
    String result;
    String specType;
    String breaking = "";

    public InterpretationResult(String result) {
        this.result = result;
    }

    public InterpretationResult() {}

    public Short getIsQuestionMark() {
        return isQuestionMark;
    }

    public void setIsQuestionMark(Short isQuestionMark) {
        this.isQuestionMark = isQuestionMark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getBreaking() {
        return breaking;
    }

    public void setBreaking(String breaking) {
        this.breaking = breaking;
    }
}
