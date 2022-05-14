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
import org.path.amr.services.domain.IntrinsicResistance;
import org.path.amr.services.repository.IntrinsicResistanceRepository;
import org.path.amr.services.service.criteria.IntrinsicResistanceCriteria;
import org.path.amr.services.service.dto.IntrinsicResistanceDTO;
import org.path.amr.services.service.mapper.IntrinsicResistanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link IntrinsicResistanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntrinsicResistanceResourceIT {

    private static final String DEFAULT_GUIDELINE = "AAAAAAAAAA";
    private static final String UPDATED_GUIDELINE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCEPTION_ORGANISM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTION_ORGANISM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTION_ORGANISM_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ABX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ABX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ABX_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ABX_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ANTIBIOTIC_EXCEPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ANTIBIOTIC_EXCEPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ENTERED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ENTERED = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/intrinsic-resistances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntrinsicResistanceRepository intrinsicResistanceRepository;

    @Autowired
    private IntrinsicResistanceMapper intrinsicResistanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntrinsicResistanceMockMvc;

    private IntrinsicResistance intrinsicResistance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrinsicResistance createEntity(EntityManager em) {
        IntrinsicResistance intrinsicResistance = new IntrinsicResistance()
            .guideline(DEFAULT_GUIDELINE)
            .referenceTable(DEFAULT_REFERENCE_TABLE)
            .organismCode(DEFAULT_ORGANISM_CODE)
            .organismCodeType(DEFAULT_ORGANISM_CODE_TYPE)
            .exceptionOrganismCode(DEFAULT_EXCEPTION_ORGANISM_CODE)
            .exceptionOrganismCodeType(DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE)
            .abxCode(DEFAULT_ABX_CODE)
            .abxCodeType(DEFAULT_ABX_CODE_TYPE)
            .antibioticExceptions(DEFAULT_ANTIBIOTIC_EXCEPTIONS)
            .dateEntered(DEFAULT_DATE_ENTERED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .comments(DEFAULT_COMMENTS);
        return intrinsicResistance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrinsicResistance createUpdatedEntity(EntityManager em) {
        IntrinsicResistance intrinsicResistance = new IntrinsicResistance()
            .guideline(UPDATED_GUIDELINE)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .exceptionOrganismCode(UPDATED_EXCEPTION_ORGANISM_CODE)
            .exceptionOrganismCodeType(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE)
            .abxCode(UPDATED_ABX_CODE)
            .abxCodeType(UPDATED_ABX_CODE_TYPE)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        return intrinsicResistance;
    }

    @BeforeEach
    public void initTest() {
        intrinsicResistance = createEntity(em);
    }

    @Test
    @Transactional
    void createIntrinsicResistance() throws Exception {
        int databaseSizeBeforeCreate = intrinsicResistanceRepository.findAll().size();
        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);
        restIntrinsicResistanceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeCreate + 1);
        IntrinsicResistance testIntrinsicResistance = intrinsicResistanceList.get(intrinsicResistanceList.size() - 1);
        assertThat(testIntrinsicResistance.getGuideline()).isEqualTo(DEFAULT_GUIDELINE);
        assertThat(testIntrinsicResistance.getReferenceTable()).isEqualTo(DEFAULT_REFERENCE_TABLE);
        assertThat(testIntrinsicResistance.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getOrganismCodeType()).isEqualTo(DEFAULT_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCode()).isEqualTo(DEFAULT_EXCEPTION_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCodeType()).isEqualTo(DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAbxCode()).isEqualTo(DEFAULT_ABX_CODE);
        assertThat(testIntrinsicResistance.getAbxCodeType()).isEqualTo(DEFAULT_ABX_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAntibioticExceptions()).isEqualTo(DEFAULT_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testIntrinsicResistance.getDateEntered()).isEqualTo(DEFAULT_DATE_ENTERED);
        assertThat(testIntrinsicResistance.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testIntrinsicResistance.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createIntrinsicResistanceWithExistingId() throws Exception {
        // Create the IntrinsicResistance with an existing ID
        intrinsicResistance.setId(1L);
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        int databaseSizeBeforeCreate = intrinsicResistanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntrinsicResistanceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistances() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intrinsicResistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].guideline").value(hasItem(DEFAULT_GUIDELINE)))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].exceptionOrganismCode").value(hasItem(DEFAULT_EXCEPTION_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].exceptionOrganismCodeType").value(hasItem(DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].abxCode").value(hasItem(DEFAULT_ABX_CODE)))
            .andExpect(jsonPath("$.[*].abxCodeType").value(hasItem(DEFAULT_ABX_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].antibioticExceptions").value(hasItem(DEFAULT_ANTIBIOTIC_EXCEPTIONS)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getIntrinsicResistance() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get the intrinsicResistance
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL_ID, intrinsicResistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intrinsicResistance.getId().intValue()))
            .andExpect(jsonPath("$.guideline").value(DEFAULT_GUIDELINE))
            .andExpect(jsonPath("$.referenceTable").value(DEFAULT_REFERENCE_TABLE))
            .andExpect(jsonPath("$.organismCode").value(DEFAULT_ORGANISM_CODE))
            .andExpect(jsonPath("$.organismCodeType").value(DEFAULT_ORGANISM_CODE_TYPE))
            .andExpect(jsonPath("$.exceptionOrganismCode").value(DEFAULT_EXCEPTION_ORGANISM_CODE))
            .andExpect(jsonPath("$.exceptionOrganismCodeType").value(DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE))
            .andExpect(jsonPath("$.abxCode").value(DEFAULT_ABX_CODE))
            .andExpect(jsonPath("$.abxCodeType").value(DEFAULT_ABX_CODE_TYPE))
            .andExpect(jsonPath("$.antibioticExceptions").value(DEFAULT_ANTIBIOTIC_EXCEPTIONS))
            .andExpect(jsonPath("$.dateEntered").value(DEFAULT_DATE_ENTERED))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getIntrinsicResistancesByIdFiltering() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        Long id = intrinsicResistance.getId();

        defaultIntrinsicResistanceShouldBeFound("id.equals=" + id);
        defaultIntrinsicResistanceShouldNotBeFound("id.notEquals=" + id);

        defaultIntrinsicResistanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIntrinsicResistanceShouldNotBeFound("id.greaterThan=" + id);

        defaultIntrinsicResistanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIntrinsicResistanceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline equals to DEFAULT_GUIDELINE
        defaultIntrinsicResistanceShouldBeFound("guideline.equals=" + DEFAULT_GUIDELINE);

        // Get all the intrinsicResistanceList where guideline equals to UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldNotBeFound("guideline.equals=" + UPDATED_GUIDELINE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline not equals to DEFAULT_GUIDELINE
        defaultIntrinsicResistanceShouldNotBeFound("guideline.notEquals=" + DEFAULT_GUIDELINE);

        // Get all the intrinsicResistanceList where guideline not equals to UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldBeFound("guideline.notEquals=" + UPDATED_GUIDELINE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline in DEFAULT_GUIDELINE or UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldBeFound("guideline.in=" + DEFAULT_GUIDELINE + "," + UPDATED_GUIDELINE);

        // Get all the intrinsicResistanceList where guideline equals to UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldNotBeFound("guideline.in=" + UPDATED_GUIDELINE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline is not null
        defaultIntrinsicResistanceShouldBeFound("guideline.specified=true");

        // Get all the intrinsicResistanceList where guideline is null
        defaultIntrinsicResistanceShouldNotBeFound("guideline.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline contains DEFAULT_GUIDELINE
        defaultIntrinsicResistanceShouldBeFound("guideline.contains=" + DEFAULT_GUIDELINE);

        // Get all the intrinsicResistanceList where guideline contains UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldNotBeFound("guideline.contains=" + UPDATED_GUIDELINE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByGuidelineNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where guideline does not contain DEFAULT_GUIDELINE
        defaultIntrinsicResistanceShouldNotBeFound("guideline.doesNotContain=" + DEFAULT_GUIDELINE);

        // Get all the intrinsicResistanceList where guideline does not contain UPDATED_GUIDELINE
        defaultIntrinsicResistanceShouldBeFound("guideline.doesNotContain=" + UPDATED_GUIDELINE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable equals to DEFAULT_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldBeFound("referenceTable.equals=" + DEFAULT_REFERENCE_TABLE);

        // Get all the intrinsicResistanceList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.equals=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable not equals to DEFAULT_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.notEquals=" + DEFAULT_REFERENCE_TABLE);

        // Get all the intrinsicResistanceList where referenceTable not equals to UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldBeFound("referenceTable.notEquals=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable in DEFAULT_REFERENCE_TABLE or UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldBeFound("referenceTable.in=" + DEFAULT_REFERENCE_TABLE + "," + UPDATED_REFERENCE_TABLE);

        // Get all the intrinsicResistanceList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.in=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable is not null
        defaultIntrinsicResistanceShouldBeFound("referenceTable.specified=true");

        // Get all the intrinsicResistanceList where referenceTable is null
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable contains DEFAULT_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldBeFound("referenceTable.contains=" + DEFAULT_REFERENCE_TABLE);

        // Get all the intrinsicResistanceList where referenceTable contains UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.contains=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByReferenceTableNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where referenceTable does not contain DEFAULT_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldNotBeFound("referenceTable.doesNotContain=" + DEFAULT_REFERENCE_TABLE);

        // Get all the intrinsicResistanceList where referenceTable does not contain UPDATED_REFERENCE_TABLE
        defaultIntrinsicResistanceShouldBeFound("referenceTable.doesNotContain=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode equals to DEFAULT_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("organismCode.equals=" + DEFAULT_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.equals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode not equals to DEFAULT_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.notEquals=" + DEFAULT_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where organismCode not equals to UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("organismCode.notEquals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode in DEFAULT_ORGANISM_CODE or UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("organismCode.in=" + DEFAULT_ORGANISM_CODE + "," + UPDATED_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.in=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode is not null
        defaultIntrinsicResistanceShouldBeFound("organismCode.specified=true");

        // Get all the intrinsicResistanceList where organismCode is null
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode contains DEFAULT_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("organismCode.contains=" + DEFAULT_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where organismCode contains UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.contains=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCode does not contain DEFAULT_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("organismCode.doesNotContain=" + DEFAULT_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where organismCode does not contain UPDATED_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("organismCode.doesNotContain=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.equals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.equals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType not equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.notEquals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where organismCodeType not equals to UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.notEquals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType in DEFAULT_ORGANISM_CODE_TYPE or UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.in=" + DEFAULT_ORGANISM_CODE_TYPE + "," + UPDATED_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.in=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType is not null
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.specified=true");

        // Get all the intrinsicResistanceList where organismCodeType is null
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType contains DEFAULT_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.contains=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where organismCodeType contains UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.contains=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByOrganismCodeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where organismCodeType does not contain DEFAULT_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("organismCodeType.doesNotContain=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where organismCodeType does not contain UPDATED_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("organismCodeType.doesNotContain=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode equals to DEFAULT_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCode.equals=" + DEFAULT_EXCEPTION_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where exceptionOrganismCode equals to UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.equals=" + UPDATED_EXCEPTION_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode not equals to DEFAULT_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.notEquals=" + DEFAULT_EXCEPTION_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where exceptionOrganismCode not equals to UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCode.notEquals=" + UPDATED_EXCEPTION_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode in DEFAULT_EXCEPTION_ORGANISM_CODE or UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound(
            "exceptionOrganismCode.in=" + DEFAULT_EXCEPTION_ORGANISM_CODE + "," + UPDATED_EXCEPTION_ORGANISM_CODE
        );

        // Get all the intrinsicResistanceList where exceptionOrganismCode equals to UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.in=" + UPDATED_EXCEPTION_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode is not null
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCode.specified=true");

        // Get all the intrinsicResistanceList where exceptionOrganismCode is null
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode contains DEFAULT_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCode.contains=" + DEFAULT_EXCEPTION_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where exceptionOrganismCode contains UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.contains=" + UPDATED_EXCEPTION_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCode does not contain DEFAULT_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCode.doesNotContain=" + DEFAULT_EXCEPTION_ORGANISM_CODE);

        // Get all the intrinsicResistanceList where exceptionOrganismCode does not contain UPDATED_EXCEPTION_ORGANISM_CODE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCode.doesNotContain=" + UPDATED_EXCEPTION_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType equals to DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCodeType.equals=" + DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType equals to UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.equals=" + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType not equals to DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.notEquals=" + DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType not equals to UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCodeType.notEquals=" + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType in DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE or UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound(
            "exceptionOrganismCodeType.in=" + DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE + "," + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        );

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType equals to UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.in=" + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType is not null
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCodeType.specified=true");

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType is null
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType contains DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCodeType.contains=" + DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType contains UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.contains=" + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByExceptionOrganismCodeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType does not contain DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("exceptionOrganismCodeType.doesNotContain=" + DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE);

        // Get all the intrinsicResistanceList where exceptionOrganismCodeType does not contain UPDATED_EXCEPTION_ORGANISM_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("exceptionOrganismCodeType.doesNotContain=" + UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode equals to DEFAULT_ABX_CODE
        defaultIntrinsicResistanceShouldBeFound("abxCode.equals=" + DEFAULT_ABX_CODE);

        // Get all the intrinsicResistanceList where abxCode equals to UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.equals=" + UPDATED_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode not equals to DEFAULT_ABX_CODE
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.notEquals=" + DEFAULT_ABX_CODE);

        // Get all the intrinsicResistanceList where abxCode not equals to UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldBeFound("abxCode.notEquals=" + UPDATED_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode in DEFAULT_ABX_CODE or UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldBeFound("abxCode.in=" + DEFAULT_ABX_CODE + "," + UPDATED_ABX_CODE);

        // Get all the intrinsicResistanceList where abxCode equals to UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.in=" + UPDATED_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode is not null
        defaultIntrinsicResistanceShouldBeFound("abxCode.specified=true");

        // Get all the intrinsicResistanceList where abxCode is null
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode contains DEFAULT_ABX_CODE
        defaultIntrinsicResistanceShouldBeFound("abxCode.contains=" + DEFAULT_ABX_CODE);

        // Get all the intrinsicResistanceList where abxCode contains UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.contains=" + UPDATED_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCode does not contain DEFAULT_ABX_CODE
        defaultIntrinsicResistanceShouldNotBeFound("abxCode.doesNotContain=" + DEFAULT_ABX_CODE);

        // Get all the intrinsicResistanceList where abxCode does not contain UPDATED_ABX_CODE
        defaultIntrinsicResistanceShouldBeFound("abxCode.doesNotContain=" + UPDATED_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType equals to DEFAULT_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.equals=" + DEFAULT_ABX_CODE_TYPE);

        // Get all the intrinsicResistanceList where abxCodeType equals to UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.equals=" + UPDATED_ABX_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType not equals to DEFAULT_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.notEquals=" + DEFAULT_ABX_CODE_TYPE);

        // Get all the intrinsicResistanceList where abxCodeType not equals to UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.notEquals=" + UPDATED_ABX_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType in DEFAULT_ABX_CODE_TYPE or UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.in=" + DEFAULT_ABX_CODE_TYPE + "," + UPDATED_ABX_CODE_TYPE);

        // Get all the intrinsicResistanceList where abxCodeType equals to UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.in=" + UPDATED_ABX_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType is not null
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.specified=true");

        // Get all the intrinsicResistanceList where abxCodeType is null
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType contains DEFAULT_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.contains=" + DEFAULT_ABX_CODE_TYPE);

        // Get all the intrinsicResistanceList where abxCodeType contains UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.contains=" + UPDATED_ABX_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAbxCodeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where abxCodeType does not contain DEFAULT_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldNotBeFound("abxCodeType.doesNotContain=" + DEFAULT_ABX_CODE_TYPE);

        // Get all the intrinsicResistanceList where abxCodeType does not contain UPDATED_ABX_CODE_TYPE
        defaultIntrinsicResistanceShouldBeFound("abxCodeType.doesNotContain=" + UPDATED_ABX_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions equals to DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldBeFound("antibioticExceptions.equals=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the intrinsicResistanceList where antibioticExceptions equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.equals=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions not equals to DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.notEquals=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the intrinsicResistanceList where antibioticExceptions not equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldBeFound("antibioticExceptions.notEquals=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions in DEFAULT_ANTIBIOTIC_EXCEPTIONS or UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldBeFound(
            "antibioticExceptions.in=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS + "," + UPDATED_ANTIBIOTIC_EXCEPTIONS
        );

        // Get all the intrinsicResistanceList where antibioticExceptions equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.in=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions is not null
        defaultIntrinsicResistanceShouldBeFound("antibioticExceptions.specified=true");

        // Get all the intrinsicResistanceList where antibioticExceptions is null
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions contains DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldBeFound("antibioticExceptions.contains=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the intrinsicResistanceList where antibioticExceptions contains UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.contains=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByAntibioticExceptionsNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where antibioticExceptions does not contain DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldNotBeFound("antibioticExceptions.doesNotContain=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the intrinsicResistanceList where antibioticExceptions does not contain UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultIntrinsicResistanceShouldBeFound("antibioticExceptions.doesNotContain=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered equals to DEFAULT_DATE_ENTERED
        defaultIntrinsicResistanceShouldBeFound("dateEntered.equals=" + DEFAULT_DATE_ENTERED);

        // Get all the intrinsicResistanceList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.equals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered not equals to DEFAULT_DATE_ENTERED
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.notEquals=" + DEFAULT_DATE_ENTERED);

        // Get all the intrinsicResistanceList where dateEntered not equals to UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldBeFound("dateEntered.notEquals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered in DEFAULT_DATE_ENTERED or UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldBeFound("dateEntered.in=" + DEFAULT_DATE_ENTERED + "," + UPDATED_DATE_ENTERED);

        // Get all the intrinsicResistanceList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.in=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered is not null
        defaultIntrinsicResistanceShouldBeFound("dateEntered.specified=true");

        // Get all the intrinsicResistanceList where dateEntered is null
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered contains DEFAULT_DATE_ENTERED
        defaultIntrinsicResistanceShouldBeFound("dateEntered.contains=" + DEFAULT_DATE_ENTERED);

        // Get all the intrinsicResistanceList where dateEntered contains UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.contains=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateEnteredNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateEntered does not contain DEFAULT_DATE_ENTERED
        defaultIntrinsicResistanceShouldNotBeFound("dateEntered.doesNotContain=" + DEFAULT_DATE_ENTERED);

        // Get all the intrinsicResistanceList where dateEntered does not contain UPDATED_DATE_ENTERED
        defaultIntrinsicResistanceShouldBeFound("dateEntered.doesNotContain=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified equals to DEFAULT_DATE_MODIFIED
        defaultIntrinsicResistanceShouldBeFound("dateModified.equals=" + DEFAULT_DATE_MODIFIED);

        // Get all the intrinsicResistanceList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.equals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified not equals to DEFAULT_DATE_MODIFIED
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.notEquals=" + DEFAULT_DATE_MODIFIED);

        // Get all the intrinsicResistanceList where dateModified not equals to UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldBeFound("dateModified.notEquals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified in DEFAULT_DATE_MODIFIED or UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldBeFound("dateModified.in=" + DEFAULT_DATE_MODIFIED + "," + UPDATED_DATE_MODIFIED);

        // Get all the intrinsicResistanceList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.in=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified is not null
        defaultIntrinsicResistanceShouldBeFound("dateModified.specified=true");

        // Get all the intrinsicResistanceList where dateModified is null
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified contains DEFAULT_DATE_MODIFIED
        defaultIntrinsicResistanceShouldBeFound("dateModified.contains=" + DEFAULT_DATE_MODIFIED);

        // Get all the intrinsicResistanceList where dateModified contains UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.contains=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByDateModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where dateModified does not contain DEFAULT_DATE_MODIFIED
        defaultIntrinsicResistanceShouldNotBeFound("dateModified.doesNotContain=" + DEFAULT_DATE_MODIFIED);

        // Get all the intrinsicResistanceList where dateModified does not contain UPDATED_DATE_MODIFIED
        defaultIntrinsicResistanceShouldBeFound("dateModified.doesNotContain=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments equals to DEFAULT_COMMENTS
        defaultIntrinsicResistanceShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the intrinsicResistanceList where comments equals to UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments not equals to DEFAULT_COMMENTS
        defaultIntrinsicResistanceShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the intrinsicResistanceList where comments not equals to UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the intrinsicResistanceList where comments equals to UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments is not null
        defaultIntrinsicResistanceShouldBeFound("comments.specified=true");

        // Get all the intrinsicResistanceList where comments is null
        defaultIntrinsicResistanceShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments contains DEFAULT_COMMENTS
        defaultIntrinsicResistanceShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the intrinsicResistanceList where comments contains UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllIntrinsicResistancesByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        // Get all the intrinsicResistanceList where comments does not contain DEFAULT_COMMENTS
        defaultIntrinsicResistanceShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the intrinsicResistanceList where comments does not contain UPDATED_COMMENTS
        defaultIntrinsicResistanceShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIntrinsicResistanceShouldBeFound(String filter) throws Exception {
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intrinsicResistance.getId().intValue())))
            .andExpect(jsonPath("$.[*].guideline").value(hasItem(DEFAULT_GUIDELINE)))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].exceptionOrganismCode").value(hasItem(DEFAULT_EXCEPTION_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].exceptionOrganismCodeType").value(hasItem(DEFAULT_EXCEPTION_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].abxCode").value(hasItem(DEFAULT_ABX_CODE)))
            .andExpect(jsonPath("$.[*].abxCodeType").value(hasItem(DEFAULT_ABX_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].antibioticExceptions").value(hasItem(DEFAULT_ANTIBIOTIC_EXCEPTIONS)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIntrinsicResistanceShouldNotBeFound(String filter) throws Exception {
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIntrinsicResistanceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIntrinsicResistance() throws Exception {
        // Get the intrinsicResistance
        restIntrinsicResistanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIntrinsicResistance() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();

        // Update the intrinsicResistance
        IntrinsicResistance updatedIntrinsicResistance = intrinsicResistanceRepository.findById(intrinsicResistance.getId()).get();
        // Disconnect from session so that the updates on updatedIntrinsicResistance are not directly saved in db
        em.detach(updatedIntrinsicResistance);
        updatedIntrinsicResistance
            .guideline(UPDATED_GUIDELINE)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .exceptionOrganismCode(UPDATED_EXCEPTION_ORGANISM_CODE)
            .exceptionOrganismCodeType(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE)
            .abxCode(UPDATED_ABX_CODE)
            .abxCodeType(UPDATED_ABX_CODE_TYPE)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(updatedIntrinsicResistance);

        restIntrinsicResistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intrinsicResistanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
        IntrinsicResistance testIntrinsicResistance = intrinsicResistanceList.get(intrinsicResistanceList.size() - 1);
        assertThat(testIntrinsicResistance.getGuideline()).isEqualTo(UPDATED_GUIDELINE);
        assertThat(testIntrinsicResistance.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testIntrinsicResistance.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCode()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCodeType()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAbxCode()).isEqualTo(UPDATED_ABX_CODE);
        assertThat(testIntrinsicResistance.getAbxCodeType()).isEqualTo(UPDATED_ABX_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testIntrinsicResistance.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testIntrinsicResistance.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testIntrinsicResistance.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intrinsicResistanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntrinsicResistanceWithPatch() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();

        // Update the intrinsicResistance using partial update
        IntrinsicResistance partialUpdatedIntrinsicResistance = new IntrinsicResistance();
        partialUpdatedIntrinsicResistance.setId(intrinsicResistance.getId());

        partialUpdatedIntrinsicResistance
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .exceptionOrganismCode(UPDATED_EXCEPTION_ORGANISM_CODE)
            .exceptionOrganismCodeType(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE)
            .abxCode(UPDATED_ABX_CODE)
            .abxCodeType(UPDATED_ABX_CODE_TYPE)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);

        restIntrinsicResistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrinsicResistance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntrinsicResistance))
            )
            .andExpect(status().isOk());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
        IntrinsicResistance testIntrinsicResistance = intrinsicResistanceList.get(intrinsicResistanceList.size() - 1);
        assertThat(testIntrinsicResistance.getGuideline()).isEqualTo(DEFAULT_GUIDELINE);
        assertThat(testIntrinsicResistance.getReferenceTable()).isEqualTo(DEFAULT_REFERENCE_TABLE);
        assertThat(testIntrinsicResistance.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCode()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCodeType()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAbxCode()).isEqualTo(UPDATED_ABX_CODE);
        assertThat(testIntrinsicResistance.getAbxCodeType()).isEqualTo(UPDATED_ABX_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testIntrinsicResistance.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testIntrinsicResistance.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testIntrinsicResistance.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateIntrinsicResistanceWithPatch() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();

        // Update the intrinsicResistance using partial update
        IntrinsicResistance partialUpdatedIntrinsicResistance = new IntrinsicResistance();
        partialUpdatedIntrinsicResistance.setId(intrinsicResistance.getId());

        partialUpdatedIntrinsicResistance
            .guideline(UPDATED_GUIDELINE)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .exceptionOrganismCode(UPDATED_EXCEPTION_ORGANISM_CODE)
            .exceptionOrganismCodeType(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE)
            .abxCode(UPDATED_ABX_CODE)
            .abxCodeType(UPDATED_ABX_CODE_TYPE)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);

        restIntrinsicResistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrinsicResistance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntrinsicResistance))
            )
            .andExpect(status().isOk());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
        IntrinsicResistance testIntrinsicResistance = intrinsicResistanceList.get(intrinsicResistanceList.size() - 1);
        assertThat(testIntrinsicResistance.getGuideline()).isEqualTo(UPDATED_GUIDELINE);
        assertThat(testIntrinsicResistance.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testIntrinsicResistance.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCode()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE);
        assertThat(testIntrinsicResistance.getExceptionOrganismCodeType()).isEqualTo(UPDATED_EXCEPTION_ORGANISM_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAbxCode()).isEqualTo(UPDATED_ABX_CODE);
        assertThat(testIntrinsicResistance.getAbxCodeType()).isEqualTo(UPDATED_ABX_CODE_TYPE);
        assertThat(testIntrinsicResistance.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testIntrinsicResistance.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testIntrinsicResistance.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testIntrinsicResistance.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intrinsicResistanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntrinsicResistance() throws Exception {
        int databaseSizeBeforeUpdate = intrinsicResistanceRepository.findAll().size();
        intrinsicResistance.setId(count.incrementAndGet());

        // Create the IntrinsicResistance
        IntrinsicResistanceDTO intrinsicResistanceDTO = intrinsicResistanceMapper.toDto(intrinsicResistance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrinsicResistanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(intrinsicResistanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrinsicResistance in the database
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntrinsicResistance() throws Exception {
        // Initialize the database
        intrinsicResistanceRepository.saveAndFlush(intrinsicResistance);

        int databaseSizeBeforeDelete = intrinsicResistanceRepository.findAll().size();

        // Delete the intrinsicResistance
        restIntrinsicResistanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, intrinsicResistance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntrinsicResistance> intrinsicResistanceList = intrinsicResistanceRepository.findAll();
        assertThat(intrinsicResistanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
