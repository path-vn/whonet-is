package org.path.amr.services.service.dto;

import io.swagger.annotations.ApiModelProperty;

public class TestRequestDTO {

    @ApiModelProperty(value = "rawValue", example = ">6")
    String rawValue;

    @ApiModelProperty(value = "whonet5Code", example = "FOX_ND30")
    String whonet5Code;

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getWhonet5Code() {
        return whonet5Code;
    }

    public void setWhonet5Code(String whonet5Code) {
        this.whonet5Code = whonet5Code;
    }
}
