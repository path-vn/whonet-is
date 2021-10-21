package org.path.amr.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Antibiotic.
 */
@Entity
@Table(name = "antibiotics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Antibiotic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "whonet_abx_code", columnDefinition = "TEXT")
    private String whonetAbxCode;

    @Column(name = "who_code", columnDefinition = "TEXT")
    private String whoCode;

    @Column(name = "din_code", columnDefinition = "TEXT")
    private String dinCode;

    @Column(name = "jac_code", columnDefinition = "TEXT")
    private String jacCode;

    @Column(name = "eucast_code", columnDefinition = "TEXT")
    private String eucastCode;

    @Column(name = "user_code", columnDefinition = "TEXT")
    private String userCode;

    @Column(name = "antibiotic", columnDefinition = "TEXT")
    private String antibiotic;

    @Column(name = "guidelines", columnDefinition = "TEXT")
    private String guidelines;

    @Column(name = "class", columnDefinition = "TEXT")
    private String antiboticClass;

    @Column(name = "clsi", columnDefinition = "TEXT")
    private String clsi;

    @Column(name = "eucast", columnDefinition = "TEXT")
    private String eucast;

    @Column(name = "sfm", columnDefinition = "TEXT")
    private String sfm;

    @Column(name = "srga", columnDefinition = "TEXT")
    private String srga;

    @Column(name = "bsac", columnDefinition = "TEXT")
    private String bsac;

    @Column(name = "din", columnDefinition = "TEXT")
    private String din;

    @Column(name = "neo", columnDefinition = "TEXT")
    private String neo;

    @Column(name = "afa", columnDefinition = "TEXT")
    private String afa;

    @Column(name = "abx_number", columnDefinition = "TEXT")
    private String abxNumber;

    @Column(name = "potency", columnDefinition = "TEXT")
    private String potency;

    @Column(name = "atc_code", columnDefinition = "TEXT")
    private String atcCode;

    @Column(name = "prof_class", columnDefinition = "TEXT")
    private String profClass;

    @Column(name = "cia_category", columnDefinition = "TEXT")
    private String ciaCategory;

    @Column(name = "clsi_order", columnDefinition = "TEXT")
    private String clsiOrder;

    @Column(name = "eucast_order", columnDefinition = "TEXT")
    private String eucastOrder;

    @Column(name = "human", columnDefinition = "TEXT")
    private String human;

    @Column(name = "veterinary", columnDefinition = "TEXT")
    private String veterinary;

    @Column(name = "animal_gp", columnDefinition = "TEXT")
    private String animalGp;

    @Column(name = "loinccomp", columnDefinition = "TEXT")
    private String loinccomp;

    @Column(name = "loincgen", columnDefinition = "TEXT")
    private String loincgen;

    @Column(name = "loincdisk", columnDefinition = "TEXT")
    private String loincdisk;

    @Column(name = "loincmic", columnDefinition = "TEXT")
    private String loincmic;

    @Column(name = "loincetest", columnDefinition = "TEXT")
    private String loincetest;

    @Column(name = "loincslow", columnDefinition = "TEXT")
    private String loincslow;

    @Column(name = "loincafb", columnDefinition = "TEXT")
    private String loincafb;

    @Column(name = "loincsbt", columnDefinition = "TEXT")
    private String loincsbt;

    @Column(name = "loincmlc", columnDefinition = "TEXT")
    private String loincmlc;

    @Column(name = "date_entered", columnDefinition = "TEXT")
    private String dateEntered;

    @Column(name = "date_modified", columnDefinition = "TEXT")
    private String dateModified;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Antibiotic id(Long id) {
        this.id = id;
        return this;
    }

    public String getWhonetAbxCode() {
        return this.whonetAbxCode;
    }

    public Antibiotic whonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
        return this;
    }

    public void setWhonetAbxCode(String whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public String getWhoCode() {
        return this.whoCode;
    }

    public Antibiotic whoCode(String whoCode) {
        this.whoCode = whoCode;
        return this;
    }

    public void setWhoCode(String whoCode) {
        this.whoCode = whoCode;
    }

    public String getDinCode() {
        return this.dinCode;
    }

    public Antibiotic dinCode(String dinCode) {
        this.dinCode = dinCode;
        return this;
    }

    public void setDinCode(String dinCode) {
        this.dinCode = dinCode;
    }

    public String getJacCode() {
        return this.jacCode;
    }

    public Antibiotic jacCode(String jacCode) {
        this.jacCode = jacCode;
        return this;
    }

    public void setJacCode(String jacCode) {
        this.jacCode = jacCode;
    }

    public String getEucastCode() {
        return this.eucastCode;
    }

    public Antibiotic eucastCode(String eucastCode) {
        this.eucastCode = eucastCode;
        return this;
    }

    public void setEucastCode(String eucastCode) {
        this.eucastCode = eucastCode;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public Antibiotic userCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAntibiotic() {
        return this.antibiotic;
    }

    public Antibiotic antibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
        return this;
    }

    public void setAntibiotic(String antibiotic) {
        this.antibiotic = antibiotic;
    }

    public String getGuidelines() {
        return this.guidelines;
    }

    public Antibiotic guidelines(String guidelines) {
        this.guidelines = guidelines;
        return this;
    }

    public void setGuidelines(String guidelines) {
        this.guidelines = guidelines;
    }

    public String getAntiboticClass() {
        return this.antiboticClass;
    }

    public Antibiotic antiboticClass(String antiboticClass) {
        this.antiboticClass = antiboticClass;
        return this;
    }

    public void setAntiboticClass(String antiboticClass) {
        this.antiboticClass = antiboticClass;
    }

    public String getClsi() {
        return this.clsi;
    }

    public Antibiotic clsi(String clsi) {
        this.clsi = clsi;
        return this;
    }

    public void setClsi(String clsi) {
        this.clsi = clsi;
    }

    public String getEucast() {
        return this.eucast;
    }

    public Antibiotic eucast(String eucast) {
        this.eucast = eucast;
        return this;
    }

    public void setEucast(String eucast) {
        this.eucast = eucast;
    }

    public String getSfm() {
        return this.sfm;
    }

    public Antibiotic sfm(String sfm) {
        this.sfm = sfm;
        return this;
    }

    public void setSfm(String sfm) {
        this.sfm = sfm;
    }

    public String getSrga() {
        return this.srga;
    }

    public Antibiotic srga(String srga) {
        this.srga = srga;
        return this;
    }

    public void setSrga(String srga) {
        this.srga = srga;
    }

    public String getBsac() {
        return this.bsac;
    }

    public Antibiotic bsac(String bsac) {
        this.bsac = bsac;
        return this;
    }

    public void setBsac(String bsac) {
        this.bsac = bsac;
    }

    public String getDin() {
        return this.din;
    }

    public Antibiotic din(String din) {
        this.din = din;
        return this;
    }

    public void setDin(String din) {
        this.din = din;
    }

    public String getNeo() {
        return this.neo;
    }

    public Antibiotic neo(String neo) {
        this.neo = neo;
        return this;
    }

    public void setNeo(String neo) {
        this.neo = neo;
    }

    public String getAfa() {
        return this.afa;
    }

    public Antibiotic afa(String afa) {
        this.afa = afa;
        return this;
    }

    public void setAfa(String afa) {
        this.afa = afa;
    }

    public String getAbxNumber() {
        return this.abxNumber;
    }

    public Antibiotic abxNumber(String abxNumber) {
        this.abxNumber = abxNumber;
        return this;
    }

    public void setAbxNumber(String abxNumber) {
        this.abxNumber = abxNumber;
    }

    public String getPotency() {
        return this.potency;
    }

    public Antibiotic potency(String potency) {
        this.potency = potency;
        return this;
    }

    public void setPotency(String potency) {
        this.potency = potency;
    }

    public String getAtcCode() {
        return this.atcCode;
    }

    public Antibiotic atcCode(String atcCode) {
        this.atcCode = atcCode;
        return this;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getProfClass() {
        return this.profClass;
    }

    public Antibiotic profClass(String profClass) {
        this.profClass = profClass;
        return this;
    }

    public void setProfClass(String profClass) {
        this.profClass = profClass;
    }

    public String getCiaCategory() {
        return this.ciaCategory;
    }

    public Antibiotic ciaCategory(String ciaCategory) {
        this.ciaCategory = ciaCategory;
        return this;
    }

    public void setCiaCategory(String ciaCategory) {
        this.ciaCategory = ciaCategory;
    }

    public String getClsiOrder() {
        return this.clsiOrder;
    }

    public Antibiotic clsiOrder(String clsiOrder) {
        this.clsiOrder = clsiOrder;
        return this;
    }

    public void setClsiOrder(String clsiOrder) {
        this.clsiOrder = clsiOrder;
    }

    public String getEucastOrder() {
        return this.eucastOrder;
    }

    public Antibiotic eucastOrder(String eucastOrder) {
        this.eucastOrder = eucastOrder;
        return this;
    }

    public void setEucastOrder(String eucastOrder) {
        this.eucastOrder = eucastOrder;
    }

    public String getHuman() {
        return this.human;
    }

    public Antibiotic human(String human) {
        this.human = human;
        return this;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public String getVeterinary() {
        return this.veterinary;
    }

    public Antibiotic veterinary(String veterinary) {
        this.veterinary = veterinary;
        return this;
    }

    public void setVeterinary(String veterinary) {
        this.veterinary = veterinary;
    }

    public String getAnimalGp() {
        return this.animalGp;
    }

    public Antibiotic animalGp(String animalGp) {
        this.animalGp = animalGp;
        return this;
    }

    public void setAnimalGp(String animalGp) {
        this.animalGp = animalGp;
    }

    public String getLoinccomp() {
        return this.loinccomp;
    }

    public Antibiotic loinccomp(String loinccomp) {
        this.loinccomp = loinccomp;
        return this;
    }

    public void setLoinccomp(String loinccomp) {
        this.loinccomp = loinccomp;
    }

    public String getLoincgen() {
        return this.loincgen;
    }

    public Antibiotic loincgen(String loincgen) {
        this.loincgen = loincgen;
        return this;
    }

    public void setLoincgen(String loincgen) {
        this.loincgen = loincgen;
    }

    public String getLoincdisk() {
        return this.loincdisk;
    }

    public Antibiotic loincdisk(String loincdisk) {
        this.loincdisk = loincdisk;
        return this;
    }

    public void setLoincdisk(String loincdisk) {
        this.loincdisk = loincdisk;
    }

    public String getLoincmic() {
        return this.loincmic;
    }

    public Antibiotic loincmic(String loincmic) {
        this.loincmic = loincmic;
        return this;
    }

    public void setLoincmic(String loincmic) {
        this.loincmic = loincmic;
    }

    public String getLoincetest() {
        return this.loincetest;
    }

    public Antibiotic loincetest(String loincetest) {
        this.loincetest = loincetest;
        return this;
    }

    public void setLoincetest(String loincetest) {
        this.loincetest = loincetest;
    }

    public String getLoincslow() {
        return this.loincslow;
    }

    public Antibiotic loincslow(String loincslow) {
        this.loincslow = loincslow;
        return this;
    }

    public void setLoincslow(String loincslow) {
        this.loincslow = loincslow;
    }

    public String getLoincafb() {
        return this.loincafb;
    }

    public Antibiotic loincafb(String loincafb) {
        this.loincafb = loincafb;
        return this;
    }

    public void setLoincafb(String loincafb) {
        this.loincafb = loincafb;
    }

    public String getLoincsbt() {
        return this.loincsbt;
    }

    public Antibiotic loincsbt(String loincsbt) {
        this.loincsbt = loincsbt;
        return this;
    }

    public void setLoincsbt(String loincsbt) {
        this.loincsbt = loincsbt;
    }

    public String getLoincmlc() {
        return this.loincmlc;
    }

    public Antibiotic loincmlc(String loincmlc) {
        this.loincmlc = loincmlc;
        return this;
    }

    public void setLoincmlc(String loincmlc) {
        this.loincmlc = loincmlc;
    }

    public String getDateEntered() {
        return this.dateEntered;
    }

    public Antibiotic dateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
        return this;
    }

    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateModified() {
        return this.dateModified;
    }

    public Antibiotic dateModified(String dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getComments() {
        return this.comments;
    }

    public Antibiotic comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Antibiotic)) {
            return false;
        }
        return id != null && id.equals(((Antibiotic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Antibiotic{" +
            "id=" + getId() +
            ", whonetAbxCode='" + getWhonetAbxCode() + "'" +
            ", whoCode='" + getWhoCode() + "'" +
            ", dinCode='" + getDinCode() + "'" +
            ", jacCode='" + getJacCode() + "'" +
            ", eucastCode='" + getEucastCode() + "'" +
            ", userCode='" + getUserCode() + "'" +
            ", antibiotic='" + getAntibiotic() + "'" +
            ", guidelines='" + getGuidelines() + "'" +
            ", antiboticClass='" + getAntiboticClass() + "'" +
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
