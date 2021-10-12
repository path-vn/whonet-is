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
 * Criteria class for the {@link org.path.amr.services.domain.Organism} entity. This class is used
 * in {@link org.path.amr.services.web.rest.OrganismResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organisms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrganismCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter whonetOrgCode;

    private StringFilter organism;

    private StringFilter taxonomicStatus;

    private StringFilter common;

    private StringFilter organismType;

    private StringFilter anaerobe;

    private StringFilter morphology;

    private StringFilter subkingdomCode;

    private StringFilter familyCode;

    private StringFilter genusGroup;

    private StringFilter genusCode;

    private StringFilter speciesGroup;

    private StringFilter serovarGroup;

    private StringFilter msfGrpClin;

    private StringFilter sctCode;

    private StringFilter sctText;

    private StringFilter dwcTaxonId;

    private StringFilter dwcTaxonomicStatus;

    private StringFilter gbifTaxonId;

    private StringFilter gbifDatasetId;

    private StringFilter gbifTaxonomicStatus;

    private StringFilter kingdom;

    private StringFilter phylum;

    private StringFilter organismClass;

    private StringFilter order;

    private StringFilter family;

    private StringFilter genus;

    public OrganismCriteria() {}

    public OrganismCriteria(OrganismCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.whonetOrgCode = other.whonetOrgCode == null ? null : other.whonetOrgCode.copy();
        this.organism = other.organism == null ? null : other.organism.copy();
        this.taxonomicStatus = other.taxonomicStatus == null ? null : other.taxonomicStatus.copy();
        this.common = other.common == null ? null : other.common.copy();
        this.organismType = other.organismType == null ? null : other.organismType.copy();
        this.anaerobe = other.anaerobe == null ? null : other.anaerobe.copy();
        this.morphology = other.morphology == null ? null : other.morphology.copy();
        this.subkingdomCode = other.subkingdomCode == null ? null : other.subkingdomCode.copy();
        this.familyCode = other.familyCode == null ? null : other.familyCode.copy();
        this.genusGroup = other.genusGroup == null ? null : other.genusGroup.copy();
        this.genusCode = other.genusCode == null ? null : other.genusCode.copy();
        this.speciesGroup = other.speciesGroup == null ? null : other.speciesGroup.copy();
        this.serovarGroup = other.serovarGroup == null ? null : other.serovarGroup.copy();
        this.msfGrpClin = other.msfGrpClin == null ? null : other.msfGrpClin.copy();
        this.sctCode = other.sctCode == null ? null : other.sctCode.copy();
        this.sctText = other.sctText == null ? null : other.sctText.copy();
        this.dwcTaxonId = other.dwcTaxonId == null ? null : other.dwcTaxonId.copy();
        this.dwcTaxonomicStatus = other.dwcTaxonomicStatus == null ? null : other.dwcTaxonomicStatus.copy();
        this.gbifTaxonId = other.gbifTaxonId == null ? null : other.gbifTaxonId.copy();
        this.gbifDatasetId = other.gbifDatasetId == null ? null : other.gbifDatasetId.copy();
        this.gbifTaxonomicStatus = other.gbifTaxonomicStatus == null ? null : other.gbifTaxonomicStatus.copy();
        this.kingdom = other.kingdom == null ? null : other.kingdom.copy();
        this.phylum = other.phylum == null ? null : other.phylum.copy();
        this.organismClass = other.organismClass == null ? null : other.organismClass.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.family = other.family == null ? null : other.family.copy();
        this.genus = other.genus == null ? null : other.genus.copy();
    }

    @Override
    public OrganismCriteria copy() {
        return new OrganismCriteria(this);
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

    public StringFilter getWhonetOrgCode() {
        return whonetOrgCode;
    }

    public StringFilter whonetOrgCode() {
        if (whonetOrgCode == null) {
            whonetOrgCode = new StringFilter();
        }
        return whonetOrgCode;
    }

    public void setWhonetOrgCode(StringFilter whonetOrgCode) {
        this.whonetOrgCode = whonetOrgCode;
    }

    public StringFilter getOrganism() {
        return organism;
    }

    public StringFilter organism() {
        if (organism == null) {
            organism = new StringFilter();
        }
        return organism;
    }

    public void setOrganism(StringFilter organism) {
        this.organism = organism;
    }

    public StringFilter getTaxonomicStatus() {
        return taxonomicStatus;
    }

    public StringFilter taxonomicStatus() {
        if (taxonomicStatus == null) {
            taxonomicStatus = new StringFilter();
        }
        return taxonomicStatus;
    }

    public void setTaxonomicStatus(StringFilter taxonomicStatus) {
        this.taxonomicStatus = taxonomicStatus;
    }

    public StringFilter getCommon() {
        return common;
    }

    public StringFilter common() {
        if (common == null) {
            common = new StringFilter();
        }
        return common;
    }

    public void setCommon(StringFilter common) {
        this.common = common;
    }

    public StringFilter getOrganismType() {
        return organismType;
    }

    public StringFilter organismType() {
        if (organismType == null) {
            organismType = new StringFilter();
        }
        return organismType;
    }

    public void setOrganismType(StringFilter organismType) {
        this.organismType = organismType;
    }

    public StringFilter getAnaerobe() {
        return anaerobe;
    }

    public StringFilter anaerobe() {
        if (anaerobe == null) {
            anaerobe = new StringFilter();
        }
        return anaerobe;
    }

    public void setAnaerobe(StringFilter anaerobe) {
        this.anaerobe = anaerobe;
    }

    public StringFilter getMorphology() {
        return morphology;
    }

    public StringFilter morphology() {
        if (morphology == null) {
            morphology = new StringFilter();
        }
        return morphology;
    }

    public void setMorphology(StringFilter morphology) {
        this.morphology = morphology;
    }

    public StringFilter getSubkingdomCode() {
        return subkingdomCode;
    }

    public StringFilter subkingdomCode() {
        if (subkingdomCode == null) {
            subkingdomCode = new StringFilter();
        }
        return subkingdomCode;
    }

    public void setSubkingdomCode(StringFilter subkingdomCode) {
        this.subkingdomCode = subkingdomCode;
    }

    public StringFilter getFamilyCode() {
        return familyCode;
    }

    public StringFilter familyCode() {
        if (familyCode == null) {
            familyCode = new StringFilter();
        }
        return familyCode;
    }

    public void setFamilyCode(StringFilter familyCode) {
        this.familyCode = familyCode;
    }

    public StringFilter getGenusGroup() {
        return genusGroup;
    }

    public StringFilter genusGroup() {
        if (genusGroup == null) {
            genusGroup = new StringFilter();
        }
        return genusGroup;
    }

    public void setGenusGroup(StringFilter genusGroup) {
        this.genusGroup = genusGroup;
    }

    public StringFilter getGenusCode() {
        return genusCode;
    }

    public StringFilter genusCode() {
        if (genusCode == null) {
            genusCode = new StringFilter();
        }
        return genusCode;
    }

    public void setGenusCode(StringFilter genusCode) {
        this.genusCode = genusCode;
    }

    public StringFilter getSpeciesGroup() {
        return speciesGroup;
    }

    public StringFilter speciesGroup() {
        if (speciesGroup == null) {
            speciesGroup = new StringFilter();
        }
        return speciesGroup;
    }

    public void setSpeciesGroup(StringFilter speciesGroup) {
        this.speciesGroup = speciesGroup;
    }

    public StringFilter getSerovarGroup() {
        return serovarGroup;
    }

    public StringFilter serovarGroup() {
        if (serovarGroup == null) {
            serovarGroup = new StringFilter();
        }
        return serovarGroup;
    }

    public void setSerovarGroup(StringFilter serovarGroup) {
        this.serovarGroup = serovarGroup;
    }

    public StringFilter getMsfGrpClin() {
        return msfGrpClin;
    }

    public StringFilter msfGrpClin() {
        if (msfGrpClin == null) {
            msfGrpClin = new StringFilter();
        }
        return msfGrpClin;
    }

    public void setMsfGrpClin(StringFilter msfGrpClin) {
        this.msfGrpClin = msfGrpClin;
    }

    public StringFilter getSctCode() {
        return sctCode;
    }

    public StringFilter sctCode() {
        if (sctCode == null) {
            sctCode = new StringFilter();
        }
        return sctCode;
    }

    public void setSctCode(StringFilter sctCode) {
        this.sctCode = sctCode;
    }

    public StringFilter getSctText() {
        return sctText;
    }

    public StringFilter sctText() {
        if (sctText == null) {
            sctText = new StringFilter();
        }
        return sctText;
    }

    public void setSctText(StringFilter sctText) {
        this.sctText = sctText;
    }

    public StringFilter getDwcTaxonId() {
        return dwcTaxonId;
    }

    public StringFilter dwcTaxonId() {
        if (dwcTaxonId == null) {
            dwcTaxonId = new StringFilter();
        }
        return dwcTaxonId;
    }

    public void setDwcTaxonId(StringFilter dwcTaxonId) {
        this.dwcTaxonId = dwcTaxonId;
    }

    public StringFilter getDwcTaxonomicStatus() {
        return dwcTaxonomicStatus;
    }

    public StringFilter dwcTaxonomicStatus() {
        if (dwcTaxonomicStatus == null) {
            dwcTaxonomicStatus = new StringFilter();
        }
        return dwcTaxonomicStatus;
    }

    public void setDwcTaxonomicStatus(StringFilter dwcTaxonomicStatus) {
        this.dwcTaxonomicStatus = dwcTaxonomicStatus;
    }

    public StringFilter getGbifTaxonId() {
        return gbifTaxonId;
    }

    public StringFilter gbifTaxonId() {
        if (gbifTaxonId == null) {
            gbifTaxonId = new StringFilter();
        }
        return gbifTaxonId;
    }

    public void setGbifTaxonId(StringFilter gbifTaxonId) {
        this.gbifTaxonId = gbifTaxonId;
    }

    public StringFilter getGbifDatasetId() {
        return gbifDatasetId;
    }

    public StringFilter gbifDatasetId() {
        if (gbifDatasetId == null) {
            gbifDatasetId = new StringFilter();
        }
        return gbifDatasetId;
    }

    public void setGbifDatasetId(StringFilter gbifDatasetId) {
        this.gbifDatasetId = gbifDatasetId;
    }

    public StringFilter getGbifTaxonomicStatus() {
        return gbifTaxonomicStatus;
    }

    public StringFilter gbifTaxonomicStatus() {
        if (gbifTaxonomicStatus == null) {
            gbifTaxonomicStatus = new StringFilter();
        }
        return gbifTaxonomicStatus;
    }

    public void setGbifTaxonomicStatus(StringFilter gbifTaxonomicStatus) {
        this.gbifTaxonomicStatus = gbifTaxonomicStatus;
    }

    public StringFilter getKingdom() {
        return kingdom;
    }

    public StringFilter kingdom() {
        if (kingdom == null) {
            kingdom = new StringFilter();
        }
        return kingdom;
    }

    public void setKingdom(StringFilter kingdom) {
        this.kingdom = kingdom;
    }

    public StringFilter getPhylum() {
        return phylum;
    }

    public StringFilter phylum() {
        if (phylum == null) {
            phylum = new StringFilter();
        }
        return phylum;
    }

    public void setPhylum(StringFilter phylum) {
        this.phylum = phylum;
    }

    public StringFilter getOrganismClass() {
        return organismClass;
    }

    public StringFilter organismClass() {
        if (organismClass == null) {
            organismClass = new StringFilter();
        }
        return organismClass;
    }

    public void setOrganismClass(StringFilter organismClass) {
        this.organismClass = organismClass;
    }

    public StringFilter getOrder() {
        return order;
    }

    public StringFilter order() {
        if (order == null) {
            order = new StringFilter();
        }
        return order;
    }

    public void setOrder(StringFilter order) {
        this.order = order;
    }

    public StringFilter getFamily() {
        return family;
    }

    public StringFilter family() {
        if (family == null) {
            family = new StringFilter();
        }
        return family;
    }

    public void setFamily(StringFilter family) {
        this.family = family;
    }

    public StringFilter getGenus() {
        return genus;
    }

    public StringFilter genus() {
        if (genus == null) {
            genus = new StringFilter();
        }
        return genus;
    }

    public void setGenus(StringFilter genus) {
        this.genus = genus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrganismCriteria that = (OrganismCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(whonetOrgCode, that.whonetOrgCode) &&
            Objects.equals(organism, that.organism) &&
            Objects.equals(taxonomicStatus, that.taxonomicStatus) &&
            Objects.equals(common, that.common) &&
            Objects.equals(organismType, that.organismType) &&
            Objects.equals(anaerobe, that.anaerobe) &&
            Objects.equals(morphology, that.morphology) &&
            Objects.equals(subkingdomCode, that.subkingdomCode) &&
            Objects.equals(familyCode, that.familyCode) &&
            Objects.equals(genusGroup, that.genusGroup) &&
            Objects.equals(genusCode, that.genusCode) &&
            Objects.equals(speciesGroup, that.speciesGroup) &&
            Objects.equals(serovarGroup, that.serovarGroup) &&
            Objects.equals(msfGrpClin, that.msfGrpClin) &&
            Objects.equals(sctCode, that.sctCode) &&
            Objects.equals(sctText, that.sctText) &&
            Objects.equals(dwcTaxonId, that.dwcTaxonId) &&
            Objects.equals(dwcTaxonomicStatus, that.dwcTaxonomicStatus) &&
            Objects.equals(gbifTaxonId, that.gbifTaxonId) &&
            Objects.equals(gbifDatasetId, that.gbifDatasetId) &&
            Objects.equals(gbifTaxonomicStatus, that.gbifTaxonomicStatus) &&
            Objects.equals(kingdom, that.kingdom) &&
            Objects.equals(phylum, that.phylum) &&
            Objects.equals(organismClass, that.organismClass) &&
            Objects.equals(order, that.order) &&
            Objects.equals(family, that.family) &&
            Objects.equals(genus, that.genus)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            whonetOrgCode,
            organism,
            taxonomicStatus,
            common,
            organismType,
            anaerobe,
            morphology,
            subkingdomCode,
            familyCode,
            genusGroup,
            genusCode,
            speciesGroup,
            serovarGroup,
            msfGrpClin,
            sctCode,
            sctText,
            dwcTaxonId,
            dwcTaxonomicStatus,
            gbifTaxonId,
            gbifDatasetId,
            gbifTaxonomicStatus,
            kingdom,
            phylum,
            organismClass,
            order,
            family,
            genus
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganismCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (whonetOrgCode != null ? "whonetOrgCode=" + whonetOrgCode + ", " : "") +
            (organism != null ? "organism=" + organism + ", " : "") +
            (taxonomicStatus != null ? "taxonomicStatus=" + taxonomicStatus + ", " : "") +
            (common != null ? "common=" + common + ", " : "") +
            (organismType != null ? "organismType=" + organismType + ", " : "") +
            (anaerobe != null ? "anaerobe=" + anaerobe + ", " : "") +
            (morphology != null ? "morphology=" + morphology + ", " : "") +
            (subkingdomCode != null ? "subkingdomCode=" + subkingdomCode + ", " : "") +
            (familyCode != null ? "familyCode=" + familyCode + ", " : "") +
            (genusGroup != null ? "genusGroup=" + genusGroup + ", " : "") +
            (genusCode != null ? "genusCode=" + genusCode + ", " : "") +
            (speciesGroup != null ? "speciesGroup=" + speciesGroup + ", " : "") +
            (serovarGroup != null ? "serovarGroup=" + serovarGroup + ", " : "") +
            (msfGrpClin != null ? "msfGrpClin=" + msfGrpClin + ", " : "") +
            (sctCode != null ? "sctCode=" + sctCode + ", " : "") +
            (sctText != null ? "sctText=" + sctText + ", " : "") +
            (dwcTaxonId != null ? "dwcTaxonId=" + dwcTaxonId + ", " : "") +
            (dwcTaxonomicStatus != null ? "dwcTaxonomicStatus=" + dwcTaxonomicStatus + ", " : "") +
            (gbifTaxonId != null ? "gbifTaxonId=" + gbifTaxonId + ", " : "") +
            (gbifDatasetId != null ? "gbifDatasetId=" + gbifDatasetId + ", " : "") +
            (gbifTaxonomicStatus != null ? "gbifTaxonomicStatus=" + gbifTaxonomicStatus + ", " : "") +
            (kingdom != null ? "kingdom=" + kingdom + ", " : "") +
            (phylum != null ? "phylum=" + phylum + ", " : "") +
            (organismClass != null ? "organismClass=" + organismClass + ", " : "") +
            (order != null ? "order=" + order + ", " : "") +
            (family != null ? "family=" + family + ", " : "") +
            (genus != null ? "genus=" + genus + ", " : "") +
            "}";
    }
}
