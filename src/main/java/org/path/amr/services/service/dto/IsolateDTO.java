package org.path.amr.services.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsolateDTO {

    String method;
    String orgCode;
    String breakpointType; // Human or Animal
    String specType; //...
    OrganismDTO organism;
    List<TestDTO> test;
    Map<String, String> dataFields;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public OrganismDTO getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismDTO organism) {
        this.organism = organism;
    }

    public List<TestDTO> getTest() {
        if (test == null) {
            return new ArrayList<>();
        }
        return test;
    }

    public void setTest(List<TestDTO> test) {
        this.test = test;
    }

    public Map<String, String> getDataFields() {
        if (dataFields == null) {
            dataFields = new HashMap<>();
        }
        return dataFields;
    }

    public void setDataFields(Map<String, String> dataFields) {
        this.dataFields = dataFields;
    }

    public String getBreakpointType() {
        return breakpointType;
    }

    public void setBreakpointType(String breakpointType) {
        this.breakpointType = breakpointType;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public void addTest(TestDTO test) {
        if (this.test == null) {
            this.test = new ArrayList<>();
        }
        this.test.add(test);
    }
}
