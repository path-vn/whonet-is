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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BreakpointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BreakpointResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_ANTIBIOTIC_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_ANTIBIOTIC_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_INTRINSIC_RESISTANCE_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_INTRINSIC_RESISTANCE_QUERY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BINARY_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BINARY_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BINARY_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BINARY_DATA_CONTENT_TYPE = "image/png";

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
            .path(DEFAULT_PATH)
            .query(DEFAULT_QUERY)
            .antibioticQuery(DEFAULT_ANTIBIOTIC_QUERY)
            .organismQuery(DEFAULT_ORGANISM_QUERY)
            .intrinsicResistanceQuery(DEFAULT_INTRINSIC_RESISTANCE_QUERY)
            .binaryData(DEFAULT_BINARY_DATA)
            .binaryDataContentType(DEFAULT_BINARY_DATA_CONTENT_TYPE);
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
            .path(UPDATED_PATH)
            .query(UPDATED_QUERY)
            .antibioticQuery(UPDATED_ANTIBIOTIC_QUERY)
            .organismQuery(UPDATED_ORGANISM_QUERY)
            .intrinsicResistanceQuery(UPDATED_INTRINSIC_RESISTANCE_QUERY)
            .binaryData(UPDATED_BINARY_DATA)
            .binaryDataContentType(UPDATED_BINARY_DATA_CONTENT_TYPE);
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
        assertThat(testBreakpoint.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testBreakpoint.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testBreakpoint.getAntibioticQuery()).isEqualTo(DEFAULT_ANTIBIOTIC_QUERY);
        assertThat(testBreakpoint.getOrganismQuery()).isEqualTo(DEFAULT_ORGANISM_QUERY);
        assertThat(testBreakpoint.getIntrinsicResistanceQuery()).isEqualTo(DEFAULT_INTRINSIC_RESISTANCE_QUERY);
        assertThat(testBreakpoint.getBinaryData()).isEqualTo(DEFAULT_BINARY_DATA);
        assertThat(testBreakpoint.getBinaryDataContentType()).isEqualTo(DEFAULT_BINARY_DATA_CONTENT_TYPE);
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
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].antibioticQuery").value(hasItem(DEFAULT_ANTIBIOTIC_QUERY)))
            .andExpect(jsonPath("$.[*].organismQuery").value(hasItem(DEFAULT_ORGANISM_QUERY)))
            .andExpect(jsonPath("$.[*].intrinsicResistanceQuery").value(hasItem(DEFAULT_INTRINSIC_RESISTANCE_QUERY)))
            .andExpect(jsonPath("$.[*].binaryDataContentType").value(hasItem(DEFAULT_BINARY_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].binaryData").value(hasItem(Base64Utils.encodeToString(DEFAULT_BINARY_DATA))));
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
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY))
            .andExpect(jsonPath("$.antibioticQuery").value(DEFAULT_ANTIBIOTIC_QUERY))
            .andExpect(jsonPath("$.organismQuery").value(DEFAULT_ORGANISM_QUERY))
            .andExpect(jsonPath("$.intrinsicResistanceQuery").value(DEFAULT_INTRINSIC_RESISTANCE_QUERY))
            .andExpect(jsonPath("$.binaryDataContentType").value(DEFAULT_BINARY_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.binaryData").value(Base64Utils.encodeToString(DEFAULT_BINARY_DATA)));
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
    void getAllBreakpointsByPathIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path equals to DEFAULT_PATH
        defaultBreakpointShouldBeFound("path.equals=" + DEFAULT_PATH);

        // Get all the breakpointList where path equals to UPDATED_PATH
        defaultBreakpointShouldNotBeFound("path.equals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path not equals to DEFAULT_PATH
        defaultBreakpointShouldNotBeFound("path.notEquals=" + DEFAULT_PATH);

        // Get all the breakpointList where path not equals to UPDATED_PATH
        defaultBreakpointShouldBeFound("path.notEquals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPathIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path in DEFAULT_PATH or UPDATED_PATH
        defaultBreakpointShouldBeFound("path.in=" + DEFAULT_PATH + "," + UPDATED_PATH);

        // Get all the breakpointList where path equals to UPDATED_PATH
        defaultBreakpointShouldNotBeFound("path.in=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path is not null
        defaultBreakpointShouldBeFound("path.specified=true");

        // Get all the breakpointList where path is null
        defaultBreakpointShouldNotBeFound("path.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByPathContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path contains DEFAULT_PATH
        defaultBreakpointShouldBeFound("path.contains=" + DEFAULT_PATH);

        // Get all the breakpointList where path contains UPDATED_PATH
        defaultBreakpointShouldNotBeFound("path.contains=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllBreakpointsByPathNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where path does not contain DEFAULT_PATH
        defaultBreakpointShouldNotBeFound("path.doesNotContain=" + DEFAULT_PATH);

        // Get all the breakpointList where path does not contain UPDATED_PATH
        defaultBreakpointShouldBeFound("path.doesNotContain=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query equals to DEFAULT_QUERY
        defaultBreakpointShouldBeFound("query.equals=" + DEFAULT_QUERY);

        // Get all the breakpointList where query equals to UPDATED_QUERY
        defaultBreakpointShouldNotBeFound("query.equals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query not equals to DEFAULT_QUERY
        defaultBreakpointShouldNotBeFound("query.notEquals=" + DEFAULT_QUERY);

        // Get all the breakpointList where query not equals to UPDATED_QUERY
        defaultBreakpointShouldBeFound("query.notEquals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query in DEFAULT_QUERY or UPDATED_QUERY
        defaultBreakpointShouldBeFound("query.in=" + DEFAULT_QUERY + "," + UPDATED_QUERY);

        // Get all the breakpointList where query equals to UPDATED_QUERY
        defaultBreakpointShouldNotBeFound("query.in=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query is not null
        defaultBreakpointShouldBeFound("query.specified=true");

        // Get all the breakpointList where query is null
        defaultBreakpointShouldNotBeFound("query.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query contains DEFAULT_QUERY
        defaultBreakpointShouldBeFound("query.contains=" + DEFAULT_QUERY);

        // Get all the breakpointList where query contains UPDATED_QUERY
        defaultBreakpointShouldNotBeFound("query.contains=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByQueryNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where query does not contain DEFAULT_QUERY
        defaultBreakpointShouldNotBeFound("query.doesNotContain=" + DEFAULT_QUERY);

        // Get all the breakpointList where query does not contain UPDATED_QUERY
        defaultBreakpointShouldBeFound("query.doesNotContain=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery equals to DEFAULT_ANTIBIOTIC_QUERY
        defaultBreakpointShouldBeFound("antibioticQuery.equals=" + DEFAULT_ANTIBIOTIC_QUERY);

        // Get all the breakpointList where antibioticQuery equals to UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldNotBeFound("antibioticQuery.equals=" + UPDATED_ANTIBIOTIC_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery not equals to DEFAULT_ANTIBIOTIC_QUERY
        defaultBreakpointShouldNotBeFound("antibioticQuery.notEquals=" + DEFAULT_ANTIBIOTIC_QUERY);

        // Get all the breakpointList where antibioticQuery not equals to UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldBeFound("antibioticQuery.notEquals=" + UPDATED_ANTIBIOTIC_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery in DEFAULT_ANTIBIOTIC_QUERY or UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldBeFound("antibioticQuery.in=" + DEFAULT_ANTIBIOTIC_QUERY + "," + UPDATED_ANTIBIOTIC_QUERY);

        // Get all the breakpointList where antibioticQuery equals to UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldNotBeFound("antibioticQuery.in=" + UPDATED_ANTIBIOTIC_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery is not null
        defaultBreakpointShouldBeFound("antibioticQuery.specified=true");

        // Get all the breakpointList where antibioticQuery is null
        defaultBreakpointShouldNotBeFound("antibioticQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery contains DEFAULT_ANTIBIOTIC_QUERY
        defaultBreakpointShouldBeFound("antibioticQuery.contains=" + DEFAULT_ANTIBIOTIC_QUERY);

        // Get all the breakpointList where antibioticQuery contains UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldNotBeFound("antibioticQuery.contains=" + UPDATED_ANTIBIOTIC_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByAntibioticQueryNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where antibioticQuery does not contain DEFAULT_ANTIBIOTIC_QUERY
        defaultBreakpointShouldNotBeFound("antibioticQuery.doesNotContain=" + DEFAULT_ANTIBIOTIC_QUERY);

        // Get all the breakpointList where antibioticQuery does not contain UPDATED_ANTIBIOTIC_QUERY
        defaultBreakpointShouldBeFound("antibioticQuery.doesNotContain=" + UPDATED_ANTIBIOTIC_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery equals to DEFAULT_ORGANISM_QUERY
        defaultBreakpointShouldBeFound("organismQuery.equals=" + DEFAULT_ORGANISM_QUERY);

        // Get all the breakpointList where organismQuery equals to UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldNotBeFound("organismQuery.equals=" + UPDATED_ORGANISM_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery not equals to DEFAULT_ORGANISM_QUERY
        defaultBreakpointShouldNotBeFound("organismQuery.notEquals=" + DEFAULT_ORGANISM_QUERY);

        // Get all the breakpointList where organismQuery not equals to UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldBeFound("organismQuery.notEquals=" + UPDATED_ORGANISM_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery in DEFAULT_ORGANISM_QUERY or UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldBeFound("organismQuery.in=" + DEFAULT_ORGANISM_QUERY + "," + UPDATED_ORGANISM_QUERY);

        // Get all the breakpointList where organismQuery equals to UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldNotBeFound("organismQuery.in=" + UPDATED_ORGANISM_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery is not null
        defaultBreakpointShouldBeFound("organismQuery.specified=true");

        // Get all the breakpointList where organismQuery is null
        defaultBreakpointShouldNotBeFound("organismQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery contains DEFAULT_ORGANISM_QUERY
        defaultBreakpointShouldBeFound("organismQuery.contains=" + DEFAULT_ORGANISM_QUERY);

        // Get all the breakpointList where organismQuery contains UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldNotBeFound("organismQuery.contains=" + UPDATED_ORGANISM_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByOrganismQueryNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where organismQuery does not contain DEFAULT_ORGANISM_QUERY
        defaultBreakpointShouldNotBeFound("organismQuery.doesNotContain=" + DEFAULT_ORGANISM_QUERY);

        // Get all the breakpointList where organismQuery does not contain UPDATED_ORGANISM_QUERY
        defaultBreakpointShouldBeFound("organismQuery.doesNotContain=" + UPDATED_ORGANISM_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery equals to DEFAULT_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldBeFound("intrinsicResistanceQuery.equals=" + DEFAULT_INTRINSIC_RESISTANCE_QUERY);

        // Get all the breakpointList where intrinsicResistanceQuery equals to UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.equals=" + UPDATED_INTRINSIC_RESISTANCE_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery not equals to DEFAULT_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.notEquals=" + DEFAULT_INTRINSIC_RESISTANCE_QUERY);

        // Get all the breakpointList where intrinsicResistanceQuery not equals to UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldBeFound("intrinsicResistanceQuery.notEquals=" + UPDATED_INTRINSIC_RESISTANCE_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryIsInShouldWork() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery in DEFAULT_INTRINSIC_RESISTANCE_QUERY or UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldBeFound(
            "intrinsicResistanceQuery.in=" + DEFAULT_INTRINSIC_RESISTANCE_QUERY + "," + UPDATED_INTRINSIC_RESISTANCE_QUERY
        );

        // Get all the breakpointList where intrinsicResistanceQuery equals to UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.in=" + UPDATED_INTRINSIC_RESISTANCE_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery is not null
        defaultBreakpointShouldBeFound("intrinsicResistanceQuery.specified=true");

        // Get all the breakpointList where intrinsicResistanceQuery is null
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.specified=false");
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery contains DEFAULT_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldBeFound("intrinsicResistanceQuery.contains=" + DEFAULT_INTRINSIC_RESISTANCE_QUERY);

        // Get all the breakpointList where intrinsicResistanceQuery contains UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.contains=" + UPDATED_INTRINSIC_RESISTANCE_QUERY);
    }

    @Test
    @Transactional
    void getAllBreakpointsByIntrinsicResistanceQueryNotContainsSomething() throws Exception {
        // Initialize the database
        breakpointRepository.saveAndFlush(breakpoint);

        // Get all the breakpointList where intrinsicResistanceQuery does not contain DEFAULT_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldNotBeFound("intrinsicResistanceQuery.doesNotContain=" + DEFAULT_INTRINSIC_RESISTANCE_QUERY);

        // Get all the breakpointList where intrinsicResistanceQuery does not contain UPDATED_INTRINSIC_RESISTANCE_QUERY
        defaultBreakpointShouldBeFound("intrinsicResistanceQuery.doesNotContain=" + UPDATED_INTRINSIC_RESISTANCE_QUERY);
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
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].antibioticQuery").value(hasItem(DEFAULT_ANTIBIOTIC_QUERY)))
            .andExpect(jsonPath("$.[*].organismQuery").value(hasItem(DEFAULT_ORGANISM_QUERY)))
            .andExpect(jsonPath("$.[*].intrinsicResistanceQuery").value(hasItem(DEFAULT_INTRINSIC_RESISTANCE_QUERY)))
            .andExpect(jsonPath("$.[*].binaryDataContentType").value(hasItem(DEFAULT_BINARY_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].binaryData").value(hasItem(Base64Utils.encodeToString(DEFAULT_BINARY_DATA))));

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
            .path(UPDATED_PATH)
            .query(UPDATED_QUERY)
            .antibioticQuery(UPDATED_ANTIBIOTIC_QUERY)
            .organismQuery(UPDATED_ORGANISM_QUERY)
            .intrinsicResistanceQuery(UPDATED_INTRINSIC_RESISTANCE_QUERY)
            .binaryData(UPDATED_BINARY_DATA)
            .binaryDataContentType(UPDATED_BINARY_DATA_CONTENT_TYPE);
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
        assertThat(testBreakpoint.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testBreakpoint.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testBreakpoint.getAntibioticQuery()).isEqualTo(UPDATED_ANTIBIOTIC_QUERY);
        assertThat(testBreakpoint.getOrganismQuery()).isEqualTo(UPDATED_ORGANISM_QUERY);
        assertThat(testBreakpoint.getIntrinsicResistanceQuery()).isEqualTo(UPDATED_INTRINSIC_RESISTANCE_QUERY);
        assertThat(testBreakpoint.getBinaryData()).isEqualTo(UPDATED_BINARY_DATA);
        assertThat(testBreakpoint.getBinaryDataContentType()).isEqualTo(UPDATED_BINARY_DATA_CONTENT_TYPE);
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
            .path(UPDATED_PATH)
            .organismQuery(UPDATED_ORGANISM_QUERY)
            .binaryData(UPDATED_BINARY_DATA)
            .binaryDataContentType(UPDATED_BINARY_DATA_CONTENT_TYPE);

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
        assertThat(testBreakpoint.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testBreakpoint.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testBreakpoint.getAntibioticQuery()).isEqualTo(DEFAULT_ANTIBIOTIC_QUERY);
        assertThat(testBreakpoint.getOrganismQuery()).isEqualTo(UPDATED_ORGANISM_QUERY);
        assertThat(testBreakpoint.getIntrinsicResistanceQuery()).isEqualTo(DEFAULT_INTRINSIC_RESISTANCE_QUERY);
        assertThat(testBreakpoint.getBinaryData()).isEqualTo(UPDATED_BINARY_DATA);
        assertThat(testBreakpoint.getBinaryDataContentType()).isEqualTo(UPDATED_BINARY_DATA_CONTENT_TYPE);
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
            .path(UPDATED_PATH)
            .query(UPDATED_QUERY)
            .antibioticQuery(UPDATED_ANTIBIOTIC_QUERY)
            .organismQuery(UPDATED_ORGANISM_QUERY)
            .intrinsicResistanceQuery(UPDATED_INTRINSIC_RESISTANCE_QUERY)
            .binaryData(UPDATED_BINARY_DATA)
            .binaryDataContentType(UPDATED_BINARY_DATA_CONTENT_TYPE);

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
        assertThat(testBreakpoint.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testBreakpoint.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testBreakpoint.getAntibioticQuery()).isEqualTo(UPDATED_ANTIBIOTIC_QUERY);
        assertThat(testBreakpoint.getOrganismQuery()).isEqualTo(UPDATED_ORGANISM_QUERY);
        assertThat(testBreakpoint.getIntrinsicResistanceQuery()).isEqualTo(UPDATED_INTRINSIC_RESISTANCE_QUERY);
        assertThat(testBreakpoint.getBinaryData()).isEqualTo(UPDATED_BINARY_DATA);
        assertThat(testBreakpoint.getBinaryDataContentType()).isEqualTo(UPDATED_BINARY_DATA_CONTENT_TYPE);
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
