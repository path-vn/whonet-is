package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.Antibiotic} entity.
 */
public class AntibioticDTO implements Serializable {

    private Long id;

    private String whonetAbxCode;

    private String whoCode;

    private String dinCode;

    private String jacCode;

    private String eucastCode;

    private String userCode;

    private String antibiotic;

    private String guidelines;

    private String clsi;

    private String eucast;

    private String sfm;

    private String srga;

    private String bsac;

    private String din;

    private String neo;

    private String afa;

    private String abxNumber;

    private String potency;

    private String atcCode;

    private String profClass;

    private String ciaCategory;

    private String clsiOrder;

    private String eucastOrder;

    private String human;

    private String veterinary;

    private String animalGp;

    private String loinccomp;

    private String loincgen;

    private String loincdisk;

    private String loincmic;

    private String loincetest;

    private String loincslow;

    private String loincafb;

    private String loincsbt;

    private String loincmlc;

    private String dateEntered;

    private String dateModified;

    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhonetAbxCode() {
        return whonetAbxCode;
    }

    public void setWhonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public String getWhoCode() {
        return whoCode;
    }

    public void setWhoCode(String whoCode) {
        this.whoCode = whoCode;
    }

    public String getDinCode() {
        return dinCode;
    }

    public void setDinCode(String dinCode) {
        this.dinCode = dinCode;
    }

    public String getJacCode() {
        return jacCode;
    }

    public void setJacCode(String jacCode) {
        this.jacCode = jacCode;
    }

    public String getEucastCode() {
        return eucastCode;
    }

    public void setEucastCode(String eucastCode) {
        this.eucastCode = eucastCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAntibiotic() {
        return antibiotic;
    }

    public void setAntibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
    }

    public String getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public String getClsi() {
        return clsi;
    }

    public void setClsi(String clsi) {
        this.clsi = clsi;
    }

    public String getEucast() {
        return eucast;
    }

    public void setEucast(String eucast) {
        this.eucast = eucast;
    }

    public String getSfm() {
        return sfm;
    }

    public void setSfm(String sfm) {
        this.sfm = sfm;
    }

    public String getSrga() {
        return srga;
    }

    public void setSrga(String srga) {
        this.srga = srga;
    }

    public String getBsac() {
        return bsac;
    }

    public void setBsac(String bsac) {
        this.bsac = bsac;
    }

    public String getDin() {
        return din;
    }

    public void setDin(String din) {
        this.din = din;
    }

    public String getNeo() {
        return neo;
    }

    public void setNeo(String neo) {
        this.neo = neo;
    }

    public String getAfa() {
        return afa;
    }

    public void setAfa(String afa) {
        this.afa = afa;
    }

    public String getAbxNumber() {
        return abxNumber;
    }

    public void setAbxNumber(String abxNumber) {
        this.abxNumber = abxNumber;
    }

    public String getPotency() {
        return potency;
    }

    public void setPotency(String potency) {
        this.potency = potency;
    }

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getProfClass() {
        return profClass;
    }

    public void setProfClass(String profClass) {
        this.profClass = profClass;
    }

    public String getCiaCategory() {
        return ciaCategory;
    }

    public void setCiaCategory(String ciaCategory) {
        this.ciaCategory = ciaCategory;
    }

    public String getClsiOrder() {
        return clsiOrder;
    }

    public void setClsiOrder(String clsiOrder) {
        this.clsiOrder = clsiOrder;
    }

    public String getEucastOrder() {
        return eucastOrder;
    }

    public void setEucastOrder(String eucastOrder) {
        this.eucastOrder = eucastOrder;
    }

    public String getHuman() {
        return human;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public String getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(String veterinary) {
        this.veterinary = veterinary;
    }

    public String getAnimalGp() {
        return animalGp;
    }

    public void setAnimalGp(String animalGp) {
        this.animalGp = animalGp;
    }

    public String getLoinccomp() {
        return loinccomp;
    }

    public void setLoinccomp(String loinccomp) {
        this.loinccomp = loinccomp;
    }

    public String getLoincgen() {
        return loincgen;
    }

    public void setLoincgen(String loincgen) {
        this.loincgen = loincgen;
    }

    public String getLoincdisk() {
        return loincdisk;
    }

    public void setLoincdisk(String loincdisk) {
        this.loincdisk = loincdisk;
    }

    public String getLoincmic() {
        return loincmic;
    }

    public void setLoincmic(String loincmic) {
        this.loincmic = loincmic;
    }

    public String getLoincetest() {
        return loincetest;
    }

    public void setLoincetest(String loincetest) {
        this.loincetest = loincetest;
    }

    public String getLoincslow() {
        return loincslow;
    }

    public void setLoincslow(String loincslow) {
        this.loincslow = loincslow;
    }

    public String getLoincafb() {
        return loincafb;
    }

    public void setLoincafb(String loincafb) {
        this.loincafb = loincafb;
    }

    public String getLoincsbt() {
        return loincsbt;
    }

    public void setLoincsbt(String loincsbt) {
        this.loincsbt = loincsbt;
    }

    public String getLoincmlc() {
        return loincmlc;
    }

    public void setLoincmlc(String loincmlc) {
        this.loincmlc = loincmlc;
    }

    public String getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AntibioticDTO)) {
            return false;
        }

        AntibioticDTO antibioticDTO = (AntibioticDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, antibioticDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AntibioticDTO{" +
            "id=" + getId() +
            ", whonetAbxCode='" + getWhonetAbxCode() + "'" +
            ", whoCode='" + getWhoCode() + "'" +
            ", dinCode='" + getDinCode() + "'" +
            ", jacCode='" + getJacCode() + "'" +
            ", eucastCode='" + getEucastCode() + "'" +
            ", userCode='" + getUserCode() + "'" +
            ", antibiotic='" + getAntibiotic() + "'" +
            ", guidelines='" + getGuidelines() + "'" +
            ", clsi='" + getClsi() + "'" +
            ", eucast='" + getEucast() + "'" +
            ", sfm='" + getSfm() + "'" +
            ", srga='" + getSrga() + "'" +
            ", bsac='" + getBsac() + "'" +
            ", din='" + getDin() + "'" +
            ", neo='" + getNeo() + "'" +
            ", afa='" + getAfa() + "'" +
            ", abxNumber='" + getAbxNumber() + "'" +
            ", potency='" + getPotency() + "'" +
            ", atcCode='" + getAtcCode() + "'" +
            ", profClass='" + getProfClass() + "'" +
            ", ciaCategory='" + getCiaCategory() + "'" +
            ", clsiOrder='" + getClsiOrder() + "'" +
            ", eucastOrder='" + getEucastOrder() + "'" +
            ", human='" + getHuman() + "'" +
            ", veterinary='" + getVeterinary() + "'" +
            ", animalGp='" + getAnimalGp() + "'" +
            ", loinccomp='" + getLoinccomp() + "'" +
            ", loincgen='" + getLoincgen() + "'" +
            ", loincdisk='" + getLoincdisk() + "'" +
            ", loincmic='" + getLoincmic() + "'" +
            ", loincetest='" + getLoincetest() + "'" +
            ", loincslow='" + getLoincslow() + "'" +
            ", loincafb='" + getLoincafb() + "'" +
            ", loincsbt='" + getLoincsbt() + "'" +
            ", loincmlc='" + getLoincmlc() + "'" +
            ", dateEntered='" + getDateEntered() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
