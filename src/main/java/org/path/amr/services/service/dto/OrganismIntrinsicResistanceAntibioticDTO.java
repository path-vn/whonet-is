package org.path.amr.services.service.dto;

public class OrganismIntrinsicResistanceAntibioticDTO {

    String orgCode;
    String abxCode;
    Long antibioticId;
    Long intrinsicResistanceId;
    AntibioticDTO antibiotic;
    IntrinsicResistanceDTO intrinsicResistance;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAbxCode() {
        return abxCode;
    }

    public void setAbxCode(String abxCode) {
        this.abxCode = abxCode;
    }

    public Long getAntibioticId() {
        return antibioticId;
    }

    public void setAntibioticId(Long antibioticId) {
        this.antibioticId = antibioticId;
    }

    public Long getIntrinsicResistanceId() {
        return intrinsicResistanceId;
    }

    public void setIntrinsicResistanceId(Long intrinsicResistanceId) {
        this.intrinsicResistanceId = intrinsicResistanceId;
    }

    public AntibioticDTO getAntibiotic() {
        return antibiotic;
    }

    public void setAntibiotic(AntibioticDTO antibiotic) {
        this.antibiotic = antibiotic;
    }

    public IntrinsicResistanceDTO getIntrinsicResistance() {
        return intrinsicResistance;
    }

    public void setIntrinsicResistance(IntrinsicResistanceDTO intrinsicResistance) {
        this.intrinsicResistance = intrinsicResistance;
    }

    public String toString() {
        String log = "";
        if (intrinsicResistance != null) {
            log = intrinsicResistance.toString();
        }
        return "orgCode: " + orgCode + ", abxCode: " + abxCode + ", IntrinsicResistance:" + log;
    }
}
