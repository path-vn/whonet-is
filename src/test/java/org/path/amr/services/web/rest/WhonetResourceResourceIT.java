package org.path.amr.services.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.path.amr.services.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.path.amr.services.IntegrationTest;
import org.path.amr.services.domain.WhonetResource;
import org.path.amr.services.repository.WhonetResourceRepository;
import org.path.amr.services.service.criteria.WhonetResourceCriteria;
import org.path.amr.services.service.dto.WhonetResourceDTO;
import org.path.amr.services.service.mapper.WhonetResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WhonetResourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WhonetResourceResourceIT {

    private static final ZonedDateTime DEFAULT_UPLOAD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOAD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPLOAD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_ANTIBIOTIC = "AAAAAAAAAA";
    private static final String UPDATED_ANTIBIOTIC = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM = "BBBBBBBBBB";

    private static final String DEFAULT_INTRINSIC_RESISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_INTRINSIC_RESISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERT_RULE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERT_RULE = "BBBBBBBBBB";

    private static final String DEFAULT_BREAK_POINT = "AAAAAAAAAA";
    private static final String UPDATED_BREAK_POINT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/whonet-resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WhonetResourceRepository whonetResourceRepository;

    @Autowired
    private WhonetResourceMapper whonetResourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWhonetResourceMockMvc;

    private WhonetResource whonetResource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WhonetResource createEntity(EntityManager em) {
        WhonetResource whonetResource = new WhonetResource()
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .antibiotic(DEFAULT_ANTIBIOTIC)
            .organism(DEFAULT_ORGANISM)
            .intrinsicResistance(DEFAULT_INTRINSIC_RESISTANCE)
            .expertRule(DEFAULT_EXPERT_RULE)
            .breakPoint(DEFAULT_BREAK_POINT);
        return whonetResource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WhonetResource createUpdatedEntity(EntityManager em) {
        WhonetResource whonetResource = new WhonetResource()
            .uploadDate(UPDATED_UPLOAD_DATE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .organism(UPDATED_ORGANISM)
            .intrinsicResistance(UPDATED_INTRINSIC_RESISTANCE)
            .expertRule(UPDATED_EXPERT_RULE)
            .breakPoint(UPDATED_BREAK_POINT);
        return whonetResource;
    }

    @BeforeEach
    public void initTest() {
        whonetResource = createEntity(em);
    }

    @Test
    @Transactional
    void createWhonetResource() throws Exception {
        int databaseSizeBeforeCreate = whonetResourceRepository.findAll().size();
        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);
        restWhonetResourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeCreate + 1);
        WhonetResource testWhonetResource = whonetResourceList.get(whonetResourceList.size() - 1);
        assertThat(testWhonetResource.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testWhonetResource.getAntibiotic()).isEqualTo(DEFAULT_ANTIBIOTIC);
        assertThat(testWhonetResource.getOrganism()).isEqualTo(DEFAULT_ORGANISM);
        assertThat(testWhonetResource.getIntrinsicResistance()).isEqualTo(DEFAULT_INTRINSIC_RESISTANCE);
        assertThat(testWhonetResource.getExpertRule()).isEqualTo(DEFAULT_EXPERT_RULE);
        assertThat(testWhonetResource.getBreakPoint()).isEqualTo(DEFAULT_BREAK_POINT);
    }

    @Test
    @Transactional
    void createWhonetResourceWithExistingId() throws Exception {
        // Create the WhonetResource with an existing ID
        whonetResource.setId(1L);
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        int databaseSizeBeforeCreate = whonetResourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWhonetResourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWhonetResources() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(whonetResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(sameInstant(DEFAULT_UPLOAD_DATE))))
            .andExpect(jsonPath("$.[*].antibiotic").value(hasItem(DEFAULT_ANTIBIOTIC)))
            .andExpect(jsonPath("$.[*].organism").value(hasItem(DEFAULT_ORGANISM)))
            .andExpect(jsonPath("$.[*].intrinsicResistance").value(hasItem(DEFAULT_INTRINSIC_RESISTANCE)))
            .andExpect(jsonPath("$.[*].expertRule").value(hasItem(DEFAULT_EXPERT_RULE)))
            .andExpect(jsonPath("$.[*].breakPoint").value(hasItem(DEFAULT_BREAK_POINT)));
    }

    @Test
    @Transactional
    void getWhonetResource() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get the whonetResource
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL_ID, whonetResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(whonetResource.getId().intValue()))
            .andExpect(jsonPath("$.uploadDate").value(sameInstant(DEFAULT_UPLOAD_DATE)))
            .andExpect(jsonPath("$.antibiotic").value(DEFAULT_ANTIBIOTIC))
            .andExpect(jsonPath("$.organism").value(DEFAULT_ORGANISM))
            .andExpect(jsonPath("$.intrinsicResistance").value(DEFAULT_INTRINSIC_RESISTANCE))
            .andExpect(jsonPath("$.expertRule").value(DEFAULT_EXPERT_RULE))
            .andExpect(jsonPath("$.breakPoint").value(DEFAULT_BREAK_POINT));
    }

    @Test
    @Transactional
    void getWhonetResourcesByIdFiltering() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        Long id = whonetResource.getId();

        defaultWhonetResourceShouldBeFound("id.equals=" + id);
        defaultWhonetResourceShouldNotBeFound("id.notEquals=" + id);

        defaultWhonetResourceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWhonetResourceShouldNotBeFound("id.greaterThan=" + id);

        defaultWhonetResourceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWhonetResourceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate equals to DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.equals=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.equals=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate not equals to DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.notEquals=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate not equals to UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.notEquals=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate in DEFAULT_UPLOAD_DATE or UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.in=" + DEFAULT_UPLOAD_DATE + "," + UPDATED_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.in=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate is not null
        defaultWhonetResourceShouldBeFound("uploadDate.specified=true");

        // Get all the whonetResourceList where uploadDate is null
        defaultWhonetResourceShouldNotBeFound("uploadDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate is greater than or equal to DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.greaterThanOrEqual=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate is greater than or equal to UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.greaterThanOrEqual=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate is less than or equal to DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.lessThanOrEqual=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate is less than or equal to SMALLER_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.lessThanOrEqual=" + SMALLER_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsLessThanSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate is less than DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.lessThan=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate is less than UPDATED_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.lessThan=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByUploadDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where uploadDate is greater than DEFAULT_UPLOAD_DATE
        defaultWhonetResourceShouldNotBeFound("uploadDate.greaterThan=" + DEFAULT_UPLOAD_DATE);

        // Get all the whonetResourceList where uploadDate is greater than SMALLER_UPLOAD_DATE
        defaultWhonetResourceShouldBeFound("uploadDate.greaterThan=" + SMALLER_UPLOAD_DATE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic equals to DEFAULT_ANTIBIOTIC
        defaultWhonetResourceShouldBeFound("antibiotic.equals=" + DEFAULT_ANTIBIOTIC);

        // Get all the whonetResourceList where antibiotic equals to UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldNotBeFound("antibiotic.equals=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic not equals to DEFAULT_ANTIBIOTIC
        defaultWhonetResourceShouldNotBeFound("antibiotic.notEquals=" + DEFAULT_ANTIBIOTIC);

        // Get all the whonetResourceList where antibiotic not equals to UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldBeFound("antibiotic.notEquals=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic in DEFAULT_ANTIBIOTIC or UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldBeFound("antibiotic.in=" + DEFAULT_ANTIBIOTIC + "," + UPDATED_ANTIBIOTIC);

        // Get all the whonetResourceList where antibiotic equals to UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldNotBeFound("antibiotic.in=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic is not null
        defaultWhonetResourceShouldBeFound("antibiotic.specified=true");

        // Get all the whonetResourceList where antibiotic is null
        defaultWhonetResourceShouldNotBeFound("antibiotic.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic contains DEFAULT_ANTIBIOTIC
        defaultWhonetResourceShouldBeFound("antibiotic.contains=" + DEFAULT_ANTIBIOTIC);

        // Get all the whonetResourceList where antibiotic contains UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldNotBeFound("antibiotic.contains=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByAntibioticNotContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where antibiotic does not contain DEFAULT_ANTIBIOTIC
        defaultWhonetResourceShouldNotBeFound("antibiotic.doesNotContain=" + DEFAULT_ANTIBIOTIC);

        // Get all the whonetResourceList where antibiotic does not contain UPDATED_ANTIBIOTIC
        defaultWhonetResourceShouldBeFound("antibiotic.doesNotContain=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism equals to DEFAULT_ORGANISM
        defaultWhonetResourceShouldBeFound("organism.equals=" + DEFAULT_ORGANISM);

        // Get all the whonetResourceList where organism equals to UPDATED_ORGANISM
        defaultWhonetResourceShouldNotBeFound("organism.equals=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism not equals to DEFAULT_ORGANISM
        defaultWhonetResourceShouldNotBeFound("organism.notEquals=" + DEFAULT_ORGANISM);

        // Get all the whonetResourceList where organism not equals to UPDATED_ORGANISM
        defaultWhonetResourceShouldBeFound("organism.notEquals=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism in DEFAULT_ORGANISM or UPDATED_ORGANISM
        defaultWhonetResourceShouldBeFound("organism.in=" + DEFAULT_ORGANISM + "," + UPDATED_ORGANISM);

        // Get all the whonetResourceList where organism equals to UPDATED_ORGANISM
        defaultWhonetResourceShouldNotBeFound("organism.in=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism is not null
        defaultWhonetResourceShouldBeFound("organism.specified=true");

        // Get all the whonetResourceList where organism is null
        defaultWhonetResourceShouldNotBeFound("organism.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism contains DEFAULT_ORGANISM
        defaultWhonetResourceShouldBeFound("organism.contains=" + DEFAULT_ORGANISM);

        // Get all the whonetResourceList where organism contains UPDATED_ORGANISM
        defaultWhonetResourceShouldNotBeFound("organism.contains=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByOrganismNotContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where organism does not contain DEFAULT_ORGANISM
        defaultWhonetResourceShouldNotBeFound("organism.doesNotContain=" + DEFAULT_ORGANISM);

        // Get all the whonetResourceList where organism does not contain UPDATED_ORGANISM
        defaultWhonetResourceShouldBeFound("organism.doesNotContain=" + UPDATED_ORGANISM);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance equals to DEFAULT_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldBeFound("intrinsicResistance.equals=" + DEFAULT_INTRINSIC_RESISTANCE);

        // Get all the whonetResourceList where intrinsicResistance equals to UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.equals=" + UPDATED_INTRINSIC_RESISTANCE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance not equals to DEFAULT_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.notEquals=" + DEFAULT_INTRINSIC_RESISTANCE);

        // Get all the whonetResourceList where intrinsicResistance not equals to UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldBeFound("intrinsicResistance.notEquals=" + UPDATED_INTRINSIC_RESISTANCE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance in DEFAULT_INTRINSIC_RESISTANCE or UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldBeFound("intrinsicResistance.in=" + DEFAULT_INTRINSIC_RESISTANCE + "," + UPDATED_INTRINSIC_RESISTANCE);

        // Get all the whonetResourceList where intrinsicResistance equals to UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.in=" + UPDATED_INTRINSIC_RESISTANCE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance is not null
        defaultWhonetResourceShouldBeFound("intrinsicResistance.specified=true");

        // Get all the whonetResourceList where intrinsicResistance is null
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance contains DEFAULT_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldBeFound("intrinsicResistance.contains=" + DEFAULT_INTRINSIC_RESISTANCE);

        // Get all the whonetResourceList where intrinsicResistance contains UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.contains=" + UPDATED_INTRINSIC_RESISTANCE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByIntrinsicResistanceNotContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where intrinsicResistance does not contain DEFAULT_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldNotBeFound("intrinsicResistance.doesNotContain=" + DEFAULT_INTRINSIC_RESISTANCE);

        // Get all the whonetResourceList where intrinsicResistance does not contain UPDATED_INTRINSIC_RESISTANCE
        defaultWhonetResourceShouldBeFound("intrinsicResistance.doesNotContain=" + UPDATED_INTRINSIC_RESISTANCE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule equals to DEFAULT_EXPERT_RULE
        defaultWhonetResourceShouldBeFound("expertRule.equals=" + DEFAULT_EXPERT_RULE);

        // Get all the whonetResourceList where expertRule equals to UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldNotBeFound("expertRule.equals=" + UPDATED_EXPERT_RULE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule not equals to DEFAULT_EXPERT_RULE
        defaultWhonetResourceShouldNotBeFound("expertRule.notEquals=" + DEFAULT_EXPERT_RULE);

        // Get all the whonetResourceList where expertRule not equals to UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldBeFound("expertRule.notEquals=" + UPDATED_EXPERT_RULE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule in DEFAULT_EXPERT_RULE or UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldBeFound("expertRule.in=" + DEFAULT_EXPERT_RULE + "," + UPDATED_EXPERT_RULE);

        // Get all the whonetResourceList where expertRule equals to UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldNotBeFound("expertRule.in=" + UPDATED_EXPERT_RULE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule is not null
        defaultWhonetResourceShouldBeFound("expertRule.specified=true");

        // Get all the whonetResourceList where expertRule is null
        defaultWhonetResourceShouldNotBeFound("expertRule.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule contains DEFAULT_EXPERT_RULE
        defaultWhonetResourceShouldBeFound("expertRule.contains=" + DEFAULT_EXPERT_RULE);

        // Get all the whonetResourceList where expertRule contains UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldNotBeFound("expertRule.contains=" + UPDATED_EXPERT_RULE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByExpertRuleNotContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where expertRule does not contain DEFAULT_EXPERT_RULE
        defaultWhonetResourceShouldNotBeFound("expertRule.doesNotContain=" + DEFAULT_EXPERT_RULE);

        // Get all the whonetResourceList where expertRule does not contain UPDATED_EXPERT_RULE
        defaultWhonetResourceShouldBeFound("expertRule.doesNotContain=" + UPDATED_EXPERT_RULE);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointIsEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint equals to DEFAULT_BREAK_POINT
        defaultWhonetResourceShouldBeFound("breakPoint.equals=" + DEFAULT_BREAK_POINT);

        // Get all the whonetResourceList where breakPoint equals to UPDATED_BREAK_POINT
        defaultWhonetResourceShouldNotBeFound("breakPoint.equals=" + UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint not equals to DEFAULT_BREAK_POINT
        defaultWhonetResourceShouldNotBeFound("breakPoint.notEquals=" + DEFAULT_BREAK_POINT);

        // Get all the whonetResourceList where breakPoint not equals to UPDATED_BREAK_POINT
        defaultWhonetResourceShouldBeFound("breakPoint.notEquals=" + UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointIsInShouldWork() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint in DEFAULT_BREAK_POINT or UPDATED_BREAK_POINT
        defaultWhonetResourceShouldBeFound("breakPoint.in=" + DEFAULT_BREAK_POINT + "," + UPDATED_BREAK_POINT);

        // Get all the whonetResourceList where breakPoint equals to UPDATED_BREAK_POINT
        defaultWhonetResourceShouldNotBeFound("breakPoint.in=" + UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint is not null
        defaultWhonetResourceShouldBeFound("breakPoint.specified=true");

        // Get all the whonetResourceList where breakPoint is null
        defaultWhonetResourceShouldNotBeFound("breakPoint.specified=false");
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint contains DEFAULT_BREAK_POINT
        defaultWhonetResourceShouldBeFound("breakPoint.contains=" + DEFAULT_BREAK_POINT);

        // Get all the whonetResourceList where breakPoint contains UPDATED_BREAK_POINT
        defaultWhonetResourceShouldNotBeFound("breakPoint.contains=" + UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void getAllWhonetResourcesByBreakPointNotContainsSomething() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        // Get all the whonetResourceList where breakPoint does not contain DEFAULT_BREAK_POINT
        defaultWhonetResourceShouldNotBeFound("breakPoint.doesNotContain=" + DEFAULT_BREAK_POINT);

        // Get all the whonetResourceList where breakPoint does not contain UPDATED_BREAK_POINT
        defaultWhonetResourceShouldBeFound("breakPoint.doesNotContain=" + UPDATED_BREAK_POINT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWhonetResourceShouldBeFound(String filter) throws Exception {
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(whonetResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(sameInstant(DEFAULT_UPLOAD_DATE))))
            .andExpect(jsonPath("$.[*].antibiotic").value(hasItem(DEFAULT_ANTIBIOTIC)))
            .andExpect(jsonPath("$.[*].organism").value(hasItem(DEFAULT_ORGANISM)))
            .andExpect(jsonPath("$.[*].intrinsicResistance").value(hasItem(DEFAULT_INTRINSIC_RESISTANCE)))
            .andExpect(jsonPath("$.[*].expertRule").value(hasItem(DEFAULT_EXPERT_RULE)))
            .andExpect(jsonPath("$.[*].breakPoint").value(hasItem(DEFAULT_BREAK_POINT)));

        // Check, that the count call also returns 1
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWhonetResourceShouldNotBeFound(String filter) throws Exception {
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWhonetResourceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWhonetResource() throws Exception {
        // Get the whonetResource
        restWhonetResourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWhonetResource() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();

        // Update the whonetResource
        WhonetResource updatedWhonetResource = whonetResourceRepository.findById(whonetResource.getId()).get();
        // Disconnect from session so that the updates on updatedWhonetResource are not directly saved in db
        em.detach(updatedWhonetResource);
        updatedWhonetResource
            .uploadDate(UPDATED_UPLOAD_DATE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .organism(UPDATED_ORGANISM)
            .intrinsicResistance(UPDATED_INTRINSIC_RESISTANCE)
            .expertRule(UPDATED_EXPERT_RULE)
            .breakPoint(UPDATED_BREAK_POINT);
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(updatedWhonetResource);

        restWhonetResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, whonetResourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
        WhonetResource testWhonetResource = whonetResourceList.get(whonetResourceList.size() - 1);
        assertThat(testWhonetResource.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testWhonetResource.getAntibiotic()).isEqualTo(UPDATED_ANTIBIOTIC);
        assertThat(testWhonetResource.getOrganism()).isEqualTo(UPDATED_ORGANISM);
        assertThat(testWhonetResource.getIntrinsicResistance()).isEqualTo(UPDATED_INTRINSIC_RESISTANCE);
        assertThat(testWhonetResource.getExpertRule()).isEqualTo(UPDATED_EXPERT_RULE);
        assertThat(testWhonetResource.getBreakPoint()).isEqualTo(UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void putNonExistingWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, whonetResourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWhonetResourceWithPatch() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();

        // Update the whonetResource using partial update
        WhonetResource partialUpdatedWhonetResource = new WhonetResource();
        partialUpdatedWhonetResource.setId(whonetResource.getId());

        partialUpdatedWhonetResource
            .uploadDate(UPDATED_UPLOAD_DATE)
            .organism(UPDATED_ORGANISM)
            .intrinsicResistance(UPDATED_INTRINSIC_RESISTANCE)
            .expertRule(UPDATED_EXPERT_RULE);

        restWhonetResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWhonetResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWhonetResource))
            )
            .andExpect(status().isOk());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
        WhonetResource testWhonetResource = whonetResourceList.get(whonetResourceList.size() - 1);
        assertThat(testWhonetResource.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testWhonetResource.getAntibiotic()).isEqualTo(DEFAULT_ANTIBIOTIC);
        assertThat(testWhonetResource.getOrganism()).isEqualTo(UPDATED_ORGANISM);
        assertThat(testWhonetResource.getIntrinsicResistance()).isEqualTo(UPDATED_INTRINSIC_RESISTANCE);
        assertThat(testWhonetResource.getExpertRule()).isEqualTo(UPDATED_EXPERT_RULE);
        assertThat(testWhonetResource.getBreakPoint()).isEqualTo(DEFAULT_BREAK_POINT);
    }

    @Test
    @Transactional
    void fullUpdateWhonetResourceWithPatch() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();

        // Update the whonetResource using partial update
        WhonetResource partialUpdatedWhonetResource = new WhonetResource();
        partialUpdatedWhonetResource.setId(whonetResource.getId());

        partialUpdatedWhonetResource
            .uploadDate(UPDATED_UPLOAD_DATE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .organism(UPDATED_ORGANISM)
            .intrinsicResistance(UPDATED_INTRINSIC_RESISTANCE)
            .expertRule(UPDATED_EXPERT_RULE)
            .breakPoint(UPDATED_BREAK_POINT);

        restWhonetResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWhonetResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWhonetResource))
            )
            .andExpect(status().isOk());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
        WhonetResource testWhonetResource = whonetResourceList.get(whonetResourceList.size() - 1);
        assertThat(testWhonetResource.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testWhonetResource.getAntibiotic()).isEqualTo(UPDATED_ANTIBIOTIC);
        assertThat(testWhonetResource.getOrganism()).isEqualTo(UPDATED_ORGANISM);
        assertThat(testWhonetResource.getIntrinsicResistance()).isEqualTo(UPDATED_INTRINSIC_RESISTANCE);
        assertThat(testWhonetResource.getExpertRule()).isEqualTo(UPDATED_EXPERT_RULE);
        assertThat(testWhonetResource.getBreakPoint()).isEqualTo(UPDATED_BREAK_POINT);
    }

    @Test
    @Transactional
    void patchNonExistingWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, whonetResourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWhonetResource() throws Exception {
        int databaseSizeBeforeUpdate = whonetResourceRepository.findAll().size();
        whonetResource.setId(count.incrementAndGet());

        // Create the WhonetResource
        WhonetResourceDTO whonetResourceDTO = whonetResourceMapper.toDto(whonetResource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWhonetResourceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(whonetResourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WhonetResource in the database
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWhonetResource() throws Exception {
        // Initialize the database
        whonetResourceRepository.saveAndFlush(whonetResource);

        int databaseSizeBeforeDelete = whonetResourceRepository.findAll().size();

        // Delete the whonetResource
        restWhonetResourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, whonetResource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WhonetResource> whonetResourceList = whonetResourceRepository.findAll();
        assertThat(whonetResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
