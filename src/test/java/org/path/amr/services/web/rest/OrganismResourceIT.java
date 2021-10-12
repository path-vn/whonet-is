package org.path.amr.services.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.amr.services.IntegrationTest;
import org.path.amr.services.domain.Organism;
import org.path.amr.services.repository.OrganismRepository;
import org.path.amr.services.service.criteria.OrganismCriteria;
import org.path.amr.services.service.dto.OrganismDTO;
import org.path.amr.services.service.mapper.OrganismMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganismResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganismResourceIT {

    private static final String DEFAULT_WHONET_ORG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WHONET_ORG_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM = "BBBBBBBBBB";

    private static final String DEFAULT_TAXONOMIC_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TAXONOMIC_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMON = "AAAAAAAAAA";
    private static final String UPDATED_COMMON = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ANAEROBE = "AAAAAAAAAA";
    private static final String UPDATED_ANAEROBE = "BBBBBBBBBB";

    private static final String DEFAULT_MORPHOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_MORPHOLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_SUBKINGDOM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBKINGDOM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GENUS_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GENUS_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_GENUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GENUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIES_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_SEROVAR_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_SEROVAR_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_MSF_GRP_CLIN = "AAAAAAAAAA";
    private static final String UPDATED_MSF_GRP_CLIN = "BBBBBBBBBB";

    private static final String DEFAULT_SCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SCT_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DWC_TAXON_ID = "AAAAAAAAAA";
    private static final String UPDATED_DWC_TAXON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DWC_TAXONOMIC_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DWC_TAXONOMIC_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_GBIF_TAXON_ID = "AAAAAAAAAA";
    private static final String UPDATED_GBIF_TAXON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GBIF_DATASET_ID = "AAAAAAAAAA";
    private static final String UPDATED_GBIF_DATASET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GBIF_TAXONOMIC_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_GBIF_TAXONOMIC_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_KINGDOM = "AAAAAAAAAA";
    private static final String UPDATED_KINGDOM = "BBBBBBBBBB";

    private static final String DEFAULT_PHYLUM = "AAAAAAAAAA";
    private static final String UPDATED_PHYLUM = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER = "BBBBBBBBBB";

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final String DEFAULT_GENUS = "AAAAAAAAAA";
    private static final String UPDATED_GENUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganismRepository organismRepository;

    @Autowired
    private OrganismMapper organismMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganismMockMvc;

    private Organism organism;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organism createEntity(EntityManager em) {
        Organism organism = new Organism()
            .whonetOrgCode(DEFAULT_WHONET_ORG_CODE)
            .organism(DEFAULT_ORGANISM)
            .taxonomicStatus(DEFAULT_TAXONOMIC_STATUS)
            .common(DEFAULT_COMMON)
            .organismType(DEFAULT_ORGANISM_TYPE)
            .anaerobe(DEFAULT_ANAEROBE)
            .morphology(DEFAULT_MORPHOLOGY)
            .subkingdomCode(DEFAULT_SUBKINGDOM_CODE)
            .familyCode(DEFAULT_FAMILY_CODE)
            .genusGroup(DEFAULT_GENUS_GROUP)
            .genusCode(DEFAULT_GENUS_CODE)
            .speciesGroup(DEFAULT_SPECIES_GROUP)
            .serovarGroup(DEFAULT_SEROVAR_GROUP)
            .msfGrpClin(DEFAULT_MSF_GRP_CLIN)
            .sctCode(DEFAULT_SCT_CODE)
            .sctText(DEFAULT_SCT_TEXT)
            .dwcTaxonId(DEFAULT_DWC_TAXON_ID)
            .dwcTaxonomicStatus(DEFAULT_DWC_TAXONOMIC_STATUS)
            .gbifTaxonId(DEFAULT_GBIF_TAXON_ID)
            .gbifDatasetId(DEFAULT_GBIF_DATASET_ID)
            .gbifTaxonomicStatus(DEFAULT_GBIF_TAXONOMIC_STATUS)
            .kingdom(DEFAULT_KINGDOM)
            .phylum(DEFAULT_PHYLUM)
            .organismClass(DEFAULT_ORGANISM_CLASS)
            .order(DEFAULT_ORDER)
            .family(DEFAULT_FAMILY)
            .genus(DEFAULT_GENUS);
        return organism;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organism createUpdatedEntity(EntityManager em) {
        Organism organism = new Organism()
            .whonetOrgCode(UPDATED_WHONET_ORG_CODE)
            .organism(UPDATED_ORGANISM)
            .taxonomicStatus(UPDATED_TAXONOMIC_STATUS)
            .common(UPDATED_COMMON)
            .organismType(UPDATED_ORGANISM_TYPE)
            .anaerobe(UPDATED_ANAEROBE)
            .morphology(UPDATED_MORPHOLOGY)
            .subkingdomCode(UPDATED_SUBKINGDOM_CODE)
            .familyCode(UPDATED_FAMILY_CODE)
            .genusGroup(UPDATED_GENUS_GROUP)
            .genusCode(UPDATED_GENUS_CODE)
            .speciesGroup(UPDATED_SPECIES_GROUP)
            .serovarGroup(UPDATED_SEROVAR_GROUP)
            .msfGrpClin(UPDATED_MSF_GRP_CLIN)
            .sctCode(UPDATED_SCT_CODE)
            .sctText(UPDATED_SCT_TEXT)
            .dwcTaxonId(UPDATED_DWC_TAXON_ID)
            .dwcTaxonomicStatus(UPDATED_DWC_TAXONOMIC_STATUS)
            .gbifTaxonId(UPDATED_GBIF_TAXON_ID)
            .gbifDatasetId(UPDATED_GBIF_DATASET_ID)
            .gbifTaxonomicStatus(UPDATED_GBIF_TAXONOMIC_STATUS)
            .kingdom(UPDATED_KINGDOM)
            .phylum(UPDATED_PHYLUM)
            .organismClass(UPDATED_ORGANISM_CLASS)
            .order(UPDATED_ORDER)
            .family(UPDATED_FAMILY)
            .genus(UPDATED_GENUS);
        return organism;
    }

    @BeforeEach
    public void initTest() {
        organism = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganism() throws Exception {
        int databaseSizeBeforeCreate = organismRepository.findAll().size();
        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);
        restOrganismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organismDTO)))
            .andExpect(status().isCreated());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeCreate + 1);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getWhonetOrgCode()).isEqualTo(DEFAULT_WHONET_ORG_CODE);
        assertThat(testOrganism.getOrganism()).isEqualTo(DEFAULT_ORGANISM);
        assertThat(testOrganism.getTaxonomicStatus()).isEqualTo(DEFAULT_TAXONOMIC_STATUS);
        assertThat(testOrganism.getCommon()).isEqualTo(DEFAULT_COMMON);
        assertThat(testOrganism.getOrganismType()).isEqualTo(DEFAULT_ORGANISM_TYPE);
        assertThat(testOrganism.getAnaerobe()).isEqualTo(DEFAULT_ANAEROBE);
        assertThat(testOrganism.getMorphology()).isEqualTo(DEFAULT_MORPHOLOGY);
        assertThat(testOrganism.getSubkingdomCode()).isEqualTo(DEFAULT_SUBKINGDOM_CODE);
        assertThat(testOrganism.getFamilyCode()).isEqualTo(DEFAULT_FAMILY_CODE);
        assertThat(testOrganism.getGenusGroup()).isEqualTo(DEFAULT_GENUS_GROUP);
        assertThat(testOrganism.getGenusCode()).isEqualTo(DEFAULT_GENUS_CODE);
        assertThat(testOrganism.getSpeciesGroup()).isEqualTo(DEFAULT_SPECIES_GROUP);
        assertThat(testOrganism.getSerovarGroup()).isEqualTo(DEFAULT_SEROVAR_GROUP);
        assertThat(testOrganism.getMsfGrpClin()).isEqualTo(DEFAULT_MSF_GRP_CLIN);
        assertThat(testOrganism.getSctCode()).isEqualTo(DEFAULT_SCT_CODE);
        assertThat(testOrganism.getSctText()).isEqualTo(DEFAULT_SCT_TEXT);
        assertThat(testOrganism.getDwcTaxonId()).isEqualTo(DEFAULT_DWC_TAXON_ID);
        assertThat(testOrganism.getDwcTaxonomicStatus()).isEqualTo(DEFAULT_DWC_TAXONOMIC_STATUS);
        assertThat(testOrganism.getGbifTaxonId()).isEqualTo(DEFAULT_GBIF_TAXON_ID);
        assertThat(testOrganism.getGbifDatasetId()).isEqualTo(DEFAULT_GBIF_DATASET_ID);
        assertThat(testOrganism.getGbifTaxonomicStatus()).isEqualTo(DEFAULT_GBIF_TAXONOMIC_STATUS);
        assertThat(testOrganism.getKingdom()).isEqualTo(DEFAULT_KINGDOM);
        assertThat(testOrganism.getPhylum()).isEqualTo(DEFAULT_PHYLUM);
        assertThat(testOrganism.getOrganismClass()).isEqualTo(DEFAULT_ORGANISM_CLASS);
        assertThat(testOrganism.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testOrganism.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testOrganism.getGenus()).isEqualTo(DEFAULT_GENUS);
    }

    @Test
    @Transactional
    void createOrganismWithExistingId() throws Exception {
        // Create the Organism with an existing ID
        organism.setId(1L);
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        int databaseSizeBeforeCreate = organismRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganismMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organismDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganisms() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organism.getId().intValue())))
            .andExpect(jsonPath("$.[*].whonetOrgCode").value(hasItem(DEFAULT_WHONET_ORG_CODE)))
            .andExpect(jsonPath("$.[*].organism").value(hasItem(DEFAULT_ORGANISM)))
            .andExpect(jsonPath("$.[*].taxonomicStatus").value(hasItem(DEFAULT_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].common").value(hasItem(DEFAULT_COMMON)))
            .andExpect(jsonPath("$.[*].organismType").value(hasItem(DEFAULT_ORGANISM_TYPE)))
            .andExpect(jsonPath("$.[*].anaerobe").value(hasItem(DEFAULT_ANAEROBE)))
            .andExpect(jsonPath("$.[*].morphology").value(hasItem(DEFAULT_MORPHOLOGY)))
            .andExpect(jsonPath("$.[*].subkingdomCode").value(hasItem(DEFAULT_SUBKINGDOM_CODE)))
            .andExpect(jsonPath("$.[*].familyCode").value(hasItem(DEFAULT_FAMILY_CODE)))
            .andExpect(jsonPath("$.[*].genusGroup").value(hasItem(DEFAULT_GENUS_GROUP)))
            .andExpect(jsonPath("$.[*].genusCode").value(hasItem(DEFAULT_GENUS_CODE)))
            .andExpect(jsonPath("$.[*].speciesGroup").value(hasItem(DEFAULT_SPECIES_GROUP)))
            .andExpect(jsonPath("$.[*].serovarGroup").value(hasItem(DEFAULT_SEROVAR_GROUP)))
            .andExpect(jsonPath("$.[*].msfGrpClin").value(hasItem(DEFAULT_MSF_GRP_CLIN)))
            .andExpect(jsonPath("$.[*].sctCode").value(hasItem(DEFAULT_SCT_CODE)))
            .andExpect(jsonPath("$.[*].sctText").value(hasItem(DEFAULT_SCT_TEXT)))
            .andExpect(jsonPath("$.[*].dwcTaxonId").value(hasItem(DEFAULT_DWC_TAXON_ID)))
            .andExpect(jsonPath("$.[*].dwcTaxonomicStatus").value(hasItem(DEFAULT_DWC_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].gbifTaxonId").value(hasItem(DEFAULT_GBIF_TAXON_ID)))
            .andExpect(jsonPath("$.[*].gbifDatasetId").value(hasItem(DEFAULT_GBIF_DATASET_ID)))
            .andExpect(jsonPath("$.[*].gbifTaxonomicStatus").value(hasItem(DEFAULT_GBIF_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].kingdom").value(hasItem(DEFAULT_KINGDOM)))
            .andExpect(jsonPath("$.[*].phylum").value(hasItem(DEFAULT_PHYLUM)))
            .andExpect(jsonPath("$.[*].organismClass").value(hasItem(DEFAULT_ORGANISM_CLASS)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].genus").value(hasItem(DEFAULT_GENUS)));
    }

    @Test
    @Transactional
    void getOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get the organism
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL_ID, organism.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organism.getId().intValue()))
            .andExpect(jsonPath("$.whonetOrgCode").value(DEFAULT_WHONET_ORG_CODE))
            .andExpect(jsonPath("$.organism").value(DEFAULT_ORGANISM))
            .andExpect(jsonPath("$.taxonomicStatus").value(DEFAULT_TAXONOMIC_STATUS))
            .andExpect(jsonPath("$.common").value(DEFAULT_COMMON))
            .andExpect(jsonPath("$.organismType").value(DEFAULT_ORGANISM_TYPE))
            .andExpect(jsonPath("$.anaerobe").value(DEFAULT_ANAEROBE))
            .andExpect(jsonPath("$.morphology").value(DEFAULT_MORPHOLOGY))
            .andExpect(jsonPath("$.subkingdomCode").value(DEFAULT_SUBKINGDOM_CODE))
            .andExpect(jsonPath("$.familyCode").value(DEFAULT_FAMILY_CODE))
            .andExpect(jsonPath("$.genusGroup").value(DEFAULT_GENUS_GROUP))
            .andExpect(jsonPath("$.genusCode").value(DEFAULT_GENUS_CODE))
            .andExpect(jsonPath("$.speciesGroup").value(DEFAULT_SPECIES_GROUP))
            .andExpect(jsonPath("$.serovarGroup").value(DEFAULT_SEROVAR_GROUP))
            .andExpect(jsonPath("$.msfGrpClin").value(DEFAULT_MSF_GRP_CLIN))
            .andExpect(jsonPath("$.sctCode").value(DEFAULT_SCT_CODE))
            .andExpect(jsonPath("$.sctText").value(DEFAULT_SCT_TEXT))
            .andExpect(jsonPath("$.dwcTaxonId").value(DEFAULT_DWC_TAXON_ID))
            .andExpect(jsonPath("$.dwcTaxonomicStatus").value(DEFAULT_DWC_TAXONOMIC_STATUS))
            .andExpect(jsonPath("$.gbifTaxonId").value(DEFAULT_GBIF_TAXON_ID))
            .andExpect(jsonPath("$.gbifDatasetId").value(DEFAULT_GBIF_DATASET_ID))
            .andExpect(jsonPath("$.gbifTaxonomicStatus").value(DEFAULT_GBIF_TAXONOMIC_STATUS))
            .andExpect(jsonPath("$.kingdom").value(DEFAULT_KINGDOM))
            .andExpect(jsonPath("$.phylum").value(DEFAULT_PHYLUM))
            .andExpect(jsonPath("$.organismClass").value(DEFAULT_ORGANISM_CLASS))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY))
            .andExpect(jsonPath("$.genus").value(DEFAULT_GENUS));
    }

    @Test
    @Transactional
    void getOrganismsByIdFiltering() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        Long id = organism.getId();

        defaultOrganismShouldBeFound("id.equals=" + id);
        defaultOrganismShouldNotBeFound("id.notEquals=" + id);

        defaultOrganismShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganismShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganismShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganismShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode equals to DEFAULT_WHONET_ORG_CODE
        defaultOrganismShouldBeFound("whonetOrgCode.equals=" + DEFAULT_WHONET_ORG_CODE);

        // Get all the organismList where whonetOrgCode equals to UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldNotBeFound("whonetOrgCode.equals=" + UPDATED_WHONET_ORG_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode not equals to DEFAULT_WHONET_ORG_CODE
        defaultOrganismShouldNotBeFound("whonetOrgCode.notEquals=" + DEFAULT_WHONET_ORG_CODE);

        // Get all the organismList where whonetOrgCode not equals to UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldBeFound("whonetOrgCode.notEquals=" + UPDATED_WHONET_ORG_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode in DEFAULT_WHONET_ORG_CODE or UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldBeFound("whonetOrgCode.in=" + DEFAULT_WHONET_ORG_CODE + "," + UPDATED_WHONET_ORG_CODE);

        // Get all the organismList where whonetOrgCode equals to UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldNotBeFound("whonetOrgCode.in=" + UPDATED_WHONET_ORG_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode is not null
        defaultOrganismShouldBeFound("whonetOrgCode.specified=true");

        // Get all the organismList where whonetOrgCode is null
        defaultOrganismShouldNotBeFound("whonetOrgCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode contains DEFAULT_WHONET_ORG_CODE
        defaultOrganismShouldBeFound("whonetOrgCode.contains=" + DEFAULT_WHONET_ORG_CODE);

        // Get all the organismList where whonetOrgCode contains UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldNotBeFound("whonetOrgCode.contains=" + UPDATED_WHONET_ORG_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByWhonetOrgCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where whonetOrgCode does not contain DEFAULT_WHONET_ORG_CODE
        defaultOrganismShouldNotBeFound("whonetOrgCode.doesNotContain=" + DEFAULT_WHONET_ORG_CODE);

        // Get all the organismList where whonetOrgCode does not contain UPDATED_WHONET_ORG_CODE
        defaultOrganismShouldBeFound("whonetOrgCode.doesNotContain=" + UPDATED_WHONET_ORG_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism equals to DEFAULT_ORGANISM
        defaultOrganismShouldBeFound("organism.equals=" + DEFAULT_ORGANISM);

        // Get all the organismList where organism equals to UPDATED_ORGANISM
        defaultOrganismShouldNotBeFound("organism.equals=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism not equals to DEFAULT_ORGANISM
        defaultOrganismShouldNotBeFound("organism.notEquals=" + DEFAULT_ORGANISM);

        // Get all the organismList where organism not equals to UPDATED_ORGANISM
        defaultOrganismShouldBeFound("organism.notEquals=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism in DEFAULT_ORGANISM or UPDATED_ORGANISM
        defaultOrganismShouldBeFound("organism.in=" + DEFAULT_ORGANISM + "," + UPDATED_ORGANISM);

        // Get all the organismList where organism equals to UPDATED_ORGANISM
        defaultOrganismShouldNotBeFound("organism.in=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism is not null
        defaultOrganismShouldBeFound("organism.specified=true");

        // Get all the organismList where organism is null
        defaultOrganismShouldNotBeFound("organism.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism contains DEFAULT_ORGANISM
        defaultOrganismShouldBeFound("organism.contains=" + DEFAULT_ORGANISM);

        // Get all the organismList where organism contains UPDATED_ORGANISM
        defaultOrganismShouldNotBeFound("organism.contains=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organism does not contain DEFAULT_ORGANISM
        defaultOrganismShouldNotBeFound("organism.doesNotContain=" + DEFAULT_ORGANISM);

        // Get all the organismList where organism does not contain UPDATED_ORGANISM
        defaultOrganismShouldBeFound("organism.doesNotContain=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus equals to DEFAULT_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("taxonomicStatus.equals=" + DEFAULT_TAXONOMIC_STATUS);

        // Get all the organismList where taxonomicStatus equals to UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("taxonomicStatus.equals=" + UPDATED_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus not equals to DEFAULT_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("taxonomicStatus.notEquals=" + DEFAULT_TAXONOMIC_STATUS);

        // Get all the organismList where taxonomicStatus not equals to UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("taxonomicStatus.notEquals=" + UPDATED_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus in DEFAULT_TAXONOMIC_STATUS or UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("taxonomicStatus.in=" + DEFAULT_TAXONOMIC_STATUS + "," + UPDATED_TAXONOMIC_STATUS);

        // Get all the organismList where taxonomicStatus equals to UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("taxonomicStatus.in=" + UPDATED_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus is not null
        defaultOrganismShouldBeFound("taxonomicStatus.specified=true");

        // Get all the organismList where taxonomicStatus is null
        defaultOrganismShouldNotBeFound("taxonomicStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus contains DEFAULT_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("taxonomicStatus.contains=" + DEFAULT_TAXONOMIC_STATUS);

        // Get all the organismList where taxonomicStatus contains UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("taxonomicStatus.contains=" + UPDATED_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByTaxonomicStatusNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where taxonomicStatus does not contain DEFAULT_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("taxonomicStatus.doesNotContain=" + DEFAULT_TAXONOMIC_STATUS);

        // Get all the organismList where taxonomicStatus does not contain UPDATED_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("taxonomicStatus.doesNotContain=" + UPDATED_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common equals to DEFAULT_COMMON
        defaultOrganismShouldBeFound("common.equals=" + DEFAULT_COMMON);

        // Get all the organismList where common equals to UPDATED_COMMON
        defaultOrganismShouldNotBeFound("common.equals=" + UPDATED_COMMON);
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common not equals to DEFAULT_COMMON
        defaultOrganismShouldNotBeFound("common.notEquals=" + DEFAULT_COMMON);

        // Get all the organismList where common not equals to UPDATED_COMMON
        defaultOrganismShouldBeFound("common.notEquals=" + UPDATED_COMMON);
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common in DEFAULT_COMMON or UPDATED_COMMON
        defaultOrganismShouldBeFound("common.in=" + DEFAULT_COMMON + "," + UPDATED_COMMON);

        // Get all the organismList where common equals to UPDATED_COMMON
        defaultOrganismShouldNotBeFound("common.in=" + UPDATED_COMMON);
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common is not null
        defaultOrganismShouldBeFound("common.specified=true");

        // Get all the organismList where common is null
        defaultOrganismShouldNotBeFound("common.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common contains DEFAULT_COMMON
        defaultOrganismShouldBeFound("common.contains=" + DEFAULT_COMMON);

        // Get all the organismList where common contains UPDATED_COMMON
        defaultOrganismShouldNotBeFound("common.contains=" + UPDATED_COMMON);
    }

    @Test
    @Transactional
    void getAllOrganismsByCommonNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where common does not contain DEFAULT_COMMON
        defaultOrganismShouldNotBeFound("common.doesNotContain=" + DEFAULT_COMMON);

        // Get all the organismList where common does not contain UPDATED_COMMON
        defaultOrganismShouldBeFound("common.doesNotContain=" + UPDATED_COMMON);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType equals to DEFAULT_ORGANISM_TYPE
        defaultOrganismShouldBeFound("organismType.equals=" + DEFAULT_ORGANISM_TYPE);

        // Get all the organismList where organismType equals to UPDATED_ORGANISM_TYPE
        defaultOrganismShouldNotBeFound("organismType.equals=" + UPDATED_ORGANISM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType not equals to DEFAULT_ORGANISM_TYPE
        defaultOrganismShouldNotBeFound("organismType.notEquals=" + DEFAULT_ORGANISM_TYPE);

        // Get all the organismList where organismType not equals to UPDATED_ORGANISM_TYPE
        defaultOrganismShouldBeFound("organismType.notEquals=" + UPDATED_ORGANISM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType in DEFAULT_ORGANISM_TYPE or UPDATED_ORGANISM_TYPE
        defaultOrganismShouldBeFound("organismType.in=" + DEFAULT_ORGANISM_TYPE + "," + UPDATED_ORGANISM_TYPE);

        // Get all the organismList where organismType equals to UPDATED_ORGANISM_TYPE
        defaultOrganismShouldNotBeFound("organismType.in=" + UPDATED_ORGANISM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType is not null
        defaultOrganismShouldBeFound("organismType.specified=true");

        // Get all the organismList where organismType is null
        defaultOrganismShouldNotBeFound("organismType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType contains DEFAULT_ORGANISM_TYPE
        defaultOrganismShouldBeFound("organismType.contains=" + DEFAULT_ORGANISM_TYPE);

        // Get all the organismList where organismType contains UPDATED_ORGANISM_TYPE
        defaultOrganismShouldNotBeFound("organismType.contains=" + UPDATED_ORGANISM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismTypeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismType does not contain DEFAULT_ORGANISM_TYPE
        defaultOrganismShouldNotBeFound("organismType.doesNotContain=" + DEFAULT_ORGANISM_TYPE);

        // Get all the organismList where organismType does not contain UPDATED_ORGANISM_TYPE
        defaultOrganismShouldBeFound("organismType.doesNotContain=" + UPDATED_ORGANISM_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe equals to DEFAULT_ANAEROBE
        defaultOrganismShouldBeFound("anaerobe.equals=" + DEFAULT_ANAEROBE);

        // Get all the organismList where anaerobe equals to UPDATED_ANAEROBE
        defaultOrganismShouldNotBeFound("anaerobe.equals=" + UPDATED_ANAEROBE);
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe not equals to DEFAULT_ANAEROBE
        defaultOrganismShouldNotBeFound("anaerobe.notEquals=" + DEFAULT_ANAEROBE);

        // Get all the organismList where anaerobe not equals to UPDATED_ANAEROBE
        defaultOrganismShouldBeFound("anaerobe.notEquals=" + UPDATED_ANAEROBE);
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe in DEFAULT_ANAEROBE or UPDATED_ANAEROBE
        defaultOrganismShouldBeFound("anaerobe.in=" + DEFAULT_ANAEROBE + "," + UPDATED_ANAEROBE);

        // Get all the organismList where anaerobe equals to UPDATED_ANAEROBE
        defaultOrganismShouldNotBeFound("anaerobe.in=" + UPDATED_ANAEROBE);
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe is not null
        defaultOrganismShouldBeFound("anaerobe.specified=true");

        // Get all the organismList where anaerobe is null
        defaultOrganismShouldNotBeFound("anaerobe.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe contains DEFAULT_ANAEROBE
        defaultOrganismShouldBeFound("anaerobe.contains=" + DEFAULT_ANAEROBE);

        // Get all the organismList where anaerobe contains UPDATED_ANAEROBE
        defaultOrganismShouldNotBeFound("anaerobe.contains=" + UPDATED_ANAEROBE);
    }

    @Test
    @Transactional
    void getAllOrganismsByAnaerobeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where anaerobe does not contain DEFAULT_ANAEROBE
        defaultOrganismShouldNotBeFound("anaerobe.doesNotContain=" + DEFAULT_ANAEROBE);

        // Get all the organismList where anaerobe does not contain UPDATED_ANAEROBE
        defaultOrganismShouldBeFound("anaerobe.doesNotContain=" + UPDATED_ANAEROBE);
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology equals to DEFAULT_MORPHOLOGY
        defaultOrganismShouldBeFound("morphology.equals=" + DEFAULT_MORPHOLOGY);

        // Get all the organismList where morphology equals to UPDATED_MORPHOLOGY
        defaultOrganismShouldNotBeFound("morphology.equals=" + UPDATED_MORPHOLOGY);
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology not equals to DEFAULT_MORPHOLOGY
        defaultOrganismShouldNotBeFound("morphology.notEquals=" + DEFAULT_MORPHOLOGY);

        // Get all the organismList where morphology not equals to UPDATED_MORPHOLOGY
        defaultOrganismShouldBeFound("morphology.notEquals=" + UPDATED_MORPHOLOGY);
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology in DEFAULT_MORPHOLOGY or UPDATED_MORPHOLOGY
        defaultOrganismShouldBeFound("morphology.in=" + DEFAULT_MORPHOLOGY + "," + UPDATED_MORPHOLOGY);

        // Get all the organismList where morphology equals to UPDATED_MORPHOLOGY
        defaultOrganismShouldNotBeFound("morphology.in=" + UPDATED_MORPHOLOGY);
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology is not null
        defaultOrganismShouldBeFound("morphology.specified=true");

        // Get all the organismList where morphology is null
        defaultOrganismShouldNotBeFound("morphology.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology contains DEFAULT_MORPHOLOGY
        defaultOrganismShouldBeFound("morphology.contains=" + DEFAULT_MORPHOLOGY);

        // Get all the organismList where morphology contains UPDATED_MORPHOLOGY
        defaultOrganismShouldNotBeFound("morphology.contains=" + UPDATED_MORPHOLOGY);
    }

    @Test
    @Transactional
    void getAllOrganismsByMorphologyNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where morphology does not contain DEFAULT_MORPHOLOGY
        defaultOrganismShouldNotBeFound("morphology.doesNotContain=" + DEFAULT_MORPHOLOGY);

        // Get all the organismList where morphology does not contain UPDATED_MORPHOLOGY
        defaultOrganismShouldBeFound("morphology.doesNotContain=" + UPDATED_MORPHOLOGY);
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode equals to DEFAULT_SUBKINGDOM_CODE
        defaultOrganismShouldBeFound("subkingdomCode.equals=" + DEFAULT_SUBKINGDOM_CODE);

        // Get all the organismList where subkingdomCode equals to UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldNotBeFound("subkingdomCode.equals=" + UPDATED_SUBKINGDOM_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode not equals to DEFAULT_SUBKINGDOM_CODE
        defaultOrganismShouldNotBeFound("subkingdomCode.notEquals=" + DEFAULT_SUBKINGDOM_CODE);

        // Get all the organismList where subkingdomCode not equals to UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldBeFound("subkingdomCode.notEquals=" + UPDATED_SUBKINGDOM_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode in DEFAULT_SUBKINGDOM_CODE or UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldBeFound("subkingdomCode.in=" + DEFAULT_SUBKINGDOM_CODE + "," + UPDATED_SUBKINGDOM_CODE);

        // Get all the organismList where subkingdomCode equals to UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldNotBeFound("subkingdomCode.in=" + UPDATED_SUBKINGDOM_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode is not null
        defaultOrganismShouldBeFound("subkingdomCode.specified=true");

        // Get all the organismList where subkingdomCode is null
        defaultOrganismShouldNotBeFound("subkingdomCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode contains DEFAULT_SUBKINGDOM_CODE
        defaultOrganismShouldBeFound("subkingdomCode.contains=" + DEFAULT_SUBKINGDOM_CODE);

        // Get all the organismList where subkingdomCode contains UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldNotBeFound("subkingdomCode.contains=" + UPDATED_SUBKINGDOM_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySubkingdomCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where subkingdomCode does not contain DEFAULT_SUBKINGDOM_CODE
        defaultOrganismShouldNotBeFound("subkingdomCode.doesNotContain=" + DEFAULT_SUBKINGDOM_CODE);

        // Get all the organismList where subkingdomCode does not contain UPDATED_SUBKINGDOM_CODE
        defaultOrganismShouldBeFound("subkingdomCode.doesNotContain=" + UPDATED_SUBKINGDOM_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode equals to DEFAULT_FAMILY_CODE
        defaultOrganismShouldBeFound("familyCode.equals=" + DEFAULT_FAMILY_CODE);

        // Get all the organismList where familyCode equals to UPDATED_FAMILY_CODE
        defaultOrganismShouldNotBeFound("familyCode.equals=" + UPDATED_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode not equals to DEFAULT_FAMILY_CODE
        defaultOrganismShouldNotBeFound("familyCode.notEquals=" + DEFAULT_FAMILY_CODE);

        // Get all the organismList where familyCode not equals to UPDATED_FAMILY_CODE
        defaultOrganismShouldBeFound("familyCode.notEquals=" + UPDATED_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode in DEFAULT_FAMILY_CODE or UPDATED_FAMILY_CODE
        defaultOrganismShouldBeFound("familyCode.in=" + DEFAULT_FAMILY_CODE + "," + UPDATED_FAMILY_CODE);

        // Get all the organismList where familyCode equals to UPDATED_FAMILY_CODE
        defaultOrganismShouldNotBeFound("familyCode.in=" + UPDATED_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode is not null
        defaultOrganismShouldBeFound("familyCode.specified=true");

        // Get all the organismList where familyCode is null
        defaultOrganismShouldNotBeFound("familyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode contains DEFAULT_FAMILY_CODE
        defaultOrganismShouldBeFound("familyCode.contains=" + DEFAULT_FAMILY_CODE);

        // Get all the organismList where familyCode contains UPDATED_FAMILY_CODE
        defaultOrganismShouldNotBeFound("familyCode.contains=" + UPDATED_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where familyCode does not contain DEFAULT_FAMILY_CODE
        defaultOrganismShouldNotBeFound("familyCode.doesNotContain=" + DEFAULT_FAMILY_CODE);

        // Get all the organismList where familyCode does not contain UPDATED_FAMILY_CODE
        defaultOrganismShouldBeFound("familyCode.doesNotContain=" + UPDATED_FAMILY_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup equals to DEFAULT_GENUS_GROUP
        defaultOrganismShouldBeFound("genusGroup.equals=" + DEFAULT_GENUS_GROUP);

        // Get all the organismList where genusGroup equals to UPDATED_GENUS_GROUP
        defaultOrganismShouldNotBeFound("genusGroup.equals=" + UPDATED_GENUS_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup not equals to DEFAULT_GENUS_GROUP
        defaultOrganismShouldNotBeFound("genusGroup.notEquals=" + DEFAULT_GENUS_GROUP);

        // Get all the organismList where genusGroup not equals to UPDATED_GENUS_GROUP
        defaultOrganismShouldBeFound("genusGroup.notEquals=" + UPDATED_GENUS_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup in DEFAULT_GENUS_GROUP or UPDATED_GENUS_GROUP
        defaultOrganismShouldBeFound("genusGroup.in=" + DEFAULT_GENUS_GROUP + "," + UPDATED_GENUS_GROUP);

        // Get all the organismList where genusGroup equals to UPDATED_GENUS_GROUP
        defaultOrganismShouldNotBeFound("genusGroup.in=" + UPDATED_GENUS_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup is not null
        defaultOrganismShouldBeFound("genusGroup.specified=true");

        // Get all the organismList where genusGroup is null
        defaultOrganismShouldNotBeFound("genusGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup contains DEFAULT_GENUS_GROUP
        defaultOrganismShouldBeFound("genusGroup.contains=" + DEFAULT_GENUS_GROUP);

        // Get all the organismList where genusGroup contains UPDATED_GENUS_GROUP
        defaultOrganismShouldNotBeFound("genusGroup.contains=" + UPDATED_GENUS_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusGroupNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusGroup does not contain DEFAULT_GENUS_GROUP
        defaultOrganismShouldNotBeFound("genusGroup.doesNotContain=" + DEFAULT_GENUS_GROUP);

        // Get all the organismList where genusGroup does not contain UPDATED_GENUS_GROUP
        defaultOrganismShouldBeFound("genusGroup.doesNotContain=" + UPDATED_GENUS_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode equals to DEFAULT_GENUS_CODE
        defaultOrganismShouldBeFound("genusCode.equals=" + DEFAULT_GENUS_CODE);

        // Get all the organismList where genusCode equals to UPDATED_GENUS_CODE
        defaultOrganismShouldNotBeFound("genusCode.equals=" + UPDATED_GENUS_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode not equals to DEFAULT_GENUS_CODE
        defaultOrganismShouldNotBeFound("genusCode.notEquals=" + DEFAULT_GENUS_CODE);

        // Get all the organismList where genusCode not equals to UPDATED_GENUS_CODE
        defaultOrganismShouldBeFound("genusCode.notEquals=" + UPDATED_GENUS_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode in DEFAULT_GENUS_CODE or UPDATED_GENUS_CODE
        defaultOrganismShouldBeFound("genusCode.in=" + DEFAULT_GENUS_CODE + "," + UPDATED_GENUS_CODE);

        // Get all the organismList where genusCode equals to UPDATED_GENUS_CODE
        defaultOrganismShouldNotBeFound("genusCode.in=" + UPDATED_GENUS_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode is not null
        defaultOrganismShouldBeFound("genusCode.specified=true");

        // Get all the organismList where genusCode is null
        defaultOrganismShouldNotBeFound("genusCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode contains DEFAULT_GENUS_CODE
        defaultOrganismShouldBeFound("genusCode.contains=" + DEFAULT_GENUS_CODE);

        // Get all the organismList where genusCode contains UPDATED_GENUS_CODE
        defaultOrganismShouldNotBeFound("genusCode.contains=" + UPDATED_GENUS_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genusCode does not contain DEFAULT_GENUS_CODE
        defaultOrganismShouldNotBeFound("genusCode.doesNotContain=" + DEFAULT_GENUS_CODE);

        // Get all the organismList where genusCode does not contain UPDATED_GENUS_CODE
        defaultOrganismShouldBeFound("genusCode.doesNotContain=" + UPDATED_GENUS_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup equals to DEFAULT_SPECIES_GROUP
        defaultOrganismShouldBeFound("speciesGroup.equals=" + DEFAULT_SPECIES_GROUP);

        // Get all the organismList where speciesGroup equals to UPDATED_SPECIES_GROUP
        defaultOrganismShouldNotBeFound("speciesGroup.equals=" + UPDATED_SPECIES_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup not equals to DEFAULT_SPECIES_GROUP
        defaultOrganismShouldNotBeFound("speciesGroup.notEquals=" + DEFAULT_SPECIES_GROUP);

        // Get all the organismList where speciesGroup not equals to UPDATED_SPECIES_GROUP
        defaultOrganismShouldBeFound("speciesGroup.notEquals=" + UPDATED_SPECIES_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup in DEFAULT_SPECIES_GROUP or UPDATED_SPECIES_GROUP
        defaultOrganismShouldBeFound("speciesGroup.in=" + DEFAULT_SPECIES_GROUP + "," + UPDATED_SPECIES_GROUP);

        // Get all the organismList where speciesGroup equals to UPDATED_SPECIES_GROUP
        defaultOrganismShouldNotBeFound("speciesGroup.in=" + UPDATED_SPECIES_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup is not null
        defaultOrganismShouldBeFound("speciesGroup.specified=true");

        // Get all the organismList where speciesGroup is null
        defaultOrganismShouldNotBeFound("speciesGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup contains DEFAULT_SPECIES_GROUP
        defaultOrganismShouldBeFound("speciesGroup.contains=" + DEFAULT_SPECIES_GROUP);

        // Get all the organismList where speciesGroup contains UPDATED_SPECIES_GROUP
        defaultOrganismShouldNotBeFound("speciesGroup.contains=" + UPDATED_SPECIES_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySpeciesGroupNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where speciesGroup does not contain DEFAULT_SPECIES_GROUP
        defaultOrganismShouldNotBeFound("speciesGroup.doesNotContain=" + DEFAULT_SPECIES_GROUP);

        // Get all the organismList where speciesGroup does not contain UPDATED_SPECIES_GROUP
        defaultOrganismShouldBeFound("speciesGroup.doesNotContain=" + UPDATED_SPECIES_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup equals to DEFAULT_SEROVAR_GROUP
        defaultOrganismShouldBeFound("serovarGroup.equals=" + DEFAULT_SEROVAR_GROUP);

        // Get all the organismList where serovarGroup equals to UPDATED_SEROVAR_GROUP
        defaultOrganismShouldNotBeFound("serovarGroup.equals=" + UPDATED_SEROVAR_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup not equals to DEFAULT_SEROVAR_GROUP
        defaultOrganismShouldNotBeFound("serovarGroup.notEquals=" + DEFAULT_SEROVAR_GROUP);

        // Get all the organismList where serovarGroup not equals to UPDATED_SEROVAR_GROUP
        defaultOrganismShouldBeFound("serovarGroup.notEquals=" + UPDATED_SEROVAR_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup in DEFAULT_SEROVAR_GROUP or UPDATED_SEROVAR_GROUP
        defaultOrganismShouldBeFound("serovarGroup.in=" + DEFAULT_SEROVAR_GROUP + "," + UPDATED_SEROVAR_GROUP);

        // Get all the organismList where serovarGroup equals to UPDATED_SEROVAR_GROUP
        defaultOrganismShouldNotBeFound("serovarGroup.in=" + UPDATED_SEROVAR_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup is not null
        defaultOrganismShouldBeFound("serovarGroup.specified=true");

        // Get all the organismList where serovarGroup is null
        defaultOrganismShouldNotBeFound("serovarGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup contains DEFAULT_SEROVAR_GROUP
        defaultOrganismShouldBeFound("serovarGroup.contains=" + DEFAULT_SEROVAR_GROUP);

        // Get all the organismList where serovarGroup contains UPDATED_SEROVAR_GROUP
        defaultOrganismShouldNotBeFound("serovarGroup.contains=" + UPDATED_SEROVAR_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsBySerovarGroupNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where serovarGroup does not contain DEFAULT_SEROVAR_GROUP
        defaultOrganismShouldNotBeFound("serovarGroup.doesNotContain=" + DEFAULT_SEROVAR_GROUP);

        // Get all the organismList where serovarGroup does not contain UPDATED_SEROVAR_GROUP
        defaultOrganismShouldBeFound("serovarGroup.doesNotContain=" + UPDATED_SEROVAR_GROUP);
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin equals to DEFAULT_MSF_GRP_CLIN
        defaultOrganismShouldBeFound("msfGrpClin.equals=" + DEFAULT_MSF_GRP_CLIN);

        // Get all the organismList where msfGrpClin equals to UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldNotBeFound("msfGrpClin.equals=" + UPDATED_MSF_GRP_CLIN);
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin not equals to DEFAULT_MSF_GRP_CLIN
        defaultOrganismShouldNotBeFound("msfGrpClin.notEquals=" + DEFAULT_MSF_GRP_CLIN);

        // Get all the organismList where msfGrpClin not equals to UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldBeFound("msfGrpClin.notEquals=" + UPDATED_MSF_GRP_CLIN);
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin in DEFAULT_MSF_GRP_CLIN or UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldBeFound("msfGrpClin.in=" + DEFAULT_MSF_GRP_CLIN + "," + UPDATED_MSF_GRP_CLIN);

        // Get all the organismList where msfGrpClin equals to UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldNotBeFound("msfGrpClin.in=" + UPDATED_MSF_GRP_CLIN);
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin is not null
        defaultOrganismShouldBeFound("msfGrpClin.specified=true");

        // Get all the organismList where msfGrpClin is null
        defaultOrganismShouldNotBeFound("msfGrpClin.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin contains DEFAULT_MSF_GRP_CLIN
        defaultOrganismShouldBeFound("msfGrpClin.contains=" + DEFAULT_MSF_GRP_CLIN);

        // Get all the organismList where msfGrpClin contains UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldNotBeFound("msfGrpClin.contains=" + UPDATED_MSF_GRP_CLIN);
    }

    @Test
    @Transactional
    void getAllOrganismsByMsfGrpClinNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where msfGrpClin does not contain DEFAULT_MSF_GRP_CLIN
        defaultOrganismShouldNotBeFound("msfGrpClin.doesNotContain=" + DEFAULT_MSF_GRP_CLIN);

        // Get all the organismList where msfGrpClin does not contain UPDATED_MSF_GRP_CLIN
        defaultOrganismShouldBeFound("msfGrpClin.doesNotContain=" + UPDATED_MSF_GRP_CLIN);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode equals to DEFAULT_SCT_CODE
        defaultOrganismShouldBeFound("sctCode.equals=" + DEFAULT_SCT_CODE);

        // Get all the organismList where sctCode equals to UPDATED_SCT_CODE
        defaultOrganismShouldNotBeFound("sctCode.equals=" + UPDATED_SCT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode not equals to DEFAULT_SCT_CODE
        defaultOrganismShouldNotBeFound("sctCode.notEquals=" + DEFAULT_SCT_CODE);

        // Get all the organismList where sctCode not equals to UPDATED_SCT_CODE
        defaultOrganismShouldBeFound("sctCode.notEquals=" + UPDATED_SCT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode in DEFAULT_SCT_CODE or UPDATED_SCT_CODE
        defaultOrganismShouldBeFound("sctCode.in=" + DEFAULT_SCT_CODE + "," + UPDATED_SCT_CODE);

        // Get all the organismList where sctCode equals to UPDATED_SCT_CODE
        defaultOrganismShouldNotBeFound("sctCode.in=" + UPDATED_SCT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode is not null
        defaultOrganismShouldBeFound("sctCode.specified=true");

        // Get all the organismList where sctCode is null
        defaultOrganismShouldNotBeFound("sctCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode contains DEFAULT_SCT_CODE
        defaultOrganismShouldBeFound("sctCode.contains=" + DEFAULT_SCT_CODE);

        // Get all the organismList where sctCode contains UPDATED_SCT_CODE
        defaultOrganismShouldNotBeFound("sctCode.contains=" + UPDATED_SCT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctCode does not contain DEFAULT_SCT_CODE
        defaultOrganismShouldNotBeFound("sctCode.doesNotContain=" + DEFAULT_SCT_CODE);

        // Get all the organismList where sctCode does not contain UPDATED_SCT_CODE
        defaultOrganismShouldBeFound("sctCode.doesNotContain=" + UPDATED_SCT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText equals to DEFAULT_SCT_TEXT
        defaultOrganismShouldBeFound("sctText.equals=" + DEFAULT_SCT_TEXT);

        // Get all the organismList where sctText equals to UPDATED_SCT_TEXT
        defaultOrganismShouldNotBeFound("sctText.equals=" + UPDATED_SCT_TEXT);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText not equals to DEFAULT_SCT_TEXT
        defaultOrganismShouldNotBeFound("sctText.notEquals=" + DEFAULT_SCT_TEXT);

        // Get all the organismList where sctText not equals to UPDATED_SCT_TEXT
        defaultOrganismShouldBeFound("sctText.notEquals=" + UPDATED_SCT_TEXT);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText in DEFAULT_SCT_TEXT or UPDATED_SCT_TEXT
        defaultOrganismShouldBeFound("sctText.in=" + DEFAULT_SCT_TEXT + "," + UPDATED_SCT_TEXT);

        // Get all the organismList where sctText equals to UPDATED_SCT_TEXT
        defaultOrganismShouldNotBeFound("sctText.in=" + UPDATED_SCT_TEXT);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText is not null
        defaultOrganismShouldBeFound("sctText.specified=true");

        // Get all the organismList where sctText is null
        defaultOrganismShouldNotBeFound("sctText.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText contains DEFAULT_SCT_TEXT
        defaultOrganismShouldBeFound("sctText.contains=" + DEFAULT_SCT_TEXT);

        // Get all the organismList where sctText contains UPDATED_SCT_TEXT
        defaultOrganismShouldNotBeFound("sctText.contains=" + UPDATED_SCT_TEXT);
    }

    @Test
    @Transactional
    void getAllOrganismsBySctTextNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where sctText does not contain DEFAULT_SCT_TEXT
        defaultOrganismShouldNotBeFound("sctText.doesNotContain=" + DEFAULT_SCT_TEXT);

        // Get all the organismList where sctText does not contain UPDATED_SCT_TEXT
        defaultOrganismShouldBeFound("sctText.doesNotContain=" + UPDATED_SCT_TEXT);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId equals to DEFAULT_DWC_TAXON_ID
        defaultOrganismShouldBeFound("dwcTaxonId.equals=" + DEFAULT_DWC_TAXON_ID);

        // Get all the organismList where dwcTaxonId equals to UPDATED_DWC_TAXON_ID
        defaultOrganismShouldNotBeFound("dwcTaxonId.equals=" + UPDATED_DWC_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId not equals to DEFAULT_DWC_TAXON_ID
        defaultOrganismShouldNotBeFound("dwcTaxonId.notEquals=" + DEFAULT_DWC_TAXON_ID);

        // Get all the organismList where dwcTaxonId not equals to UPDATED_DWC_TAXON_ID
        defaultOrganismShouldBeFound("dwcTaxonId.notEquals=" + UPDATED_DWC_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId in DEFAULT_DWC_TAXON_ID or UPDATED_DWC_TAXON_ID
        defaultOrganismShouldBeFound("dwcTaxonId.in=" + DEFAULT_DWC_TAXON_ID + "," + UPDATED_DWC_TAXON_ID);

        // Get all the organismList where dwcTaxonId equals to UPDATED_DWC_TAXON_ID
        defaultOrganismShouldNotBeFound("dwcTaxonId.in=" + UPDATED_DWC_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId is not null
        defaultOrganismShouldBeFound("dwcTaxonId.specified=true");

        // Get all the organismList where dwcTaxonId is null
        defaultOrganismShouldNotBeFound("dwcTaxonId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId contains DEFAULT_DWC_TAXON_ID
        defaultOrganismShouldBeFound("dwcTaxonId.contains=" + DEFAULT_DWC_TAXON_ID);

        // Get all the organismList where dwcTaxonId contains UPDATED_DWC_TAXON_ID
        defaultOrganismShouldNotBeFound("dwcTaxonId.contains=" + UPDATED_DWC_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonIdNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonId does not contain DEFAULT_DWC_TAXON_ID
        defaultOrganismShouldNotBeFound("dwcTaxonId.doesNotContain=" + DEFAULT_DWC_TAXON_ID);

        // Get all the organismList where dwcTaxonId does not contain UPDATED_DWC_TAXON_ID
        defaultOrganismShouldBeFound("dwcTaxonId.doesNotContain=" + UPDATED_DWC_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus equals to DEFAULT_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.equals=" + DEFAULT_DWC_TAXONOMIC_STATUS);

        // Get all the organismList where dwcTaxonomicStatus equals to UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.equals=" + UPDATED_DWC_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus not equals to DEFAULT_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.notEquals=" + DEFAULT_DWC_TAXONOMIC_STATUS);

        // Get all the organismList where dwcTaxonomicStatus not equals to UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.notEquals=" + UPDATED_DWC_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus in DEFAULT_DWC_TAXONOMIC_STATUS or UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.in=" + DEFAULT_DWC_TAXONOMIC_STATUS + "," + UPDATED_DWC_TAXONOMIC_STATUS);

        // Get all the organismList where dwcTaxonomicStatus equals to UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.in=" + UPDATED_DWC_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus is not null
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.specified=true");

        // Get all the organismList where dwcTaxonomicStatus is null
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus contains DEFAULT_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.contains=" + DEFAULT_DWC_TAXONOMIC_STATUS);

        // Get all the organismList where dwcTaxonomicStatus contains UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.contains=" + UPDATED_DWC_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByDwcTaxonomicStatusNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where dwcTaxonomicStatus does not contain DEFAULT_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("dwcTaxonomicStatus.doesNotContain=" + DEFAULT_DWC_TAXONOMIC_STATUS);

        // Get all the organismList where dwcTaxonomicStatus does not contain UPDATED_DWC_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("dwcTaxonomicStatus.doesNotContain=" + UPDATED_DWC_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId equals to DEFAULT_GBIF_TAXON_ID
        defaultOrganismShouldBeFound("gbifTaxonId.equals=" + DEFAULT_GBIF_TAXON_ID);

        // Get all the organismList where gbifTaxonId equals to UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldNotBeFound("gbifTaxonId.equals=" + UPDATED_GBIF_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId not equals to DEFAULT_GBIF_TAXON_ID
        defaultOrganismShouldNotBeFound("gbifTaxonId.notEquals=" + DEFAULT_GBIF_TAXON_ID);

        // Get all the organismList where gbifTaxonId not equals to UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldBeFound("gbifTaxonId.notEquals=" + UPDATED_GBIF_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId in DEFAULT_GBIF_TAXON_ID or UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldBeFound("gbifTaxonId.in=" + DEFAULT_GBIF_TAXON_ID + "," + UPDATED_GBIF_TAXON_ID);

        // Get all the organismList where gbifTaxonId equals to UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldNotBeFound("gbifTaxonId.in=" + UPDATED_GBIF_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId is not null
        defaultOrganismShouldBeFound("gbifTaxonId.specified=true");

        // Get all the organismList where gbifTaxonId is null
        defaultOrganismShouldNotBeFound("gbifTaxonId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId contains DEFAULT_GBIF_TAXON_ID
        defaultOrganismShouldBeFound("gbifTaxonId.contains=" + DEFAULT_GBIF_TAXON_ID);

        // Get all the organismList where gbifTaxonId contains UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldNotBeFound("gbifTaxonId.contains=" + UPDATED_GBIF_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonIdNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonId does not contain DEFAULT_GBIF_TAXON_ID
        defaultOrganismShouldNotBeFound("gbifTaxonId.doesNotContain=" + DEFAULT_GBIF_TAXON_ID);

        // Get all the organismList where gbifTaxonId does not contain UPDATED_GBIF_TAXON_ID
        defaultOrganismShouldBeFound("gbifTaxonId.doesNotContain=" + UPDATED_GBIF_TAXON_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId equals to DEFAULT_GBIF_DATASET_ID
        defaultOrganismShouldBeFound("gbifDatasetId.equals=" + DEFAULT_GBIF_DATASET_ID);

        // Get all the organismList where gbifDatasetId equals to UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldNotBeFound("gbifDatasetId.equals=" + UPDATED_GBIF_DATASET_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId not equals to DEFAULT_GBIF_DATASET_ID
        defaultOrganismShouldNotBeFound("gbifDatasetId.notEquals=" + DEFAULT_GBIF_DATASET_ID);

        // Get all the organismList where gbifDatasetId not equals to UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldBeFound("gbifDatasetId.notEquals=" + UPDATED_GBIF_DATASET_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId in DEFAULT_GBIF_DATASET_ID or UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldBeFound("gbifDatasetId.in=" + DEFAULT_GBIF_DATASET_ID + "," + UPDATED_GBIF_DATASET_ID);

        // Get all the organismList where gbifDatasetId equals to UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldNotBeFound("gbifDatasetId.in=" + UPDATED_GBIF_DATASET_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId is not null
        defaultOrganismShouldBeFound("gbifDatasetId.specified=true");

        // Get all the organismList where gbifDatasetId is null
        defaultOrganismShouldNotBeFound("gbifDatasetId.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId contains DEFAULT_GBIF_DATASET_ID
        defaultOrganismShouldBeFound("gbifDatasetId.contains=" + DEFAULT_GBIF_DATASET_ID);

        // Get all the organismList where gbifDatasetId contains UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldNotBeFound("gbifDatasetId.contains=" + UPDATED_GBIF_DATASET_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifDatasetIdNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifDatasetId does not contain DEFAULT_GBIF_DATASET_ID
        defaultOrganismShouldNotBeFound("gbifDatasetId.doesNotContain=" + DEFAULT_GBIF_DATASET_ID);

        // Get all the organismList where gbifDatasetId does not contain UPDATED_GBIF_DATASET_ID
        defaultOrganismShouldBeFound("gbifDatasetId.doesNotContain=" + UPDATED_GBIF_DATASET_ID);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus equals to DEFAULT_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.equals=" + DEFAULT_GBIF_TAXONOMIC_STATUS);

        // Get all the organismList where gbifTaxonomicStatus equals to UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.equals=" + UPDATED_GBIF_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus not equals to DEFAULT_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.notEquals=" + DEFAULT_GBIF_TAXONOMIC_STATUS);

        // Get all the organismList where gbifTaxonomicStatus not equals to UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.notEquals=" + UPDATED_GBIF_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus in DEFAULT_GBIF_TAXONOMIC_STATUS or UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.in=" + DEFAULT_GBIF_TAXONOMIC_STATUS + "," + UPDATED_GBIF_TAXONOMIC_STATUS);

        // Get all the organismList where gbifTaxonomicStatus equals to UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.in=" + UPDATED_GBIF_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus is not null
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.specified=true");

        // Get all the organismList where gbifTaxonomicStatus is null
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus contains DEFAULT_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.contains=" + DEFAULT_GBIF_TAXONOMIC_STATUS);

        // Get all the organismList where gbifTaxonomicStatus contains UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.contains=" + UPDATED_GBIF_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGbifTaxonomicStatusNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where gbifTaxonomicStatus does not contain DEFAULT_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldNotBeFound("gbifTaxonomicStatus.doesNotContain=" + DEFAULT_GBIF_TAXONOMIC_STATUS);

        // Get all the organismList where gbifTaxonomicStatus does not contain UPDATED_GBIF_TAXONOMIC_STATUS
        defaultOrganismShouldBeFound("gbifTaxonomicStatus.doesNotContain=" + UPDATED_GBIF_TAXONOMIC_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom equals to DEFAULT_KINGDOM
        defaultOrganismShouldBeFound("kingdom.equals=" + DEFAULT_KINGDOM);

        // Get all the organismList where kingdom equals to UPDATED_KINGDOM
        defaultOrganismShouldNotBeFound("kingdom.equals=" + UPDATED_KINGDOM);
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom not equals to DEFAULT_KINGDOM
        defaultOrganismShouldNotBeFound("kingdom.notEquals=" + DEFAULT_KINGDOM);

        // Get all the organismList where kingdom not equals to UPDATED_KINGDOM
        defaultOrganismShouldBeFound("kingdom.notEquals=" + UPDATED_KINGDOM);
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom in DEFAULT_KINGDOM or UPDATED_KINGDOM
        defaultOrganismShouldBeFound("kingdom.in=" + DEFAULT_KINGDOM + "," + UPDATED_KINGDOM);

        // Get all the organismList where kingdom equals to UPDATED_KINGDOM
        defaultOrganismShouldNotBeFound("kingdom.in=" + UPDATED_KINGDOM);
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom is not null
        defaultOrganismShouldBeFound("kingdom.specified=true");

        // Get all the organismList where kingdom is null
        defaultOrganismShouldNotBeFound("kingdom.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom contains DEFAULT_KINGDOM
        defaultOrganismShouldBeFound("kingdom.contains=" + DEFAULT_KINGDOM);

        // Get all the organismList where kingdom contains UPDATED_KINGDOM
        defaultOrganismShouldNotBeFound("kingdom.contains=" + UPDATED_KINGDOM);
    }

    @Test
    @Transactional
    void getAllOrganismsByKingdomNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where kingdom does not contain DEFAULT_KINGDOM
        defaultOrganismShouldNotBeFound("kingdom.doesNotContain=" + DEFAULT_KINGDOM);

        // Get all the organismList where kingdom does not contain UPDATED_KINGDOM
        defaultOrganismShouldBeFound("kingdom.doesNotContain=" + UPDATED_KINGDOM);
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum equals to DEFAULT_PHYLUM
        defaultOrganismShouldBeFound("phylum.equals=" + DEFAULT_PHYLUM);

        // Get all the organismList where phylum equals to UPDATED_PHYLUM
        defaultOrganismShouldNotBeFound("phylum.equals=" + UPDATED_PHYLUM);
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum not equals to DEFAULT_PHYLUM
        defaultOrganismShouldNotBeFound("phylum.notEquals=" + DEFAULT_PHYLUM);

        // Get all the organismList where phylum not equals to UPDATED_PHYLUM
        defaultOrganismShouldBeFound("phylum.notEquals=" + UPDATED_PHYLUM);
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum in DEFAULT_PHYLUM or UPDATED_PHYLUM
        defaultOrganismShouldBeFound("phylum.in=" + DEFAULT_PHYLUM + "," + UPDATED_PHYLUM);

        // Get all the organismList where phylum equals to UPDATED_PHYLUM
        defaultOrganismShouldNotBeFound("phylum.in=" + UPDATED_PHYLUM);
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum is not null
        defaultOrganismShouldBeFound("phylum.specified=true");

        // Get all the organismList where phylum is null
        defaultOrganismShouldNotBeFound("phylum.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum contains DEFAULT_PHYLUM
        defaultOrganismShouldBeFound("phylum.contains=" + DEFAULT_PHYLUM);

        // Get all the organismList where phylum contains UPDATED_PHYLUM
        defaultOrganismShouldNotBeFound("phylum.contains=" + UPDATED_PHYLUM);
    }

    @Test
    @Transactional
    void getAllOrganismsByPhylumNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where phylum does not contain DEFAULT_PHYLUM
        defaultOrganismShouldNotBeFound("phylum.doesNotContain=" + DEFAULT_PHYLUM);

        // Get all the organismList where phylum does not contain UPDATED_PHYLUM
        defaultOrganismShouldBeFound("phylum.doesNotContain=" + UPDATED_PHYLUM);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass equals to DEFAULT_ORGANISM_CLASS
        defaultOrganismShouldBeFound("organismClass.equals=" + DEFAULT_ORGANISM_CLASS);

        // Get all the organismList where organismClass equals to UPDATED_ORGANISM_CLASS
        defaultOrganismShouldNotBeFound("organismClass.equals=" + UPDATED_ORGANISM_CLASS);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass not equals to DEFAULT_ORGANISM_CLASS
        defaultOrganismShouldNotBeFound("organismClass.notEquals=" + DEFAULT_ORGANISM_CLASS);

        // Get all the organismList where organismClass not equals to UPDATED_ORGANISM_CLASS
        defaultOrganismShouldBeFound("organismClass.notEquals=" + UPDATED_ORGANISM_CLASS);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass in DEFAULT_ORGANISM_CLASS or UPDATED_ORGANISM_CLASS
        defaultOrganismShouldBeFound("organismClass.in=" + DEFAULT_ORGANISM_CLASS + "," + UPDATED_ORGANISM_CLASS);

        // Get all the organismList where organismClass equals to UPDATED_ORGANISM_CLASS
        defaultOrganismShouldNotBeFound("organismClass.in=" + UPDATED_ORGANISM_CLASS);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass is not null
        defaultOrganismShouldBeFound("organismClass.specified=true");

        // Get all the organismList where organismClass is null
        defaultOrganismShouldNotBeFound("organismClass.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass contains DEFAULT_ORGANISM_CLASS
        defaultOrganismShouldBeFound("organismClass.contains=" + DEFAULT_ORGANISM_CLASS);

        // Get all the organismList where organismClass contains UPDATED_ORGANISM_CLASS
        defaultOrganismShouldNotBeFound("organismClass.contains=" + UPDATED_ORGANISM_CLASS);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrganismClassNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where organismClass does not contain DEFAULT_ORGANISM_CLASS
        defaultOrganismShouldNotBeFound("organismClass.doesNotContain=" + DEFAULT_ORGANISM_CLASS);

        // Get all the organismList where organismClass does not contain UPDATED_ORGANISM_CLASS
        defaultOrganismShouldBeFound("organismClass.doesNotContain=" + UPDATED_ORGANISM_CLASS);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order equals to DEFAULT_ORDER
        defaultOrganismShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the organismList where order equals to UPDATED_ORDER
        defaultOrganismShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order not equals to DEFAULT_ORDER
        defaultOrganismShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the organismList where order not equals to UPDATED_ORDER
        defaultOrganismShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultOrganismShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the organismList where order equals to UPDATED_ORDER
        defaultOrganismShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order is not null
        defaultOrganismShouldBeFound("order.specified=true");

        // Get all the organismList where order is null
        defaultOrganismShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order contains DEFAULT_ORDER
        defaultOrganismShouldBeFound("order.contains=" + DEFAULT_ORDER);

        // Get all the organismList where order contains UPDATED_ORDER
        defaultOrganismShouldNotBeFound("order.contains=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllOrganismsByOrderNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where order does not contain DEFAULT_ORDER
        defaultOrganismShouldNotBeFound("order.doesNotContain=" + DEFAULT_ORDER);

        // Get all the organismList where order does not contain UPDATED_ORDER
        defaultOrganismShouldBeFound("order.doesNotContain=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family equals to DEFAULT_FAMILY
        defaultOrganismShouldBeFound("family.equals=" + DEFAULT_FAMILY);

        // Get all the organismList where family equals to UPDATED_FAMILY
        defaultOrganismShouldNotBeFound("family.equals=" + UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family not equals to DEFAULT_FAMILY
        defaultOrganismShouldNotBeFound("family.notEquals=" + DEFAULT_FAMILY);

        // Get all the organismList where family not equals to UPDATED_FAMILY
        defaultOrganismShouldBeFound("family.notEquals=" + UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family in DEFAULT_FAMILY or UPDATED_FAMILY
        defaultOrganismShouldBeFound("family.in=" + DEFAULT_FAMILY + "," + UPDATED_FAMILY);

        // Get all the organismList where family equals to UPDATED_FAMILY
        defaultOrganismShouldNotBeFound("family.in=" + UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family is not null
        defaultOrganismShouldBeFound("family.specified=true");

        // Get all the organismList where family is null
        defaultOrganismShouldNotBeFound("family.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family contains DEFAULT_FAMILY
        defaultOrganismShouldBeFound("family.contains=" + DEFAULT_FAMILY);

        // Get all the organismList where family contains UPDATED_FAMILY
        defaultOrganismShouldNotBeFound("family.contains=" + UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void getAllOrganismsByFamilyNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where family does not contain DEFAULT_FAMILY
        defaultOrganismShouldNotBeFound("family.doesNotContain=" + DEFAULT_FAMILY);

        // Get all the organismList where family does not contain UPDATED_FAMILY
        defaultOrganismShouldBeFound("family.doesNotContain=" + UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusIsEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus equals to DEFAULT_GENUS
        defaultOrganismShouldBeFound("genus.equals=" + DEFAULT_GENUS);

        // Get all the organismList where genus equals to UPDATED_GENUS
        defaultOrganismShouldNotBeFound("genus.equals=" + UPDATED_GENUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus not equals to DEFAULT_GENUS
        defaultOrganismShouldNotBeFound("genus.notEquals=" + DEFAULT_GENUS);

        // Get all the organismList where genus not equals to UPDATED_GENUS
        defaultOrganismShouldBeFound("genus.notEquals=" + UPDATED_GENUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusIsInShouldWork() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus in DEFAULT_GENUS or UPDATED_GENUS
        defaultOrganismShouldBeFound("genus.in=" + DEFAULT_GENUS + "," + UPDATED_GENUS);

        // Get all the organismList where genus equals to UPDATED_GENUS
        defaultOrganismShouldNotBeFound("genus.in=" + UPDATED_GENUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusIsNullOrNotNull() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus is not null
        defaultOrganismShouldBeFound("genus.specified=true");

        // Get all the organismList where genus is null
        defaultOrganismShouldNotBeFound("genus.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus contains DEFAULT_GENUS
        defaultOrganismShouldBeFound("genus.contains=" + DEFAULT_GENUS);

        // Get all the organismList where genus contains UPDATED_GENUS
        defaultOrganismShouldNotBeFound("genus.contains=" + UPDATED_GENUS);
    }

    @Test
    @Transactional
    void getAllOrganismsByGenusNotContainsSomething() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        // Get all the organismList where genus does not contain DEFAULT_GENUS
        defaultOrganismShouldNotBeFound("genus.doesNotContain=" + DEFAULT_GENUS);

        // Get all the organismList where genus does not contain UPDATED_GENUS
        defaultOrganismShouldBeFound("genus.doesNotContain=" + UPDATED_GENUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganismShouldBeFound(String filter) throws Exception {
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organism.getId().intValue())))
            .andExpect(jsonPath("$.[*].whonetOrgCode").value(hasItem(DEFAULT_WHONET_ORG_CODE)))
            .andExpect(jsonPath("$.[*].organism").value(hasItem(DEFAULT_ORGANISM)))
            .andExpect(jsonPath("$.[*].taxonomicStatus").value(hasItem(DEFAULT_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].common").value(hasItem(DEFAULT_COMMON)))
            .andExpect(jsonPath("$.[*].organismType").value(hasItem(DEFAULT_ORGANISM_TYPE)))
            .andExpect(jsonPath("$.[*].anaerobe").value(hasItem(DEFAULT_ANAEROBE)))
            .andExpect(jsonPath("$.[*].morphology").value(hasItem(DEFAULT_MORPHOLOGY)))
            .andExpect(jsonPath("$.[*].subkingdomCode").value(hasItem(DEFAULT_SUBKINGDOM_CODE)))
            .andExpect(jsonPath("$.[*].familyCode").value(hasItem(DEFAULT_FAMILY_CODE)))
            .andExpect(jsonPath("$.[*].genusGroup").value(hasItem(DEFAULT_GENUS_GROUP)))
            .andExpect(jsonPath("$.[*].genusCode").value(hasItem(DEFAULT_GENUS_CODE)))
            .andExpect(jsonPath("$.[*].speciesGroup").value(hasItem(DEFAULT_SPECIES_GROUP)))
            .andExpect(jsonPath("$.[*].serovarGroup").value(hasItem(DEFAULT_SEROVAR_GROUP)))
            .andExpect(jsonPath("$.[*].msfGrpClin").value(hasItem(DEFAULT_MSF_GRP_CLIN)))
            .andExpect(jsonPath("$.[*].sctCode").value(hasItem(DEFAULT_SCT_CODE)))
            .andExpect(jsonPath("$.[*].sctText").value(hasItem(DEFAULT_SCT_TEXT)))
            .andExpect(jsonPath("$.[*].dwcTaxonId").value(hasItem(DEFAULT_DWC_TAXON_ID)))
            .andExpect(jsonPath("$.[*].dwcTaxonomicStatus").value(hasItem(DEFAULT_DWC_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].gbifTaxonId").value(hasItem(DEFAULT_GBIF_TAXON_ID)))
            .andExpect(jsonPath("$.[*].gbifDatasetId").value(hasItem(DEFAULT_GBIF_DATASET_ID)))
            .andExpect(jsonPath("$.[*].gbifTaxonomicStatus").value(hasItem(DEFAULT_GBIF_TAXONOMIC_STATUS)))
            .andExpect(jsonPath("$.[*].kingdom").value(hasItem(DEFAULT_KINGDOM)))
            .andExpect(jsonPath("$.[*].phylum").value(hasItem(DEFAULT_PHYLUM)))
            .andExpect(jsonPath("$.[*].organismClass").value(hasItem(DEFAULT_ORGANISM_CLASS)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)))
            .andExpect(jsonPath("$.[*].genus").value(hasItem(DEFAULT_GENUS)));

        // Check, that the count call also returns 1
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganismShouldNotBeFound(String filter) throws Exception {
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganismMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganism() throws Exception {
        // Get the organism
        restOrganismMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism
        Organism updatedOrganism = organismRepository.findById(organism.getId()).get();
        // Disconnect from session so that the updates on updatedOrganism are not directly saved in db
        em.detach(updatedOrganism);
        updatedOrganism
            .whonetOrgCode(UPDATED_WHONET_ORG_CODE)
            .organism(UPDATED_ORGANISM)
            .taxonomicStatus(UPDATED_TAXONOMIC_STATUS)
            .common(UPDATED_COMMON)
            .organismType(UPDATED_ORGANISM_TYPE)
            .anaerobe(UPDATED_ANAEROBE)
            .morphology(UPDATED_MORPHOLOGY)
            .subkingdomCode(UPDATED_SUBKINGDOM_CODE)
            .familyCode(UPDATED_FAMILY_CODE)
            .genusGroup(UPDATED_GENUS_GROUP)
            .genusCode(UPDATED_GENUS_CODE)
            .speciesGroup(UPDATED_SPECIES_GROUP)
            .serovarGroup(UPDATED_SEROVAR_GROUP)
            .msfGrpClin(UPDATED_MSF_GRP_CLIN)
            .sctCode(UPDATED_SCT_CODE)
            .sctText(UPDATED_SCT_TEXT)
            .dwcTaxonId(UPDATED_DWC_TAXON_ID)
            .dwcTaxonomicStatus(UPDATED_DWC_TAXONOMIC_STATUS)
            .gbifTaxonId(UPDATED_GBIF_TAXON_ID)
            .gbifDatasetId(UPDATED_GBIF_DATASET_ID)
            .gbifTaxonomicStatus(UPDATED_GBIF_TAXONOMIC_STATUS)
            .kingdom(UPDATED_KINGDOM)
            .phylum(UPDATED_PHYLUM)
            .organismClass(UPDATED_ORGANISM_CLASS)
            .order(UPDATED_ORDER)
            .family(UPDATED_FAMILY)
            .genus(UPDATED_GENUS);
        OrganismDTO organismDTO = organismMapper.toDto(updatedOrganism);

        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organismDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getWhonetOrgCode()).isEqualTo(UPDATED_WHONET_ORG_CODE);
        assertThat(testOrganism.getOrganism()).isEqualTo(UPDATED_ORGANISM);
        assertThat(testOrganism.getTaxonomicStatus()).isEqualTo(UPDATED_TAXONOMIC_STATUS);
        assertThat(testOrganism.getCommon()).isEqualTo(UPDATED_COMMON);
        assertThat(testOrganism.getOrganismType()).isEqualTo(UPDATED_ORGANISM_TYPE);
        assertThat(testOrganism.getAnaerobe()).isEqualTo(UPDATED_ANAEROBE);
        assertThat(testOrganism.getMorphology()).isEqualTo(UPDATED_MORPHOLOGY);
        assertThat(testOrganism.getSubkingdomCode()).isEqualTo(UPDATED_SUBKINGDOM_CODE);
        assertThat(testOrganism.getFamilyCode()).isEqualTo(UPDATED_FAMILY_CODE);
        assertThat(testOrganism.getGenusGroup()).isEqualTo(UPDATED_GENUS_GROUP);
        assertThat(testOrganism.getGenusCode()).isEqualTo(UPDATED_GENUS_CODE);
        assertThat(testOrganism.getSpeciesGroup()).isEqualTo(UPDATED_SPECIES_GROUP);
        assertThat(testOrganism.getSerovarGroup()).isEqualTo(UPDATED_SEROVAR_GROUP);
        assertThat(testOrganism.getMsfGrpClin()).isEqualTo(UPDATED_MSF_GRP_CLIN);
        assertThat(testOrganism.getSctCode()).isEqualTo(UPDATED_SCT_CODE);
        assertThat(testOrganism.getSctText()).isEqualTo(UPDATED_SCT_TEXT);
        assertThat(testOrganism.getDwcTaxonId()).isEqualTo(UPDATED_DWC_TAXON_ID);
        assertThat(testOrganism.getDwcTaxonomicStatus()).isEqualTo(UPDATED_DWC_TAXONOMIC_STATUS);
        assertThat(testOrganism.getGbifTaxonId()).isEqualTo(UPDATED_GBIF_TAXON_ID);
        assertThat(testOrganism.getGbifDatasetId()).isEqualTo(UPDATED_GBIF_DATASET_ID);
        assertThat(testOrganism.getGbifTaxonomicStatus()).isEqualTo(UPDATED_GBIF_TAXONOMIC_STATUS);
        assertThat(testOrganism.getKingdom()).isEqualTo(UPDATED_KINGDOM);
        assertThat(testOrganism.getPhylum()).isEqualTo(UPDATED_PHYLUM);
        assertThat(testOrganism.getOrganismClass()).isEqualTo(UPDATED_ORGANISM_CLASS);
        assertThat(testOrganism.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testOrganism.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testOrganism.getGenus()).isEqualTo(UPDATED_GENUS);
    }

    @Test
    @Transactional
    void putNonExistingOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organismDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organismDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganismWithPatch() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism using partial update
        Organism partialUpdatedOrganism = new Organism();
        partialUpdatedOrganism.setId(organism.getId());

        partialUpdatedOrganism
            .taxonomicStatus(UPDATED_TAXONOMIC_STATUS)
            .morphology(UPDATED_MORPHOLOGY)
            .speciesGroup(UPDATED_SPECIES_GROUP)
            .serovarGroup(UPDATED_SEROVAR_GROUP)
            .msfGrpClin(UPDATED_MSF_GRP_CLIN)
            .gbifTaxonId(UPDATED_GBIF_TAXON_ID)
            .gbifDatasetId(UPDATED_GBIF_DATASET_ID)
            .gbifTaxonomicStatus(UPDATED_GBIF_TAXONOMIC_STATUS)
            .phylum(UPDATED_PHYLUM)
            .order(UPDATED_ORDER)
            .family(UPDATED_FAMILY)
            .genus(UPDATED_GENUS);

        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganism))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getWhonetOrgCode()).isEqualTo(DEFAULT_WHONET_ORG_CODE);
        assertThat(testOrganism.getOrganism()).isEqualTo(DEFAULT_ORGANISM);
        assertThat(testOrganism.getTaxonomicStatus()).isEqualTo(UPDATED_TAXONOMIC_STATUS);
        assertThat(testOrganism.getCommon()).isEqualTo(DEFAULT_COMMON);
        assertThat(testOrganism.getOrganismType()).isEqualTo(DEFAULT_ORGANISM_TYPE);
        assertThat(testOrganism.getAnaerobe()).isEqualTo(DEFAULT_ANAEROBE);
        assertThat(testOrganism.getMorphology()).isEqualTo(UPDATED_MORPHOLOGY);
        assertThat(testOrganism.getSubkingdomCode()).isEqualTo(DEFAULT_SUBKINGDOM_CODE);
        assertThat(testOrganism.getFamilyCode()).isEqualTo(DEFAULT_FAMILY_CODE);
        assertThat(testOrganism.getGenusGroup()).isEqualTo(DEFAULT_GENUS_GROUP);
        assertThat(testOrganism.getGenusCode()).isEqualTo(DEFAULT_GENUS_CODE);
        assertThat(testOrganism.getSpeciesGroup()).isEqualTo(UPDATED_SPECIES_GROUP);
        assertThat(testOrganism.getSerovarGroup()).isEqualTo(UPDATED_SEROVAR_GROUP);
        assertThat(testOrganism.getMsfGrpClin()).isEqualTo(UPDATED_MSF_GRP_CLIN);
        assertThat(testOrganism.getSctCode()).isEqualTo(DEFAULT_SCT_CODE);
        assertThat(testOrganism.getSctText()).isEqualTo(DEFAULT_SCT_TEXT);
        assertThat(testOrganism.getDwcTaxonId()).isEqualTo(DEFAULT_DWC_TAXON_ID);
        assertThat(testOrganism.getDwcTaxonomicStatus()).isEqualTo(DEFAULT_DWC_TAXONOMIC_STATUS);
        assertThat(testOrganism.getGbifTaxonId()).isEqualTo(UPDATED_GBIF_TAXON_ID);
        assertThat(testOrganism.getGbifDatasetId()).isEqualTo(UPDATED_GBIF_DATASET_ID);
        assertThat(testOrganism.getGbifTaxonomicStatus()).isEqualTo(UPDATED_GBIF_TAXONOMIC_STATUS);
        assertThat(testOrganism.getKingdom()).isEqualTo(DEFAULT_KINGDOM);
        assertThat(testOrganism.getPhylum()).isEqualTo(UPDATED_PHYLUM);
        assertThat(testOrganism.getOrganismClass()).isEqualTo(DEFAULT_ORGANISM_CLASS);
        assertThat(testOrganism.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testOrganism.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testOrganism.getGenus()).isEqualTo(UPDATED_GENUS);
    }

    @Test
    @Transactional
    void fullUpdateOrganismWithPatch() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeUpdate = organismRepository.findAll().size();

        // Update the organism using partial update
        Organism partialUpdatedOrganism = new Organism();
        partialUpdatedOrganism.setId(organism.getId());

        partialUpdatedOrganism
            .whonetOrgCode(UPDATED_WHONET_ORG_CODE)
            .organism(UPDATED_ORGANISM)
            .taxonomicStatus(UPDATED_TAXONOMIC_STATUS)
            .common(UPDATED_COMMON)
            .organismType(UPDATED_ORGANISM_TYPE)
            .anaerobe(UPDATED_ANAEROBE)
            .morphology(UPDATED_MORPHOLOGY)
            .subkingdomCode(UPDATED_SUBKINGDOM_CODE)
            .familyCode(UPDATED_FAMILY_CODE)
            .genusGroup(UPDATED_GENUS_GROUP)
            .genusCode(UPDATED_GENUS_CODE)
            .speciesGroup(UPDATED_SPECIES_GROUP)
            .serovarGroup(UPDATED_SEROVAR_GROUP)
            .msfGrpClin(UPDATED_MSF_GRP_CLIN)
            .sctCode(UPDATED_SCT_CODE)
            .sctText(UPDATED_SCT_TEXT)
            .dwcTaxonId(UPDATED_DWC_TAXON_ID)
            .dwcTaxonomicStatus(UPDATED_DWC_TAXONOMIC_STATUS)
            .gbifTaxonId(UPDATED_GBIF_TAXON_ID)
            .gbifDatasetId(UPDATED_GBIF_DATASET_ID)
            .gbifTaxonomicStatus(UPDATED_GBIF_TAXONOMIC_STATUS)
            .kingdom(UPDATED_KINGDOM)
            .phylum(UPDATED_PHYLUM)
            .organismClass(UPDATED_ORGANISM_CLASS)
            .order(UPDATED_ORDER)
            .family(UPDATED_FAMILY)
            .genus(UPDATED_GENUS);

        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganism.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganism))
            )
            .andExpect(status().isOk());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
        Organism testOrganism = organismList.get(organismList.size() - 1);
        assertThat(testOrganism.getWhonetOrgCode()).isEqualTo(UPDATED_WHONET_ORG_CODE);
        assertThat(testOrganism.getOrganism()).isEqualTo(UPDATED_ORGANISM);
        assertThat(testOrganism.getTaxonomicStatus()).isEqualTo(UPDATED_TAXONOMIC_STATUS);
        assertThat(testOrganism.getCommon()).isEqualTo(UPDATED_COMMON);
        assertThat(testOrganism.getOrganismType()).isEqualTo(UPDATED_ORGANISM_TYPE);
        assertThat(testOrganism.getAnaerobe()).isEqualTo(UPDATED_ANAEROBE);
        assertThat(testOrganism.getMorphology()).isEqualTo(UPDATED_MORPHOLOGY);
        assertThat(testOrganism.getSubkingdomCode()).isEqualTo(UPDATED_SUBKINGDOM_CODE);
        assertThat(testOrganism.getFamilyCode()).isEqualTo(UPDATED_FAMILY_CODE);
        assertThat(testOrganism.getGenusGroup()).isEqualTo(UPDATED_GENUS_GROUP);
        assertThat(testOrganism.getGenusCode()).isEqualTo(UPDATED_GENUS_CODE);
        assertThat(testOrganism.getSpeciesGroup()).isEqualTo(UPDATED_SPECIES_GROUP);
        assertThat(testOrganism.getSerovarGroup()).isEqualTo(UPDATED_SEROVAR_GROUP);
        assertThat(testOrganism.getMsfGrpClin()).isEqualTo(UPDATED_MSF_GRP_CLIN);
        assertThat(testOrganism.getSctCode()).isEqualTo(UPDATED_SCT_CODE);
        assertThat(testOrganism.getSctText()).isEqualTo(UPDATED_SCT_TEXT);
        assertThat(testOrganism.getDwcTaxonId()).isEqualTo(UPDATED_DWC_TAXON_ID);
        assertThat(testOrganism.getDwcTaxonomicStatus()).isEqualTo(UPDATED_DWC_TAXONOMIC_STATUS);
        assertThat(testOrganism.getGbifTaxonId()).isEqualTo(UPDATED_GBIF_TAXON_ID);
        assertThat(testOrganism.getGbifDatasetId()).isEqualTo(UPDATED_GBIF_DATASET_ID);
        assertThat(testOrganism.getGbifTaxonomicStatus()).isEqualTo(UPDATED_GBIF_TAXONOMIC_STATUS);
        assertThat(testOrganism.getKingdom()).isEqualTo(UPDATED_KINGDOM);
        assertThat(testOrganism.getPhylum()).isEqualTo(UPDATED_PHYLUM);
        assertThat(testOrganism.getOrganismClass()).isEqualTo(UPDATED_ORGANISM_CLASS);
        assertThat(testOrganism.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testOrganism.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testOrganism.getGenus()).isEqualTo(UPDATED_GENUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organismDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganism() throws Exception {
        int databaseSizeBeforeUpdate = organismRepository.findAll().size();
        organism.setId(count.incrementAndGet());

        // Create the Organism
        OrganismDTO organismDTO = organismMapper.toDto(organism);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganismMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organismDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organism in the database
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganism() throws Exception {
        // Initialize the database
        organismRepository.saveAndFlush(organism);

        int databaseSizeBeforeDelete = organismRepository.findAll().size();

        // Delete the organism
        restOrganismMockMvc
            .perform(delete(ENTITY_API_URL_ID, organism.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organism> organismList = organismRepository.findAll();
        assertThat(organismList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
