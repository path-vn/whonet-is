package org.path.amr.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organism.
 */
@Entity
@Table(name = "organism")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organism implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "whonet_org_code")
    private String whonetOrgCode;

    @Column(name = "organism")
    private String organism;

    @Column(name = "taxonomic_status")
    private String taxonomicStatus;

    @Column(name = "common")
    private String common;

    @Column(name = "organism_type")
    private String organismType;

    @Column(name = "anaerobe")
    private String anaerobe;

    @Column(name = "morphology")
    private String morphology;

    @Column(name = "subkingdom_code")
    private String subkingdomCode;

    @Column(name = "family_code")
    private String familyCode;

    @Column(name = "genus_group")
    private String genusGroup;

    @Column(name = "genus_code")
    private String genusCode;

    @Column(name = "species_group")
    private String speciesGroup;

    @Column(name = "serovar_group")
    private String serovarGroup;

    @Column(name = "msf_grp_clin")
    private String msfGrpClin;

    @Column(name = "sct_code")
    private String sctCode;

    @Column(name = "sct_text")
    private String sctText;

    @Column(name = "dwc_taxon_id")
    private String dwcTaxonId;

    @Column(name = "dwc_taxonomic_status")
    private String dwcTaxonomicStatus;

    @Column(name = "gbif_taxon_id")
    private String gbifTaxonId;

    @Column(name = "gbif_dataset_id")
    private String gbifDatasetId;

    @Column(name = "gbif_taxonomic_status")
    private String gbifTaxonomicStatus;

    @Column(name = "kingdom")
    private String kingdom;

    @Column(name = "phylum")
    private String phylum;

    @Column(name = "organism_class")
    private String organismClass;

    @Column(name = "jhi_order")
    private String order;

    @Column(name = "family")
    private String family;

    @Column(name = "genus")
    private String genus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organism id(Long id) {
        this.id = id;
        return this;
    }

    public String getWhonetOrgCode() {
        return this.whonetOrgCode;
    }

    public Organism whonetOrgCode(String whonetOrgCode) {
        this.whonetOrgCode = whonetOrgCode;
        return this;
    }

    public void setWhonetOrgCode(String whonetOrgCode) {
        this.whonetOrgCode = whonetOrgCode;
    }

    public String getOrganism() {
        return this.organism;
    }

    public Organism organism(String organism) {
        this.organism = organism;
        return this;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getTaxonomicStatus() {
        return this.taxonomicStatus;
    }

    public Organism taxonomicStatus(String taxonomicStatus) {
        this.taxonomicStatus = taxonomicStatus;
        return this;
    }

    public void setTaxonomicStatus(String taxonomicStatus) {
        this.taxonomicStatus = taxonomicStatus;
    }

    public String getCommon() {
        return this.common;
    }

    public Organism common(String common) {
        this.common = common;
        return this;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getOrganismType() {
        return this.organismType;
    }

    public Organism organismType(String organismType) {
        this.organismType = organismType;
        return this;
    }

    public void setOrganismType(String organismType) {
        this.organismType = organismType;
    }

    public String getAnaerobe() {
        return this.anaerobe;
    }

    public Organism anaerobe(String anaerobe) {
        this.anaerobe = anaerobe;
        return this;
    }

    public void setAnaerobe(String anaerobe) {
        this.anaerobe = anaerobe;
    }

    public String getMorphology() {
        return this.morphology;
    }

    public Organism morphology(String morphology) {
        this.morphology = morphology;
        return this;
    }

    public void setMorphology(String morphology) {
        this.morphology = morphology;
    }

    public String getSubkingdomCode() {
        return this.subkingdomCode;
    }

    public Organism subkingdomCode(String subkingdomCode) {
        this.subkingdomCode = subkingdomCode;
        return this;
    }

    public void setSubkingdomCode(String subkingdomCode) {
        this.subkingdomCode = subkingdomCode;
    }

    public String getFamilyCode() {
        return this.familyCode;
    }

    public Organism familyCode(String familyCode) {
        this.familyCode = familyCode;
        return this;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getGenusGroup() {
        return this.genusGroup;
    }

    public Organism genusGroup(String genusGroup) {
        this.genusGroup = genusGroup;
        return this;
    }

    public void setGenusGroup(String genusGroup) {
        this.genusGroup = genusGroup;
    }

    public String getGenusCode() {
        return this.genusCode;
    }

    public Organism genusCode(String genusCode) {
        this.genusCode = genusCode;
        return this;
    }

    public void setGenusCode(String genusCode) {
        this.genusCode = genusCode;
    }

    public String getSpeciesGroup() {
        return this.speciesGroup;
    }

    public Organism speciesGroup(String speciesGroup) {
        this.speciesGroup = speciesGroup;
        return this;
    }

    public void setSpeciesGroup(String speciesGroup) {
        this.speciesGroup = speciesGroup;
    }

    public String getSerovarGroup() {
        return this.serovarGroup;
    }

    public Organism serovarGroup(String serovarGroup) {
        this.serovarGroup = serovarGroup;
        return this;
    }

    public void setSerovarGroup(String serovarGroup) {
        this.serovarGroup = serovarGroup;
    }

    public String getMsfGrpClin() {
        return this.msfGrpClin;
    }

    public Organism msfGrpClin(String msfGrpClin) {
        this.msfGrpClin = msfGrpClin;
        return this;
    }

    public void setMsfGrpClin(String msfGrpClin) {
        this.msfGrpClin = msfGrpClin;
    }

    public String getSctCode() {
        return this.sctCode;
    }

    public Organism sctCode(String sctCode) {
        this.sctCode = sctCode;
        return this;
    }

    public void setSctCode(String sctCode) {
        this.sctCode = sctCode;
    }

    public String getSctText() {
        return this.sctText;
    }

    public Organism sctText(String sctText) {
        this.sctText = sctText;
        return this;
    }

    public void setSctText(String sctText) {
        this.sctText = sctText;
    }

    public String getDwcTaxonId() {
        return this.dwcTaxonId;
    }

    public Organism dwcTaxonId(String dwcTaxonId) {
        this.dwcTaxonId = dwcTaxonId;
        return this;
    }

    public void setDwcTaxonId(String dwcTaxonId) {
        this.dwcTaxonId = dwcTaxonId;
    }

    public String getDwcTaxonomicStatus() {
        return this.dwcTaxonomicStatus;
    }

    public Organism dwcTaxonomicStatus(String dwcTaxonomicStatus) {
        this.dwcTaxonomicStatus = dwcTaxonomicStatus;
        return this;
    }

    public void setDwcTaxonomicStatus(String dwcTaxonomicStatus) {
        this.dwcTaxonomicStatus = dwcTaxonomicStatus;
    }

    public String getGbifTaxonId() {
        return this.gbifTaxonId;
    }

    public Organism gbifTaxonId(String gbifTaxonId) {
        this.gbifTaxonId = gbifTaxonId;
        return this;
    }

    public void setGbifTaxonId(String gbifTaxonId) {
        this.gbifTaxonId = gbifTaxonId;
    }

    public String getGbifDatasetId() {
        return this.gbifDatasetId;
    }

    public Organism gbifDatasetId(String gbifDatasetId) {
        this.gbifDatasetId = gbifDatasetId;
        return this;
    }

    public void setGbifDatasetId(String gbifDatasetId) {
        this.gbifDatasetId = gbifDatasetId;
    }

    public String getGbifTaxonomicStatus() {
        return this.gbifTaxonomicStatus;
    }

    public Organism gbifTaxonomicStatus(String gbifTaxonomicStatus) {
        this.gbifTaxonomicStatus = gbifTaxonomicStatus;
        return this;
    }

    public void setGbifTaxonomicStatus(String gbifTaxonomicStatus) {
        this.gbifTaxonomicStatus = gbifTaxonomicStatus;
    }

    public String getKingdom() {
        return this.kingdom;
    }

    public Organism kingdom(String kingdom) {
        this.kingdom = kingdom;
        return this;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getPhylum() {
        return this.phylum;
    }

    public Organism phylum(String phylum) {
        this.phylum = phylum;
        return this;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    public String getOrganismClass() {
        return this.organismClass;
    }

    public Organism organismClass(String organismClass) {
        this.organismClass = organismClass;
        return this;
    }

    public void setOrganismClass(String organismClass) {
        this.organismClass = organismClass;
    }

    public String getOrder() {
        return this.order;
    }

    public Organism order(String order) {
        this.order = order;
        return this;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return this.family;
    }

    public Organism family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return this.genus;
    }

    public Organism genus(String genus) {
        this.genus = genus;
        return this;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organism)) {
            return false;
        }
        return id != null && id.equals(((Organism) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organism{" +
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
