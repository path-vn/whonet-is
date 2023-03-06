package org.path.amr.services.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;

public class IsolateRequestDTO {

    @ApiModelProperty(value = "orgCode", example = "eco")
    String orgCode;

    @ApiModelProperty(value = "guidelines", example = "[\"CLSI\"]")
    List<String> guidelines;

    @ApiModelProperty(value = "year", example = "2022")
    Integer year;

    @ApiModelProperty(value = "dataFields", example = "{\"BETA_LACT\":\"+\"}")
    Map<String, String> dataFields;

    @ApiModelProperty(value = "requestID", example = "idToMapRequest")
    String requestID;

    List<TestRequestDTO> test;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public Map<String, String> getDataFields() {
        return dataFields;
    }

    public void setDataFields(Map<String, String> dataFields) {
        this.dataFields = dataFields;
    }

    public List<TestRequestDTO> getTest() {
        return test;
    }

    public void setTest(List<TestRequestDTO> test) {
        this.test = test;
    }
}
