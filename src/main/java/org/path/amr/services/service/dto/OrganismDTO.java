package org.path.amr.services.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.path.amr.services.domain.Organism} entity.
 */
public class OrganismDTO implements Serializable {

    private Long id;

    private String whonetOrgCode;

    private String organism;

    private String taxonomicStatus;

    private String common;

    private String organismType;

    private String anaerobe;

    private String morphology;

    private String subkingdomCode;

    private String familyCode;

    private String genusGroup;

    private String genusCode;

    private String speciesGroup;

    private String serovarGroup;

    private String msfGrpClin;

    private String sctCode;

    private String sctText;

    private String dwcTaxonId;

    private String dwcTaxonomicStatus;

    private String gbifTaxonId;

    private String gbifDatasetId;

    private String gbifTaxonomicStatus;

    private String kingdom;

    private String phylum;

    private String organismClass;

    private String order;

    private String family;

    private String genus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhonetOrgCode() {
        return whonetOrgCode;
    }

    public void setWhonetOrgCode(String whonetOrgCode) {
        this.whonetOrgCode = whonetOrgCode;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getTaxonomicStatus() {
        return taxonomicStatus;
    }

    public void setTaxonomicStatus(String taxonomicStatus) {
        this.taxonomicStatus = taxonomicStatus;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOrganismType() {
        return organismType;
    }

    public void setOrganismType(String organismType) {
        this.organismType = organismType;
    }

    public String getAnaerobe() {
        return anaerobe;
    }

    public void setAnaerobe(String anaerobe) {
        this.anaerobe = anaerobe;
    }

    public String getMorphology() {
        return morphology;
    }

    public void setMorphology(String morphology) {
        this.morphology = morphology;
    }

    public String getSubkingdomCode() {
        return subkingdomCode;
    }

    public void setSubkingdomCode(String subkingdomCode) {
        this.subkingdomCode = subkingdomCode;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getGenusGroup() {
        return genusGroup;
    }

    public void setGenusGroup(String genusGroup) {
        this.genusGroup = genusGroup;
    }

    public String getGenusCode() {
        return genusCode;
    }

    public void setGenusCode(String genusCode) {
        this.genusCode = genusCode;
    }

    public String getSpeciesGroup() {
        return speciesGroup;
    }

    public void setSpeciesGroup(String speciesGroup) {
        this.speciesGroup = speciesGroup;
    }

    public String getSerovarGroup() {
        return serovarGroup;
    }

    public void setSerovarGroup(String serovarGroup) {
        this.serovarGroup = serovarGroup;
    }

    public String getMsfGrpClin() {
        return msfGrpClin;
    }

    public void setMsfGrpClin(String msfGrpClin) {
        this.msfGrpClin = msfGrpClin;
    }

    public String getSctCode() {
        return sctCode;
    }

    public void setSctCode(String sctCode) {
        this.sctCode = sctCode;
    }

    public String getSctText() {
        return sctText;
    }

    public void setSctText(String sctText) {
        this.sctText = sctText;
    }

    public String getDwcTaxonId() {
        return dwcTaxonId;
    }

    public void setDwcTaxonId(String dwcTaxonId) {
        this.dwcTaxonId = dwcTaxonId;
    }

    public String getDwcTaxonomicStatus() {
        return dwcTaxonomicStatus;
    }

    public void setDwcTaxonomicStatus(String dwcTaxonomicStatus) {
        this.dwcTaxonomicStatus = dwcTaxonomicStatus;
    }

    public String getGbifTaxonId() {
        return gbifTaxonId;
    }

    public void setGbifTaxonId(String gbifTaxonId) {
        this.gbifTaxonId = gbifTaxonId;
    }

    public String getGbifDatasetId() {
        return gbifDatasetId;
    }

    public void setGbifDatasetId(String gbifDatasetId) {
        this.gbifDatasetId = gbifDatasetId;
    }

    public String getGbifTaxonomicStatus() {
        return gbifTaxonomicStatus;
    }

    public void setGbifTaxonomicStatus(String gbifTaxonomicStatus) {
        this.gbifTaxonomicStatus = gbifTaxonomicStatus;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public String getOrganismClass() {
        return organismClass;
    }

    public void setOrganismClass(String organismClass) {
        this.organismClass = organismClass;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganismDTO)) {
            return false;
        }

        OrganismDTO organismDTO = (OrganismDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organismDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganismDTO{" +
            "id=" + getId() +
            ", whonetOrgCode='" + getWhonetOrgCode() + "'" +
            ", organism='" + getOrganism() + "'" +
            ", taxonomicStatus='" + getTaxonomicStatus() + "'" +
            ", common='" + getCommon() + "'" +
            ", organismType='" + getOrganismType() + "'" +
            ", anaerobe='" + getAnaerobe() + "'" +
            ", morphology='" + getMorphology() + "'" +
            ", subkingdomCode='" + getSubkingdomCode() + "'" +
            ", familyCode='" + getFamilyCode() + "'" +
            ", genusGroup='" + getGenusGroup() + "'" +
            ", genusCode='" + getGenusCode() + "'" +
            ", speciesGroup='" + getSpeciesGroup() + "'" +
            ", serovarGroup='" + getSerovarGroup() + "'" +
            ", msfGrpClin='" + getMsfGrpClin() + "'" +
            ", sctCode='" + getSctCode() + "'" +
            ", sctText='" + getSctText() + "'" +
            ", dwcTaxonId='" + getDwcTaxonId() + "'" +
            ", dwcTaxonomicStatus='" + getDwcTaxonomicStatus() + "'" +
            ", gbifTaxonId='" + getGbifTaxonId() + "'" +
            ", gbifDatasetId='" + getGbifDatasetId() + "'" +
            ", gbifTaxonomicStatus='" + getGbifTaxonomicStatus() + "'" +
            ", kingdom='" + getKingdom() + "'" +
            ", phylum='" + getPhylum() + "'" +
            ", organismClass='" + getOrganismClass() + "'" +
            ", order='" + getOrder() + "'" +
            ", family='" + getFamily() + "'" +
            ", genus='" + getGenus() + "'" +
            "}";
    }
}
