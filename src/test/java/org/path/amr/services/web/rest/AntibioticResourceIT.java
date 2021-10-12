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
import org.path.amr.services.domain.Antibiotic;
import org.path.amr.services.repository.AntibioticRepository;
import org.path.amr.services.service.criteria.AntibioticCriteria;
import org.path.amr.services.service.dto.AntibioticDTO;
import org.path.amr.services.service.mapper.AntibioticMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AntibioticResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AntibioticResourceIT {

    private static final String DEFAULT_WHONET_ABX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WHONET_ABX_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WHO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WHO_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_JAC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_JAC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EUCAST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EUCAST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_USER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ANTIBIOTIC = "AAAAAAAAAA";
    private static final String UPDATED_ANTIBIOTIC = "BBBBBBBBBB";

    private static final String DEFAULT_GUIDELINES = "AAAAAAAAAA";
    private static final String UPDATED_GUIDELINES = "BBBBBBBBBB";

    private static final String DEFAULT_CLSI = "AAAAAAAAAA";
    private static final String UPDATED_CLSI = "BBBBBBBBBB";

    private static final String DEFAULT_EUCAST = "AAAAAAAAAA";
    private static final String UPDATED_EUCAST = "BBBBBBBBBB";

    private static final String DEFAULT_SFM = "AAAAAAAAAA";
    private static final String UPDATED_SFM = "BBBBBBBBBB";

    private static final String DEFAULT_SRGA = "AAAAAAAAAA";
    private static final String UPDATED_SRGA = "BBBBBBBBBB";

    private static final String DEFAULT_BSAC = "AAAAAAAAAA";
    private static final String UPDATED_BSAC = "BBBBBBBBBB";

    private static final String DEFAULT_DIN = "AAAAAAAAAA";
    private static final String UPDATED_DIN = "BBBBBBBBBB";

    private static final String DEFAULT_NEO = "AAAAAAAAAA";
    private static final String UPDATED_NEO = "BBBBBBBBBB";

    private static final String DEFAULT_AFA = "AAAAAAAAAA";
    private static final String UPDATED_AFA = "BBBBBBBBBB";

    private static final String DEFAULT_ABX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ABX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POTENCY = "AAAAAAAAAA";
    private static final String UPDATED_POTENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ATC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ATC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROF_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_PROF_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_CIA_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CIA_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_CLSI_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_CLSI_ORDER = "BBBBBBBBBB";

    private static final String DEFAULT_EUCAST_ORDER = "AAAAAAAAAA";
    private static final String UPDATED_EUCAST_ORDER = "BBBBBBBBBB";

    private static final String DEFAULT_HUMAN = "AAAAAAAAAA";
    private static final String UPDATED_HUMAN = "BBBBBBBBBB";

    private static final String DEFAULT_VETERINARY = "AAAAAAAAAA";
    private static final String UPDATED_VETERINARY = "BBBBBBBBBB";

    private static final String DEFAULT_ANIMAL_GP = "AAAAAAAAAA";
    private static final String UPDATED_ANIMAL_GP = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCCOMP = "AAAAAAAAAA";
    private static final String UPDATED_LOINCCOMP = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCGEN = "AAAAAAAAAA";
    private static final String UPDATED_LOINCGEN = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCDISK = "AAAAAAAAAA";
    private static final String UPDATED_LOINCDISK = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCMIC = "AAAAAAAAAA";
    private static final String UPDATED_LOINCMIC = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCETEST = "AAAAAAAAAA";
    private static final String UPDATED_LOINCETEST = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCSLOW = "AAAAAAAAAA";
    private static final String UPDATED_LOINCSLOW = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCAFB = "AAAAAAAAAA";
    private static final String UPDATED_LOINCAFB = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCSBT = "AAAAAAAAAA";
    private static final String UPDATED_LOINCSBT = "BBBBBBBBBB";

    private static final String DEFAULT_LOINCMLC = "AAAAAAAAAA";
    private static final String UPDATED_LOINCMLC = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_ENTERED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_ENTERED = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_DATE_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/antibiotics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AntibioticRepository antibioticRepository;

    @Autowired
    private AntibioticMapper antibioticMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntibioticMockMvc;

    private Antibiotic antibiotic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antibiotic createEntity(EntityManager em) {
        Antibiotic antibiotic = new Antibiotic()
            .whonetAbxCode(DEFAULT_WHONET_ABX_CODE)
            .whoCode(DEFAULT_WHO_CODE)
            .dinCode(DEFAULT_DIN_CODE)
            .jacCode(DEFAULT_JAC_CODE)
            .eucastCode(DEFAULT_EUCAST_CODE)
            .userCode(DEFAULT_USER_CODE)
            .antibiotic(DEFAULT_ANTIBIOTIC)
            .guidelines(DEFAULT_GUIDELINES)
            .clsi(DEFAULT_CLSI)
            .eucast(DEFAULT_EUCAST)
            .sfm(DEFAULT_SFM)
            .srga(DEFAULT_SRGA)
            .bsac(DEFAULT_BSAC)
            .din(DEFAULT_DIN)
            .neo(DEFAULT_NEO)
            .afa(DEFAULT_AFA)
            .abxNumber(DEFAULT_ABX_NUMBER)
            .potency(DEFAULT_POTENCY)
            .atcCode(DEFAULT_ATC_CODE)
            .profClass(DEFAULT_PROF_CLASS)
            .ciaCategory(DEFAULT_CIA_CATEGORY)
            .clsiOrder(DEFAULT_CLSI_ORDER)
            .eucastOrder(DEFAULT_EUCAST_ORDER)
            .human(DEFAULT_HUMAN)
            .veterinary(DEFAULT_VETERINARY)
            .animalGp(DEFAULT_ANIMAL_GP)
            .loinccomp(DEFAULT_LOINCCOMP)
            .loincgen(DEFAULT_LOINCGEN)
            .loincdisk(DEFAULT_LOINCDISK)
            .loincmic(DEFAULT_LOINCMIC)
            .loincetest(DEFAULT_LOINCETEST)
            .loincslow(DEFAULT_LOINCSLOW)
            .loincafb(DEFAULT_LOINCAFB)
            .loincsbt(DEFAULT_LOINCSBT)
            .loincmlc(DEFAULT_LOINCMLC)
            .dateEntered(DEFAULT_DATE_ENTERED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .comments(DEFAULT_COMMENTS);
        return antibiotic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antibiotic createUpdatedEntity(EntityManager em) {
        Antibiotic antibiotic = new Antibiotic()
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whoCode(UPDATED_WHO_CODE)
            .dinCode(UPDATED_DIN_CODE)
            .jacCode(UPDATED_JAC_CODE)
            .eucastCode(UPDATED_EUCAST_CODE)
            .userCode(UPDATED_USER_CODE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .guidelines(UPDATED_GUIDELINES)
            .clsi(UPDATED_CLSI)
            .eucast(UPDATED_EUCAST)
            .sfm(UPDATED_SFM)
            .srga(UPDATED_SRGA)
            .bsac(UPDATED_BSAC)
            .din(UPDATED_DIN)
            .neo(UPDATED_NEO)
            .afa(UPDATED_AFA)
            .abxNumber(UPDATED_ABX_NUMBER)
            .potency(UPDATED_POTENCY)
            .atcCode(UPDATED_ATC_CODE)
            .profClass(UPDATED_PROF_CLASS)
            .ciaCategory(UPDATED_CIA_CATEGORY)
            .clsiOrder(UPDATED_CLSI_ORDER)
            .eucastOrder(UPDATED_EUCAST_ORDER)
            .human(UPDATED_HUMAN)
            .veterinary(UPDATED_VETERINARY)
            .animalGp(UPDATED_ANIMAL_GP)
            .loinccomp(UPDATED_LOINCCOMP)
            .loincgen(UPDATED_LOINCGEN)
            .loincdisk(UPDATED_LOINCDISK)
            .loincmic(UPDATED_LOINCMIC)
            .loincetest(UPDATED_LOINCETEST)
            .loincslow(UPDATED_LOINCSLOW)
            .loincafb(UPDATED_LOINCAFB)
            .loincsbt(UPDATED_LOINCSBT)
            .loincmlc(UPDATED_LOINCMLC)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        return antibiotic;
    }

    @BeforeEach
    public void initTest() {
        antibiotic = createEntity(em);
    }

    @Test
    @Transactional
    void createAntibiotic() throws Exception {
        int databaseSizeBeforeCreate = antibioticRepository.findAll().size();
        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);
        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticDTO)))
            .andExpect(status().isCreated());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeCreate + 1);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getWhonetAbxCode()).isEqualTo(DEFAULT_WHONET_ABX_CODE);
        assertThat(testAntibiotic.getWhoCode()).isEqualTo(DEFAULT_WHO_CODE);
        assertThat(testAntibiotic.getDinCode()).isEqualTo(DEFAULT_DIN_CODE);
        assertThat(testAntibiotic.getJacCode()).isEqualTo(DEFAULT_JAC_CODE);
        assertThat(testAntibiotic.getEucastCode()).isEqualTo(DEFAULT_EUCAST_CODE);
        assertThat(testAntibiotic.getUserCode()).isEqualTo(DEFAULT_USER_CODE);
        assertThat(testAntibiotic.getAntibiotic()).isEqualTo(DEFAULT_ANTIBIOTIC);
        assertThat(testAntibiotic.getGuidelines()).isEqualTo(DEFAULT_GUIDELINES);
        assertThat(testAntibiotic.getClsi()).isEqualTo(DEFAULT_CLSI);
        assertThat(testAntibiotic.getEucast()).isEqualTo(DEFAULT_EUCAST);
        assertThat(testAntibiotic.getSfm()).isEqualTo(DEFAULT_SFM);
        assertThat(testAntibiotic.getSrga()).isEqualTo(DEFAULT_SRGA);
        assertThat(testAntibiotic.getBsac()).isEqualTo(DEFAULT_BSAC);
        assertThat(testAntibiotic.getDin()).isEqualTo(DEFAULT_DIN);
        assertThat(testAntibiotic.getNeo()).isEqualTo(DEFAULT_NEO);
        assertThat(testAntibiotic.getAfa()).isEqualTo(DEFAULT_AFA);
        assertThat(testAntibiotic.getAbxNumber()).isEqualTo(DEFAULT_ABX_NUMBER);
        assertThat(testAntibiotic.getPotency()).isEqualTo(DEFAULT_POTENCY);
        assertThat(testAntibiotic.getAtcCode()).isEqualTo(DEFAULT_ATC_CODE);
        assertThat(testAntibiotic.getProfClass()).isEqualTo(DEFAULT_PROF_CLASS);
        assertThat(testAntibiotic.getCiaCategory()).isEqualTo(DEFAULT_CIA_CATEGORY);
        assertThat(testAntibiotic.getClsiOrder()).isEqualTo(DEFAULT_CLSI_ORDER);
        assertThat(testAntibiotic.getEucastOrder()).isEqualTo(DEFAULT_EUCAST_ORDER);
        assertThat(testAntibiotic.getHuman()).isEqualTo(DEFAULT_HUMAN);
        assertThat(testAntibiotic.getVeterinary()).isEqualTo(DEFAULT_VETERINARY);
        assertThat(testAntibiotic.getAnimalGp()).isEqualTo(DEFAULT_ANIMAL_GP);
        assertThat(testAntibiotic.getLoinccomp()).isEqualTo(DEFAULT_LOINCCOMP);
        assertThat(testAntibiotic.getLoincgen()).isEqualTo(DEFAULT_LOINCGEN);
        assertThat(testAntibiotic.getLoincdisk()).isEqualTo(DEFAULT_LOINCDISK);
        assertThat(testAntibiotic.getLoincmic()).isEqualTo(DEFAULT_LOINCMIC);
        assertThat(testAntibiotic.getLoincetest()).isEqualTo(DEFAULT_LOINCETEST);
        assertThat(testAntibiotic.getLoincslow()).isEqualTo(DEFAULT_LOINCSLOW);
        assertThat(testAntibiotic.getLoincafb()).isEqualTo(DEFAULT_LOINCAFB);
        assertThat(testAntibiotic.getLoincsbt()).isEqualTo(DEFAULT_LOINCSBT);
        assertThat(testAntibiotic.getLoincmlc()).isEqualTo(DEFAULT_LOINCMLC);
        assertThat(testAntibiotic.getDateEntered()).isEqualTo(DEFAULT_DATE_ENTERED);
        assertThat(testAntibiotic.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testAntibiotic.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createAntibioticWithExistingId() throws Exception {
        // Create the Antibiotic with an existing ID
        antibiotic.setId(1L);
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        int databaseSizeBeforeCreate = antibioticRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntibioticMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAntibiotics() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antibiotic.getId().intValue())))
            .andExpect(jsonPath("$.[*].whonetAbxCode").value(hasItem(DEFAULT_WHONET_ABX_CODE)))
            .andExpect(jsonPath("$.[*].whoCode").value(hasItem(DEFAULT_WHO_CODE)))
            .andExpect(jsonPath("$.[*].dinCode").value(hasItem(DEFAULT_DIN_CODE)))
            .andExpect(jsonPath("$.[*].jacCode").value(hasItem(DEFAULT_JAC_CODE)))
            .andExpect(jsonPath("$.[*].eucastCode").value(hasItem(DEFAULT_EUCAST_CODE)))
            .andExpect(jsonPath("$.[*].userCode").value(hasItem(DEFAULT_USER_CODE)))
            .andExpect(jsonPath("$.[*].antibiotic").value(hasItem(DEFAULT_ANTIBIOTIC)))
            .andExpect(jsonPath("$.[*].guidelines").value(hasItem(DEFAULT_GUIDELINES)))
            .andExpect(jsonPath("$.[*].clsi").value(hasItem(DEFAULT_CLSI)))
            .andExpect(jsonPath("$.[*].eucast").value(hasItem(DEFAULT_EUCAST)))
            .andExpect(jsonPath("$.[*].sfm").value(hasItem(DEFAULT_SFM)))
            .andExpect(jsonPath("$.[*].srga").value(hasItem(DEFAULT_SRGA)))
            .andExpect(jsonPath("$.[*].bsac").value(hasItem(DEFAULT_BSAC)))
            .andExpect(jsonPath("$.[*].din").value(hasItem(DEFAULT_DIN)))
            .andExpect(jsonPath("$.[*].neo").value(hasItem(DEFAULT_NEO)))
            .andExpect(jsonPath("$.[*].afa").value(hasItem(DEFAULT_AFA)))
            .andExpect(jsonPath("$.[*].abxNumber").value(hasItem(DEFAULT_ABX_NUMBER)))
            .andExpect(jsonPath("$.[*].potency").value(hasItem(DEFAULT_POTENCY)))
            .andExpect(jsonPath("$.[*].atcCode").value(hasItem(DEFAULT_ATC_CODE)))
            .andExpect(jsonPath("$.[*].profClass").value(hasItem(DEFAULT_PROF_CLASS)))
            .andExpect(jsonPath("$.[*].ciaCategory").value(hasItem(DEFAULT_CIA_CATEGORY)))
            .andExpect(jsonPath("$.[*].clsiOrder").value(hasItem(DEFAULT_CLSI_ORDER)))
            .andExpect(jsonPath("$.[*].eucastOrder").value(hasItem(DEFAULT_EUCAST_ORDER)))
            .andExpect(jsonPath("$.[*].human").value(hasItem(DEFAULT_HUMAN)))
            .andExpect(jsonPath("$.[*].veterinary").value(hasItem(DEFAULT_VETERINARY)))
            .andExpect(jsonPath("$.[*].animalGp").value(hasItem(DEFAULT_ANIMAL_GP)))
            .andExpect(jsonPath("$.[*].loinccomp").value(hasItem(DEFAULT_LOINCCOMP)))
            .andExpect(jsonPath("$.[*].loincgen").value(hasItem(DEFAULT_LOINCGEN)))
            .andExpect(jsonPath("$.[*].loincdisk").value(hasItem(DEFAULT_LOINCDISK)))
            .andExpect(jsonPath("$.[*].loincmic").value(hasItem(DEFAULT_LOINCMIC)))
            .andExpect(jsonPath("$.[*].loincetest").value(hasItem(DEFAULT_LOINCETEST)))
            .andExpect(jsonPath("$.[*].loincslow").value(hasItem(DEFAULT_LOINCSLOW)))
            .andExpect(jsonPath("$.[*].loincafb").value(hasItem(DEFAULT_LOINCAFB)))
            .andExpect(jsonPath("$.[*].loincsbt").value(hasItem(DEFAULT_LOINCSBT)))
            .andExpect(jsonPath("$.[*].loincmlc").value(hasItem(DEFAULT_LOINCMLC)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    void getAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get the antibiotic
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL_ID, antibiotic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antibiotic.getId().intValue()))
            .andExpect(jsonPath("$.whonetAbxCode").value(DEFAULT_WHONET_ABX_CODE))
            .andExpect(jsonPath("$.whoCode").value(DEFAULT_WHO_CODE))
            .andExpect(jsonPath("$.dinCode").value(DEFAULT_DIN_CODE))
            .andExpect(jsonPath("$.jacCode").value(DEFAULT_JAC_CODE))
            .andExpect(jsonPath("$.eucastCode").value(DEFAULT_EUCAST_CODE))
            .andExpect(jsonPath("$.userCode").value(DEFAULT_USER_CODE))
            .andExpect(jsonPath("$.antibiotic").value(DEFAULT_ANTIBIOTIC))
            .andExpect(jsonPath("$.guidelines").value(DEFAULT_GUIDELINES))
            .andExpect(jsonPath("$.clsi").value(DEFAULT_CLSI))
            .andExpect(jsonPath("$.eucast").value(DEFAULT_EUCAST))
            .andExpect(jsonPath("$.sfm").value(DEFAULT_SFM))
            .andExpect(jsonPath("$.srga").value(DEFAULT_SRGA))
            .andExpect(jsonPath("$.bsac").value(DEFAULT_BSAC))
            .andExpect(jsonPath("$.din").value(DEFAULT_DIN))
            .andExpect(jsonPath("$.neo").value(DEFAULT_NEO))
            .andExpect(jsonPath("$.afa").value(DEFAULT_AFA))
            .andExpect(jsonPath("$.abxNumber").value(DEFAULT_ABX_NUMBER))
            .andExpect(jsonPath("$.potency").value(DEFAULT_POTENCY))
            .andExpect(jsonPath("$.atcCode").value(DEFAULT_ATC_CODE))
            .andExpect(jsonPath("$.profClass").value(DEFAULT_PROF_CLASS))
            .andExpect(jsonPath("$.ciaCategory").value(DEFAULT_CIA_CATEGORY))
            .andExpect(jsonPath("$.clsiOrder").value(DEFAULT_CLSI_ORDER))
            .andExpect(jsonPath("$.eucastOrder").value(DEFAULT_EUCAST_ORDER))
            .andExpect(jsonPath("$.human").value(DEFAULT_HUMAN))
            .andExpect(jsonPath("$.veterinary").value(DEFAULT_VETERINARY))
            .andExpect(jsonPath("$.animalGp").value(DEFAULT_ANIMAL_GP))
            .andExpect(jsonPath("$.loinccomp").value(DEFAULT_LOINCCOMP))
            .andExpect(jsonPath("$.loincgen").value(DEFAULT_LOINCGEN))
            .andExpect(jsonPath("$.loincdisk").value(DEFAULT_LOINCDISK))
            .andExpect(jsonPath("$.loincmic").value(DEFAULT_LOINCMIC))
            .andExpect(jsonPath("$.loincetest").value(DEFAULT_LOINCETEST))
            .andExpect(jsonPath("$.loincslow").value(DEFAULT_LOINCSLOW))
            .andExpect(jsonPath("$.loincafb").value(DEFAULT_LOINCAFB))
            .andExpect(jsonPath("$.loincsbt").value(DEFAULT_LOINCSBT))
            .andExpect(jsonPath("$.loincmlc").value(DEFAULT_LOINCMLC))
            .andExpect(jsonPath("$.dateEntered").value(DEFAULT_DATE_ENTERED))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getAntibioticsByIdFiltering() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        Long id = antibiotic.getId();

        defaultAntibioticShouldBeFound("id.equals=" + id);
        defaultAntibioticShouldNotBeFound("id.notEquals=" + id);

        defaultAntibioticShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAntibioticShouldNotBeFound("id.greaterThan=" + id);

        defaultAntibioticShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAntibioticShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode equals to DEFAULT_WHONET_ABX_CODE
        defaultAntibioticShouldBeFound("whonetAbxCode.equals=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the antibioticList where whonetAbxCode equals to UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldNotBeFound("whonetAbxCode.equals=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode not equals to DEFAULT_WHONET_ABX_CODE
        defaultAntibioticShouldNotBeFound("whonetAbxCode.notEquals=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the antibioticList where whonetAbxCode not equals to UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldBeFound("whonetAbxCode.notEquals=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode in DEFAULT_WHONET_ABX_CODE or UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldBeFound("whonetAbxCode.in=" + DEFAULT_WHONET_ABX_CODE + "," + UPDATED_WHONET_ABX_CODE);

        // Get all the antibioticList where whonetAbxCode equals to UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldNotBeFound("whonetAbxCode.in=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode is not null
        defaultAntibioticShouldBeFound("whonetAbxCode.specified=true");

        // Get all the antibioticList where whonetAbxCode is null
        defaultAntibioticShouldNotBeFound("whonetAbxCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode contains DEFAULT_WHONET_ABX_CODE
        defaultAntibioticShouldBeFound("whonetAbxCode.contains=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the antibioticList where whonetAbxCode contains UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldNotBeFound("whonetAbxCode.contains=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhonetAbxCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whonetAbxCode does not contain DEFAULT_WHONET_ABX_CODE
        defaultAntibioticShouldNotBeFound("whonetAbxCode.doesNotContain=" + DEFAULT_WHONET_ABX_CODE);

        // Get all the antibioticList where whonetAbxCode does not contain UPDATED_WHONET_ABX_CODE
        defaultAntibioticShouldBeFound("whonetAbxCode.doesNotContain=" + UPDATED_WHONET_ABX_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode equals to DEFAULT_WHO_CODE
        defaultAntibioticShouldBeFound("whoCode.equals=" + DEFAULT_WHO_CODE);

        // Get all the antibioticList where whoCode equals to UPDATED_WHO_CODE
        defaultAntibioticShouldNotBeFound("whoCode.equals=" + UPDATED_WHO_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode not equals to DEFAULT_WHO_CODE
        defaultAntibioticShouldNotBeFound("whoCode.notEquals=" + DEFAULT_WHO_CODE);

        // Get all the antibioticList where whoCode not equals to UPDATED_WHO_CODE
        defaultAntibioticShouldBeFound("whoCode.notEquals=" + UPDATED_WHO_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode in DEFAULT_WHO_CODE or UPDATED_WHO_CODE
        defaultAntibioticShouldBeFound("whoCode.in=" + DEFAULT_WHO_CODE + "," + UPDATED_WHO_CODE);

        // Get all the antibioticList where whoCode equals to UPDATED_WHO_CODE
        defaultAntibioticShouldNotBeFound("whoCode.in=" + UPDATED_WHO_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode is not null
        defaultAntibioticShouldBeFound("whoCode.specified=true");

        // Get all the antibioticList where whoCode is null
        defaultAntibioticShouldNotBeFound("whoCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode contains DEFAULT_WHO_CODE
        defaultAntibioticShouldBeFound("whoCode.contains=" + DEFAULT_WHO_CODE);

        // Get all the antibioticList where whoCode contains UPDATED_WHO_CODE
        defaultAntibioticShouldNotBeFound("whoCode.contains=" + UPDATED_WHO_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByWhoCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where whoCode does not contain DEFAULT_WHO_CODE
        defaultAntibioticShouldNotBeFound("whoCode.doesNotContain=" + DEFAULT_WHO_CODE);

        // Get all the antibioticList where whoCode does not contain UPDATED_WHO_CODE
        defaultAntibioticShouldBeFound("whoCode.doesNotContain=" + UPDATED_WHO_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode equals to DEFAULT_DIN_CODE
        defaultAntibioticShouldBeFound("dinCode.equals=" + DEFAULT_DIN_CODE);

        // Get all the antibioticList where dinCode equals to UPDATED_DIN_CODE
        defaultAntibioticShouldNotBeFound("dinCode.equals=" + UPDATED_DIN_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode not equals to DEFAULT_DIN_CODE
        defaultAntibioticShouldNotBeFound("dinCode.notEquals=" + DEFAULT_DIN_CODE);

        // Get all the antibioticList where dinCode not equals to UPDATED_DIN_CODE
        defaultAntibioticShouldBeFound("dinCode.notEquals=" + UPDATED_DIN_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode in DEFAULT_DIN_CODE or UPDATED_DIN_CODE
        defaultAntibioticShouldBeFound("dinCode.in=" + DEFAULT_DIN_CODE + "," + UPDATED_DIN_CODE);

        // Get all the antibioticList where dinCode equals to UPDATED_DIN_CODE
        defaultAntibioticShouldNotBeFound("dinCode.in=" + UPDATED_DIN_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode is not null
        defaultAntibioticShouldBeFound("dinCode.specified=true");

        // Get all the antibioticList where dinCode is null
        defaultAntibioticShouldNotBeFound("dinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode contains DEFAULT_DIN_CODE
        defaultAntibioticShouldBeFound("dinCode.contains=" + DEFAULT_DIN_CODE);

        // Get all the antibioticList where dinCode contains UPDATED_DIN_CODE
        defaultAntibioticShouldNotBeFound("dinCode.contains=" + UPDATED_DIN_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dinCode does not contain DEFAULT_DIN_CODE
        defaultAntibioticShouldNotBeFound("dinCode.doesNotContain=" + DEFAULT_DIN_CODE);

        // Get all the antibioticList where dinCode does not contain UPDATED_DIN_CODE
        defaultAntibioticShouldBeFound("dinCode.doesNotContain=" + UPDATED_DIN_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode equals to DEFAULT_JAC_CODE
        defaultAntibioticShouldBeFound("jacCode.equals=" + DEFAULT_JAC_CODE);

        // Get all the antibioticList where jacCode equals to UPDATED_JAC_CODE
        defaultAntibioticShouldNotBeFound("jacCode.equals=" + UPDATED_JAC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode not equals to DEFAULT_JAC_CODE
        defaultAntibioticShouldNotBeFound("jacCode.notEquals=" + DEFAULT_JAC_CODE);

        // Get all the antibioticList where jacCode not equals to UPDATED_JAC_CODE
        defaultAntibioticShouldBeFound("jacCode.notEquals=" + UPDATED_JAC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode in DEFAULT_JAC_CODE or UPDATED_JAC_CODE
        defaultAntibioticShouldBeFound("jacCode.in=" + DEFAULT_JAC_CODE + "," + UPDATED_JAC_CODE);

        // Get all the antibioticList where jacCode equals to UPDATED_JAC_CODE
        defaultAntibioticShouldNotBeFound("jacCode.in=" + UPDATED_JAC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode is not null
        defaultAntibioticShouldBeFound("jacCode.specified=true");

        // Get all the antibioticList where jacCode is null
        defaultAntibioticShouldNotBeFound("jacCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode contains DEFAULT_JAC_CODE
        defaultAntibioticShouldBeFound("jacCode.contains=" + DEFAULT_JAC_CODE);

        // Get all the antibioticList where jacCode contains UPDATED_JAC_CODE
        defaultAntibioticShouldNotBeFound("jacCode.contains=" + UPDATED_JAC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByJacCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where jacCode does not contain DEFAULT_JAC_CODE
        defaultAntibioticShouldNotBeFound("jacCode.doesNotContain=" + DEFAULT_JAC_CODE);

        // Get all the antibioticList where jacCode does not contain UPDATED_JAC_CODE
        defaultAntibioticShouldBeFound("jacCode.doesNotContain=" + UPDATED_JAC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode equals to DEFAULT_EUCAST_CODE
        defaultAntibioticShouldBeFound("eucastCode.equals=" + DEFAULT_EUCAST_CODE);

        // Get all the antibioticList where eucastCode equals to UPDATED_EUCAST_CODE
        defaultAntibioticShouldNotBeFound("eucastCode.equals=" + UPDATED_EUCAST_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode not equals to DEFAULT_EUCAST_CODE
        defaultAntibioticShouldNotBeFound("eucastCode.notEquals=" + DEFAULT_EUCAST_CODE);

        // Get all the antibioticList where eucastCode not equals to UPDATED_EUCAST_CODE
        defaultAntibioticShouldBeFound("eucastCode.notEquals=" + UPDATED_EUCAST_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode in DEFAULT_EUCAST_CODE or UPDATED_EUCAST_CODE
        defaultAntibioticShouldBeFound("eucastCode.in=" + DEFAULT_EUCAST_CODE + "," + UPDATED_EUCAST_CODE);

        // Get all the antibioticList where eucastCode equals to UPDATED_EUCAST_CODE
        defaultAntibioticShouldNotBeFound("eucastCode.in=" + UPDATED_EUCAST_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode is not null
        defaultAntibioticShouldBeFound("eucastCode.specified=true");

        // Get all the antibioticList where eucastCode is null
        defaultAntibioticShouldNotBeFound("eucastCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode contains DEFAULT_EUCAST_CODE
        defaultAntibioticShouldBeFound("eucastCode.contains=" + DEFAULT_EUCAST_CODE);

        // Get all the antibioticList where eucastCode contains UPDATED_EUCAST_CODE
        defaultAntibioticShouldNotBeFound("eucastCode.contains=" + UPDATED_EUCAST_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastCode does not contain DEFAULT_EUCAST_CODE
        defaultAntibioticShouldNotBeFound("eucastCode.doesNotContain=" + DEFAULT_EUCAST_CODE);

        // Get all the antibioticList where eucastCode does not contain UPDATED_EUCAST_CODE
        defaultAntibioticShouldBeFound("eucastCode.doesNotContain=" + UPDATED_EUCAST_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode equals to DEFAULT_USER_CODE
        defaultAntibioticShouldBeFound("userCode.equals=" + DEFAULT_USER_CODE);

        // Get all the antibioticList where userCode equals to UPDATED_USER_CODE
        defaultAntibioticShouldNotBeFound("userCode.equals=" + UPDATED_USER_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode not equals to DEFAULT_USER_CODE
        defaultAntibioticShouldNotBeFound("userCode.notEquals=" + DEFAULT_USER_CODE);

        // Get all the antibioticList where userCode not equals to UPDATED_USER_CODE
        defaultAntibioticShouldBeFound("userCode.notEquals=" + UPDATED_USER_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode in DEFAULT_USER_CODE or UPDATED_USER_CODE
        defaultAntibioticShouldBeFound("userCode.in=" + DEFAULT_USER_CODE + "," + UPDATED_USER_CODE);

        // Get all the antibioticList where userCode equals to UPDATED_USER_CODE
        defaultAntibioticShouldNotBeFound("userCode.in=" + UPDATED_USER_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode is not null
        defaultAntibioticShouldBeFound("userCode.specified=true");

        // Get all the antibioticList where userCode is null
        defaultAntibioticShouldNotBeFound("userCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode contains DEFAULT_USER_CODE
        defaultAntibioticShouldBeFound("userCode.contains=" + DEFAULT_USER_CODE);

        // Get all the antibioticList where userCode contains UPDATED_USER_CODE
        defaultAntibioticShouldNotBeFound("userCode.contains=" + UPDATED_USER_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByUserCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where userCode does not contain DEFAULT_USER_CODE
        defaultAntibioticShouldNotBeFound("userCode.doesNotContain=" + DEFAULT_USER_CODE);

        // Get all the antibioticList where userCode does not contain UPDATED_USER_CODE
        defaultAntibioticShouldBeFound("userCode.doesNotContain=" + UPDATED_USER_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic equals to DEFAULT_ANTIBIOTIC
        defaultAntibioticShouldBeFound("antibiotic.equals=" + DEFAULT_ANTIBIOTIC);

        // Get all the antibioticList where antibiotic equals to UPDATED_ANTIBIOTIC
        defaultAntibioticShouldNotBeFound("antibiotic.equals=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic not equals to DEFAULT_ANTIBIOTIC
        defaultAntibioticShouldNotBeFound("antibiotic.notEquals=" + DEFAULT_ANTIBIOTIC);

        // Get all the antibioticList where antibiotic not equals to UPDATED_ANTIBIOTIC
        defaultAntibioticShouldBeFound("antibiotic.notEquals=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic in DEFAULT_ANTIBIOTIC or UPDATED_ANTIBIOTIC
        defaultAntibioticShouldBeFound("antibiotic.in=" + DEFAULT_ANTIBIOTIC + "," + UPDATED_ANTIBIOTIC);

        // Get all the antibioticList where antibiotic equals to UPDATED_ANTIBIOTIC
        defaultAntibioticShouldNotBeFound("antibiotic.in=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic is not null
        defaultAntibioticShouldBeFound("antibiotic.specified=true");

        // Get all the antibioticList where antibiotic is null
        defaultAntibioticShouldNotBeFound("antibiotic.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic contains DEFAULT_ANTIBIOTIC
        defaultAntibioticShouldBeFound("antibiotic.contains=" + DEFAULT_ANTIBIOTIC);

        // Get all the antibioticList where antibiotic contains UPDATED_ANTIBIOTIC
        defaultAntibioticShouldNotBeFound("antibiotic.contains=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAntibioticNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where antibiotic does not contain DEFAULT_ANTIBIOTIC
        defaultAntibioticShouldNotBeFound("antibiotic.doesNotContain=" + DEFAULT_ANTIBIOTIC);

        // Get all the antibioticList where antibiotic does not contain UPDATED_ANTIBIOTIC
        defaultAntibioticShouldBeFound("antibiotic.doesNotContain=" + UPDATED_ANTIBIOTIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines equals to DEFAULT_GUIDELINES
        defaultAntibioticShouldBeFound("guidelines.equals=" + DEFAULT_GUIDELINES);

        // Get all the antibioticList where guidelines equals to UPDATED_GUIDELINES
        defaultAntibioticShouldNotBeFound("guidelines.equals=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines not equals to DEFAULT_GUIDELINES
        defaultAntibioticShouldNotBeFound("guidelines.notEquals=" + DEFAULT_GUIDELINES);

        // Get all the antibioticList where guidelines not equals to UPDATED_GUIDELINES
        defaultAntibioticShouldBeFound("guidelines.notEquals=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines in DEFAULT_GUIDELINES or UPDATED_GUIDELINES
        defaultAntibioticShouldBeFound("guidelines.in=" + DEFAULT_GUIDELINES + "," + UPDATED_GUIDELINES);

        // Get all the antibioticList where guidelines equals to UPDATED_GUIDELINES
        defaultAntibioticShouldNotBeFound("guidelines.in=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines is not null
        defaultAntibioticShouldBeFound("guidelines.specified=true");

        // Get all the antibioticList where guidelines is null
        defaultAntibioticShouldNotBeFound("guidelines.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines contains DEFAULT_GUIDELINES
        defaultAntibioticShouldBeFound("guidelines.contains=" + DEFAULT_GUIDELINES);

        // Get all the antibioticList where guidelines contains UPDATED_GUIDELINES
        defaultAntibioticShouldNotBeFound("guidelines.contains=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllAntibioticsByGuidelinesNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where guidelines does not contain DEFAULT_GUIDELINES
        defaultAntibioticShouldNotBeFound("guidelines.doesNotContain=" + DEFAULT_GUIDELINES);

        // Get all the antibioticList where guidelines does not contain UPDATED_GUIDELINES
        defaultAntibioticShouldBeFound("guidelines.doesNotContain=" + UPDATED_GUIDELINES);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi equals to DEFAULT_CLSI
        defaultAntibioticShouldBeFound("clsi.equals=" + DEFAULT_CLSI);

        // Get all the antibioticList where clsi equals to UPDATED_CLSI
        defaultAntibioticShouldNotBeFound("clsi.equals=" + UPDATED_CLSI);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi not equals to DEFAULT_CLSI
        defaultAntibioticShouldNotBeFound("clsi.notEquals=" + DEFAULT_CLSI);

        // Get all the antibioticList where clsi not equals to UPDATED_CLSI
        defaultAntibioticShouldBeFound("clsi.notEquals=" + UPDATED_CLSI);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi in DEFAULT_CLSI or UPDATED_CLSI
        defaultAntibioticShouldBeFound("clsi.in=" + DEFAULT_CLSI + "," + UPDATED_CLSI);

        // Get all the antibioticList where clsi equals to UPDATED_CLSI
        defaultAntibioticShouldNotBeFound("clsi.in=" + UPDATED_CLSI);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi is not null
        defaultAntibioticShouldBeFound("clsi.specified=true");

        // Get all the antibioticList where clsi is null
        defaultAntibioticShouldNotBeFound("clsi.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi contains DEFAULT_CLSI
        defaultAntibioticShouldBeFound("clsi.contains=" + DEFAULT_CLSI);

        // Get all the antibioticList where clsi contains UPDATED_CLSI
        defaultAntibioticShouldNotBeFound("clsi.contains=" + UPDATED_CLSI);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsi does not contain DEFAULT_CLSI
        defaultAntibioticShouldNotBeFound("clsi.doesNotContain=" + DEFAULT_CLSI);

        // Get all the antibioticList where clsi does not contain UPDATED_CLSI
        defaultAntibioticShouldBeFound("clsi.doesNotContain=" + UPDATED_CLSI);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast equals to DEFAULT_EUCAST
        defaultAntibioticShouldBeFound("eucast.equals=" + DEFAULT_EUCAST);

        // Get all the antibioticList where eucast equals to UPDATED_EUCAST
        defaultAntibioticShouldNotBeFound("eucast.equals=" + UPDATED_EUCAST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast not equals to DEFAULT_EUCAST
        defaultAntibioticShouldNotBeFound("eucast.notEquals=" + DEFAULT_EUCAST);

        // Get all the antibioticList where eucast not equals to UPDATED_EUCAST
        defaultAntibioticShouldBeFound("eucast.notEquals=" + UPDATED_EUCAST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast in DEFAULT_EUCAST or UPDATED_EUCAST
        defaultAntibioticShouldBeFound("eucast.in=" + DEFAULT_EUCAST + "," + UPDATED_EUCAST);

        // Get all the antibioticList where eucast equals to UPDATED_EUCAST
        defaultAntibioticShouldNotBeFound("eucast.in=" + UPDATED_EUCAST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast is not null
        defaultAntibioticShouldBeFound("eucast.specified=true");

        // Get all the antibioticList where eucast is null
        defaultAntibioticShouldNotBeFound("eucast.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast contains DEFAULT_EUCAST
        defaultAntibioticShouldBeFound("eucast.contains=" + DEFAULT_EUCAST);

        // Get all the antibioticList where eucast contains UPDATED_EUCAST
        defaultAntibioticShouldNotBeFound("eucast.contains=" + UPDATED_EUCAST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucast does not contain DEFAULT_EUCAST
        defaultAntibioticShouldNotBeFound("eucast.doesNotContain=" + DEFAULT_EUCAST);

        // Get all the antibioticList where eucast does not contain UPDATED_EUCAST
        defaultAntibioticShouldBeFound("eucast.doesNotContain=" + UPDATED_EUCAST);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm equals to DEFAULT_SFM
        defaultAntibioticShouldBeFound("sfm.equals=" + DEFAULT_SFM);

        // Get all the antibioticList where sfm equals to UPDATED_SFM
        defaultAntibioticShouldNotBeFound("sfm.equals=" + UPDATED_SFM);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm not equals to DEFAULT_SFM
        defaultAntibioticShouldNotBeFound("sfm.notEquals=" + DEFAULT_SFM);

        // Get all the antibioticList where sfm not equals to UPDATED_SFM
        defaultAntibioticShouldBeFound("sfm.notEquals=" + UPDATED_SFM);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm in DEFAULT_SFM or UPDATED_SFM
        defaultAntibioticShouldBeFound("sfm.in=" + DEFAULT_SFM + "," + UPDATED_SFM);

        // Get all the antibioticList where sfm equals to UPDATED_SFM
        defaultAntibioticShouldNotBeFound("sfm.in=" + UPDATED_SFM);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm is not null
        defaultAntibioticShouldBeFound("sfm.specified=true");

        // Get all the antibioticList where sfm is null
        defaultAntibioticShouldNotBeFound("sfm.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm contains DEFAULT_SFM
        defaultAntibioticShouldBeFound("sfm.contains=" + DEFAULT_SFM);

        // Get all the antibioticList where sfm contains UPDATED_SFM
        defaultAntibioticShouldNotBeFound("sfm.contains=" + UPDATED_SFM);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySfmNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where sfm does not contain DEFAULT_SFM
        defaultAntibioticShouldNotBeFound("sfm.doesNotContain=" + DEFAULT_SFM);

        // Get all the antibioticList where sfm does not contain UPDATED_SFM
        defaultAntibioticShouldBeFound("sfm.doesNotContain=" + UPDATED_SFM);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga equals to DEFAULT_SRGA
        defaultAntibioticShouldBeFound("srga.equals=" + DEFAULT_SRGA);

        // Get all the antibioticList where srga equals to UPDATED_SRGA
        defaultAntibioticShouldNotBeFound("srga.equals=" + UPDATED_SRGA);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga not equals to DEFAULT_SRGA
        defaultAntibioticShouldNotBeFound("srga.notEquals=" + DEFAULT_SRGA);

        // Get all the antibioticList where srga not equals to UPDATED_SRGA
        defaultAntibioticShouldBeFound("srga.notEquals=" + UPDATED_SRGA);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga in DEFAULT_SRGA or UPDATED_SRGA
        defaultAntibioticShouldBeFound("srga.in=" + DEFAULT_SRGA + "," + UPDATED_SRGA);

        // Get all the antibioticList where srga equals to UPDATED_SRGA
        defaultAntibioticShouldNotBeFound("srga.in=" + UPDATED_SRGA);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga is not null
        defaultAntibioticShouldBeFound("srga.specified=true");

        // Get all the antibioticList where srga is null
        defaultAntibioticShouldNotBeFound("srga.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga contains DEFAULT_SRGA
        defaultAntibioticShouldBeFound("srga.contains=" + DEFAULT_SRGA);

        // Get all the antibioticList where srga contains UPDATED_SRGA
        defaultAntibioticShouldNotBeFound("srga.contains=" + UPDATED_SRGA);
    }

    @Test
    @Transactional
    void getAllAntibioticsBySrgaNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where srga does not contain DEFAULT_SRGA
        defaultAntibioticShouldNotBeFound("srga.doesNotContain=" + DEFAULT_SRGA);

        // Get all the antibioticList where srga does not contain UPDATED_SRGA
        defaultAntibioticShouldBeFound("srga.doesNotContain=" + UPDATED_SRGA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac equals to DEFAULT_BSAC
        defaultAntibioticShouldBeFound("bsac.equals=" + DEFAULT_BSAC);

        // Get all the antibioticList where bsac equals to UPDATED_BSAC
        defaultAntibioticShouldNotBeFound("bsac.equals=" + UPDATED_BSAC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac not equals to DEFAULT_BSAC
        defaultAntibioticShouldNotBeFound("bsac.notEquals=" + DEFAULT_BSAC);

        // Get all the antibioticList where bsac not equals to UPDATED_BSAC
        defaultAntibioticShouldBeFound("bsac.notEquals=" + UPDATED_BSAC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac in DEFAULT_BSAC or UPDATED_BSAC
        defaultAntibioticShouldBeFound("bsac.in=" + DEFAULT_BSAC + "," + UPDATED_BSAC);

        // Get all the antibioticList where bsac equals to UPDATED_BSAC
        defaultAntibioticShouldNotBeFound("bsac.in=" + UPDATED_BSAC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac is not null
        defaultAntibioticShouldBeFound("bsac.specified=true");

        // Get all the antibioticList where bsac is null
        defaultAntibioticShouldNotBeFound("bsac.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac contains DEFAULT_BSAC
        defaultAntibioticShouldBeFound("bsac.contains=" + DEFAULT_BSAC);

        // Get all the antibioticList where bsac contains UPDATED_BSAC
        defaultAntibioticShouldNotBeFound("bsac.contains=" + UPDATED_BSAC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByBsacNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where bsac does not contain DEFAULT_BSAC
        defaultAntibioticShouldNotBeFound("bsac.doesNotContain=" + DEFAULT_BSAC);

        // Get all the antibioticList where bsac does not contain UPDATED_BSAC
        defaultAntibioticShouldBeFound("bsac.doesNotContain=" + UPDATED_BSAC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din equals to DEFAULT_DIN
        defaultAntibioticShouldBeFound("din.equals=" + DEFAULT_DIN);

        // Get all the antibioticList where din equals to UPDATED_DIN
        defaultAntibioticShouldNotBeFound("din.equals=" + UPDATED_DIN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din not equals to DEFAULT_DIN
        defaultAntibioticShouldNotBeFound("din.notEquals=" + DEFAULT_DIN);

        // Get all the antibioticList where din not equals to UPDATED_DIN
        defaultAntibioticShouldBeFound("din.notEquals=" + UPDATED_DIN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din in DEFAULT_DIN or UPDATED_DIN
        defaultAntibioticShouldBeFound("din.in=" + DEFAULT_DIN + "," + UPDATED_DIN);

        // Get all the antibioticList where din equals to UPDATED_DIN
        defaultAntibioticShouldNotBeFound("din.in=" + UPDATED_DIN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din is not null
        defaultAntibioticShouldBeFound("din.specified=true");

        // Get all the antibioticList where din is null
        defaultAntibioticShouldNotBeFound("din.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din contains DEFAULT_DIN
        defaultAntibioticShouldBeFound("din.contains=" + DEFAULT_DIN);

        // Get all the antibioticList where din contains UPDATED_DIN
        defaultAntibioticShouldNotBeFound("din.contains=" + UPDATED_DIN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDinNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where din does not contain DEFAULT_DIN
        defaultAntibioticShouldNotBeFound("din.doesNotContain=" + DEFAULT_DIN);

        // Get all the antibioticList where din does not contain UPDATED_DIN
        defaultAntibioticShouldBeFound("din.doesNotContain=" + UPDATED_DIN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo equals to DEFAULT_NEO
        defaultAntibioticShouldBeFound("neo.equals=" + DEFAULT_NEO);

        // Get all the antibioticList where neo equals to UPDATED_NEO
        defaultAntibioticShouldNotBeFound("neo.equals=" + UPDATED_NEO);
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo not equals to DEFAULT_NEO
        defaultAntibioticShouldNotBeFound("neo.notEquals=" + DEFAULT_NEO);

        // Get all the antibioticList where neo not equals to UPDATED_NEO
        defaultAntibioticShouldBeFound("neo.notEquals=" + UPDATED_NEO);
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo in DEFAULT_NEO or UPDATED_NEO
        defaultAntibioticShouldBeFound("neo.in=" + DEFAULT_NEO + "," + UPDATED_NEO);

        // Get all the antibioticList where neo equals to UPDATED_NEO
        defaultAntibioticShouldNotBeFound("neo.in=" + UPDATED_NEO);
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo is not null
        defaultAntibioticShouldBeFound("neo.specified=true");

        // Get all the antibioticList where neo is null
        defaultAntibioticShouldNotBeFound("neo.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo contains DEFAULT_NEO
        defaultAntibioticShouldBeFound("neo.contains=" + DEFAULT_NEO);

        // Get all the antibioticList where neo contains UPDATED_NEO
        defaultAntibioticShouldNotBeFound("neo.contains=" + UPDATED_NEO);
    }

    @Test
    @Transactional
    void getAllAntibioticsByNeoNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where neo does not contain DEFAULT_NEO
        defaultAntibioticShouldNotBeFound("neo.doesNotContain=" + DEFAULT_NEO);

        // Get all the antibioticList where neo does not contain UPDATED_NEO
        defaultAntibioticShouldBeFound("neo.doesNotContain=" + UPDATED_NEO);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa equals to DEFAULT_AFA
        defaultAntibioticShouldBeFound("afa.equals=" + DEFAULT_AFA);

        // Get all the antibioticList where afa equals to UPDATED_AFA
        defaultAntibioticShouldNotBeFound("afa.equals=" + UPDATED_AFA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa not equals to DEFAULT_AFA
        defaultAntibioticShouldNotBeFound("afa.notEquals=" + DEFAULT_AFA);

        // Get all the antibioticList where afa not equals to UPDATED_AFA
        defaultAntibioticShouldBeFound("afa.notEquals=" + UPDATED_AFA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa in DEFAULT_AFA or UPDATED_AFA
        defaultAntibioticShouldBeFound("afa.in=" + DEFAULT_AFA + "," + UPDATED_AFA);

        // Get all the antibioticList where afa equals to UPDATED_AFA
        defaultAntibioticShouldNotBeFound("afa.in=" + UPDATED_AFA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa is not null
        defaultAntibioticShouldBeFound("afa.specified=true");

        // Get all the antibioticList where afa is null
        defaultAntibioticShouldNotBeFound("afa.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa contains DEFAULT_AFA
        defaultAntibioticShouldBeFound("afa.contains=" + DEFAULT_AFA);

        // Get all the antibioticList where afa contains UPDATED_AFA
        defaultAntibioticShouldNotBeFound("afa.contains=" + UPDATED_AFA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAfaNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where afa does not contain DEFAULT_AFA
        defaultAntibioticShouldNotBeFound("afa.doesNotContain=" + DEFAULT_AFA);

        // Get all the antibioticList where afa does not contain UPDATED_AFA
        defaultAntibioticShouldBeFound("afa.doesNotContain=" + UPDATED_AFA);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber equals to DEFAULT_ABX_NUMBER
        defaultAntibioticShouldBeFound("abxNumber.equals=" + DEFAULT_ABX_NUMBER);

        // Get all the antibioticList where abxNumber equals to UPDATED_ABX_NUMBER
        defaultAntibioticShouldNotBeFound("abxNumber.equals=" + UPDATED_ABX_NUMBER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber not equals to DEFAULT_ABX_NUMBER
        defaultAntibioticShouldNotBeFound("abxNumber.notEquals=" + DEFAULT_ABX_NUMBER);

        // Get all the antibioticList where abxNumber not equals to UPDATED_ABX_NUMBER
        defaultAntibioticShouldBeFound("abxNumber.notEquals=" + UPDATED_ABX_NUMBER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber in DEFAULT_ABX_NUMBER or UPDATED_ABX_NUMBER
        defaultAntibioticShouldBeFound("abxNumber.in=" + DEFAULT_ABX_NUMBER + "," + UPDATED_ABX_NUMBER);

        // Get all the antibioticList where abxNumber equals to UPDATED_ABX_NUMBER
        defaultAntibioticShouldNotBeFound("abxNumber.in=" + UPDATED_ABX_NUMBER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber is not null
        defaultAntibioticShouldBeFound("abxNumber.specified=true");

        // Get all the antibioticList where abxNumber is null
        defaultAntibioticShouldNotBeFound("abxNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber contains DEFAULT_ABX_NUMBER
        defaultAntibioticShouldBeFound("abxNumber.contains=" + DEFAULT_ABX_NUMBER);

        // Get all the antibioticList where abxNumber contains UPDATED_ABX_NUMBER
        defaultAntibioticShouldNotBeFound("abxNumber.contains=" + UPDATED_ABX_NUMBER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAbxNumberNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where abxNumber does not contain DEFAULT_ABX_NUMBER
        defaultAntibioticShouldNotBeFound("abxNumber.doesNotContain=" + DEFAULT_ABX_NUMBER);

        // Get all the antibioticList where abxNumber does not contain UPDATED_ABX_NUMBER
        defaultAntibioticShouldBeFound("abxNumber.doesNotContain=" + UPDATED_ABX_NUMBER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency equals to DEFAULT_POTENCY
        defaultAntibioticShouldBeFound("potency.equals=" + DEFAULT_POTENCY);

        // Get all the antibioticList where potency equals to UPDATED_POTENCY
        defaultAntibioticShouldNotBeFound("potency.equals=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency not equals to DEFAULT_POTENCY
        defaultAntibioticShouldNotBeFound("potency.notEquals=" + DEFAULT_POTENCY);

        // Get all the antibioticList where potency not equals to UPDATED_POTENCY
        defaultAntibioticShouldBeFound("potency.notEquals=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency in DEFAULT_POTENCY or UPDATED_POTENCY
        defaultAntibioticShouldBeFound("potency.in=" + DEFAULT_POTENCY + "," + UPDATED_POTENCY);

        // Get all the antibioticList where potency equals to UPDATED_POTENCY
        defaultAntibioticShouldNotBeFound("potency.in=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency is not null
        defaultAntibioticShouldBeFound("potency.specified=true");

        // Get all the antibioticList where potency is null
        defaultAntibioticShouldNotBeFound("potency.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency contains DEFAULT_POTENCY
        defaultAntibioticShouldBeFound("potency.contains=" + DEFAULT_POTENCY);

        // Get all the antibioticList where potency contains UPDATED_POTENCY
        defaultAntibioticShouldNotBeFound("potency.contains=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByPotencyNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where potency does not contain DEFAULT_POTENCY
        defaultAntibioticShouldNotBeFound("potency.doesNotContain=" + DEFAULT_POTENCY);

        // Get all the antibioticList where potency does not contain UPDATED_POTENCY
        defaultAntibioticShouldBeFound("potency.doesNotContain=" + UPDATED_POTENCY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode equals to DEFAULT_ATC_CODE
        defaultAntibioticShouldBeFound("atcCode.equals=" + DEFAULT_ATC_CODE);

        // Get all the antibioticList where atcCode equals to UPDATED_ATC_CODE
        defaultAntibioticShouldNotBeFound("atcCode.equals=" + UPDATED_ATC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode not equals to DEFAULT_ATC_CODE
        defaultAntibioticShouldNotBeFound("atcCode.notEquals=" + DEFAULT_ATC_CODE);

        // Get all the antibioticList where atcCode not equals to UPDATED_ATC_CODE
        defaultAntibioticShouldBeFound("atcCode.notEquals=" + UPDATED_ATC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode in DEFAULT_ATC_CODE or UPDATED_ATC_CODE
        defaultAntibioticShouldBeFound("atcCode.in=" + DEFAULT_ATC_CODE + "," + UPDATED_ATC_CODE);

        // Get all the antibioticList where atcCode equals to UPDATED_ATC_CODE
        defaultAntibioticShouldNotBeFound("atcCode.in=" + UPDATED_ATC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode is not null
        defaultAntibioticShouldBeFound("atcCode.specified=true");

        // Get all the antibioticList where atcCode is null
        defaultAntibioticShouldNotBeFound("atcCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode contains DEFAULT_ATC_CODE
        defaultAntibioticShouldBeFound("atcCode.contains=" + DEFAULT_ATC_CODE);

        // Get all the antibioticList where atcCode contains UPDATED_ATC_CODE
        defaultAntibioticShouldNotBeFound("atcCode.contains=" + UPDATED_ATC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAtcCodeNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where atcCode does not contain DEFAULT_ATC_CODE
        defaultAntibioticShouldNotBeFound("atcCode.doesNotContain=" + DEFAULT_ATC_CODE);

        // Get all the antibioticList where atcCode does not contain UPDATED_ATC_CODE
        defaultAntibioticShouldBeFound("atcCode.doesNotContain=" + UPDATED_ATC_CODE);
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass equals to DEFAULT_PROF_CLASS
        defaultAntibioticShouldBeFound("profClass.equals=" + DEFAULT_PROF_CLASS);

        // Get all the antibioticList where profClass equals to UPDATED_PROF_CLASS
        defaultAntibioticShouldNotBeFound("profClass.equals=" + UPDATED_PROF_CLASS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass not equals to DEFAULT_PROF_CLASS
        defaultAntibioticShouldNotBeFound("profClass.notEquals=" + DEFAULT_PROF_CLASS);

        // Get all the antibioticList where profClass not equals to UPDATED_PROF_CLASS
        defaultAntibioticShouldBeFound("profClass.notEquals=" + UPDATED_PROF_CLASS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass in DEFAULT_PROF_CLASS or UPDATED_PROF_CLASS
        defaultAntibioticShouldBeFound("profClass.in=" + DEFAULT_PROF_CLASS + "," + UPDATED_PROF_CLASS);

        // Get all the antibioticList where profClass equals to UPDATED_PROF_CLASS
        defaultAntibioticShouldNotBeFound("profClass.in=" + UPDATED_PROF_CLASS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass is not null
        defaultAntibioticShouldBeFound("profClass.specified=true");

        // Get all the antibioticList where profClass is null
        defaultAntibioticShouldNotBeFound("profClass.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass contains DEFAULT_PROF_CLASS
        defaultAntibioticShouldBeFound("profClass.contains=" + DEFAULT_PROF_CLASS);

        // Get all the antibioticList where profClass contains UPDATED_PROF_CLASS
        defaultAntibioticShouldNotBeFound("profClass.contains=" + UPDATED_PROF_CLASS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByProfClassNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where profClass does not contain DEFAULT_PROF_CLASS
        defaultAntibioticShouldNotBeFound("profClass.doesNotContain=" + DEFAULT_PROF_CLASS);

        // Get all the antibioticList where profClass does not contain UPDATED_PROF_CLASS
        defaultAntibioticShouldBeFound("profClass.doesNotContain=" + UPDATED_PROF_CLASS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory equals to DEFAULT_CIA_CATEGORY
        defaultAntibioticShouldBeFound("ciaCategory.equals=" + DEFAULT_CIA_CATEGORY);

        // Get all the antibioticList where ciaCategory equals to UPDATED_CIA_CATEGORY
        defaultAntibioticShouldNotBeFound("ciaCategory.equals=" + UPDATED_CIA_CATEGORY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory not equals to DEFAULT_CIA_CATEGORY
        defaultAntibioticShouldNotBeFound("ciaCategory.notEquals=" + DEFAULT_CIA_CATEGORY);

        // Get all the antibioticList where ciaCategory not equals to UPDATED_CIA_CATEGORY
        defaultAntibioticShouldBeFound("ciaCategory.notEquals=" + UPDATED_CIA_CATEGORY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory in DEFAULT_CIA_CATEGORY or UPDATED_CIA_CATEGORY
        defaultAntibioticShouldBeFound("ciaCategory.in=" + DEFAULT_CIA_CATEGORY + "," + UPDATED_CIA_CATEGORY);

        // Get all the antibioticList where ciaCategory equals to UPDATED_CIA_CATEGORY
        defaultAntibioticShouldNotBeFound("ciaCategory.in=" + UPDATED_CIA_CATEGORY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory is not null
        defaultAntibioticShouldBeFound("ciaCategory.specified=true");

        // Get all the antibioticList where ciaCategory is null
        defaultAntibioticShouldNotBeFound("ciaCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory contains DEFAULT_CIA_CATEGORY
        defaultAntibioticShouldBeFound("ciaCategory.contains=" + DEFAULT_CIA_CATEGORY);

        // Get all the antibioticList where ciaCategory contains UPDATED_CIA_CATEGORY
        defaultAntibioticShouldNotBeFound("ciaCategory.contains=" + UPDATED_CIA_CATEGORY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCiaCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where ciaCategory does not contain DEFAULT_CIA_CATEGORY
        defaultAntibioticShouldNotBeFound("ciaCategory.doesNotContain=" + DEFAULT_CIA_CATEGORY);

        // Get all the antibioticList where ciaCategory does not contain UPDATED_CIA_CATEGORY
        defaultAntibioticShouldBeFound("ciaCategory.doesNotContain=" + UPDATED_CIA_CATEGORY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder equals to DEFAULT_CLSI_ORDER
        defaultAntibioticShouldBeFound("clsiOrder.equals=" + DEFAULT_CLSI_ORDER);

        // Get all the antibioticList where clsiOrder equals to UPDATED_CLSI_ORDER
        defaultAntibioticShouldNotBeFound("clsiOrder.equals=" + UPDATED_CLSI_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder not equals to DEFAULT_CLSI_ORDER
        defaultAntibioticShouldNotBeFound("clsiOrder.notEquals=" + DEFAULT_CLSI_ORDER);

        // Get all the antibioticList where clsiOrder not equals to UPDATED_CLSI_ORDER
        defaultAntibioticShouldBeFound("clsiOrder.notEquals=" + UPDATED_CLSI_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder in DEFAULT_CLSI_ORDER or UPDATED_CLSI_ORDER
        defaultAntibioticShouldBeFound("clsiOrder.in=" + DEFAULT_CLSI_ORDER + "," + UPDATED_CLSI_ORDER);

        // Get all the antibioticList where clsiOrder equals to UPDATED_CLSI_ORDER
        defaultAntibioticShouldNotBeFound("clsiOrder.in=" + UPDATED_CLSI_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder is not null
        defaultAntibioticShouldBeFound("clsiOrder.specified=true");

        // Get all the antibioticList where clsiOrder is null
        defaultAntibioticShouldNotBeFound("clsiOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder contains DEFAULT_CLSI_ORDER
        defaultAntibioticShouldBeFound("clsiOrder.contains=" + DEFAULT_CLSI_ORDER);

        // Get all the antibioticList where clsiOrder contains UPDATED_CLSI_ORDER
        defaultAntibioticShouldNotBeFound("clsiOrder.contains=" + UPDATED_CLSI_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByClsiOrderNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where clsiOrder does not contain DEFAULT_CLSI_ORDER
        defaultAntibioticShouldNotBeFound("clsiOrder.doesNotContain=" + DEFAULT_CLSI_ORDER);

        // Get all the antibioticList where clsiOrder does not contain UPDATED_CLSI_ORDER
        defaultAntibioticShouldBeFound("clsiOrder.doesNotContain=" + UPDATED_CLSI_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder equals to DEFAULT_EUCAST_ORDER
        defaultAntibioticShouldBeFound("eucastOrder.equals=" + DEFAULT_EUCAST_ORDER);

        // Get all the antibioticList where eucastOrder equals to UPDATED_EUCAST_ORDER
        defaultAntibioticShouldNotBeFound("eucastOrder.equals=" + UPDATED_EUCAST_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder not equals to DEFAULT_EUCAST_ORDER
        defaultAntibioticShouldNotBeFound("eucastOrder.notEquals=" + DEFAULT_EUCAST_ORDER);

        // Get all the antibioticList where eucastOrder not equals to UPDATED_EUCAST_ORDER
        defaultAntibioticShouldBeFound("eucastOrder.notEquals=" + UPDATED_EUCAST_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder in DEFAULT_EUCAST_ORDER or UPDATED_EUCAST_ORDER
        defaultAntibioticShouldBeFound("eucastOrder.in=" + DEFAULT_EUCAST_ORDER + "," + UPDATED_EUCAST_ORDER);

        // Get all the antibioticList where eucastOrder equals to UPDATED_EUCAST_ORDER
        defaultAntibioticShouldNotBeFound("eucastOrder.in=" + UPDATED_EUCAST_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder is not null
        defaultAntibioticShouldBeFound("eucastOrder.specified=true");

        // Get all the antibioticList where eucastOrder is null
        defaultAntibioticShouldNotBeFound("eucastOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder contains DEFAULT_EUCAST_ORDER
        defaultAntibioticShouldBeFound("eucastOrder.contains=" + DEFAULT_EUCAST_ORDER);

        // Get all the antibioticList where eucastOrder contains UPDATED_EUCAST_ORDER
        defaultAntibioticShouldNotBeFound("eucastOrder.contains=" + UPDATED_EUCAST_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByEucastOrderNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where eucastOrder does not contain DEFAULT_EUCAST_ORDER
        defaultAntibioticShouldNotBeFound("eucastOrder.doesNotContain=" + DEFAULT_EUCAST_ORDER);

        // Get all the antibioticList where eucastOrder does not contain UPDATED_EUCAST_ORDER
        defaultAntibioticShouldBeFound("eucastOrder.doesNotContain=" + UPDATED_EUCAST_ORDER);
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human equals to DEFAULT_HUMAN
        defaultAntibioticShouldBeFound("human.equals=" + DEFAULT_HUMAN);

        // Get all the antibioticList where human equals to UPDATED_HUMAN
        defaultAntibioticShouldNotBeFound("human.equals=" + UPDATED_HUMAN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human not equals to DEFAULT_HUMAN
        defaultAntibioticShouldNotBeFound("human.notEquals=" + DEFAULT_HUMAN);

        // Get all the antibioticList where human not equals to UPDATED_HUMAN
        defaultAntibioticShouldBeFound("human.notEquals=" + UPDATED_HUMAN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human in DEFAULT_HUMAN or UPDATED_HUMAN
        defaultAntibioticShouldBeFound("human.in=" + DEFAULT_HUMAN + "," + UPDATED_HUMAN);

        // Get all the antibioticList where human equals to UPDATED_HUMAN
        defaultAntibioticShouldNotBeFound("human.in=" + UPDATED_HUMAN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human is not null
        defaultAntibioticShouldBeFound("human.specified=true");

        // Get all the antibioticList where human is null
        defaultAntibioticShouldNotBeFound("human.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human contains DEFAULT_HUMAN
        defaultAntibioticShouldBeFound("human.contains=" + DEFAULT_HUMAN);

        // Get all the antibioticList where human contains UPDATED_HUMAN
        defaultAntibioticShouldNotBeFound("human.contains=" + UPDATED_HUMAN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByHumanNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where human does not contain DEFAULT_HUMAN
        defaultAntibioticShouldNotBeFound("human.doesNotContain=" + DEFAULT_HUMAN);

        // Get all the antibioticList where human does not contain UPDATED_HUMAN
        defaultAntibioticShouldBeFound("human.doesNotContain=" + UPDATED_HUMAN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary equals to DEFAULT_VETERINARY
        defaultAntibioticShouldBeFound("veterinary.equals=" + DEFAULT_VETERINARY);

        // Get all the antibioticList where veterinary equals to UPDATED_VETERINARY
        defaultAntibioticShouldNotBeFound("veterinary.equals=" + UPDATED_VETERINARY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary not equals to DEFAULT_VETERINARY
        defaultAntibioticShouldNotBeFound("veterinary.notEquals=" + DEFAULT_VETERINARY);

        // Get all the antibioticList where veterinary not equals to UPDATED_VETERINARY
        defaultAntibioticShouldBeFound("veterinary.notEquals=" + UPDATED_VETERINARY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary in DEFAULT_VETERINARY or UPDATED_VETERINARY
        defaultAntibioticShouldBeFound("veterinary.in=" + DEFAULT_VETERINARY + "," + UPDATED_VETERINARY);

        // Get all the antibioticList where veterinary equals to UPDATED_VETERINARY
        defaultAntibioticShouldNotBeFound("veterinary.in=" + UPDATED_VETERINARY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary is not null
        defaultAntibioticShouldBeFound("veterinary.specified=true");

        // Get all the antibioticList where veterinary is null
        defaultAntibioticShouldNotBeFound("veterinary.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary contains DEFAULT_VETERINARY
        defaultAntibioticShouldBeFound("veterinary.contains=" + DEFAULT_VETERINARY);

        // Get all the antibioticList where veterinary contains UPDATED_VETERINARY
        defaultAntibioticShouldNotBeFound("veterinary.contains=" + UPDATED_VETERINARY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByVeterinaryNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where veterinary does not contain DEFAULT_VETERINARY
        defaultAntibioticShouldNotBeFound("veterinary.doesNotContain=" + DEFAULT_VETERINARY);

        // Get all the antibioticList where veterinary does not contain UPDATED_VETERINARY
        defaultAntibioticShouldBeFound("veterinary.doesNotContain=" + UPDATED_VETERINARY);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp equals to DEFAULT_ANIMAL_GP
        defaultAntibioticShouldBeFound("animalGp.equals=" + DEFAULT_ANIMAL_GP);

        // Get all the antibioticList where animalGp equals to UPDATED_ANIMAL_GP
        defaultAntibioticShouldNotBeFound("animalGp.equals=" + UPDATED_ANIMAL_GP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp not equals to DEFAULT_ANIMAL_GP
        defaultAntibioticShouldNotBeFound("animalGp.notEquals=" + DEFAULT_ANIMAL_GP);

        // Get all the antibioticList where animalGp not equals to UPDATED_ANIMAL_GP
        defaultAntibioticShouldBeFound("animalGp.notEquals=" + UPDATED_ANIMAL_GP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp in DEFAULT_ANIMAL_GP or UPDATED_ANIMAL_GP
        defaultAntibioticShouldBeFound("animalGp.in=" + DEFAULT_ANIMAL_GP + "," + UPDATED_ANIMAL_GP);

        // Get all the antibioticList where animalGp equals to UPDATED_ANIMAL_GP
        defaultAntibioticShouldNotBeFound("animalGp.in=" + UPDATED_ANIMAL_GP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp is not null
        defaultAntibioticShouldBeFound("animalGp.specified=true");

        // Get all the antibioticList where animalGp is null
        defaultAntibioticShouldNotBeFound("animalGp.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp contains DEFAULT_ANIMAL_GP
        defaultAntibioticShouldBeFound("animalGp.contains=" + DEFAULT_ANIMAL_GP);

        // Get all the antibioticList where animalGp contains UPDATED_ANIMAL_GP
        defaultAntibioticShouldNotBeFound("animalGp.contains=" + UPDATED_ANIMAL_GP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByAnimalGpNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where animalGp does not contain DEFAULT_ANIMAL_GP
        defaultAntibioticShouldNotBeFound("animalGp.doesNotContain=" + DEFAULT_ANIMAL_GP);

        // Get all the antibioticList where animalGp does not contain UPDATED_ANIMAL_GP
        defaultAntibioticShouldBeFound("animalGp.doesNotContain=" + UPDATED_ANIMAL_GP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp equals to DEFAULT_LOINCCOMP
        defaultAntibioticShouldBeFound("loinccomp.equals=" + DEFAULT_LOINCCOMP);

        // Get all the antibioticList where loinccomp equals to UPDATED_LOINCCOMP
        defaultAntibioticShouldNotBeFound("loinccomp.equals=" + UPDATED_LOINCCOMP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp not equals to DEFAULT_LOINCCOMP
        defaultAntibioticShouldNotBeFound("loinccomp.notEquals=" + DEFAULT_LOINCCOMP);

        // Get all the antibioticList where loinccomp not equals to UPDATED_LOINCCOMP
        defaultAntibioticShouldBeFound("loinccomp.notEquals=" + UPDATED_LOINCCOMP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp in DEFAULT_LOINCCOMP or UPDATED_LOINCCOMP
        defaultAntibioticShouldBeFound("loinccomp.in=" + DEFAULT_LOINCCOMP + "," + UPDATED_LOINCCOMP);

        // Get all the antibioticList where loinccomp equals to UPDATED_LOINCCOMP
        defaultAntibioticShouldNotBeFound("loinccomp.in=" + UPDATED_LOINCCOMP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp is not null
        defaultAntibioticShouldBeFound("loinccomp.specified=true");

        // Get all the antibioticList where loinccomp is null
        defaultAntibioticShouldNotBeFound("loinccomp.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp contains DEFAULT_LOINCCOMP
        defaultAntibioticShouldBeFound("loinccomp.contains=" + DEFAULT_LOINCCOMP);

        // Get all the antibioticList where loinccomp contains UPDATED_LOINCCOMP
        defaultAntibioticShouldNotBeFound("loinccomp.contains=" + UPDATED_LOINCCOMP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoinccompNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loinccomp does not contain DEFAULT_LOINCCOMP
        defaultAntibioticShouldNotBeFound("loinccomp.doesNotContain=" + DEFAULT_LOINCCOMP);

        // Get all the antibioticList where loinccomp does not contain UPDATED_LOINCCOMP
        defaultAntibioticShouldBeFound("loinccomp.doesNotContain=" + UPDATED_LOINCCOMP);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen equals to DEFAULT_LOINCGEN
        defaultAntibioticShouldBeFound("loincgen.equals=" + DEFAULT_LOINCGEN);

        // Get all the antibioticList where loincgen equals to UPDATED_LOINCGEN
        defaultAntibioticShouldNotBeFound("loincgen.equals=" + UPDATED_LOINCGEN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen not equals to DEFAULT_LOINCGEN
        defaultAntibioticShouldNotBeFound("loincgen.notEquals=" + DEFAULT_LOINCGEN);

        // Get all the antibioticList where loincgen not equals to UPDATED_LOINCGEN
        defaultAntibioticShouldBeFound("loincgen.notEquals=" + UPDATED_LOINCGEN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen in DEFAULT_LOINCGEN or UPDATED_LOINCGEN
        defaultAntibioticShouldBeFound("loincgen.in=" + DEFAULT_LOINCGEN + "," + UPDATED_LOINCGEN);

        // Get all the antibioticList where loincgen equals to UPDATED_LOINCGEN
        defaultAntibioticShouldNotBeFound("loincgen.in=" + UPDATED_LOINCGEN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen is not null
        defaultAntibioticShouldBeFound("loincgen.specified=true");

        // Get all the antibioticList where loincgen is null
        defaultAntibioticShouldNotBeFound("loincgen.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen contains DEFAULT_LOINCGEN
        defaultAntibioticShouldBeFound("loincgen.contains=" + DEFAULT_LOINCGEN);

        // Get all the antibioticList where loincgen contains UPDATED_LOINCGEN
        defaultAntibioticShouldNotBeFound("loincgen.contains=" + UPDATED_LOINCGEN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincgenNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincgen does not contain DEFAULT_LOINCGEN
        defaultAntibioticShouldNotBeFound("loincgen.doesNotContain=" + DEFAULT_LOINCGEN);

        // Get all the antibioticList where loincgen does not contain UPDATED_LOINCGEN
        defaultAntibioticShouldBeFound("loincgen.doesNotContain=" + UPDATED_LOINCGEN);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk equals to DEFAULT_LOINCDISK
        defaultAntibioticShouldBeFound("loincdisk.equals=" + DEFAULT_LOINCDISK);

        // Get all the antibioticList where loincdisk equals to UPDATED_LOINCDISK
        defaultAntibioticShouldNotBeFound("loincdisk.equals=" + UPDATED_LOINCDISK);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk not equals to DEFAULT_LOINCDISK
        defaultAntibioticShouldNotBeFound("loincdisk.notEquals=" + DEFAULT_LOINCDISK);

        // Get all the antibioticList where loincdisk not equals to UPDATED_LOINCDISK
        defaultAntibioticShouldBeFound("loincdisk.notEquals=" + UPDATED_LOINCDISK);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk in DEFAULT_LOINCDISK or UPDATED_LOINCDISK
        defaultAntibioticShouldBeFound("loincdisk.in=" + DEFAULT_LOINCDISK + "," + UPDATED_LOINCDISK);

        // Get all the antibioticList where loincdisk equals to UPDATED_LOINCDISK
        defaultAntibioticShouldNotBeFound("loincdisk.in=" + UPDATED_LOINCDISK);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk is not null
        defaultAntibioticShouldBeFound("loincdisk.specified=true");

        // Get all the antibioticList where loincdisk is null
        defaultAntibioticShouldNotBeFound("loincdisk.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk contains DEFAULT_LOINCDISK
        defaultAntibioticShouldBeFound("loincdisk.contains=" + DEFAULT_LOINCDISK);

        // Get all the antibioticList where loincdisk contains UPDATED_LOINCDISK
        defaultAntibioticShouldNotBeFound("loincdisk.contains=" + UPDATED_LOINCDISK);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincdiskNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincdisk does not contain DEFAULT_LOINCDISK
        defaultAntibioticShouldNotBeFound("loincdisk.doesNotContain=" + DEFAULT_LOINCDISK);

        // Get all the antibioticList where loincdisk does not contain UPDATED_LOINCDISK
        defaultAntibioticShouldBeFound("loincdisk.doesNotContain=" + UPDATED_LOINCDISK);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic equals to DEFAULT_LOINCMIC
        defaultAntibioticShouldBeFound("loincmic.equals=" + DEFAULT_LOINCMIC);

        // Get all the antibioticList where loincmic equals to UPDATED_LOINCMIC
        defaultAntibioticShouldNotBeFound("loincmic.equals=" + UPDATED_LOINCMIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic not equals to DEFAULT_LOINCMIC
        defaultAntibioticShouldNotBeFound("loincmic.notEquals=" + DEFAULT_LOINCMIC);

        // Get all the antibioticList where loincmic not equals to UPDATED_LOINCMIC
        defaultAntibioticShouldBeFound("loincmic.notEquals=" + UPDATED_LOINCMIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic in DEFAULT_LOINCMIC or UPDATED_LOINCMIC
        defaultAntibioticShouldBeFound("loincmic.in=" + DEFAULT_LOINCMIC + "," + UPDATED_LOINCMIC);

        // Get all the antibioticList where loincmic equals to UPDATED_LOINCMIC
        defaultAntibioticShouldNotBeFound("loincmic.in=" + UPDATED_LOINCMIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic is not null
        defaultAntibioticShouldBeFound("loincmic.specified=true");

        // Get all the antibioticList where loincmic is null
        defaultAntibioticShouldNotBeFound("loincmic.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic contains DEFAULT_LOINCMIC
        defaultAntibioticShouldBeFound("loincmic.contains=" + DEFAULT_LOINCMIC);

        // Get all the antibioticList where loincmic contains UPDATED_LOINCMIC
        defaultAntibioticShouldNotBeFound("loincmic.contains=" + UPDATED_LOINCMIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmicNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmic does not contain DEFAULT_LOINCMIC
        defaultAntibioticShouldNotBeFound("loincmic.doesNotContain=" + DEFAULT_LOINCMIC);

        // Get all the antibioticList where loincmic does not contain UPDATED_LOINCMIC
        defaultAntibioticShouldBeFound("loincmic.doesNotContain=" + UPDATED_LOINCMIC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest equals to DEFAULT_LOINCETEST
        defaultAntibioticShouldBeFound("loincetest.equals=" + DEFAULT_LOINCETEST);

        // Get all the antibioticList where loincetest equals to UPDATED_LOINCETEST
        defaultAntibioticShouldNotBeFound("loincetest.equals=" + UPDATED_LOINCETEST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest not equals to DEFAULT_LOINCETEST
        defaultAntibioticShouldNotBeFound("loincetest.notEquals=" + DEFAULT_LOINCETEST);

        // Get all the antibioticList where loincetest not equals to UPDATED_LOINCETEST
        defaultAntibioticShouldBeFound("loincetest.notEquals=" + UPDATED_LOINCETEST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest in DEFAULT_LOINCETEST or UPDATED_LOINCETEST
        defaultAntibioticShouldBeFound("loincetest.in=" + DEFAULT_LOINCETEST + "," + UPDATED_LOINCETEST);

        // Get all the antibioticList where loincetest equals to UPDATED_LOINCETEST
        defaultAntibioticShouldNotBeFound("loincetest.in=" + UPDATED_LOINCETEST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest is not null
        defaultAntibioticShouldBeFound("loincetest.specified=true");

        // Get all the antibioticList where loincetest is null
        defaultAntibioticShouldNotBeFound("loincetest.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest contains DEFAULT_LOINCETEST
        defaultAntibioticShouldBeFound("loincetest.contains=" + DEFAULT_LOINCETEST);

        // Get all the antibioticList where loincetest contains UPDATED_LOINCETEST
        defaultAntibioticShouldNotBeFound("loincetest.contains=" + UPDATED_LOINCETEST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincetestNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincetest does not contain DEFAULT_LOINCETEST
        defaultAntibioticShouldNotBeFound("loincetest.doesNotContain=" + DEFAULT_LOINCETEST);

        // Get all the antibioticList where loincetest does not contain UPDATED_LOINCETEST
        defaultAntibioticShouldBeFound("loincetest.doesNotContain=" + UPDATED_LOINCETEST);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow equals to DEFAULT_LOINCSLOW
        defaultAntibioticShouldBeFound("loincslow.equals=" + DEFAULT_LOINCSLOW);

        // Get all the antibioticList where loincslow equals to UPDATED_LOINCSLOW
        defaultAntibioticShouldNotBeFound("loincslow.equals=" + UPDATED_LOINCSLOW);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow not equals to DEFAULT_LOINCSLOW
        defaultAntibioticShouldNotBeFound("loincslow.notEquals=" + DEFAULT_LOINCSLOW);

        // Get all the antibioticList where loincslow not equals to UPDATED_LOINCSLOW
        defaultAntibioticShouldBeFound("loincslow.notEquals=" + UPDATED_LOINCSLOW);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow in DEFAULT_LOINCSLOW or UPDATED_LOINCSLOW
        defaultAntibioticShouldBeFound("loincslow.in=" + DEFAULT_LOINCSLOW + "," + UPDATED_LOINCSLOW);

        // Get all the antibioticList where loincslow equals to UPDATED_LOINCSLOW
        defaultAntibioticShouldNotBeFound("loincslow.in=" + UPDATED_LOINCSLOW);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow is not null
        defaultAntibioticShouldBeFound("loincslow.specified=true");

        // Get all the antibioticList where loincslow is null
        defaultAntibioticShouldNotBeFound("loincslow.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow contains DEFAULT_LOINCSLOW
        defaultAntibioticShouldBeFound("loincslow.contains=" + DEFAULT_LOINCSLOW);

        // Get all the antibioticList where loincslow contains UPDATED_LOINCSLOW
        defaultAntibioticShouldNotBeFound("loincslow.contains=" + UPDATED_LOINCSLOW);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincslowNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincslow does not contain DEFAULT_LOINCSLOW
        defaultAntibioticShouldNotBeFound("loincslow.doesNotContain=" + DEFAULT_LOINCSLOW);

        // Get all the antibioticList where loincslow does not contain UPDATED_LOINCSLOW
        defaultAntibioticShouldBeFound("loincslow.doesNotContain=" + UPDATED_LOINCSLOW);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb equals to DEFAULT_LOINCAFB
        defaultAntibioticShouldBeFound("loincafb.equals=" + DEFAULT_LOINCAFB);

        // Get all the antibioticList where loincafb equals to UPDATED_LOINCAFB
        defaultAntibioticShouldNotBeFound("loincafb.equals=" + UPDATED_LOINCAFB);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb not equals to DEFAULT_LOINCAFB
        defaultAntibioticShouldNotBeFound("loincafb.notEquals=" + DEFAULT_LOINCAFB);

        // Get all the antibioticList where loincafb not equals to UPDATED_LOINCAFB
        defaultAntibioticShouldBeFound("loincafb.notEquals=" + UPDATED_LOINCAFB);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb in DEFAULT_LOINCAFB or UPDATED_LOINCAFB
        defaultAntibioticShouldBeFound("loincafb.in=" + DEFAULT_LOINCAFB + "," + UPDATED_LOINCAFB);

        // Get all the antibioticList where loincafb equals to UPDATED_LOINCAFB
        defaultAntibioticShouldNotBeFound("loincafb.in=" + UPDATED_LOINCAFB);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb is not null
        defaultAntibioticShouldBeFound("loincafb.specified=true");

        // Get all the antibioticList where loincafb is null
        defaultAntibioticShouldNotBeFound("loincafb.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb contains DEFAULT_LOINCAFB
        defaultAntibioticShouldBeFound("loincafb.contains=" + DEFAULT_LOINCAFB);

        // Get all the antibioticList where loincafb contains UPDATED_LOINCAFB
        defaultAntibioticShouldNotBeFound("loincafb.contains=" + UPDATED_LOINCAFB);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincafbNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincafb does not contain DEFAULT_LOINCAFB
        defaultAntibioticShouldNotBeFound("loincafb.doesNotContain=" + DEFAULT_LOINCAFB);

        // Get all the antibioticList where loincafb does not contain UPDATED_LOINCAFB
        defaultAntibioticShouldBeFound("loincafb.doesNotContain=" + UPDATED_LOINCAFB);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt equals to DEFAULT_LOINCSBT
        defaultAntibioticShouldBeFound("loincsbt.equals=" + DEFAULT_LOINCSBT);

        // Get all the antibioticList where loincsbt equals to UPDATED_LOINCSBT
        defaultAntibioticShouldNotBeFound("loincsbt.equals=" + UPDATED_LOINCSBT);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt not equals to DEFAULT_LOINCSBT
        defaultAntibioticShouldNotBeFound("loincsbt.notEquals=" + DEFAULT_LOINCSBT);

        // Get all the antibioticList where loincsbt not equals to UPDATED_LOINCSBT
        defaultAntibioticShouldBeFound("loincsbt.notEquals=" + UPDATED_LOINCSBT);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt in DEFAULT_LOINCSBT or UPDATED_LOINCSBT
        defaultAntibioticShouldBeFound("loincsbt.in=" + DEFAULT_LOINCSBT + "," + UPDATED_LOINCSBT);

        // Get all the antibioticList where loincsbt equals to UPDATED_LOINCSBT
        defaultAntibioticShouldNotBeFound("loincsbt.in=" + UPDATED_LOINCSBT);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt is not null
        defaultAntibioticShouldBeFound("loincsbt.specified=true");

        // Get all the antibioticList where loincsbt is null
        defaultAntibioticShouldNotBeFound("loincsbt.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt contains DEFAULT_LOINCSBT
        defaultAntibioticShouldBeFound("loincsbt.contains=" + DEFAULT_LOINCSBT);

        // Get all the antibioticList where loincsbt contains UPDATED_LOINCSBT
        defaultAntibioticShouldNotBeFound("loincsbt.contains=" + UPDATED_LOINCSBT);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincsbtNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincsbt does not contain DEFAULT_LOINCSBT
        defaultAntibioticShouldNotBeFound("loincsbt.doesNotContain=" + DEFAULT_LOINCSBT);

        // Get all the antibioticList where loincsbt does not contain UPDATED_LOINCSBT
        defaultAntibioticShouldBeFound("loincsbt.doesNotContain=" + UPDATED_LOINCSBT);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc equals to DEFAULT_LOINCMLC
        defaultAntibioticShouldBeFound("loincmlc.equals=" + DEFAULT_LOINCMLC);

        // Get all the antibioticList where loincmlc equals to UPDATED_LOINCMLC
        defaultAntibioticShouldNotBeFound("loincmlc.equals=" + UPDATED_LOINCMLC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc not equals to DEFAULT_LOINCMLC
        defaultAntibioticShouldNotBeFound("loincmlc.notEquals=" + DEFAULT_LOINCMLC);

        // Get all the antibioticList where loincmlc not equals to UPDATED_LOINCMLC
        defaultAntibioticShouldBeFound("loincmlc.notEquals=" + UPDATED_LOINCMLC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc in DEFAULT_LOINCMLC or UPDATED_LOINCMLC
        defaultAntibioticShouldBeFound("loincmlc.in=" + DEFAULT_LOINCMLC + "," + UPDATED_LOINCMLC);

        // Get all the antibioticList where loincmlc equals to UPDATED_LOINCMLC
        defaultAntibioticShouldNotBeFound("loincmlc.in=" + UPDATED_LOINCMLC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc is not null
        defaultAntibioticShouldBeFound("loincmlc.specified=true");

        // Get all the antibioticList where loincmlc is null
        defaultAntibioticShouldNotBeFound("loincmlc.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc contains DEFAULT_LOINCMLC
        defaultAntibioticShouldBeFound("loincmlc.contains=" + DEFAULT_LOINCMLC);

        // Get all the antibioticList where loincmlc contains UPDATED_LOINCMLC
        defaultAntibioticShouldNotBeFound("loincmlc.contains=" + UPDATED_LOINCMLC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByLoincmlcNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where loincmlc does not contain DEFAULT_LOINCMLC
        defaultAntibioticShouldNotBeFound("loincmlc.doesNotContain=" + DEFAULT_LOINCMLC);

        // Get all the antibioticList where loincmlc does not contain UPDATED_LOINCMLC
        defaultAntibioticShouldBeFound("loincmlc.doesNotContain=" + UPDATED_LOINCMLC);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered equals to DEFAULT_DATE_ENTERED
        defaultAntibioticShouldBeFound("dateEntered.equals=" + DEFAULT_DATE_ENTERED);

        // Get all the antibioticList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultAntibioticShouldNotBeFound("dateEntered.equals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered not equals to DEFAULT_DATE_ENTERED
        defaultAntibioticShouldNotBeFound("dateEntered.notEquals=" + DEFAULT_DATE_ENTERED);

        // Get all the antibioticList where dateEntered not equals to UPDATED_DATE_ENTERED
        defaultAntibioticShouldBeFound("dateEntered.notEquals=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered in DEFAULT_DATE_ENTERED or UPDATED_DATE_ENTERED
        defaultAntibioticShouldBeFound("dateEntered.in=" + DEFAULT_DATE_ENTERED + "," + UPDATED_DATE_ENTERED);

        // Get all the antibioticList where dateEntered equals to UPDATED_DATE_ENTERED
        defaultAntibioticShouldNotBeFound("dateEntered.in=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered is not null
        defaultAntibioticShouldBeFound("dateEntered.specified=true");

        // Get all the antibioticList where dateEntered is null
        defaultAntibioticShouldNotBeFound("dateEntered.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered contains DEFAULT_DATE_ENTERED
        defaultAntibioticShouldBeFound("dateEntered.contains=" + DEFAULT_DATE_ENTERED);

        // Get all the antibioticList where dateEntered contains UPDATED_DATE_ENTERED
        defaultAntibioticShouldNotBeFound("dateEntered.contains=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateEnteredNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateEntered does not contain DEFAULT_DATE_ENTERED
        defaultAntibioticShouldNotBeFound("dateEntered.doesNotContain=" + DEFAULT_DATE_ENTERED);

        // Get all the antibioticList where dateEntered does not contain UPDATED_DATE_ENTERED
        defaultAntibioticShouldBeFound("dateEntered.doesNotContain=" + UPDATED_DATE_ENTERED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified equals to DEFAULT_DATE_MODIFIED
        defaultAntibioticShouldBeFound("dateModified.equals=" + DEFAULT_DATE_MODIFIED);

        // Get all the antibioticList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultAntibioticShouldNotBeFound("dateModified.equals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified not equals to DEFAULT_DATE_MODIFIED
        defaultAntibioticShouldNotBeFound("dateModified.notEquals=" + DEFAULT_DATE_MODIFIED);

        // Get all the antibioticList where dateModified not equals to UPDATED_DATE_MODIFIED
        defaultAntibioticShouldBeFound("dateModified.notEquals=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified in DEFAULT_DATE_MODIFIED or UPDATED_DATE_MODIFIED
        defaultAntibioticShouldBeFound("dateModified.in=" + DEFAULT_DATE_MODIFIED + "," + UPDATED_DATE_MODIFIED);

        // Get all the antibioticList where dateModified equals to UPDATED_DATE_MODIFIED
        defaultAntibioticShouldNotBeFound("dateModified.in=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified is not null
        defaultAntibioticShouldBeFound("dateModified.specified=true");

        // Get all the antibioticList where dateModified is null
        defaultAntibioticShouldNotBeFound("dateModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified contains DEFAULT_DATE_MODIFIED
        defaultAntibioticShouldBeFound("dateModified.contains=" + DEFAULT_DATE_MODIFIED);

        // Get all the antibioticList where dateModified contains UPDATED_DATE_MODIFIED
        defaultAntibioticShouldNotBeFound("dateModified.contains=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByDateModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where dateModified does not contain DEFAULT_DATE_MODIFIED
        defaultAntibioticShouldNotBeFound("dateModified.doesNotContain=" + DEFAULT_DATE_MODIFIED);

        // Get all the antibioticList where dateModified does not contain UPDATED_DATE_MODIFIED
        defaultAntibioticShouldBeFound("dateModified.doesNotContain=" + UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments equals to DEFAULT_COMMENTS
        defaultAntibioticShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the antibioticList where comments equals to UPDATED_COMMENTS
        defaultAntibioticShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments not equals to DEFAULT_COMMENTS
        defaultAntibioticShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the antibioticList where comments not equals to UPDATED_COMMENTS
        defaultAntibioticShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultAntibioticShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the antibioticList where comments equals to UPDATED_COMMENTS
        defaultAntibioticShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments is not null
        defaultAntibioticShouldBeFound("comments.specified=true");

        // Get all the antibioticList where comments is null
        defaultAntibioticShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments contains DEFAULT_COMMENTS
        defaultAntibioticShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the antibioticList where comments contains UPDATED_COMMENTS
        defaultAntibioticShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllAntibioticsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        // Get all the antibioticList where comments does not contain DEFAULT_COMMENTS
        defaultAntibioticShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the antibioticList where comments does not contain UPDATED_COMMENTS
        defaultAntibioticShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAntibioticShouldBeFound(String filter) throws Exception {
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antibiotic.getId().intValue())))
            .andExpect(jsonPath("$.[*].whonetAbxCode").value(hasItem(DEFAULT_WHONET_ABX_CODE)))
            .andExpect(jsonPath("$.[*].whoCode").value(hasItem(DEFAULT_WHO_CODE)))
            .andExpect(jsonPath("$.[*].dinCode").value(hasItem(DEFAULT_DIN_CODE)))
            .andExpect(jsonPath("$.[*].jacCode").value(hasItem(DEFAULT_JAC_CODE)))
            .andExpect(jsonPath("$.[*].eucastCode").value(hasItem(DEFAULT_EUCAST_CODE)))
            .andExpect(jsonPath("$.[*].userCode").value(hasItem(DEFAULT_USER_CODE)))
            .andExpect(jsonPath("$.[*].antibiotic").value(hasItem(DEFAULT_ANTIBIOTIC)))
            .andExpect(jsonPath("$.[*].guidelines").value(hasItem(DEFAULT_GUIDELINES)))
            .andExpect(jsonPath("$.[*].clsi").value(hasItem(DEFAULT_CLSI)))
            .andExpect(jsonPath("$.[*].eucast").value(hasItem(DEFAULT_EUCAST)))
            .andExpect(jsonPath("$.[*].sfm").value(hasItem(DEFAULT_SFM)))
            .andExpect(jsonPath("$.[*].srga").value(hasItem(DEFAULT_SRGA)))
            .andExpect(jsonPath("$.[*].bsac").value(hasItem(DEFAULT_BSAC)))
            .andExpect(jsonPath("$.[*].din").value(hasItem(DEFAULT_DIN)))
            .andExpect(jsonPath("$.[*].neo").value(hasItem(DEFAULT_NEO)))
            .andExpect(jsonPath("$.[*].afa").value(hasItem(DEFAULT_AFA)))
            .andExpect(jsonPath("$.[*].abxNumber").value(hasItem(DEFAULT_ABX_NUMBER)))
            .andExpect(jsonPath("$.[*].potency").value(hasItem(DEFAULT_POTENCY)))
            .andExpect(jsonPath("$.[*].atcCode").value(hasItem(DEFAULT_ATC_CODE)))
            .andExpect(jsonPath("$.[*].profClass").value(hasItem(DEFAULT_PROF_CLASS)))
            .andExpect(jsonPath("$.[*].ciaCategory").value(hasItem(DEFAULT_CIA_CATEGORY)))
            .andExpect(jsonPath("$.[*].clsiOrder").value(hasItem(DEFAULT_CLSI_ORDER)))
            .andExpect(jsonPath("$.[*].eucastOrder").value(hasItem(DEFAULT_EUCAST_ORDER)))
            .andExpect(jsonPath("$.[*].human").value(hasItem(DEFAULT_HUMAN)))
            .andExpect(jsonPath("$.[*].veterinary").value(hasItem(DEFAULT_VETERINARY)))
            .andExpect(jsonPath("$.[*].animalGp").value(hasItem(DEFAULT_ANIMAL_GP)))
            .andExpect(jsonPath("$.[*].loinccomp").value(hasItem(DEFAULT_LOINCCOMP)))
            .andExpect(jsonPath("$.[*].loincgen").value(hasItem(DEFAULT_LOINCGEN)))
            .andExpect(jsonPath("$.[*].loincdisk").value(hasItem(DEFAULT_LOINCDISK)))
            .andExpect(jsonPath("$.[*].loincmic").value(hasItem(DEFAULT_LOINCMIC)))
            .andExpect(jsonPath("$.[*].loincetest").value(hasItem(DEFAULT_LOINCETEST)))
            .andExpect(jsonPath("$.[*].loincslow").value(hasItem(DEFAULT_LOINCSLOW)))
            .andExpect(jsonPath("$.[*].loincafb").value(hasItem(DEFAULT_LOINCAFB)))
            .andExpect(jsonPath("$.[*].loincsbt").value(hasItem(DEFAULT_LOINCSBT)))
            .andExpect(jsonPath("$.[*].loincmlc").value(hasItem(DEFAULT_LOINCMLC)))
            .andExpect(jsonPath("$.[*].dateEntered").value(hasItem(DEFAULT_DATE_ENTERED)))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAntibioticShouldNotBeFound(String filter) throws Exception {
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAntibioticMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAntibiotic() throws Exception {
        // Get the antibiotic
        restAntibioticMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic
        Antibiotic updatedAntibiotic = antibioticRepository.findById(antibiotic.getId()).get();
        // Disconnect from session so that the updates on updatedAntibiotic are not directly saved in db
        em.detach(updatedAntibiotic);
        updatedAntibiotic
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whoCode(UPDATED_WHO_CODE)
            .dinCode(UPDATED_DIN_CODE)
            .jacCode(UPDATED_JAC_CODE)
            .eucastCode(UPDATED_EUCAST_CODE)
            .userCode(UPDATED_USER_CODE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .guidelines(UPDATED_GUIDELINES)
            .clsi(UPDATED_CLSI)
            .eucast(UPDATED_EUCAST)
            .sfm(UPDATED_SFM)
            .srga(UPDATED_SRGA)
            .bsac(UPDATED_BSAC)
            .din(UPDATED_DIN)
            .neo(UPDATED_NEO)
            .afa(UPDATED_AFA)
            .abxNumber(UPDATED_ABX_NUMBER)
            .potency(UPDATED_POTENCY)
            .atcCode(UPDATED_ATC_CODE)
            .profClass(UPDATED_PROF_CLASS)
            .ciaCategory(UPDATED_CIA_CATEGORY)
            .clsiOrder(UPDATED_CLSI_ORDER)
            .eucastOrder(UPDATED_EUCAST_ORDER)
            .human(UPDATED_HUMAN)
            .veterinary(UPDATED_VETERINARY)
            .animalGp(UPDATED_ANIMAL_GP)
            .loinccomp(UPDATED_LOINCCOMP)
            .loincgen(UPDATED_LOINCGEN)
            .loincdisk(UPDATED_LOINCDISK)
            .loincmic(UPDATED_LOINCMIC)
            .loincetest(UPDATED_LOINCETEST)
            .loincslow(UPDATED_LOINCSLOW)
            .loincafb(UPDATED_LOINCAFB)
            .loincsbt(UPDATED_LOINCSBT)
            .loincmlc(UPDATED_LOINCMLC)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(updatedAntibiotic);

        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, antibioticDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testAntibiotic.getWhoCode()).isEqualTo(UPDATED_WHO_CODE);
        assertThat(testAntibiotic.getDinCode()).isEqualTo(UPDATED_DIN_CODE);
        assertThat(testAntibiotic.getJacCode()).isEqualTo(UPDATED_JAC_CODE);
        assertThat(testAntibiotic.getEucastCode()).isEqualTo(UPDATED_EUCAST_CODE);
        assertThat(testAntibiotic.getUserCode()).isEqualTo(UPDATED_USER_CODE);
        assertThat(testAntibiotic.getAntibiotic()).isEqualTo(UPDATED_ANTIBIOTIC);
        assertThat(testAntibiotic.getGuidelines()).isEqualTo(UPDATED_GUIDELINES);
        assertThat(testAntibiotic.getClsi()).isEqualTo(UPDATED_CLSI);
        assertThat(testAntibiotic.getEucast()).isEqualTo(UPDATED_EUCAST);
        assertThat(testAntibiotic.getSfm()).isEqualTo(UPDATED_SFM);
        assertThat(testAntibiotic.getSrga()).isEqualTo(UPDATED_SRGA);
        assertThat(testAntibiotic.getBsac()).isEqualTo(UPDATED_BSAC);
        assertThat(testAntibiotic.getDin()).isEqualTo(UPDATED_DIN);
        assertThat(testAntibiotic.getNeo()).isEqualTo(UPDATED_NEO);
        assertThat(testAntibiotic.getAfa()).isEqualTo(UPDATED_AFA);
        assertThat(testAntibiotic.getAbxNumber()).isEqualTo(UPDATED_ABX_NUMBER);
        assertThat(testAntibiotic.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testAntibiotic.getAtcCode()).isEqualTo(UPDATED_ATC_CODE);
        assertThat(testAntibiotic.getProfClass()).isEqualTo(UPDATED_PROF_CLASS);
        assertThat(testAntibiotic.getCiaCategory()).isEqualTo(UPDATED_CIA_CATEGORY);
        assertThat(testAntibiotic.getClsiOrder()).isEqualTo(UPDATED_CLSI_ORDER);
        assertThat(testAntibiotic.getEucastOrder()).isEqualTo(UPDATED_EUCAST_ORDER);
        assertThat(testAntibiotic.getHuman()).isEqualTo(UPDATED_HUMAN);
        assertThat(testAntibiotic.getVeterinary()).isEqualTo(UPDATED_VETERINARY);
        assertThat(testAntibiotic.getAnimalGp()).isEqualTo(UPDATED_ANIMAL_GP);
        assertThat(testAntibiotic.getLoinccomp()).isEqualTo(UPDATED_LOINCCOMP);
        assertThat(testAntibiotic.getLoincgen()).isEqualTo(UPDATED_LOINCGEN);
        assertThat(testAntibiotic.getLoincdisk()).isEqualTo(UPDATED_LOINCDISK);
        assertThat(testAntibiotic.getLoincmic()).isEqualTo(UPDATED_LOINCMIC);
        assertThat(testAntibiotic.getLoincetest()).isEqualTo(UPDATED_LOINCETEST);
        assertThat(testAntibiotic.getLoincslow()).isEqualTo(UPDATED_LOINCSLOW);
        assertThat(testAntibiotic.getLoincafb()).isEqualTo(UPDATED_LOINCAFB);
        assertThat(testAntibiotic.getLoincsbt()).isEqualTo(UPDATED_LOINCSBT);
        assertThat(testAntibiotic.getLoincmlc()).isEqualTo(UPDATED_LOINCMLC);
        assertThat(testAntibiotic.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testAntibiotic.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testAntibiotic.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, antibioticDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(antibioticDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAntibioticWithPatch() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic using partial update
        Antibiotic partialUpdatedAntibiotic = new Antibiotic();
        partialUpdatedAntibiotic.setId(antibiotic.getId());

        partialUpdatedAntibiotic
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .dinCode(UPDATED_DIN_CODE)
            .eucastCode(UPDATED_EUCAST_CODE)
            .userCode(UPDATED_USER_CODE)
            .clsi(UPDATED_CLSI)
            .sfm(UPDATED_SFM)
            .din(UPDATED_DIN)
            .potency(UPDATED_POTENCY)
            .atcCode(UPDATED_ATC_CODE)
            .clsiOrder(UPDATED_CLSI_ORDER)
            .eucastOrder(UPDATED_EUCAST_ORDER)
            .human(UPDATED_HUMAN)
            .animalGp(UPDATED_ANIMAL_GP)
            .loinccomp(UPDATED_LOINCCOMP)
            .loincgen(UPDATED_LOINCGEN)
            .loincmic(UPDATED_LOINCMIC)
            .loincsbt(UPDATED_LOINCSBT)
            .loincmlc(UPDATED_LOINCMLC)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);

        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibiotic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibiotic))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testAntibiotic.getWhoCode()).isEqualTo(DEFAULT_WHO_CODE);
        assertThat(testAntibiotic.getDinCode()).isEqualTo(UPDATED_DIN_CODE);
        assertThat(testAntibiotic.getJacCode()).isEqualTo(DEFAULT_JAC_CODE);
        assertThat(testAntibiotic.getEucastCode()).isEqualTo(UPDATED_EUCAST_CODE);
        assertThat(testAntibiotic.getUserCode()).isEqualTo(UPDATED_USER_CODE);
        assertThat(testAntibiotic.getAntibiotic()).isEqualTo(DEFAULT_ANTIBIOTIC);
        assertThat(testAntibiotic.getGuidelines()).isEqualTo(DEFAULT_GUIDELINES);
        assertThat(testAntibiotic.getClsi()).isEqualTo(UPDATED_CLSI);
        assertThat(testAntibiotic.getEucast()).isEqualTo(DEFAULT_EUCAST);
        assertThat(testAntibiotic.getSfm()).isEqualTo(UPDATED_SFM);
        assertThat(testAntibiotic.getSrga()).isEqualTo(DEFAULT_SRGA);
        assertThat(testAntibiotic.getBsac()).isEqualTo(DEFAULT_BSAC);
        assertThat(testAntibiotic.getDin()).isEqualTo(UPDATED_DIN);
        assertThat(testAntibiotic.getNeo()).isEqualTo(DEFAULT_NEO);
        assertThat(testAntibiotic.getAfa()).isEqualTo(DEFAULT_AFA);
        assertThat(testAntibiotic.getAbxNumber()).isEqualTo(DEFAULT_ABX_NUMBER);
        assertThat(testAntibiotic.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testAntibiotic.getAtcCode()).isEqualTo(UPDATED_ATC_CODE);
        assertThat(testAntibiotic.getProfClass()).isEqualTo(DEFAULT_PROF_CLASS);
        assertThat(testAntibiotic.getCiaCategory()).isEqualTo(DEFAULT_CIA_CATEGORY);
        assertThat(testAntibiotic.getClsiOrder()).isEqualTo(UPDATED_CLSI_ORDER);
        assertThat(testAntibiotic.getEucastOrder()).isEqualTo(UPDATED_EUCAST_ORDER);
        assertThat(testAntibiotic.getHuman()).isEqualTo(UPDATED_HUMAN);
        assertThat(testAntibiotic.getVeterinary()).isEqualTo(DEFAULT_VETERINARY);
        assertThat(testAntibiotic.getAnimalGp()).isEqualTo(UPDATED_ANIMAL_GP);
        assertThat(testAntibiotic.getLoinccomp()).isEqualTo(UPDATED_LOINCCOMP);
        assertThat(testAntibiotic.getLoincgen()).isEqualTo(UPDATED_LOINCGEN);
        assertThat(testAntibiotic.getLoincdisk()).isEqualTo(DEFAULT_LOINCDISK);
        assertThat(testAntibiotic.getLoincmic()).isEqualTo(UPDATED_LOINCMIC);
        assertThat(testAntibiotic.getLoincetest()).isEqualTo(DEFAULT_LOINCETEST);
        assertThat(testAntibiotic.getLoincslow()).isEqualTo(DEFAULT_LOINCSLOW);
        assertThat(testAntibiotic.getLoincafb()).isEqualTo(DEFAULT_LOINCAFB);
        assertThat(testAntibiotic.getLoincsbt()).isEqualTo(UPDATED_LOINCSBT);
        assertThat(testAntibiotic.getLoincmlc()).isEqualTo(UPDATED_LOINCMLC);
        assertThat(testAntibiotic.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testAntibiotic.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testAntibiotic.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateAntibioticWithPatch() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();

        // Update the antibiotic using partial update
        Antibiotic partialUpdatedAntibiotic = new Antibiotic();
        partialUpdatedAntibiotic.setId(antibiotic.getId());

        partialUpdatedAntibiotic
            .whonetAbxCode(UPDATED_WHONET_ABX_CODE)
            .whoCode(UPDATED_WHO_CODE)
            .dinCode(UPDATED_DIN_CODE)
            .jacCode(UPDATED_JAC_CODE)
            .eucastCode(UPDATED_EUCAST_CODE)
            .userCode(UPDATED_USER_CODE)
            .antibiotic(UPDATED_ANTIBIOTIC)
            .guidelines(UPDATED_GUIDELINES)
            .clsi(UPDATED_CLSI)
            .eucast(UPDATED_EUCAST)
            .sfm(UPDATED_SFM)
            .srga(UPDATED_SRGA)
            .bsac(UPDATED_BSAC)
            .din(UPDATED_DIN)
            .neo(UPDATED_NEO)
            .afa(UPDATED_AFA)
            .abxNumber(UPDATED_ABX_NUMBER)
            .potency(UPDATED_POTENCY)
            .atcCode(UPDATED_ATC_CODE)
            .profClass(UPDATED_PROF_CLASS)
            .ciaCategory(UPDATED_CIA_CATEGORY)
            .clsiOrder(UPDATED_CLSI_ORDER)
            .eucastOrder(UPDATED_EUCAST_ORDER)
            .human(UPDATED_HUMAN)
            .veterinary(UPDATED_VETERINARY)
            .animalGp(UPDATED_ANIMAL_GP)
            .loinccomp(UPDATED_LOINCCOMP)
            .loincgen(UPDATED_LOINCGEN)
            .loincdisk(UPDATED_LOINCDISK)
            .loincmic(UPDATED_LOINCMIC)
            .loincetest(UPDATED_LOINCETEST)
            .loincslow(UPDATED_LOINCSLOW)
            .loincafb(UPDATED_LOINCAFB)
            .loincsbt(UPDATED_LOINCSBT)
            .loincmlc(UPDATED_LOINCMLC)
            .dateEntered(UPDATED_DATE_ENTERED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .comments(UPDATED_COMMENTS);

        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAntibiotic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAntibiotic))
            )
            .andExpect(status().isOk());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
        Antibiotic testAntibiotic = antibioticList.get(antibioticList.size() - 1);
        assertThat(testAntibiotic.getWhonetAbxCode()).isEqualTo(UPDATED_WHONET_ABX_CODE);
        assertThat(testAntibiotic.getWhoCode()).isEqualTo(UPDATED_WHO_CODE);
        assertThat(testAntibiotic.getDinCode()).isEqualTo(UPDATED_DIN_CODE);
        assertThat(testAntibiotic.getJacCode()).isEqualTo(UPDATED_JAC_CODE);
        assertThat(testAntibiotic.getEucastCode()).isEqualTo(UPDATED_EUCAST_CODE);
        assertThat(testAntibiotic.getUserCode()).isEqualTo(UPDATED_USER_CODE);
        assertThat(testAntibiotic.getAntibiotic()).isEqualTo(UPDATED_ANTIBIOTIC);
        assertThat(testAntibiotic.getGuidelines()).isEqualTo(UPDATED_GUIDELINES);
        assertThat(testAntibiotic.getClsi()).isEqualTo(UPDATED_CLSI);
        assertThat(testAntibiotic.getEucast()).isEqualTo(UPDATED_EUCAST);
        assertThat(testAntibiotic.getSfm()).isEqualTo(UPDATED_SFM);
        assertThat(testAntibiotic.getSrga()).isEqualTo(UPDATED_SRGA);
        assertThat(testAntibiotic.getBsac()).isEqualTo(UPDATED_BSAC);
        assertThat(testAntibiotic.getDin()).isEqualTo(UPDATED_DIN);
        assertThat(testAntibiotic.getNeo()).isEqualTo(UPDATED_NEO);
        assertThat(testAntibiotic.getAfa()).isEqualTo(UPDATED_AFA);
        assertThat(testAntibiotic.getAbxNumber()).isEqualTo(UPDATED_ABX_NUMBER);
        assertThat(testAntibiotic.getPotency()).isEqualTo(UPDATED_POTENCY);
        assertThat(testAntibiotic.getAtcCode()).isEqualTo(UPDATED_ATC_CODE);
        assertThat(testAntibiotic.getProfClass()).isEqualTo(UPDATED_PROF_CLASS);
        assertThat(testAntibiotic.getCiaCategory()).isEqualTo(UPDATED_CIA_CATEGORY);
        assertThat(testAntibiotic.getClsiOrder()).isEqualTo(UPDATED_CLSI_ORDER);
        assertThat(testAntibiotic.getEucastOrder()).isEqualTo(UPDATED_EUCAST_ORDER);
        assertThat(testAntibiotic.getHuman()).isEqualTo(UPDATED_HUMAN);
        assertThat(testAntibiotic.getVeterinary()).isEqualTo(UPDATED_VETERINARY);
        assertThat(testAntibiotic.getAnimalGp()).isEqualTo(UPDATED_ANIMAL_GP);
        assertThat(testAntibiotic.getLoinccomp()).isEqualTo(UPDATED_LOINCCOMP);
        assertThat(testAntibiotic.getLoincgen()).isEqualTo(UPDATED_LOINCGEN);
        assertThat(testAntibiotic.getLoincdisk()).isEqualTo(UPDATED_LOINCDISK);
        assertThat(testAntibiotic.getLoincmic()).isEqualTo(UPDATED_LOINCMIC);
        assertThat(testAntibiotic.getLoincetest()).isEqualTo(UPDATED_LOINCETEST);
        assertThat(testAntibiotic.getLoincslow()).isEqualTo(UPDATED_LOINCSLOW);
        assertThat(testAntibiotic.getLoincafb()).isEqualTo(UPDATED_LOINCAFB);
        assertThat(testAntibiotic.getLoincsbt()).isEqualTo(UPDATED_LOINCSBT);
        assertThat(testAntibiotic.getLoincmlc()).isEqualTo(UPDATED_LOINCMLC);
        assertThat(testAntibiotic.getDateEntered()).isEqualTo(UPDATED_DATE_ENTERED);
        assertThat(testAntibiotic.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testAntibiotic.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, antibioticDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAntibiotic() throws Exception {
        int databaseSizeBeforeUpdate = antibioticRepository.findAll().size();
        antibiotic.setId(count.incrementAndGet());

        // Create the Antibiotic
        AntibioticDTO antibioticDTO = antibioticMapper.toDto(antibiotic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAntibioticMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(antibioticDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Antibiotic in the database
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAntibiotic() throws Exception {
        // Initialize the database
        antibioticRepository.saveAndFlush(antibiotic);

        int databaseSizeBeforeDelete = antibioticRepository.findAll().size();

        // Delete the antibiotic
        restAntibioticMockMvc
            .perform(delete(ENTITY_API_URL_ID, antibiotic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Antibiotic> antibioticList = antibioticRepository.findAll();
        assertThat(antibioticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
