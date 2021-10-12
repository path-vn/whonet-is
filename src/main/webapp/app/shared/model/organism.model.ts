export interface IOrganism {
  id?: number;
  whonetOrgCode?: string | null;
  organism?: string | null;
  taxonomicStatus?: string | null;
  common?: string | null;
  organismType?: string | null;
  anaerobe?: string | null;
  morphology?: string | null;
  subkingdomCode?: string | null;
  familyCode?: string | null;
  genusGroup?: string | null;
  genusCode?: string | null;
  speciesGroup?: string | null;
  serovarGroup?: string | null;
  msfGrpClin?: string | null;
  sctCode?: string | null;
  sctText?: string | null;
  dwcTaxonId?: string | null;
  dwcTaxonomicStatus?: string | null;
  gbifTaxonId?: string | null;
  gbifDatasetId?: string | null;
  gbifTaxonomicStatus?: string | null;
  kingdom?: string | null;
  phylum?: string | null;
  organismClass?: string | null;
  order?: string | null;
  family?: string | null;
  genus?: string | null;
}

export const defaultValue: Readonly<IOrganism> = {};
