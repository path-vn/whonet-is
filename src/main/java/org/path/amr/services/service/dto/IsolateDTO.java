package org.path.amr.services.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsolateDTO {

    String requestID;
    String method;

    String orgCode;
    List<String> guidelines;
    Integer year;
    Map<String, String> dataFields;
    List<TestDTO> test;

    String breakpointType; // Human or Animal
    String host;
    String breakpointTypeOrder;
    String specType; // filter field site_of_infection, comma separated
    OrganismDTO organism;

    String organismCodeTypeOrder;

    public IsolateDTO() {
        dataFields = new HashMap<>();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBreakpointTypeOrder() {
        return breakpointTypeOrder;
    }

    public void setBreakpointTypeOrder(String breakpointTypeOrder) {
        this.breakpointTypeOrder = breakpointTypeOrder;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

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
        if (specType == null) {
            return "";
        }
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

    public List<String> getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(List<String> guidelines) {
        this.guidelines = guidelines;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOrganismCodeTypeOrder() {
        return organismCodeTypeOrder;
    }

    public void setOrganismCodeTypeOrder(String organismCodeTypeOrder) {
        this.organismCodeTypeOrder = organismCodeTypeOrder;
    }

    public void setDataFields(Map<String, Integer> headerMap, String[] columns) {
        dataFields.put("MRSA", getColumnIfExits(headerMap, columns, "MRSA"));
        dataFields.put("VRE", getColumnIfExits(headerMap, columns, "VRE"));
        dataFields.put("BETA_LACT", getColumnIfExits(headerMap, columns, "BETA_LACT"));
        dataFields.put("ESBL", getColumnIfExits(headerMap, columns, "ESBL"));
        dataFields.put("INDUC_CLI", getColumnIfExits(headerMap, columns, "INDUC_CLI"));
        dataFields.put("X_BLEE", getColumnIfExits(headerMap, columns, "X_BLEE"));
        dataFields.put("MECA_PCR", getColumnIfExits(headerMap, columns, "MECA_PCR"));
        dataFields.put("OXA_SCREEN", getColumnIfExits(headerMap, columns, "OXA_SCREEN"));
        dataFields.put("MRSA_SCRN", getColumnIfExits(headerMap, columns, "MRSA_SCRN"));
        dataFields.put("PBP2A_AGGL", getColumnIfExits(headerMap, columns, "PBP2A_AGGL"));
        dataFields.put("MLS_DTEST", getColumnIfExits(headerMap, columns, "MLS_DTEST"));
        dataFields.put("X_MLS", getColumnIfExits(headerMap, columns, "X_MLS"));
        dataFields.put("X_MLS_IND", getColumnIfExits(headerMap, columns, "X_MLS_IND"));
    }

    private String getColumnIfExits(Map<String, Integer> headerMap, String[] columns, String name) {
        String result = "";
        if (headerMap.containsKey(name)) {
            int index = headerMap.get(name);
            if (index >= 0 && index < columns.length) {
                result = columns[index];
            }
        }
        return result;
    }
}
