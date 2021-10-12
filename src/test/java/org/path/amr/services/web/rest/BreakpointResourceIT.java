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
import org.path.amr.services.domain.Breakpoint;
import org.path.amr.services.repository.BreakpointRepository;
import org.path.amr.services.service.criteria.BreakpointCriteria;
import org.path.amr.services.service.dto.BreakpointDTO;
import org.path.amr.services.service.mapper.BreakpointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BreakpointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BreakpointResourceIT {

    private static final String DEFAULT_GUIDELINES = "AAAAAAAAAA";
    private static final String UPDATED_GUIDELINES = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 2020;
    private static final Integer UPDATED_YEAR = 2020;

    private static final String DEFAULT_TEST_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_TEST_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_POTENCY = "AAAAAAAAAA";
    private static final String UPDATED_POTENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BREAKPOINT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BREAKPOINT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_OF_INFECTION = "AAAAAAAAAA";
    private static final String UPDATED_SITE_OF_INFECTION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_SEQUENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_SEQUENCE = "BBBBBBBBBB";

    private static final String DEFAULT_WHONET_ABX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WHONET_ABX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WHONET_TEST = "AAAAAAAAAA";
    private static final String UPDATED_WHONET_TEST = "BBBBBBBBBB";

    private static final String DEFAULT_R = "AAAAAAAAAA";
    private static final String UPDATED_R = "BBBBBBBBBB";

    private static final String DEFAULT_I = "AAAAAAAAAA";
    private static final String UPDATED_I = "BBBBBBBBBB";

    private static final String DEFAULT_SDD = "AAAAAAAAAA";
    private static final String UPDATED_SDD = "BBBBBBBBBB";

    private static final String DEFAULT_S = "AAAAAAAAAA";
    private static final String UPDATED_S = "BBBBBBBBBB";

    private static final String DEFAULT_ECV_ECOFF = "AAAAAAAAAA";
    private static final String UPDATED_ECV_ECOFF = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ENTERED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ENTERED = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/breakpoints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BreakpointRepository breakpointRepository;

    @Autowired
    private BreakpointMapper breakpointMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBreakpointMockMvc;

    private Breakpoint breakpoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Breakpoint createEntity(EntityManager em) {
        Breakpoint breakpoint = new Breakpoint()
            .guidelines(DEFAULT_GUIDELINES)
            .year(DEFAULT_YEAR)
            .testMethod(DEFAULT_TEST_METHOD)
            .potency(DEFAULT_POTENCY)
            .organismCode(DEFAULT_ORGANISM_CODE)
            .organismCodeType(DEFAULT_ORGANISM_CODE_TYPE)
            .breakpointType(DEFAULT_BREAKPOINT_TYPE)
            .host(DEFAULT_HOST)
            .siteOfInfection(DEFAULT_SITE_OF_INFECTION)
            .referenceTable(DEFAULT_REFERENCE_TABLE)
            .referenceSequence(DEFAULT_REFERENCE_SEQUENCE)
            .whonetAbxCode(DEFAULT_WHONET_ABX_CODE)
            .whonetTest(DEFAULT_WHONET_TEST)
            .r(DEFAULT_R)
            .i(DEFAULT_I)
            .sdd(DEFAULT_SDD)
            .s(DEFAULT_S)
            .ecvEcoff(DEFAULT_ECV_ECOFF)
            .dateEntered(DEFAULT_DATE_ENTERED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .comments(DEFAULT_COMMENTS);
        return breakpoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Breakpoint createUpdatedEntity(EntityManager em) {
        Breakpoint breakpoint = new Breakpoint()
            .guidelines(UPDATED_GUIDELINES)
            .year(UPDATED_YEAR)
            .testMethod(UPDATED_TEST_METHOD)
            .potency(UPDATED_POTENCY)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .breakpointType(UPDATED_BREAKPOINT_TYPE)
            .host(UPDATED_HOST)
            .siteOfInfection(UPDATED_SITE_OF_INFECTION)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .referenceSequence(UPDATED_REFERENCE_SEQUENCE)
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whonetTest(UPDATED_WHONET_TEST)
            .r(UPDATED_R)
            .i(UPDATED_I)
            .sdd(UPDATED_SDD)
            .s(UPDATED_S)
            .ecvEcoff(UPDATED_ECV_ECOFF)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        return breakpoint;
    }

    @BeforeEach
    public void initTest() {
        breakpoint = createEntity(em);
    }

    @Test
    @Transactional
    void createBreakpoint() throws Exception {
        int databaseSizeBeforeCreate = breakpointRepository.findAll().size();
        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);
        restBreakpointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breakpointDTO)))
            .andExpect(status().isCreated());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeCreate + 1);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getGuidelines()).isEqualTo(DEFAULT_GUIDELINES);
        assertThat(testBreakpoint.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBreakpoint.getTestMethod()).isEqualTo(DEFAULT_TEST_METHOD);
        assertThat(testBreakpoint.getPotency()).isEqualTo(DEFAULT_POTENCY);
        assertThat(testBreakpoint.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testBreakpoint.getOrganismCodeType()).isEqualTo(DEFAULT_ORGANISM_CODE_TYPE);
        assertThat(testBreakpoint.getBreakpointType()).isEqualTo(DEFAULT_BREAKPOINT_TYPE);
        assertThat(testBreakpoint.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testBreakpoint.getSiteOfInfection()).isEqualTo(DEFAULT_SITE_OF_INFECTION);
        assertThat(testBreakpoint.getReferenceTable()).isEqualTo(DEFAULT_REFERENCE_TABLE);
        assertThat(testBreakpoint.getReferenceSequence()).isEqualTo(DEFAULT_REFERENCE_SEQUENCE);
        assertThat(testBreakpoint.getWhonetAbxCode()).isEqualTo(DEFAULT_WHONET_ABX_CODE);
        assertThat(testBreakpoint.getWhonetTest()).isEqualTo(DEFAULT_WHONET_TEST);
        assertThat(testBreakpoint.getR()).isEqualTo(DEFAULT_R);
        assertThat(testBreakpoint.getI()).isEqualTo(DEFAULT_I);
        assertThat(testBreakpoint.getSdd()).isEqualTo(DEFAULT_SDD);
        assertThat(testBreakpoint.getS()).isEqualTo(DEFAULT_S);
        assertThat(testBreakpoint.getEcvEcoff()).isEqualTo(DEFAULT_ECV_ECOFF);
        assertThat(testBreakpoint.getDateEntered()).isEqualTo(DEFAULT_DATE_ENTERED);
        assertThat(testBreakpoint.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testBreakpoint.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createBreakpointWithExistingId() throws Exception {
        // Create the Breakpoint with an existing ID
        breakpoint.setId(1L);
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        int databaseSizeBeforeCreate = breakpointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreakpointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breakpointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBreakpoints() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breakpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].guidelines").value(hasItem(DEFAULT_GUIDELINES)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].testMethod").value(hasItem(DEFAULT_TEST_METHOD)))
            .andExpect(jsonPath("$.[*].potency").value(hasItem(DEFAULT_POTENCY)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].breakpointType").value(hasItem(DEFAULT_BREAKPOINT_TYPE)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].siteOfInfection").value(hasItem(DEFAULT_SITE_OF_INFECTION)))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE)))
            .andExpect(jsonPath("$.[*].referenceSequence").value(hasItem(DEFAULT_REFERENCE_SEQUENCE)))
            .andExpect(jsonPath("$.[*].whonetAbxCode").value(hasItem(DEFAULT_WHONET_ABX_CODE)))
            .andExpect(jsonPath("$.[*].whonetTest").value(hasItem(DEFAULT_WHONET_TEST)))
            .andExpect(jsonPath("$.[*].r").value(hasItem(DEFAULT_R)))
            .andExpect(jsonPath("$.[*].i").value(hasItem(DEFAULT_I)))
            .andExpect(jsonPath("$.[*].sdd").value(hasItem(DEFAULT_SDD)))
            .andExpect(jsonPath("$.[*].s").value(hasItem(DEFAULT_S)))
            .andExpect(jsonPath("$.[*].ecvEcoff").value(hasItem(DEFAULT_ECV_ECOFF)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get the breakpoint
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL_ID, breakpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(breakpoint.getId().intValue()))
            .andExpect(jsonPath("$.guidelines").value(DEFAULT_GUIDELINES))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.testMethod").value(DEFAULT_TEST_METHOD))
            .andExpect(jsonPath("$.potency").value(DEFAULT_POTENCY))
            .andExpect(jsonPath("$.organismCode").value(DEFAULT_ORGANISM_CODE))
            .andExpect(jsonPath("$.organismCodeType").value(DEFAULT_ORGANISM_CODE_TYPE))
            .andExpect(jsonPath("$.breakpointType").value(DEFAULT_BREAKPOINT_TYPE))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.siteOfInfection").value(DEFAULT_SITE_OF_INFECTION))
            .andExpect(jsonPath("$.referenceTable").value(DEFAULT_REFERENCE_TABLE))
            .andExpect(jsonPath("$.referenceSequence").value(DEFAULT_REFERENCE_SEQUENCE))
            .andExpect(jsonPath("$.whonetAbxCode").value(DEFAULT_WHONET_ABX_CODE))
            .andExpect(jsonPath("$.whonetTest").value(DEFAULT_WHONET_TEST))
            .andExpect(jsonPath("$.r").value(DEFAULT_R))
            .andExpect(jsonPath("$.i").value(DEFAULT_I))
            .andExpect(jsonPath("$.sdd").value(DEFAULT_SDD))
            .andExpect(jsonPath("$.s").value(DEFAULT_S))
            .andExpect(jsonPath("$.ecvEcoff").value(DEFAULT_ECV_ECOFF))
            .andExpect(jsonPath("$.dateEntered").value(DEFAULT_DATE_ENTERED))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getBreakpointsByIdFiltering() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        Long id = breakpoint.getId();

        defaultBreakpointShouldBeFound("id.equals=" + id);
        defaultBreakpointShouldNotBeFound("id.notEquals=" + id);

        defaultBreakpointShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBreakpointShouldNotBeFound("id.greaterThan=" + id);

        defaultBreakpointShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBreakpointShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines equals to DEFAULT_GUIDELINES
        defaultBreakpointShouldBeFound("guidelines.equals=" + DEFAULT_GUIDELINES);

        // Get all the breakpointList where guidelines equals to UPDATED_GUIDELINES
        defaultBreakpointShouldNotBeFound("guidelines.equals=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines not equals to DEFAULT_GUIDELINES
        defaultBreakpointShouldNotBeFound("guidelines.notEquals=" + DEFAULT_GUIDELINES);

        // Get all the breakpointList where guidelines not equals to UPDATED_GUIDELINES
        defaultBreakpointShouldBeFound("guidelines.notEquals=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines in DEFAULT_GUIDELINES or UPDATED_GUIDELINES
        defaultBreakpointShouldBeFound("guidelines.in=" + DEFAULT_GUIDELINES + "," + UPDATED_GUIDELINES);

        // Get all the breakpointList where guidelines equals to UPDATED_GUIDELINES
        defaultBreakpointShouldNotBeFound("guidelines.in=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines is not null
        defaultBreakpointShouldBeFound("guidelines.specified=true");

        // Get all the breakpointList where guidelines is null
        defaultBreakpointShouldNotBeFound("guidelines.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines contains DEFAULT_GUIDELINES
        defaultBreakpointShouldBeFound("guidelines.contains=" + DEFAULT_GUIDELINES);

        // Get all the breakpointList where guidelines contains UPDATED_GUIDELINES
        defaultBreakpointShouldNotBeFound("guidelines.contains=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllBreakpointsByGuidelinesNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where guidelines does not contain DEFAULT_GUIDELINES
        defaultBreakpointShouldNotBeFound("guidelines.doesNotContain=" + DEFAULT_GUIDELINES);

        // Get all the breakpointList where guidelines does not contain UPDATED_GUIDELINES
        defaultBreakpointShouldBeFound("guidelines.doesNotContain=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year equals to DEFAULT_YEAR
        defaultBreakpointShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the breakpointList where year equals to UPDATED_YEAR
        defaultBreakpointShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year not equals to DEFAULT_YEAR
        defaultBreakpointShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the breakpointList where year not equals to UPDATED_YEAR
        defaultBreakpointShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultBreakpointShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the breakpointList where year equals to UPDATED_YEAR
        defaultBreakpointShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year is not null
        defaultBreakpointShouldBeFound("year.specified=true");

        // Get all the breakpointList where year is null
        defaultBreakpointShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year contains DEFAULT_YEAR
        defaultBreakpointShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the breakpointList where year contains UPDATED_YEAR
        defaultBreakpointShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBreakpointsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where year does not contain DEFAULT_YEAR
        defaultBreakpointShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the breakpointList where year does not contain UPDATED_YEAR
        defaultBreakpointShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod equals to DEFAULT_TEST_METHOD
        defaultBreakpointShouldBeFound("testMethod.equals=" + DEFAULT_TEST_METHOD);

        // Get all the breakpointList where testMethod equals to UPDATED_TEST_METHOD
        defaultBreakpointShouldNotBeFound("testMethod.equals=" + UPDATED_TEST_METHOD);
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod not equals to DEFAULT_TEST_METHOD
        defaultBreakpointShouldNotBeFound("testMethod.notEquals=" + DEFAULT_TEST_METHOD);

        // Get all the breakpointList where testMethod not equals to UPDATED_TEST_METHOD
        defaultBreakpointShouldBeFound("testMethod.notEquals=" + UPDATED_TEST_METHOD);
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod in DEFAULT_TEST_METHOD or UPDATED_TEST_METHOD
        defaultBreakpointShouldBeFound("testMethod.in=" + DEFAULT_TEST_METHOD + "," + UPDATED_TEST_METHOD);

        // Get all the breakpointList where testMethod equals to UPDATED_TEST_METHOD
        defaultBreakpointShouldNotBeFound("testMethod.in=" + UPDATED_TEST_METHOD);
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod is not null
        defaultBreakpointShouldBeFound("testMethod.specified=true");

        // Get all the breakpointList where testMethod is null
        defaultBreakpointShouldNotBeFound("testMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod contains DEFAULT_TEST_METHOD
        defaultBreakpointShouldBeFound("testMethod.contains=" + DEFAULT_TEST_METHOD);

        // Get all the breakpointList where testMethod contains UPDATED_TEST_METHOD
        defaultBreakpointShouldNotBeFound("testMethod.contains=" + UPDATED_TEST_METHOD);
    }

    @Test
    @Transactional
    void getAllBreakpointsByTestMethodNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where testMethod does not contain DEFAULT_TEST_METHOD
        defaultBreakpointShouldNotBeFound("testMethod.doesNotContain=" + DEFAULT_TEST_METHOD);

        // Get all the breakpointList where testMethod does not contain UPDATED_TEST_METHOD
        defaultBreakpointShouldBeFound("testMethod.doesNotContain=" + UPDATED_TEST_METHOD);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency equals to DEFAULT_POTENCY
        defaultBreakpointShouldBeFound("potency.equals=" + DEFAULT_POTENCY);

        // Get all the breakpointList where potency equals to UPDATED_POTENCY
        defaultBreakpointShouldNotBeFound("potency.equals=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency not equals to DEFAULT_POTENCY
        defaultBreakpointShouldNotBeFound("potency.notEquals=" + DEFAULT_POTENCY);

        // Get all the breakpointList where potency not equals to UPDATED_POTENCY
        defaultBreakpointShouldBeFound("potency.notEquals=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency in DEFAULT_POTENCY or UPDATED_POTENCY
        defaultBreakpointShouldBeFound("potency.in=" + DEFAULT_POTENCY + "," + UPDATED_POTENCY);

        // Get all the breakpointList where potency equals to UPDATED_POTENCY
        defaultBreakpointShouldNotBeFound("potency.in=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency is not null
        defaultBreakpointShouldBeFound("potency.specified=true");

        // Get all the breakpointList where potency is null
        defaultBreakpointShouldNotBeFound("potency.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency contains DEFAULT_POTENCY
        defaultBreakpointShouldBeFound("potency.contains=" + DEFAULT_POTENCY);

        // Get all the breakpointList where potency contains UPDATED_POTENCY
        defaultBreakpointShouldNotBeFound("potency.contains=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPotencyNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where potency does not contain DEFAULT_POTENCY
        defaultBreakpointShouldNotBeFound("potency.doesNotContain=" + DEFAULT_POTENCY);

        // Get all the breakpointList where potency does not contain UPDATED_POTENCY
        defaultBreakpointShouldBeFound("potency.doesNotContain=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode equals to DEFAULT_ORGANISM_CODE
        defaultBreakpointShouldBeFound("organismCode.equals=" + DEFAULT_ORGANISM_CODE);

        // Get all the breakpointList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultBreakpointShouldNotBeFound("organismCode.equals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode not equals to DEFAULT_ORGANISM_CODE
        defaultBreakpointShouldNotBeFound("organismCode.notEquals=" + DEFAULT_ORGANISM_CODE);

        // Get all the breakpointList where organismCode not equals to UPDATED_ORGANISM_CODE
        defaultBreakpointShouldBeFound("organismCode.notEquals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode in DEFAULT_ORGANISM_CODE or UPDATED_ORGANISM_CODE
        defaultBreakpointShouldBeFound("organismCode.in=" + DEFAULT_ORGANISM_CODE + "," + UPDATED_ORGANISM_CODE);

        // Get all the breakpointList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultBreakpointShouldNotBeFound("organismCode.in=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode is not null
        defaultBreakpointShouldBeFound("organismCode.specified=true");

        // Get all the breakpointList where organismCode is null
        defaultBreakpointShouldNotBeFound("organismCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode contains DEFAULT_ORGANISM_CODE
        defaultBreakpointShouldBeFound("organismCode.contains=" + DEFAULT_ORGANISM_CODE);

        // Get all the breakpointList where organismCode contains UPDATED_ORGANISM_CODE
        defaultBreakpointShouldNotBeFound("organismCode.contains=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCode does not contain DEFAULT_ORGANISM_CODE
        defaultBreakpointShouldNotBeFound("organismCode.doesNotContain=" + DEFAULT_ORGANISM_CODE);

        // Get all the breakpointList where organismCode does not contain UPDATED_ORGANISM_CODE
        defaultBreakpointShouldBeFound("organismCode.doesNotContain=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultBreakpointShouldBeFound("organismCodeType.equals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the breakpointList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldNotBeFound("organismCodeType.equals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType not equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultBreakpointShouldNotBeFound("organismCodeType.notEquals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the breakpointList where organismCodeType not equals to UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldBeFound("organismCodeType.notEquals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType in DEFAULT_ORGANISM_CODE_TYPE or UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldBeFound("organismCodeType.in=" + DEFAULT_ORGANISM_CODE_TYPE + "," + UPDATED_ORGANISM_CODE_TYPE);

        // Get all the breakpointList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldNotBeFound("organismCodeType.in=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType is not null
        defaultBreakpointShouldBeFound("organismCodeType.specified=true");

        // Get all the breakpointList where organismCodeType is null
        defaultBreakpointShouldNotBeFound("organismCodeType.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType contains DEFAULT_ORGANISM_CODE_TYPE
        defaultBreakpointShouldBeFound("organismCodeType.contains=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the breakpointList where organismCodeType contains UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldNotBeFound("organismCodeType.contains=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismCodeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismCodeType does not contain DEFAULT_ORGANISM_CODE_TYPE
        defaultBreakpointShouldNotBeFound("organismCodeType.doesNotContain=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the breakpointList where organismCodeType does not contain UPDATED_ORGANISM_CODE_TYPE
        defaultBreakpointShouldBeFound("organismCodeType.doesNotContain=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType equals to DEFAULT_BREAKPOINT_TYPE
        defaultBreakpointShouldBeFound("breakpointType.equals=" + DEFAULT_BREAKPOINT_TYPE);

        // Get all the breakpointList where breakpointType equals to UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldNotBeFound("breakpointType.equals=" + UPDATED_BREAKPOINT_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType not equals to DEFAULT_BREAKPOINT_TYPE
        defaultBreakpointShouldNotBeFound("breakpointType.notEquals=" + DEFAULT_BREAKPOINT_TYPE);

        // Get all the breakpointList where breakpointType not equals to UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldBeFound("breakpointType.notEquals=" + UPDATED_BREAKPOINT_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType in DEFAULT_BREAKPOINT_TYPE or UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldBeFound("breakpointType.in=" + DEFAULT_BREAKPOINT_TYPE + "," + UPDATED_BREAKPOINT_TYPE);

        // Get all the breakpointList where breakpointType equals to UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldNotBeFound("breakpointType.in=" + UPDATED_BREAKPOINT_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType is not null
        defaultBreakpointShouldBeFound("breakpointType.specified=true");

        // Get all the breakpointList where breakpointType is null
        defaultBreakpointShouldNotBeFound("breakpointType.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType contains DEFAULT_BREAKPOINT_TYPE
        defaultBreakpointShouldBeFound("breakpointType.contains=" + DEFAULT_BREAKPOINT_TYPE);

        // Get all the breakpointList where breakpointType contains UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldNotBeFound("breakpointType.contains=" + UPDATED_BREAKPOINT_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByBreakpointTypeNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where breakpointType does not contain DEFAULT_BREAKPOINT_TYPE
        defaultBreakpointShouldNotBeFound("breakpointType.doesNotContain=" + DEFAULT_BREAKPOINT_TYPE);

        // Get all the breakpointList where breakpointType does not contain UPDATED_BREAKPOINT_TYPE
        defaultBreakpointShouldBeFound("breakpointType.doesNotContain=" + UPDATED_BREAKPOINT_TYPE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host equals to DEFAULT_HOST
        defaultBreakpointShouldBeFound("host.equals=" + DEFAULT_HOST);

        // Get all the breakpointList where host equals to UPDATED_HOST
        defaultBreakpointShouldNotBeFound("host.equals=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host not equals to DEFAULT_HOST
        defaultBreakpointShouldNotBeFound("host.notEquals=" + DEFAULT_HOST);

        // Get all the breakpointList where host not equals to UPDATED_HOST
        defaultBreakpointShouldBeFound("host.notEquals=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host in DEFAULT_HOST or UPDATED_HOST
        defaultBreakpointShouldBeFound("host.in=" + DEFAULT_HOST + "," + UPDATED_HOST);

        // Get all the breakpointList where host equals to UPDATED_HOST
        defaultBreakpointShouldNotBeFound("host.in=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host is not null
        defaultBreakpointShouldBeFound("host.specified=true");

        // Get all the breakpointList where host is null
        defaultBreakpointShouldNotBeFound("host.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host contains DEFAULT_HOST
        defaultBreakpointShouldBeFound("host.contains=" + DEFAULT_HOST);

        // Get all the breakpointList where host contains UPDATED_HOST
        defaultBreakpointShouldNotBeFound("host.contains=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByHostNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where host does not contain DEFAULT_HOST
        defaultBreakpointShouldNotBeFound("host.doesNotContain=" + DEFAULT_HOST);

        // Get all the breakpointList where host does not contain UPDATED_HOST
        defaultBreakpointShouldBeFound("host.doesNotContain=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection equals to DEFAULT_SITE_OF_INFECTION
        defaultBreakpointShouldBeFound("siteOfInfection.equals=" + DEFAULT_SITE_OF_INFECTION);

        // Get all the breakpointList where siteOfInfection equals to UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldNotBeFound("siteOfInfection.equals=" + UPDATED_SITE_OF_INFECTION);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection not equals to DEFAULT_SITE_OF_INFECTION
        defaultBreakpointShouldNotBeFound("siteOfInfection.notEquals=" + DEFAULT_SITE_OF_INFECTION);

        // Get all the breakpointList where siteOfInfection not equals to UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldBeFound("siteOfInfection.notEquals=" + UPDATED_SITE_OF_INFECTION);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection in DEFAULT_SITE_OF_INFECTION or UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldBeFound("siteOfInfection.in=" + DEFAULT_SITE_OF_INFECTION + "," + UPDATED_SITE_OF_INFECTION);

        // Get all the breakpointList where siteOfInfection equals to UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldNotBeFound("siteOfInfection.in=" + UPDATED_SITE_OF_INFECTION);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection is not null
        defaultBreakpointShouldBeFound("siteOfInfection.specified=true");

        // Get all the breakpointList where siteOfInfection is null
        defaultBreakpointShouldNotBeFound("siteOfInfection.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection contains DEFAULT_SITE_OF_INFECTION
        defaultBreakpointShouldBeFound("siteOfInfection.contains=" + DEFAULT_SITE_OF_INFECTION);

        // Get all the breakpointList where siteOfInfection contains UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldNotBeFound("siteOfInfection.contains=" + UPDATED_SITE_OF_INFECTION);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySiteOfInfectionNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where siteOfInfection does not contain DEFAULT_SITE_OF_INFECTION
        defaultBreakpointShouldNotBeFound("siteOfInfection.doesNotContain=" + DEFAULT_SITE_OF_INFECTION);

        // Get all the breakpointList where siteOfInfection does not contain UPDATED_SITE_OF_INFECTION
        defaultBreakpointShouldBeFound("siteOfInfection.doesNotContain=" + UPDATED_SITE_OF_INFECTION);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable equals to DEFAULT_REFERENCE_TABLE
        defaultBreakpointShouldBeFound("referenceTable.equals=" + DEFAULT_REFERENCE_TABLE);

        // Get all the breakpointList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldNotBeFound("referenceTable.equals=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable not equals to DEFAULT_REFERENCE_TABLE
        defaultBreakpointShouldNotBeFound("referenceTable.notEquals=" + DEFAULT_REFERENCE_TABLE);

        // Get all the breakpointList where referenceTable not equals to UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldBeFound("referenceTable.notEquals=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable in DEFAULT_REFERENCE_TABLE or UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldBeFound("referenceTable.in=" + DEFAULT_REFERENCE_TABLE + "," + UPDATED_REFERENCE_TABLE);

        // Get all the breakpointList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldNotBeFound("referenceTable.in=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable is not null
        defaultBreakpointShouldBeFound("referenceTable.specified=true");

        // Get all the breakpointList where referenceTable is null
        defaultBreakpointShouldNotBeFound("referenceTable.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable contains DEFAULT_REFERENCE_TABLE
        defaultBreakpointShouldBeFound("referenceTable.contains=" + DEFAULT_REFERENCE_TABLE);

        // Get all the breakpointList where referenceTable contains UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldNotBeFound("referenceTable.contains=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceTableNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceTable does not contain DEFAULT_REFERENCE_TABLE
        defaultBreakpointShouldNotBeFound("referenceTable.doesNotContain=" + DEFAULT_REFERENCE_TABLE);

        // Get all the breakpointList where referenceTable does not contain UPDATED_REFERENCE_TABLE
        defaultBreakpointShouldBeFound("referenceTable.doesNotContain=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence equals to DEFAULT_REFERENCE_SEQUENCE
        defaultBreakpointShouldBeFound("referenceSequence.equals=" + DEFAULT_REFERENCE_SEQUENCE);

        // Get all the breakpointList where referenceSequence equals to UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldNotBeFound("referenceSequence.equals=" + UPDATED_REFERENCE_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence not equals to DEFAULT_REFERENCE_SEQUENCE
        defaultBreakpointShouldNotBeFound("referenceSequence.notEquals=" + DEFAULT_REFERENCE_SEQUENCE);

        // Get all the breakpointList where referenceSequence not equals to UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldBeFound("referenceSequence.notEquals=" + UPDATED_REFERENCE_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence in DEFAULT_REFERENCE_SEQUENCE or UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldBeFound("referenceSequence.in=" + DEFAULT_REFERENCE_SEQUENCE + "," + UPDATED_REFERENCE_SEQUENCE);

        // Get all the breakpointList where referenceSequence equals to UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldNotBeFound("referenceSequence.in=" + UPDATED_REFERENCE_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence is not null
        defaultBreakpointShouldBeFound("referenceSequence.specified=true");

        // Get all the breakpointList where referenceSequence is null
        defaultBreakpointShouldNotBeFound("referenceSequence.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence contains DEFAULT_REFERENCE_SEQUENCE
        defaultBreakpointShouldBeFound("referenceSequence.contains=" + DEFAULT_REFERENCE_SEQUENCE);

        // Get all the breakpointList where referenceSequence contains UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldNotBeFound("referenceSequence.contains=" + UPDATED_REFERENCE_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByReferenceSequenceNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where referenceSequence does not contain DEFAULT_REFERENCE_SEQUENCE
        defaultBreakpointShouldNotBeFound("referenceSequence.doesNotContain=" + DEFAULT_REFERENCE_SEQUENCE);

        // Get all the breakpointList where referenceSequence does not contain UPDATED_REFERENCE_SEQUENCE
        defaultBreakpointShouldBeFound("referenceSequence.doesNotContain=" + UPDATED_REFERENCE_SEQUENCE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode equals to DEFAULT_WHONET_ABX_CODE
        defaultBreakpointShouldBeFound("whonetAbxCode.equals=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the breakpointList where whonetAbxCode equals to UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldNotBeFound("whonetAbxCode.equals=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode not equals to DEFAULT_WHONET_ABX_CODE
        defaultBreakpointShouldNotBeFound("whonetAbxCode.notEquals=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the breakpointList where whonetAbxCode not equals to UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldBeFound("whonetAbxCode.notEquals=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode in DEFAULT_WHONET_ABX_CODE or UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldBeFound("whonetAbxCode.in=" + DEFAULT_WHONET_ABX_CODE + "," + UPDATED_WHONET_ABX_CODE);

        // Get all the breakpointList where whonetAbxCode equals to UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldNotBeFound("whonetAbxCode.in=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode is not null
        defaultBreakpointShouldBeFound("whonetAbxCode.specified=true");

        // Get all the breakpointList where whonetAbxCode is null
        defaultBreakpointShouldNotBeFound("whonetAbxCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode contains DEFAULT_WHONET_ABX_CODE
        defaultBreakpointShouldBeFound("whonetAbxCode.contains=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the breakpointList where whonetAbxCode contains UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldNotBeFound("whonetAbxCode.contains=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetAbxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetAbxCode does not contain DEFAULT_WHONET_ABX_CODE
        defaultBreakpointShouldNotBeFound("whonetAbxCode.doesNotContain=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the breakpointList where whonetAbxCode does not contain UPDATED_WHONET_ABX_CODE
        defaultBreakpointShouldBeFound("whonetAbxCode.doesNotContain=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest equals to DEFAULT_WHONET_TEST
        defaultBreakpointShouldBeFound("whonetTest.equals=" + DEFAULT_WHONET_TEST);

        // Get all the breakpointList where whonetTest equals to UPDATED_WHONET_TEST
        defaultBreakpointShouldNotBeFound("whonetTest.equals=" + UPDATED_WHONET_TEST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest not equals to DEFAULT_WHONET_TEST
        defaultBreakpointShouldNotBeFound("whonetTest.notEquals=" + DEFAULT_WHONET_TEST);

        // Get all the breakpointList where whonetTest not equals to UPDATED_WHONET_TEST
        defaultBreakpointShouldBeFound("whonetTest.notEquals=" + UPDATED_WHONET_TEST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest in DEFAULT_WHONET_TEST or UPDATED_WHONET_TEST
        defaultBreakpointShouldBeFound("whonetTest.in=" + DEFAULT_WHONET_TEST + "," + UPDATED_WHONET_TEST);

        // Get all the breakpointList where whonetTest equals to UPDATED_WHONET_TEST
        defaultBreakpointShouldNotBeFound("whonetTest.in=" + UPDATED_WHONET_TEST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest is not null
        defaultBreakpointShouldBeFound("whonetTest.specified=true");

        // Get all the breakpointList where whonetTest is null
        defaultBreakpointShouldNotBeFound("whonetTest.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest contains DEFAULT_WHONET_TEST
        defaultBreakpointShouldBeFound("whonetTest.contains=" + DEFAULT_WHONET_TEST);

        // Get all the breakpointList where whonetTest contains UPDATED_WHONET_TEST
        defaultBreakpointShouldNotBeFound("whonetTest.contains=" + UPDATED_WHONET_TEST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByWhonetTestNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where whonetTest does not contain DEFAULT_WHONET_TEST
        defaultBreakpointShouldNotBeFound("whonetTest.doesNotContain=" + DEFAULT_WHONET_TEST);

        // Get all the breakpointList where whonetTest does not contain UPDATED_WHONET_TEST
        defaultBreakpointShouldBeFound("whonetTest.doesNotContain=" + UPDATED_WHONET_TEST);
    }

    @Test
    @Transactional
    void getAllBreakpointsByRIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r equals to DEFAULT_R
        defaultBreakpointShouldBeFound("r.equals=" + DEFAULT_R);

        // Get all the breakpointList where r equals to UPDATED_R
        defaultBreakpointShouldNotBeFound("r.equals=" + UPDATED_R);
    }

    @Test
    @Transactional
    void getAllBreakpointsByRIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r not equals to DEFAULT_R
        defaultBreakpointShouldNotBeFound("r.notEquals=" + DEFAULT_R);

        // Get all the breakpointList where r not equals to UPDATED_R
        defaultBreakpointShouldBeFound("r.notEquals=" + UPDATED_R);
    }

    @Test
    @Transactional
    void getAllBreakpointsByRIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r in DEFAULT_R or UPDATED_R
        defaultBreakpointShouldBeFound("r.in=" + DEFAULT_R + "," + UPDATED_R);

        // Get all the breakpointList where r equals to UPDATED_R
        defaultBreakpointShouldNotBeFound("r.in=" + UPDATED_R);
    }

    @Test
    @Transactional
    void getAllBreakpointsByRIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r is not null
        defaultBreakpointShouldBeFound("r.specified=true");

        // Get all the breakpointList where r is null
        defaultBreakpointShouldNotBeFound("r.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByRContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r contains DEFAULT_R
        defaultBreakpointShouldBeFound("r.contains=" + DEFAULT_R);

        // Get all the breakpointList where r contains UPDATED_R
        defaultBreakpointShouldNotBeFound("r.contains=" + UPDATED_R);
    }

    @Test
    @Transactional
    void getAllBreakpointsByRNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where r does not contain DEFAULT_R
        defaultBreakpointShouldNotBeFound("r.doesNotContain=" + DEFAULT_R);

        // Get all the breakpointList where r does not contain UPDATED_R
        defaultBreakpointShouldBeFound("r.doesNotContain=" + UPDATED_R);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i equals to DEFAULT_I
        defaultBreakpointShouldBeFound("i.equals=" + DEFAULT_I);

        // Get all the breakpointList where i equals to UPDATED_I
        defaultBreakpointShouldNotBeFound("i.equals=" + UPDATED_I);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i not equals to DEFAULT_I
        defaultBreakpointShouldNotBeFound("i.notEquals=" + DEFAULT_I);

        // Get all the breakpointList where i not equals to UPDATED_I
        defaultBreakpointShouldBeFound("i.notEquals=" + UPDATED_I);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i in DEFAULT_I or UPDATED_I
        defaultBreakpointShouldBeFound("i.in=" + DEFAULT_I + "," + UPDATED_I);

        // Get all the breakpointList where i equals to UPDATED_I
        defaultBreakpointShouldNotBeFound("i.in=" + UPDATED_I);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i is not null
        defaultBreakpointShouldBeFound("i.specified=true");

        // Get all the breakpointList where i is null
        defaultBreakpointShouldNotBeFound("i.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByIContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i contains DEFAULT_I
        defaultBreakpointShouldBeFound("i.contains=" + DEFAULT_I);

        // Get all the breakpointList where i contains UPDATED_I
        defaultBreakpointShouldNotBeFound("i.contains=" + UPDATED_I);
    }

    @Test
    @Transactional
    void getAllBreakpointsByINotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where i does not contain DEFAULT_I
        defaultBreakpointShouldNotBeFound("i.doesNotContain=" + DEFAULT_I);

        // Get all the breakpointList where i does not contain UPDATED_I
        defaultBreakpointShouldBeFound("i.doesNotContain=" + UPDATED_I);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd equals to DEFAULT_SDD
        defaultBreakpointShouldBeFound("sdd.equals=" + DEFAULT_SDD);

        // Get all the breakpointList where sdd equals to UPDATED_SDD
        defaultBreakpointShouldNotBeFound("sdd.equals=" + UPDATED_SDD);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd not equals to DEFAULT_SDD
        defaultBreakpointShouldNotBeFound("sdd.notEquals=" + DEFAULT_SDD);

        // Get all the breakpointList where sdd not equals to UPDATED_SDD
        defaultBreakpointShouldBeFound("sdd.notEquals=" + UPDATED_SDD);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd in DEFAULT_SDD or UPDATED_SDD
        defaultBreakpointShouldBeFound("sdd.in=" + DEFAULT_SDD + "," + UPDATED_SDD);

        // Get all the breakpointList where sdd equals to UPDATED_SDD
        defaultBreakpointShouldNotBeFound("sdd.in=" + UPDATED_SDD);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd is not null
        defaultBreakpointShouldBeFound("sdd.specified=true");

        // Get all the breakpointList where sdd is null
        defaultBreakpointShouldNotBeFound("sdd.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd contains DEFAULT_SDD
        defaultBreakpointShouldBeFound("sdd.contains=" + DEFAULT_SDD);

        // Get all the breakpointList where sdd contains UPDATED_SDD
        defaultBreakpointShouldNotBeFound("sdd.contains=" + UPDATED_SDD);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySddNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where sdd does not contain DEFAULT_SDD
        defaultBreakpointShouldNotBeFound("sdd.doesNotContain=" + DEFAULT_SDD);

        // Get all the breakpointList where sdd does not contain UPDATED_SDD
        defaultBreakpointShouldBeFound("sdd.doesNotContain=" + UPDATED_SDD);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s equals to DEFAULT_S
        defaultBreakpointShouldBeFound("s.equals=" + DEFAULT_S);

        // Get all the breakpointList where s equals to UPDATED_S
        defaultBreakpointShouldNotBeFound("s.equals=" + UPDATED_S);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s not equals to DEFAULT_S
        defaultBreakpointShouldNotBeFound("s.notEquals=" + DEFAULT_S);

        // Get all the breakpointList where s not equals to UPDATED_S
        defaultBreakpointShouldBeFound("s.notEquals=" + UPDATED_S);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s in DEFAULT_S or UPDATED_S
        defaultBreakpointShouldBeFound("s.in=" + DEFAULT_S + "," + UPDATED_S);

        // Get all the breakpointList where s equals to UPDATED_S
        defaultBreakpointShouldNotBeFound("s.in=" + UPDATED_S);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s is not null
        defaultBreakpointShouldBeFound("s.specified=true");

        // Get all the breakpointList where s is null
        defaultBreakpointShouldNotBeFound("s.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsBySContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s contains DEFAULT_S
        defaultBreakpointShouldBeFound("s.contains=" + DEFAULT_S);

        // Get all the breakpointList where s contains UPDATED_S
        defaultBreakpointShouldNotBeFound("s.contains=" + UPDATED_S);
    }

    @Test
    @Transactional
    void getAllBreakpointsBySNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where s does not contain DEFAULT_S
        defaultBreakpointShouldNotBeFound("s.doesNotContain=" + DEFAULT_S);

        // Get all the breakpointList where s does not contain UPDATED_S
        defaultBreakpointShouldBeFound("s.doesNotContain=" + UPDATED_S);
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff equals to DEFAULT_ECV_ECOFF
        defaultBreakpointShouldBeFound("ecvEcoff.equals=" + DEFAULT_ECV_ECOFF);

        // Get all the breakpointList where ecvEcoff equals to UPDATED_ECV_ECOFF
        defaultBreakpointShouldNotBeFound("ecvEcoff.equals=" + UPDATED_ECV_ECOFF);
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff not equals to DEFAULT_ECV_ECOFF
        defaultBreakpointShouldNotBeFound("ecvEcoff.notEquals=" + DEFAULT_ECV_ECOFF);

        // Get all the breakpointList where ecvEcoff not equals to UPDATED_ECV_ECOFF
        defaultBreakpointShouldBeFound("ecvEcoff.notEquals=" + UPDATED_ECV_ECOFF);
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff in DEFAULT_ECV_ECOFF or UPDATED_ECV_ECOFF
        defaultBreakpointShouldBeFound("ecvEcoff.in=" + DEFAULT_ECV_ECOFF + "," + UPDATED_ECV_ECOFF);

        // Get all the breakpointList where ecvEcoff equals to UPDATED_ECV_ECOFF
        defaultBreakpointShouldNotBeFound("ecvEcoff.in=" + UPDATED_ECV_ECOFF);
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff is not null
        defaultBreakpointShouldBeFound("ecvEcoff.specified=true");

        // Get all the breakpointList where ecvEcoff is null
        defaultBreakpointShouldNotBeFound("ecvEcoff.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff contains DEFAULT_ECV_ECOFF
        defaultBreakpointShouldBeFound("ecvEcoff.contains=" + DEFAULT_ECV_ECOFF);

        // Get all the breakpointList where ecvEcoff contains UPDATED_ECV_ECOFF
        defaultBreakpointShouldNotBeFound("ecvEcoff.contains=" + UPDATED_ECV_ECOFF);
    }

    @Test
    @Transactional
    void getAllBreakpointsByEcvEcoffNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where ecvEcoff does not contain DEFAULT_ECV_ECOFF
        defaultBreakpointShouldNotBeFound("ecvEcoff.doesNotContain=" + DEFAULT_ECV_ECOFF);

        // Get all the breakpointList where ecvEcoff does not contain UPDATED_ECV_ECOFF
        defaultBreakpointShouldBeFound("ecvEcoff.doesNotContain=" + UPDATED_ECV_ECOFF);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered equals to DEFAULT_DATE_ENTERED
        defaultBreakpointShouldBeFound("dateEntered.equals=" + DEFAULT_DATE_ENTERED);

        // Get all the breakpointList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultBreakpointShouldNotBeFound("dateEntered.equals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered not equals to DEFAULT_DATE_ENTERED
        defaultBreakpointShouldNotBeFound("dateEntered.notEquals=" + DEFAULT_DATE_ENTERED);

        // Get all the breakpointList where dateEntered not equals to UPDATED_DATE_ENTERED
        defaultBreakpointShouldBeFound("dateEntered.notEquals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered in DEFAULT_DATE_ENTERED or UPDATED_DATE_ENTERED
        defaultBreakpointShouldBeFound("dateEntered.in=" + DEFAULT_DATE_ENTERED + "," + UPDATED_DATE_ENTERED);

        // Get all the breakpointList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultBreakpointShouldNotBeFound("dateEntered.in=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered is not null
        defaultBreakpointShouldBeFound("dateEntered.specified=true");

        // Get all the breakpointList where dateEntered is null
        defaultBreakpointShouldNotBeFound("dateEntered.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered contains DEFAULT_DATE_ENTERED
        defaultBreakpointShouldBeFound("dateEntered.contains=" + DEFAULT_DATE_ENTERED);

        // Get all the breakpointList where dateEntered contains UPDATED_DATE_ENTERED
        defaultBreakpointShouldNotBeFound("dateEntered.contains=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateEnteredNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateEntered does not contain DEFAULT_DATE_ENTERED
        defaultBreakpointShouldNotBeFound("dateEntered.doesNotContain=" + DEFAULT_DATE_ENTERED);

        // Get all the breakpointList where dateEntered does not contain UPDATED_DATE_ENTERED
        defaultBreakpointShouldBeFound("dateEntered.doesNotContain=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified equals to DEFAULT_DATE_MODIFIED
        defaultBreakpointShouldBeFound("dateModified.equals=" + DEFAULT_DATE_MODIFIED);

        // Get all the breakpointList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultBreakpointShouldNotBeFound("dateModified.equals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified not equals to DEFAULT_DATE_MODIFIED
        defaultBreakpointShouldNotBeFound("dateModified.notEquals=" + DEFAULT_DATE_MODIFIED);

        // Get all the breakpointList where dateModified not equals to UPDATED_DATE_MODIFIED
        defaultBreakpointShouldBeFound("dateModified.notEquals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified in DEFAULT_DATE_MODIFIED or UPDATED_DATE_MODIFIED
        defaultBreakpointShouldBeFound("dateModified.in=" + DEFAULT_DATE_MODIFIED + "," + UPDATED_DATE_MODIFIED);

        // Get all the breakpointList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultBreakpointShouldNotBeFound("dateModified.in=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified is not null
        defaultBreakpointShouldBeFound("dateModified.specified=true");

        // Get all the breakpointList where dateModified is null
        defaultBreakpointShouldNotBeFound("dateModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified contains DEFAULT_DATE_MODIFIED
        defaultBreakpointShouldBeFound("dateModified.contains=" + DEFAULT_DATE_MODIFIED);

        // Get all the breakpointList where dateModified contains UPDATED_DATE_MODIFIED
        defaultBreakpointShouldNotBeFound("dateModified.contains=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByDateModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where dateModified does not contain DEFAULT_DATE_MODIFIED
        defaultBreakpointShouldNotBeFound("dateModified.doesNotContain=" + DEFAULT_DATE_MODIFIED);

        // Get all the breakpointList where dateModified does not contain UPDATED_DATE_MODIFIED
        defaultBreakpointShouldBeFound("dateModified.doesNotContain=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments equals to DEFAULT_COMMENTS
        defaultBreakpointShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the breakpointList where comments equals to UPDATED_COMMENTS
        defaultBreakpointShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments not equals to DEFAULT_COMMENTS
        defaultBreakpointShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the breakpointList where comments not equals to UPDATED_COMMENTS
        defaultBreakpointShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultBreakpointShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the breakpointList where comments equals to UPDATED_COMMENTS
        defaultBreakpointShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments is not null
        defaultBreakpointShouldBeFound("comments.specified=true");

        // Get all the breakpointList where comments is null
        defaultBreakpointShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments contains DEFAULT_COMMENTS
        defaultBreakpointShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the breakpointList where comments contains UPDATED_COMMENTS
        defaultBreakpointShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllBreakpointsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where comments does not contain DEFAULT_COMMENTS
        defaultBreakpointShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the breakpointList where comments does not contain UPDATED_COMMENTS
        defaultBreakpointShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBreakpointShouldBeFound(String filter) throws Exception {
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breakpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].guidelines").value(hasItem(DEFAULT_GUIDELINES)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].testMethod").value(hasItem(DEFAULT_TEST_METHOD)))
            .andExpect(jsonPath("$.[*].potency").value(hasItem(DEFAULT_POTENCY)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].breakpointType").value(hasItem(DEFAULT_BREAKPOINT_TYPE)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].siteOfInfection").value(hasItem(DEFAULT_SITE_OF_INFECTION)))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE)))
            .andExpect(jsonPath("$.[*].referenceSequence").value(hasItem(DEFAULT_REFERENCE_SEQUENCE)))
            .andExpect(jsonPath("$.[*].whonetAbxCode").value(hasItem(DEFAULT_WHONET_ABX_CODE)))
            .andExpect(jsonPath("$.[*].whonetTest").value(hasItem(DEFAULT_WHONET_TEST)))
            .andExpect(jsonPath("$.[*].r").value(hasItem(DEFAULT_R)))
            .andExpect(jsonPath("$.[*].i").value(hasItem(DEFAULT_I)))
            .andExpect(jsonPath("$.[*].sdd").value(hasItem(DEFAULT_SDD)))
            .andExpect(jsonPath("$.[*].s").value(hasItem(DEFAULT_S)))
            .andExpect(jsonPath("$.[*].ecvEcoff").value(hasItem(DEFAULT_ECV_ECOFF)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBreakpointShouldNotBeFound(String filter) throws Exception {
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBreakpointMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBreakpoint() throws Exception {
        // Get the breakpoint
        restBreakpointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();

        // Update the breakpoint
        Breakpoint updatedBreakpoint = breakpointRepository.findById(breakpoint.getId()).get();
        // Disconnect from session so that the updates on updatedBreakpoint are not directly saved in db
        em.detach(updatedBreakpoint);
        updatedBreakpoint
            .guidelines(UPDATED_GUIDELINES)
            .year(UPDATED_YEAR)
            .testMethod(UPDATED_TEST_METHOD)
            .potency(UPDATED_POTENCY)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .breakpointType(UPDATED_BREAKPOINT_TYPE)
            .host(UPDATED_HOST)
            .siteOfInfection(UPDATED_SITE_OF_INFECTION)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .referenceSequence(UPDATED_REFERENCE_SEQUENCE)
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whonetTest(UPDATED_WHONET_TEST)
            .r(UPDATED_R)
            .i(UPDATED_I)
            .sdd(UPDATED_SDD)
            .s(UPDATED_S)
            .ecvEcoff(UPDATED_ECV_ECOFF)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(updatedBreakpoint);

        restBreakpointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, breakpointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isOk());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getGuidelines()).isEqualTo(UPDATED_GUIDELINES);
        assertThat(testBreakpoint.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBreakpoint.getTestMethod()).isEqualTo(UPDATED_TEST_METHOD);
        assertThat(testBreakpoint.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testBreakpoint.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testBreakpoint.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testBreakpoint.getBreakpointType()).isEqualTo(UPDATED_BREAKPOINT_TYPE);
        assertThat(testBreakpoint.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testBreakpoint.getSiteOfInfection()).isEqualTo(UPDATED_SITE_OF_INFECTION);
        assertThat(testBreakpoint.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testBreakpoint.getReferenceSequence()).isEqualTo(UPDATED_REFERENCE_SEQUENCE);
        assertThat(testBreakpoint.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testBreakpoint.getWhonetTest()).isEqualTo(UPDATED_WHONET_TEST);
        assertThat(testBreakpoint.getR()).isEqualTo(UPDATED_R);
        assertThat(testBreakpoint.getI()).isEqualTo(UPDATED_I);
        assertThat(testBreakpoint.getSdd()).isEqualTo(UPDATED_SDD);
        assertThat(testBreakpoint.getS()).isEqualTo(UPDATED_S);
        assertThat(testBreakpoint.getEcvEcoff()).isEqualTo(UPDATED_ECV_ECOFF);
        assertThat(testBreakpoint.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testBreakpoint.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testBreakpoint.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, breakpointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(breakpointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBreakpointWithPatch() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();

        // Update the breakpoint using partial update
        Breakpoint partialUpdatedBreakpoint = new Breakpoint();
        partialUpdatedBreakpoint.setId(breakpoint.getId());

        partialUpdatedBreakpoint
            .guidelines(UPDATED_GUIDELINES)
            .potency(UPDATED_POTENCY)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .host(UPDATED_HOST)
            .siteOfInfection(UPDATED_SITE_OF_INFECTION)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .s(UPDATED_S)
            .ecvEcoff(UPDATED_ECV_ECOFF)
            .dateEntered(UPDATED_DATE_ENTERED);

        restBreakpointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBreakpoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBreakpoint))
            )
            .andExpect(status().isOk());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getGuidelines()).isEqualTo(UPDATED_GUIDELINES);
        assertThat(testBreakpoint.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBreakpoint.getTestMethod()).isEqualTo(DEFAULT_TEST_METHOD);
        assertThat(testBreakpoint.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testBreakpoint.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testBreakpoint.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testBreakpoint.getBreakpointType()).isEqualTo(DEFAULT_BREAKPOINT_TYPE);
        assertThat(testBreakpoint.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testBreakpoint.getSiteOfInfection()).isEqualTo(UPDATED_SITE_OF_INFECTION);
        assertThat(testBreakpoint.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testBreakpoint.getReferenceSequence()).isEqualTo(DEFAULT_REFERENCE_SEQUENCE);
        assertThat(testBreakpoint.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testBreakpoint.getWhonetTest()).isEqualTo(DEFAULT_WHONET_TEST);
        assertThat(testBreakpoint.getR()).isEqualTo(DEFAULT_R);
        assertThat(testBreakpoint.getI()).isEqualTo(DEFAULT_I);
        assertThat(testBreakpoint.getSdd()).isEqualTo(DEFAULT_SDD);
        assertThat(testBreakpoint.getS()).isEqualTo(UPDATED_S);
        assertThat(testBreakpoint.getEcvEcoff()).isEqualTo(UPDATED_ECV_ECOFF);
        assertThat(testBreakpoint.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testBreakpoint.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testBreakpoint.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateBreakpointWithPatch() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();

        // Update the breakpoint using partial update
        Breakpoint partialUpdatedBreakpoint = new Breakpoint();
        partialUpdatedBreakpoint.setId(breakpoint.getId());

        partialUpdatedBreakpoint
            .guidelines(UPDATED_GUIDELINES)
            .year(UPDATED_YEAR)
            .testMethod(UPDATED_TEST_METHOD)
            .potency(UPDATED_POTENCY)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .breakpointType(UPDATED_BREAKPOINT_TYPE)
            .host(UPDATED_HOST)
            .siteOfInfection(UPDATED_SITE_OF_INFECTION)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .referenceSequence(UPDATED_REFERENCE_SEQUENCE)
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whonetTest(UPDATED_WHONET_TEST)
            .r(UPDATED_R)
            .i(UPDATED_I)
            .sdd(UPDATED_SDD)
            .s(UPDATED_S)
            .ecvEcoff(UPDATED_ECV_ECOFF)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);

        restBreakpointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBreakpoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBreakpoint))
            )
            .andExpect(status().isOk());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
        Breakpoint testBreakpoint = breakpointList.get(breakpointList.size() - 1);
        assertThat(testBreakpoint.getGuidelines()).isEqualTo(UPDATED_GUIDELINES);
        assertThat(testBreakpoint.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBreakpoint.getTestMethod()).isEqualTo(UPDATED_TEST_METHOD);
        assertThat(testBreakpoint.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testBreakpoint.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testBreakpoint.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testBreakpoint.getBreakpointType()).isEqualTo(UPDATED_BREAKPOINT_TYPE);
        assertThat(testBreakpoint.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testBreakpoint.getSiteOfInfection()).isEqualTo(UPDATED_SITE_OF_INFECTION);
        assertThat(testBreakpoint.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testBreakpoint.getReferenceSequence()).isEqualTo(UPDATED_REFERENCE_SEQUENCE);
        assertThat(testBreakpoint.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testBreakpoint.getWhonetTest()).isEqualTo(UPDATED_WHONET_TEST);
        assertThat(testBreakpoint.getR()).isEqualTo(UPDATED_R);
        assertThat(testBreakpoint.getI()).isEqualTo(UPDATED_I);
        assertThat(testBreakpoint.getSdd()).isEqualTo(UPDATED_SDD);
        assertThat(testBreakpoint.getS()).isEqualTo(UPDATED_S);
        assertThat(testBreakpoint.getEcvEcoff()).isEqualTo(UPDATED_ECV_ECOFF);
        assertThat(testBreakpoint.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testBreakpoint.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testBreakpoint.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, breakpointDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBreakpoint() throws Exception {
        int databaseSizeBeforeUpdate = breakpointRepository.findAll().size();
        breakpoint.setId(count.incrementAndGet());

        // Create the Breakpoint
        BreakpointDTO breakpointDTO = breakpointMapper.toDto(breakpoint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBreakpointMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(breakpointDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Breakpoint in the database
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBreakpoint() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        int databaseSizeBeforeDelete = breakpointRepository.findAll().size();

        // Delete the breakpoint
        restBreakpointMockMvc
            .perform(delete(ENTITY_API_URL_ID, breakpoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Breakpoint> breakpointList = breakpointRepository.findAll();
        assertThat(breakpointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
