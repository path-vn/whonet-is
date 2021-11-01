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
import org.path.amr.services.domain.ExpertInterpretationRules;
import org.path.amr.services.repository.ExpertInterpretationRulesRepository;
import org.path.amr.services.service.criteria.ExpertInterpretationRulesCriteria;
import org.path.amr.services.service.dto.ExpertInterpretationRulesDTO;
import org.path.amr.services.service.mapper.ExpertInterpretationRulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExpertInterpretationRulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpertInterpretationRulesResourceIT {

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISM_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISM_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_AFFECTED_ANTIBIOTICS = "AAAAAAAAAA";
    private static final String UPDATED_AFFECTED_ANTIBIOTICS = "BBBBBBBBBB";

    private static final String DEFAULT_ANTIBIOTIC_EXCEPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ANTIBIOTIC_EXCEPTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/expert-interpretation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpertInterpretationRulesRepository expertInterpretationRulesRepository;

    @Autowired
    private ExpertInterpretationRulesMapper expertInterpretationRulesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpertInterpretationRulesMockMvc;

    private ExpertInterpretationRules expertInterpretationRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpertInterpretationRules createEntity(EntityManager em) {
        ExpertInterpretationRules expertInterpretationRules = new ExpertInterpretationRules()
            .ruleCode(DEFAULT_RULE_CODE)
            .description(DEFAULT_DESCRIPTION)
            .organismCode(DEFAULT_ORGANISM_CODE)
            .organismCodeType(DEFAULT_ORGANISM_CODE_TYPE)
            .ruleCriteria(DEFAULT_RULE_CRITERIA)
            .affectedAntibiotics(DEFAULT_AFFECTED_ANTIBIOTICS)
            .antibioticExceptions(DEFAULT_ANTIBIOTIC_EXCEPTIONS)
            .result(DEFAULT_RESULT);
        return expertInterpretationRules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpertInterpretationRules createUpdatedEntity(EntityManager em) {
        ExpertInterpretationRules expertInterpretationRules = new ExpertInterpretationRules()
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .ruleCriteria(UPDATED_RULE_CRITERIA)
            .affectedAntibiotics(UPDATED_AFFECTED_ANTIBIOTICS)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .result(UPDATED_RESULT);
        return expertInterpretationRules;
    }

    @BeforeEach
    public void initTest() {
        expertInterpretationRules = createEntity(em);
    }

    @Test
    @Transactional
    void createExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeCreate = expertInterpretationRulesRepository.findAll().size();
        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);
        restExpertInterpretationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeCreate + 1);
        ExpertInterpretationRules testExpertInterpretationRules = expertInterpretationRulesList.get(
            expertInterpretationRulesList.size() - 1
        );
        assertThat(testExpertInterpretationRules.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testExpertInterpretationRules.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExpertInterpretationRules.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testExpertInterpretationRules.getOrganismCodeType()).isEqualTo(DEFAULT_ORGANISM_CODE_TYPE);
        assertThat(testExpertInterpretationRules.getRuleCriteria()).isEqualTo(DEFAULT_RULE_CRITERIA);
        assertThat(testExpertInterpretationRules.getAffectedAntibiotics()).isEqualTo(DEFAULT_AFFECTED_ANTIBIOTICS);
        assertThat(testExpertInterpretationRules.getAntibioticExceptions()).isEqualTo(DEFAULT_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testExpertInterpretationRules.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    void createExpertInterpretationRulesWithExistingId() throws Exception {
        // Create the ExpertInterpretationRules with an existing ID
        expertInterpretationRules.setId(1L);
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        int databaseSizeBeforeCreate = expertInterpretationRulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpertInterpretationRulesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRules() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expertInterpretationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].ruleCriteria").value(hasItem(DEFAULT_RULE_CRITERIA)))
            .andExpect(jsonPath("$.[*].affectedAntibiotics").value(hasItem(DEFAULT_AFFECTED_ANTIBIOTICS)))
            .andExpect(jsonPath("$.[*].antibioticExceptions").value(hasItem(DEFAULT_ANTIBIOTIC_EXCEPTIONS)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    @Transactional
    void getExpertInterpretationRules() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get the expertInterpretationRules
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL_ID, expertInterpretationRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expertInterpretationRules.getId().intValue()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.organismCode").value(DEFAULT_ORGANISM_CODE))
            .andExpect(jsonPath("$.organismCodeType").value(DEFAULT_ORGANISM_CODE_TYPE))
            .andExpect(jsonPath("$.ruleCriteria").value(DEFAULT_RULE_CRITERIA))
            .andExpect(jsonPath("$.affectedAntibiotics").value(DEFAULT_AFFECTED_ANTIBIOTICS))
            .andExpect(jsonPath("$.antibioticExceptions").value(DEFAULT_ANTIBIOTIC_EXCEPTIONS))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    @Transactional
    void getExpertInterpretationRulesByIdFiltering() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        Long id = expertInterpretationRules.getId();

        defaultExpertInterpretationRulesShouldBeFound("id.equals=" + id);
        defaultExpertInterpretationRulesShouldNotBeFound("id.notEquals=" + id);

        defaultExpertInterpretationRulesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpertInterpretationRulesShouldNotBeFound("id.greaterThan=" + id);

        defaultExpertInterpretationRulesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpertInterpretationRulesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode equals to DEFAULT_RULE_CODE
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.equals=" + DEFAULT_RULE_CODE);

        // Get all the expertInterpretationRulesList where ruleCode equals to UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.equals=" + UPDATED_RULE_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode not equals to DEFAULT_RULE_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.notEquals=" + DEFAULT_RULE_CODE);

        // Get all the expertInterpretationRulesList where ruleCode not equals to UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.notEquals=" + UPDATED_RULE_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode in DEFAULT_RULE_CODE or UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.in=" + DEFAULT_RULE_CODE + "," + UPDATED_RULE_CODE);

        // Get all the expertInterpretationRulesList where ruleCode equals to UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.in=" + UPDATED_RULE_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode is not null
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.specified=true");

        // Get all the expertInterpretationRulesList where ruleCode is null
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode contains DEFAULT_RULE_CODE
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.contains=" + DEFAULT_RULE_CODE);

        // Get all the expertInterpretationRulesList where ruleCode contains UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.contains=" + UPDATED_RULE_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCodeNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCode does not contain DEFAULT_RULE_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCode.doesNotContain=" + DEFAULT_RULE_CODE);

        // Get all the expertInterpretationRulesList where ruleCode does not contain UPDATED_RULE_CODE
        defaultExpertInterpretationRulesShouldBeFound("ruleCode.doesNotContain=" + UPDATED_RULE_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description equals to DEFAULT_DESCRIPTION
        defaultExpertInterpretationRulesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the expertInterpretationRulesList where description equals to UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description not equals to DEFAULT_DESCRIPTION
        defaultExpertInterpretationRulesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the expertInterpretationRulesList where description not equals to UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the expertInterpretationRulesList where description equals to UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description is not null
        defaultExpertInterpretationRulesShouldBeFound("description.specified=true");

        // Get all the expertInterpretationRulesList where description is null
        defaultExpertInterpretationRulesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description contains DEFAULT_DESCRIPTION
        defaultExpertInterpretationRulesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the expertInterpretationRulesList where description contains UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where description does not contain DEFAULT_DESCRIPTION
        defaultExpertInterpretationRulesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the expertInterpretationRulesList where description does not contain UPDATED_DESCRIPTION
        defaultExpertInterpretationRulesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode equals to DEFAULT_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldBeFound("organismCode.equals=" + DEFAULT_ORGANISM_CODE);

        // Get all the expertInterpretationRulesList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.equals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode not equals to DEFAULT_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.notEquals=" + DEFAULT_ORGANISM_CODE);

        // Get all the expertInterpretationRulesList where organismCode not equals to UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldBeFound("organismCode.notEquals=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode in DEFAULT_ORGANISM_CODE or UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldBeFound("organismCode.in=" + DEFAULT_ORGANISM_CODE + "," + UPDATED_ORGANISM_CODE);

        // Get all the expertInterpretationRulesList where organismCode equals to UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.in=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode is not null
        defaultExpertInterpretationRulesShouldBeFound("organismCode.specified=true");

        // Get all the expertInterpretationRulesList where organismCode is null
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode contains DEFAULT_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldBeFound("organismCode.contains=" + DEFAULT_ORGANISM_CODE);

        // Get all the expertInterpretationRulesList where organismCode contains UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.contains=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCode does not contain DEFAULT_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCode.doesNotContain=" + DEFAULT_ORGANISM_CODE);

        // Get all the expertInterpretationRulesList where organismCode does not contain UPDATED_ORGANISM_CODE
        defaultExpertInterpretationRulesShouldBeFound("organismCode.doesNotContain=" + UPDATED_ORGANISM_CODE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldBeFound("organismCodeType.equals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the expertInterpretationRulesList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.equals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType not equals to DEFAULT_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.notEquals=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the expertInterpretationRulesList where organismCodeType not equals to UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldBeFound("organismCodeType.notEquals=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType in DEFAULT_ORGANISM_CODE_TYPE or UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldBeFound(
            "organismCodeType.in=" + DEFAULT_ORGANISM_CODE_TYPE + "," + UPDATED_ORGANISM_CODE_TYPE
        );

        // Get all the expertInterpretationRulesList where organismCodeType equals to UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.in=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType is not null
        defaultExpertInterpretationRulesShouldBeFound("organismCodeType.specified=true");

        // Get all the expertInterpretationRulesList where organismCodeType is null
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType contains DEFAULT_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldBeFound("organismCodeType.contains=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the expertInterpretationRulesList where organismCodeType contains UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.contains=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByOrganismCodeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where organismCodeType does not contain DEFAULT_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldNotBeFound("organismCodeType.doesNotContain=" + DEFAULT_ORGANISM_CODE_TYPE);

        // Get all the expertInterpretationRulesList where organismCodeType does not contain UPDATED_ORGANISM_CODE_TYPE
        defaultExpertInterpretationRulesShouldBeFound("organismCodeType.doesNotContain=" + UPDATED_ORGANISM_CODE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria equals to DEFAULT_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.equals=" + DEFAULT_RULE_CRITERIA);

        // Get all the expertInterpretationRulesList where ruleCriteria equals to UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.equals=" + UPDATED_RULE_CRITERIA);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria not equals to DEFAULT_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.notEquals=" + DEFAULT_RULE_CRITERIA);

        // Get all the expertInterpretationRulesList where ruleCriteria not equals to UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.notEquals=" + UPDATED_RULE_CRITERIA);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria in DEFAULT_RULE_CRITERIA or UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.in=" + DEFAULT_RULE_CRITERIA + "," + UPDATED_RULE_CRITERIA);

        // Get all the expertInterpretationRulesList where ruleCriteria equals to UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.in=" + UPDATED_RULE_CRITERIA);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria is not null
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.specified=true");

        // Get all the expertInterpretationRulesList where ruleCriteria is null
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria contains DEFAULT_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.contains=" + DEFAULT_RULE_CRITERIA);

        // Get all the expertInterpretationRulesList where ruleCriteria contains UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.contains=" + UPDATED_RULE_CRITERIA);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByRuleCriteriaNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where ruleCriteria does not contain DEFAULT_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldNotBeFound("ruleCriteria.doesNotContain=" + DEFAULT_RULE_CRITERIA);

        // Get all the expertInterpretationRulesList where ruleCriteria does not contain UPDATED_RULE_CRITERIA
        defaultExpertInterpretationRulesShouldBeFound("ruleCriteria.doesNotContain=" + UPDATED_RULE_CRITERIA);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics equals to DEFAULT_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldBeFound("affectedAntibiotics.equals=" + DEFAULT_AFFECTED_ANTIBIOTICS);

        // Get all the expertInterpretationRulesList where affectedAntibiotics equals to UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.equals=" + UPDATED_AFFECTED_ANTIBIOTICS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics not equals to DEFAULT_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.notEquals=" + DEFAULT_AFFECTED_ANTIBIOTICS);

        // Get all the expertInterpretationRulesList where affectedAntibiotics not equals to UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldBeFound("affectedAntibiotics.notEquals=" + UPDATED_AFFECTED_ANTIBIOTICS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics in DEFAULT_AFFECTED_ANTIBIOTICS or UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldBeFound(
            "affectedAntibiotics.in=" + DEFAULT_AFFECTED_ANTIBIOTICS + "," + UPDATED_AFFECTED_ANTIBIOTICS
        );

        // Get all the expertInterpretationRulesList where affectedAntibiotics equals to UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.in=" + UPDATED_AFFECTED_ANTIBIOTICS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics is not null
        defaultExpertInterpretationRulesShouldBeFound("affectedAntibiotics.specified=true");

        // Get all the expertInterpretationRulesList where affectedAntibiotics is null
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics contains DEFAULT_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldBeFound("affectedAntibiotics.contains=" + DEFAULT_AFFECTED_ANTIBIOTICS);

        // Get all the expertInterpretationRulesList where affectedAntibiotics contains UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.contains=" + UPDATED_AFFECTED_ANTIBIOTICS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAffectedAntibioticsNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where affectedAntibiotics does not contain DEFAULT_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldNotBeFound("affectedAntibiotics.doesNotContain=" + DEFAULT_AFFECTED_ANTIBIOTICS);

        // Get all the expertInterpretationRulesList where affectedAntibiotics does not contain UPDATED_AFFECTED_ANTIBIOTICS
        defaultExpertInterpretationRulesShouldBeFound("affectedAntibiotics.doesNotContain=" + UPDATED_AFFECTED_ANTIBIOTICS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions equals to DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldBeFound("antibioticExceptions.equals=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the expertInterpretationRulesList where antibioticExceptions equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.equals=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions not equals to DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.notEquals=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the expertInterpretationRulesList where antibioticExceptions not equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldBeFound("antibioticExceptions.notEquals=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions in DEFAULT_ANTIBIOTIC_EXCEPTIONS or UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldBeFound(
            "antibioticExceptions.in=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS + "," + UPDATED_ANTIBIOTIC_EXCEPTIONS
        );

        // Get all the expertInterpretationRulesList where antibioticExceptions equals to UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.in=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions is not null
        defaultExpertInterpretationRulesShouldBeFound("antibioticExceptions.specified=true");

        // Get all the expertInterpretationRulesList where antibioticExceptions is null
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions contains DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldBeFound("antibioticExceptions.contains=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the expertInterpretationRulesList where antibioticExceptions contains UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.contains=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByAntibioticExceptionsNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where antibioticExceptions does not contain DEFAULT_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldNotBeFound("antibioticExceptions.doesNotContain=" + DEFAULT_ANTIBIOTIC_EXCEPTIONS);

        // Get all the expertInterpretationRulesList where antibioticExceptions does not contain UPDATED_ANTIBIOTIC_EXCEPTIONS
        defaultExpertInterpretationRulesShouldBeFound("antibioticExceptions.doesNotContain=" + UPDATED_ANTIBIOTIC_EXCEPTIONS);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result equals to DEFAULT_RESULT
        defaultExpertInterpretationRulesShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the expertInterpretationRulesList where result equals to UPDATED_RESULT
        defaultExpertInterpretationRulesShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result not equals to DEFAULT_RESULT
        defaultExpertInterpretationRulesShouldNotBeFound("result.notEquals=" + DEFAULT_RESULT);

        // Get all the expertInterpretationRulesList where result not equals to UPDATED_RESULT
        defaultExpertInterpretationRulesShouldBeFound("result.notEquals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultIsInShouldWork() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultExpertInterpretationRulesShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the expertInterpretationRulesList where result equals to UPDATED_RESULT
        defaultExpertInterpretationRulesShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result is not null
        defaultExpertInterpretationRulesShouldBeFound("result.specified=true");

        // Get all the expertInterpretationRulesList where result is null
        defaultExpertInterpretationRulesShouldNotBeFound("result.specified=false");
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result contains DEFAULT_RESULT
        defaultExpertInterpretationRulesShouldBeFound("result.contains=" + DEFAULT_RESULT);

        // Get all the expertInterpretationRulesList where result contains UPDATED_RESULT
        defaultExpertInterpretationRulesShouldNotBeFound("result.contains=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllExpertInterpretationRulesByResultNotContainsSomething() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        // Get all the expertInterpretationRulesList where result does not contain DEFAULT_RESULT
        defaultExpertInterpretationRulesShouldNotBeFound("result.doesNotContain=" + DEFAULT_RESULT);

        // Get all the expertInterpretationRulesList where result does not contain UPDATED_RESULT
        defaultExpertInterpretationRulesShouldBeFound("result.doesNotContain=" + UPDATED_RESULT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpertInterpretationRulesShouldBeFound(String filter) throws Exception {
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expertInterpretationRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].organismCode").value(hasItem(DEFAULT_ORGANISM_CODE)))
            .andExpect(jsonPath("$.[*].organismCodeType").value(hasItem(DEFAULT_ORGANISM_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].ruleCriteria").value(hasItem(DEFAULT_RULE_CRITERIA)))
            .andExpect(jsonPath("$.[*].affectedAntibiotics").value(hasItem(DEFAULT_AFFECTED_ANTIBIOTICS)))
            .andExpect(jsonPath("$.[*].antibioticExceptions").value(hasItem(DEFAULT_ANTIBIOTIC_EXCEPTIONS)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));

        // Check, that the count call also returns 1
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpertInterpretationRulesShouldNotBeFound(String filter) throws Exception {
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpertInterpretationRulesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExpertInterpretationRules() throws Exception {
        // Get the expertInterpretationRules
        restExpertInterpretationRulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExpertInterpretationRules() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();

        // Update the expertInterpretationRules
        ExpertInterpretationRules updatedExpertInterpretationRules = expertInterpretationRulesRepository
            .findById(expertInterpretationRules.getId())
            .get();
        // Disconnect from session so that the updates on updatedExpertInterpretationRules are not directly saved in db
        em.detach(updatedExpertInterpretationRules);
        updatedExpertInterpretationRules
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .ruleCriteria(UPDATED_RULE_CRITERIA)
            .affectedAntibiotics(UPDATED_AFFECTED_ANTIBIOTICS)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .result(UPDATED_RESULT);
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(updatedExpertInterpretationRules);

        restExpertInterpretationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expertInterpretationRulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
        ExpertInterpretationRules testExpertInterpretationRules = expertInterpretationRulesList.get(
            expertInterpretationRulesList.size() - 1
        );
        assertThat(testExpertInterpretationRules.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testExpertInterpretationRules.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpertInterpretationRules.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testExpertInterpretationRules.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testExpertInterpretationRules.getRuleCriteria()).isEqualTo(UPDATED_RULE_CRITERIA);
        assertThat(testExpertInterpretationRules.getAffectedAntibiotics()).isEqualTo(UPDATED_AFFECTED_ANTIBIOTICS);
        assertThat(testExpertInterpretationRules.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testExpertInterpretationRules.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expertInterpretationRulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpertInterpretationRulesWithPatch() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();

        // Update the expertInterpretationRules using partial update
        ExpertInterpretationRules partialUpdatedExpertInterpretationRules = new ExpertInterpretationRules();
        partialUpdatedExpertInterpretationRules.setId(expertInterpretationRules.getId());

        partialUpdatedExpertInterpretationRules
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .ruleCriteria(UPDATED_RULE_CRITERIA)
            .affectedAntibiotics(UPDATED_AFFECTED_ANTIBIOTICS)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .result(UPDATED_RESULT);

        restExpertInterpretationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpertInterpretationRules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpertInterpretationRules))
            )
            .andExpect(status().isOk());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
        ExpertInterpretationRules testExpertInterpretationRules = expertInterpretationRulesList.get(
            expertInterpretationRulesList.size() - 1
        );
        assertThat(testExpertInterpretationRules.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testExpertInterpretationRules.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpertInterpretationRules.getOrganismCode()).isEqualTo(DEFAULT_ORGANISM_CODE);
        assertThat(testExpertInterpretationRules.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testExpertInterpretationRules.getRuleCriteria()).isEqualTo(UPDATED_RULE_CRITERIA);
        assertThat(testExpertInterpretationRules.getAffectedAntibiotics()).isEqualTo(UPDATED_AFFECTED_ANTIBIOTICS);
        assertThat(testExpertInterpretationRules.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testExpertInterpretationRules.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateExpertInterpretationRulesWithPatch() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();

        // Update the expertInterpretationRules using partial update
        ExpertInterpretationRules partialUpdatedExpertInterpretationRules = new ExpertInterpretationRules();
        partialUpdatedExpertInterpretationRules.setId(expertInterpretationRules.getId());

        partialUpdatedExpertInterpretationRules
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .organismCode(UPDATED_ORGANISM_CODE)
            .organismCodeType(UPDATED_ORGANISM_CODE_TYPE)
            .ruleCriteria(UPDATED_RULE_CRITERIA)
            .affectedAntibiotics(UPDATED_AFFECTED_ANTIBIOTICS)
            .antibioticExceptions(UPDATED_ANTIBIOTIC_EXCEPTIONS)
            .result(UPDATED_RESULT);

        restExpertInterpretationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpertInterpretationRules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpertInterpretationRules))
            )
            .andExpect(status().isOk());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
        ExpertInterpretationRules testExpertInterpretationRules = expertInterpretationRulesList.get(
            expertInterpretationRulesList.size() - 1
        );
        assertThat(testExpertInterpretationRules.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testExpertInterpretationRules.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpertInterpretationRules.getOrganismCode()).isEqualTo(UPDATED_ORGANISM_CODE);
        assertThat(testExpertInterpretationRules.getOrganismCodeType()).isEqualTo(UPDATED_ORGANISM_CODE_TYPE);
        assertThat(testExpertInterpretationRules.getRuleCriteria()).isEqualTo(UPDATED_RULE_CRITERIA);
        assertThat(testExpertInterpretationRules.getAffectedAntibiotics()).isEqualTo(UPDATED_AFFECTED_ANTIBIOTICS);
        assertThat(testExpertInterpretationRules.getAntibioticExceptions()).isEqualTo(UPDATED_ANTIBIOTIC_EXCEPTIONS);
        assertThat(testExpertInterpretationRules.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expertInterpretationRulesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpertInterpretationRules() throws Exception {
        int databaseSizeBeforeUpdate = expertInterpretationRulesRepository.findAll().size();
        expertInterpretationRules.setId(count.incrementAndGet());

        // Create the ExpertInterpretationRules
        ExpertInterpretationRulesDTO expertInterpretationRulesDTO = expertInterpretationRulesMapper.toDto(expertInterpretationRules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpertInterpretationRulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expertInterpretationRulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpertInterpretationRules in the database
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpertInterpretationRules() throws Exception {
        // Initialize the database
        expertInterpretationRulesRepository.saveAndFlush(expertInterpretationRules);

        int databaseSizeBeforeDelete = expertInterpretationRulesRepository.findAll().size();

        // Delete the expertInterpretationRules
        restExpertInterpretationRulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, expertInterpretationRules.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpertInterpretationRules> expertInterpretationRulesList = expertInterpretationRulesRepository.findAll();
        assertThat(expertInterpretationRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
