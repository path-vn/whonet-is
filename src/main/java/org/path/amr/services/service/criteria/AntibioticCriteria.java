package org.path.amr.services.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.path.amr.services.domain.Antibiotic} entity. This class is used
 * in {@link org.path.amr.services.web.rest.AntibioticResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /antibiotics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AntibioticCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter whonetAbxCode;

    private StringFilter whoCode;

    private StringFilter dinCode;

    private StringFilter jacCode;

    private StringFilter eucastCode;

    private StringFilter userCode;

    private StringFilter antibiotic;

    private StringFilter guidelines;

    private StringFilter antiboticClass;

    private StringFilter clsi;

    private StringFilter eucast;

    private StringFilter sfm;

    private StringFilter srga;

    private StringFilter bsac;

    private StringFilter din;

    private StringFilter neo;

    private StringFilter afa;

    private StringFilter abxNumber;

    private StringFilter potency;

    private StringFilter atcCode;

    private StringFilter profClass;

    private StringFilter ciaCategory;

    private StringFilter clsiOrder;

    private StringFilter eucastOrder;

    private StringFilter human;

    private StringFilter veterinary;

    private StringFilter animalGp;

    private StringFilter loinccomp;

    private StringFilter loincgen;

    private StringFilter loincdisk;

    private StringFilter loincmic;

    private StringFilter loincetest;

    private StringFilter loincslow;

    private StringFilter loincafb;

    private StringFilter loincsbt;

    private StringFilter loincmlc;

    private StringFilter dateEntered;

    private StringFilter dateModified;

    private StringFilter comments;

    public AntibioticCriteria() {}

    public AntibioticCriteria(AntibioticCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.whonetAbxCode = other.whonetAbxCode == null ? null : other.whonetAbxCode.copy();
        this.whoCode = other.whoCode == null ? null : other.whoCode.copy();
        this.dinCode = other.dinCode == null ? null : other.dinCode.copy();
        this.jacCode = other.jacCode == null ? null : other.jacCode.copy();
        this.eucastCode = other.eucastCode == null ? null : other.eucastCode.copy();
        this.userCode = other.userCode == null ? null : other.userCode.copy();
        this.antibiotic = other.antibiotic == null ? null : other.antibiotic.copy();
        this.guidelines = other.guidelines == null ? null : other.guidelines.copy();
        this.antiboticClass = other.antiboticClass == null ? null : other.antiboticClass.copy();
        this.clsi = other.clsi == null ? null : other.clsi.copy();
        this.eucast = other.eucast == null ? null : other.eucast.copy();
        this.sfm = other.sfm == null ? null : other.sfm.copy();
        this.srga = other.srga == null ? null : other.srga.copy();
        this.bsac = other.bsac == null ? null : other.bsac.copy();
        this.din = other.din == null ? null : other.din.copy();
        this.neo = other.neo == null ? null : other.neo.copy();
        this.afa = other.afa == null ? null : other.afa.copy();
        this.abxNumber = other.abxNumber == null ? null : other.abxNumber.copy();
        this.potency = other.potency == null ? null : other.potency.copy();
        this.atcCode = other.atcCode == null ? null : other.atcCode.copy();
        this.profClass = other.profClass == null ? null : other.profClass.copy();
        this.ciaCategory = other.ciaCategory == null ? null : other.ciaCategory.copy();
        this.clsiOrder = other.clsiOrder == null ? null : other.clsiOrder.copy();
        this.eucastOrder = other.eucastOrder == null ? null : other.eucastOrder.copy();
        this.human = other.human == null ? null : other.human.copy();
        this.veterinary = other.veterinary == null ? null : other.veterinary.copy();
        this.animalGp = other.animalGp == null ? null : other.animalGp.copy();
        this.loinccomp = other.loinccomp == null ? null : other.loinccomp.copy();
        this.loincgen = other.loincgen == null ? null : other.loincgen.copy();
        this.loincdisk = other.loincdisk == null ? null : other.loincdisk.copy();
        this.loincmic = other.loincmic == null ? null : other.loincmic.copy();
        this.loincetest = other.loincetest == null ? null : other.loincetest.copy();
        this.loincslow = other.loincslow == null ? null : other.loincslow.copy();
        this.loincafb = other.loincafb == null ? null : other.loincafb.copy();
        this.loincsbt = other.loincsbt == null ? null : other.loincsbt.copy();
        this.loincmlc = other.loincmlc == null ? null : other.loincmlc.copy();
        this.dateEntered = other.dateEntered == null ? null : other.dateEntered.copy();
        this.dateModified = other.dateModified == null ? null : other.dateModified.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
    }

    @Override
    public AntibioticCriteria copy() {
        return new AntibioticCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getWhonetAbxCode() {
        return whonetAbxCode;
    }

    public StringFilter whonetAbxCode() {
        if (whonetAbxCode == null) {
            whonetAbxCode = new StringFilter();
        }
        return whonetAbxCode;
    }

    public void setWhonetAbxCode(StringFilter whonetAbxCode) {
        this.whonetAbxCode = whonetAbxCode;
    }

    public StringFilter getWhoCode() {
        return whoCode;
    }

    public StringFilter whoCode() {
        if (whoCode == null) {
            whoCode = new StringFilter();
        }
        return whoCode;
    }

    public void setWhoCode(StringFilter whoCode) {
        this.whoCode = whoCode;
    }

    public StringFilter getDinCode() {
        return dinCode;
    }

    public StringFilter dinCode() {
        if (dinCode == null) {
            dinCode = new StringFilter();
        }
        return dinCode;
    }

    public void setDinCode(StringFilter dinCode) {
        this.dinCode = dinCode;
    }

    public StringFilter getJacCode() {
        return jacCode;
    }

    public StringFilter jacCode() {
        if (jacCode == null) {
            jacCode = new StringFilter();
        }
        return jacCode;
    }

    public void setJacCode(StringFilter jacCode) {
        this.jacCode = jacCode;
    }

    public StringFilter getEucastCode() {
        return eucastCode;
    }

    public StringFilter eucastCode() {
        if (eucastCode == null) {
            eucastCode = new StringFilter();
        }
        return eucastCode;
    }

    public void setEucastCode(StringFilter eucastCode) {
        this.eucastCode = eucastCode;
    }

    public StringFilter getUserCode() {
        return userCode;
    }

    public StringFilter userCode() {
        if (userCode == null) {
            userCode = new StringFilter();
        }
        return userCode;
    }

    public void setUserCode(StringFilter userCode) {
        this.userCode = userCode;
    }

    public StringFilter getAntibiotic() {
        return antibiotic;
    }

    public StringFilter antibiotic() {
        if (antibiotic == null) {
            antibiotic = new StringFilter();
        }
        return antibiotic;
    }

    public void setAntibiotic(StringFilter antibiotic) {
        this.antibiotic = antibiotic;
    }

    public StringFilter getGuidelines() {
        return guidelines;
    }

    public StringFilter guidelines() {
        if (guidelines == null) {
            guidelines = new StringFilter();
        }
        return guidelines;
    }

    public void setGuidelines(StringFilter guidelines) {
        this.guidelines = guidelines;
    }

    public StringFilter getAntiboticClass() {
        return antiboticClass;
    }

    public StringFilter antiboticClass() {
        if (antiboticClass == null) {
            antiboticClass = new StringFilter();
        }
        return antiboticClass;
    }

    public void setAntiboticClass(StringFilter antiboticClass) {
        this.antiboticClass = antiboticClass;
    }

    public StringFilter getClsi() {
        return clsi;
    }

    public StringFilter clsi() {
        if (clsi == null) {
            clsi = new StringFilter();
        }
        return clsi;
    }

    public void setClsi(StringFilter clsi) {
        this.clsi = clsi;
    }

    public StringFilter getEucast() {
        return eucast;
    }

    public StringFilter eucast() {
        if (eucast == null) {
            eucast = new StringFilter();
        }
        return eucast;
    }

    public void setEucast(StringFilter eucast) {
        this.eucast = eucast;
    }

    public StringFilter getSfm() {
        return sfm;
    }

    public StringFilter sfm() {
        if (sfm == null) {
            sfm = new StringFilter();
        }
        return sfm;
    }

    public void setSfm(StringFilter sfm) {
        this.sfm = sfm;
    }

    public StringFilter getSrga() {
        return srga;
    }

    public StringFilter srga() {
        if (srga == null) {
            srga = new StringFilter();
        }
        return srga;
    }

    public void setSrga(StringFilter srga) {
        this.srga = srga;
    }

    public StringFilter getBsac() {
        return bsac;
    }

    public StringFilter bsac() {
        if (bsac == null) {
            bsac = new StringFilter();
        }
        return bsac;
    }

    public void setBsac(StringFilter bsac) {
        this.bsac = bsac;
    }

    public StringFilter getDin() {
        return din;
    }

    public StringFilter din() {
        if (din == null) {
            din = new StringFilter();
        }
        return din;
    }

    public void setDin(StringFilter din) {
        this.din = din;
    }

    public StringFilter getNeo() {
        return neo;
    }

    public StringFilter neo() {
        if (neo == null) {
            neo = new StringFilter();
        }
        return neo;
    }

    public void setNeo(StringFilter neo) {
        this.neo = neo;
    }

    public StringFilter getAfa() {
        return afa;
    }

    public StringFilter afa() {
        if (afa == null) {
            afa = new StringFilter();
        }
        return afa;
    }

    public void setAfa(StringFilter afa) {
        this.afa = afa;
    }

    public StringFilter getAbxNumber() {
        return abxNumber;
    }

    public StringFilter abxNumber() {
        if (abxNumber == null) {
            abxNumber = new StringFilter();
        }
        return abxNumber;
    }

    public void setAbxNumber(StringFilter abxNumber) {
        this.abxNumber = abxNumber;
    }

    public StringFilter getPotency() {
        return potency;
    }

    public StringFilter potency() {
        if (potency == null) {
            potency = new StringFilter();
        }
        return potency;
    }

    public void setPotency(StringFilter potency) {
        this.potency = potency;
    }

    public StringFilter getAtcCode() {
        return atcCode;
    }

    public StringFilter atcCode() {
        if (atcCode == null) {
            atcCode = new StringFilter();
        }
        return atcCode;
    }

    public void setAtcCode(StringFilter atcCode) {
        this.atcCode = atcCode;
    }

    public StringFilter getProfClass() {
        return profClass;
    }

    public StringFilter profClass() {
        if (profClass == null) {
            profClass = new StringFilter();
        }
        return profClass;
    }

    public void setProfClass(StringFilter profClass) {
        this.profClass = profClass;
    }

    public StringFilter getCiaCategory() {
        return ciaCategory;
    }

    public StringFilter ciaCategory() {
        if (ciaCategory == null) {
            ciaCategory = new StringFilter();
        }
        return ciaCategory;
    }

    public void setCiaCategory(StringFilter ciaCategory) {
        this.ciaCategory = ciaCategory;
    }

    public StringFilter getClsiOrder() {
        return clsiOrder;
    }

    public StringFilter clsiOrder() {
        if (clsiOrder == null) {
            clsiOrder = new StringFilter();
        }
        return clsiOrder;
    }

    public void setClsiOrder(StringFilter clsiOrder) {
        this.clsiOrder = clsiOrder;
    }

    public StringFilter getEucastOrder() {
        return eucastOrder;
    }

    public StringFilter eucastOrder() {
        if (eucastOrder == null) {
            eucastOrder = new StringFilter();
        }
        return eucastOrder;
    }

    public void setEucastOrder(StringFilter eucastOrder) {
        this.eucastOrder = eucastOrder;
    }

    public StringFilter getHuman() {
        return human;
    }

    public StringFilter human() {
        if (human == null) {
            human = new StringFilter();
        }
        return human;
    }

    public void setHuman(StringFilter human) {
        this.human = human;
    }

    public StringFilter getVeterinary() {
        return veterinary;
    }

    public StringFilter veterinary() {
        if (veterinary == null) {
            veterinary = new StringFilter();
        }
        return veterinary;
    }

    public void setVeterinary(StringFilter veterinary) {
        this.veterinary = veterinary;
    }

    public StringFilter getAnimalGp() {
        return animalGp;
    }

    public StringFilter animalGp() {
        if (animalGp == null) {
            animalGp = new StringFilter();
        }
        return animalGp;
    }

    public void setAnimalGp(StringFilter animalGp) {
        this.animalGp = animalGp;
    }

    public StringFilter getLoinccomp() {
        return loinccomp;
    }

    public StringFilter loinccomp() {
        if (loinccomp == null) {
            loinccomp = new StringFilter();
        }
        return loinccomp;
    }

    public void setLoinccomp(StringFilter loinccomp) {
        this.loinccomp = loinccomp;
    }

    public StringFilter getLoincgen() {
        return loincgen;
    }

    public StringFilter loincgen() {
        if (loincgen == null) {
            loincgen = new StringFilter();
        }
        return loincgen;
    }

    public void setLoincgen(StringFilter loincgen) {
        this.loincgen = loincgen;
    }

    public StringFilter getLoincdisk() {
        return loincdisk;
    }

    public StringFilter loincdisk() {
        if (loincdisk == null) {
            loincdisk = new StringFilter();
        }
        return loincdisk;
    }

    public void setLoincdisk(StringFilter loincdisk) {
        this.loincdisk = loincdisk;
    }

    public StringFilter getLoincmic() {
        return loincmic;
    }

    public StringFilter loincmic() {
        if (loincmic == null) {
            loincmic = new StringFilter();
        }
        return loincmic;
    }

    public void setLoincmic(StringFilter loincmic) {
        this.loincmic = loincmic;
    }

    public StringFilter getLoincetest() {
        return loincetest;
    }

    public StringFilter loincetest() {
        if (loincetest == null) {
            loincetest = new StringFilter();
        }
        return loincetest;
    }

    public void setLoincetest(StringFilter loincetest) {
        this.loincetest = loincetest;
    }

    public StringFilter getLoincslow() {
        return loincslow;
    }

    public StringFilter loincslow() {
        if (loincslow == null) {
            loincslow = new StringFilter();
        }
        return loincslow;
    }

    public void setLoincslow(StringFilter loincslow) {
        this.loincslow = loincslow;
    }

    public StringFilter getLoincafb() {
        return loincafb;
    }

    public StringFilter loincafb() {
        if (loincafb == null) {
            loincafb = new StringFilter();
        }
        return loincafb;
    }

    public void setLoincafb(StringFilter loincafb) {
        this.loincafb = loincafb;
    }

    public StringFilter getLoincsbt() {
        return loincsbt;
    }

    public StringFilter loincsbt() {
        if (loincsbt == null) {
            loincsbt = new StringFilter();
        }
        return loincsbt;
    }

    public void setLoincsbt(StringFilter loincsbt) {
        this.loincsbt = loincsbt;
    }

    public StringFilter getLoincmlc() {
        return loincmlc;
    }

    public StringFilter loincmlc() {
        if (loincmlc == null) {
            loincmlc = new StringFilter();
        }
        return loincmlc;
    }

    public void setLoincmlc(StringFilter loincmlc) {
        this.loincmlc = loincmlc;
    }

    public StringFilter getDateEntered() {
        return dateEntered;
    }

    public StringFilter dateEntered() {
        if (dateEntered == null) {
            dateEntered = new StringFilter();
        }
        return dateEntered;
    }

    public void setDateEntered(StringFilter dateEntered) {
        this.dateEntered = dateEntered;
    }

    public StringFilter getDateModified() {
        return dateModified;
    }

    public StringFilter dateModified() {
        if (dateModified == null) {
            dateModified = new StringFilter();
        }
        return dateModified;
    }

    public void setDateModified(StringFilter dateModified) {
        this.dateModified = dateModified;
    }

    public StringFilter getComments() {
        return comments;
    }

    public StringFilter comments() {
        if (comments == null) {
            comments = new StringFilter();
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AntibioticCriteria that = (AntibioticCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(whonetAbxCode, that.whonetAbxCode) &&
            Objects.equals(whoCode, that.whoCode) &&
            Objects.equals(dinCode, that.dinCode) &&
            Objects.equals(jacCode, that.jacCode) &&
            Objects.equals(eucastCode, that.eucastCode) &&
            Objects.equals(userCode, that.userCode) &&
            Objects.equals(antibiotic, that.antibiotic) &&
            Objects.equals(guidelines, that.guidelines) &&
            Objects.equals(antiboticClass, that.antiboticClass) &&
            Objects.equals(clsi, that.clsi) &&
            Objects.equals(eucast, that.eucast) &&
            Objects.equals(sfm, that.sfm) &&
            Objects.equals(srga, that.srga) &&
            Objects.equals(bsac, that.bsac) &&
            Objects.equals(din, that.din) &&
            Objects.equals(neo, that.neo) &&
            Objects.equals(afa, that.afa) &&
            Objects.equals(abxNumber, that.abxNumber) &&
            Objects.equals(potency, that.potency) &&
            Objects.equals(atcCode, that.atcCode) &&
            Objects.equals(profClass, that.profClass) &&
            Objects.equals(ciaCategory, that.ciaCategory) &&
            Objects.equals(clsiOrder, that.clsiOrder) &&
            Objects.equals(eucastOrder, that.eucastOrder) &&
            Objects.equals(human, that.human) &&
            Objects.equals(veterinary, that.veterinary) &&
            Objects.equals(animalGp, that.animalGp) &&
            Objects.equals(loinccomp, that.loinccomp) &&
            Objects.equals(loincgen, that.loincgen) &&
            Objects.equals(loincdisk, that.loincdisk) &&
            Objects.equals(loincmic, that.loincmic) &&
            Objects.equals(loincetest, that.loincetest) &&
            Objects.equals(loincslow, that.loincslow) &&
            Objects.equals(loincafb, that.loincafb) &&
            Objects.equals(loincsbt, that.loincsbt) &&
            Objects.equals(loincmlc, that.loincmlc) &&
            Objects.equals(dateEntered, that.dateEntered) &&
            Objects.equals(dateModified, that.dateModified) &&
            Objects.equals(comments, that.comments)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            whonetAbxCode,
            whoCode,
            dinCode,
            jacCode,
            eucastCode,
            userCode,
            antibiotic,
            guidelines,
            antiboticClass,
            clsi,
            eucast,
            sfm,
            srga,
            bsac,
            din,
            neo,
            afa,
            abxNumber,
            potency,
            atcCode,
            profClass,
            ciaCategory,
            clsiOrder,
            eucastOrder,
            human,
            veterinary,
            animalGp,
            loinccomp,
            loincgen,
            loincdisk,
            loincmic,
            loincetest,
            loincslow,
            loincafb,
            loincsbt,
            loincmlc,
            dateEntered,
            dateModified,
            comments
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AntibioticCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (whonetAbxCode != null ? "whonetAbxCode=" + whonetAbxCode + ", " : "") +
            (whoCode != null ? "whoCode=" + whoCode + ", " : "") +
            (dinCode != null ? "dinCode=" + dinCode + ", " : "") +
            (jacCode != null ? "jacCode=" + jacCode + ", " : "") +
            (eucastCode != null ? "eucastCode=" + eucastCode + ", " : "") +
            (userCode != null ? "userCode=" + userCode + ", " : "") +
            (antibiotic != null ? "antibiotic=" + antibiotic + ", " : "") +
            (guidelines != null ? "guidelines=" + guidelines + ", " : "") +
            (antiboticClass != null ? "antiboticClass=" + antiboticClass + ", " : "") +
            (clsi != null ? "clsi=" + clsi + ", " : "") +
            (eucast != null ? "eucast=" + eucast + ", " : "") +
            (sfm != null ? "sfm=" + sfm + ", " : "") +
            (srga != null ? "srga=" + srga + ", " : "") +
            (bsac != null ? "bsac=" + bsac + ", " : "") +
            (din != null ? "din=" + din + ", " : "") +
            (neo != null ? "neo=" + neo + ", " : "") +
            (afa != null ? "afa=" + afa + ", " : "") +
            (abxNumber != null ? "abxNumber=" + abxNumber + ", " : "") +
            (potency != null ? "potency=" + potency + ", " : "") +
            (atcCode != null ? "atcCode=" + atcCode + ", " : "") +
            (profClass != null ? "profClass=" + profClass + ", " : "") +
            (ciaCategory != null ? "ciaCategory=" + ciaCategory + ", " : "") +
            (clsiOrder != null ? "clsiOrder=" + clsiOrder + ", " : "") +
            (eucastOrder != null ? "eucastOrder=" + eucastOrder + ", " : "") +
            (human != null ? "human=" + human + ", " : "") +
            (veterinary != null ? "veterinary=" + veterinary + ", " : "") +
            (animalGp != null ? "animalGp=" + animalGp + ", " : "") +
            (loinccomp != null ? "loinccomp=" + loinccomp + ", " : "") +
            (loincgen != null ? "loincgen=" + loincgen + ", " : "") +
            (loincdisk != null ? "loincdisk=" + loincdisk + ", " : "") +
            (loincmic != null ? "loincmic=" + loincmic + ", " : "") +
            (loincetest != null ? "loincetest=" + loincetest + ", " : "") +
            (loincslow != null ? "loincslow=" + loincslow + ", " : "") +
            (loincafb != null ? "loincafb=" + loincafb + ", " : "") +
            (loincsbt != null ? "loincsbt=" + loincsbt + ", " : "") +
            (loincmlc != null ? "loincmlc=" + loincmlc + ", " : "") +
            (dateEntered != null ? "dateEntered=" + dateEntered + ", " : "") +
            (dateModified != null ? "dateModified=" + dateModified + ", " : "") +
            (comments != null ? "comments=" + comments + ", " : "") +
            "}";
    }
}
