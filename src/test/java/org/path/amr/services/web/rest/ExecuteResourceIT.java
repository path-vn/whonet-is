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
import org.path.amr.services.domain.Execute;
import org.path.amr.services.repository.ExecuteRepository;
import org.path.amr.services.service.criteria.ExecuteCriteria;
import org.path.amr.services.service.dto.ExecuteDTO;
import org.path.amr.services.service.mapper.ExecuteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ExecuteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExecuteResourceIT {

    private static final String DEFAULT_REQUEST = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_STARTED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_STARTED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_TIME = 1L;
    private static final Long UPDATED_TIME = 2L;
    private static final Long SMALLER_TIME = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/executes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExecuteRepository executeRepository;

    @Autowired
    private ExecuteMapper executeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExecuteMockMvc;

    private Execute execute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Execute createEntity(EntityManager em) {
        Execute execute = new Execute()
            .request(DEFAULT_REQUEST)
            .response(DEFAULT_RESPONSE)
            .startedAt(DEFAULT_STARTED_AT)
            .time(DEFAULT_TIME);
        return execute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Execute createUpdatedEntity(EntityManager em) {
        Execute execute = new Execute()
            .request(UPDATED_REQUEST)
            .response(UPDATED_RESPONSE)
            .startedAt(UPDATED_STARTED_AT)
            .time(UPDATED_TIME);
        return execute;
    }

    @BeforeEach
    public void initTest() {
        execute = createEntity(em);
    }

    @Test
    @Transactional
    void createExecute() throws Exception {
        int databaseSizeBeforeCreate = executeRepository.findAll().size();
        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);
        restExecuteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(executeDTO)))
            .andExpect(status().isCreated());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeCreate + 1);
        Execute testExecute = executeList.get(executeList.size() - 1);
        assertThat(testExecute.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testExecute.getResponse()).isEqualTo(DEFAULT_RESPONSE);
        assertThat(testExecute.getStartedAt()).isEqualTo(DEFAULT_STARTED_AT);
        assertThat(testExecute.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    void createExecuteWithExistingId() throws Exception {
        // Create the Execute with an existing ID
        execute.setId(1L);
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        int databaseSizeBeforeCreate = executeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExecuteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(executeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExecutes() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(execute.getId().intValue())))
            .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE)))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(sameInstant(DEFAULT_STARTED_AT))))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())));
    }

    @Test
    @Transactional
    void getExecute() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get the execute
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL_ID, execute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(execute.getId().intValue()))
            .andExpect(jsonPath("$.request").value(DEFAULT_REQUEST))
            .andExpect(jsonPath("$.response").value(DEFAULT_RESPONSE))
            .andExpect(jsonPath("$.startedAt").value(sameInstant(DEFAULT_STARTED_AT)))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()));
    }

    @Test
    @Transactional
    void getExecutesByIdFiltering() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        Long id = execute.getId();

        defaultExecuteShouldBeFound("id.equals=" + id);
        defaultExecuteShouldNotBeFound("id.notEquals=" + id);

        defaultExecuteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExecuteShouldNotBeFound("id.greaterThan=" + id);

        defaultExecuteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExecuteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExecutesByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request equals to DEFAULT_REQUEST
        defaultExecuteShouldBeFound("request.equals=" + DEFAULT_REQUEST);

        // Get all the executeList where request equals to UPDATED_REQUEST
        defaultExecuteShouldNotBeFound("request.equals=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllExecutesByRequestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request not equals to DEFAULT_REQUEST
        defaultExecuteShouldNotBeFound("request.notEquals=" + DEFAULT_REQUEST);

        // Get all the executeList where request not equals to UPDATED_REQUEST
        defaultExecuteShouldBeFound("request.notEquals=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllExecutesByRequestIsInShouldWork() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request in DEFAULT_REQUEST or UPDATED_REQUEST
        defaultExecuteShouldBeFound("request.in=" + DEFAULT_REQUEST + "," + UPDATED_REQUEST);

        // Get all the executeList where request equals to UPDATED_REQUEST
        defaultExecuteShouldNotBeFound("request.in=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllExecutesByRequestIsNullOrNotNull() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request is not null
        defaultExecuteShouldBeFound("request.specified=true");

        // Get all the executeList where request is null
        defaultExecuteShouldNotBeFound("request.specified=false");
    }

    @Test
    @Transactional
    void getAllExecutesByRequestContainsSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request contains DEFAULT_REQUEST
        defaultExecuteShouldBeFound("request.contains=" + DEFAULT_REQUEST);

        // Get all the executeList where request contains UPDATED_REQUEST
        defaultExecuteShouldNotBeFound("request.contains=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllExecutesByRequestNotContainsSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where request does not contain DEFAULT_REQUEST
        defaultExecuteShouldNotBeFound("request.doesNotContain=" + DEFAULT_REQUEST);

        // Get all the executeList where request does not contain UPDATED_REQUEST
        defaultExecuteShouldBeFound("request.doesNotContain=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllExecutesByResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response equals to DEFAULT_RESPONSE
        defaultExecuteShouldBeFound("response.equals=" + DEFAULT_RESPONSE);

        // Get all the executeList where response equals to UPDATED_RESPONSE
        defaultExecuteShouldNotBeFound("response.equals=" + UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void getAllExecutesByResponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response not equals to DEFAULT_RESPONSE
        defaultExecuteShouldNotBeFound("response.notEquals=" + DEFAULT_RESPONSE);

        // Get all the executeList where response not equals to UPDATED_RESPONSE
        defaultExecuteShouldBeFound("response.notEquals=" + UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void getAllExecutesByResponseIsInShouldWork() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response in DEFAULT_RESPONSE or UPDATED_RESPONSE
        defaultExecuteShouldBeFound("response.in=" + DEFAULT_RESPONSE + "," + UPDATED_RESPONSE);

        // Get all the executeList where response equals to UPDATED_RESPONSE
        defaultExecuteShouldNotBeFound("response.in=" + UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void getAllExecutesByResponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response is not null
        defaultExecuteShouldBeFound("response.specified=true");

        // Get all the executeList where response is null
        defaultExecuteShouldNotBeFound("response.specified=false");
    }

    @Test
    @Transactional
    void getAllExecutesByResponseContainsSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response contains DEFAULT_RESPONSE
        defaultExecuteShouldBeFound("response.contains=" + DEFAULT_RESPONSE);

        // Get all the executeList where response contains UPDATED_RESPONSE
        defaultExecuteShouldNotBeFound("response.contains=" + UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void getAllExecutesByResponseNotContainsSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where response does not contain DEFAULT_RESPONSE
        defaultExecuteShouldNotBeFound("response.doesNotContain=" + DEFAULT_RESPONSE);

        // Get all the executeList where response does not contain UPDATED_RESPONSE
        defaultExecuteShouldBeFound("response.doesNotContain=" + UPDATED_RESPONSE);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt equals to DEFAULT_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.equals=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt equals to UPDATED_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.equals=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt not equals to DEFAULT_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.notEquals=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt not equals to UPDATED_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.notEquals=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsInShouldWork() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt in DEFAULT_STARTED_AT or UPDATED_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.in=" + DEFAULT_STARTED_AT + "," + UPDATED_STARTED_AT);

        // Get all the executeList where startedAt equals to UPDATED_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.in=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt is not null
        defaultExecuteShouldBeFound("startedAt.specified=true");

        // Get all the executeList where startedAt is null
        defaultExecuteShouldNotBeFound("startedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt is greater than or equal to DEFAULT_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.greaterThanOrEqual=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt is greater than or equal to UPDATED_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.greaterThanOrEqual=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt is less than or equal to DEFAULT_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.lessThanOrEqual=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt is less than or equal to SMALLER_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.lessThanOrEqual=" + SMALLER_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt is less than DEFAULT_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.lessThan=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt is less than UPDATED_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.lessThan=" + UPDATED_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByStartedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where startedAt is greater than DEFAULT_STARTED_AT
        defaultExecuteShouldNotBeFound("startedAt.greaterThan=" + DEFAULT_STARTED_AT);

        // Get all the executeList where startedAt is greater than SMALLER_STARTED_AT
        defaultExecuteShouldBeFound("startedAt.greaterThan=" + SMALLER_STARTED_AT);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time equals to DEFAULT_TIME
        defaultExecuteShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the executeList where time equals to UPDATED_TIME
        defaultExecuteShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time not equals to DEFAULT_TIME
        defaultExecuteShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the executeList where time not equals to UPDATED_TIME
        defaultExecuteShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time in DEFAULT_TIME or UPDATED_TIME
        defaultExecuteShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the executeList where time equals to UPDATED_TIME
        defaultExecuteShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time is not null
        defaultExecuteShouldBeFound("time.specified=true");

        // Get all the executeList where time is null
        defaultExecuteShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time is greater than or equal to DEFAULT_TIME
        defaultExecuteShouldBeFound("time.greaterThanOrEqual=" + DEFAULT_TIME);

        // Get all the executeList where time is greater than or equal to UPDATED_TIME
        defaultExecuteShouldNotBeFound("time.greaterThanOrEqual=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time is less than or equal to DEFAULT_TIME
        defaultExecuteShouldBeFound("time.lessThanOrEqual=" + DEFAULT_TIME);

        // Get all the executeList where time is less than or equal to SMALLER_TIME
        defaultExecuteShouldNotBeFound("time.lessThanOrEqual=" + SMALLER_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time is less than DEFAULT_TIME
        defaultExecuteShouldNotBeFound("time.lessThan=" + DEFAULT_TIME);

        // Get all the executeList where time is less than UPDATED_TIME
        defaultExecuteShouldBeFound("time.lessThan=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllExecutesByTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        // Get all the executeList where time is greater than DEFAULT_TIME
        defaultExecuteShouldNotBeFound("time.greaterThan=" + DEFAULT_TIME);

        // Get all the executeList where time is greater than SMALLER_TIME
        defaultExecuteShouldBeFound("time.greaterThan=" + SMALLER_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExecuteShouldBeFound(String filter) throws Exception {
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(execute.getId().intValue())))
            .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.[*].response").value(hasItem(DEFAULT_RESPONSE)))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(sameInstant(DEFAULT_STARTED_AT))))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())));

        // Check, that the count call also returns 1
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExecuteShouldNotBeFound(String filter) throws Exception {
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExecuteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExecute() throws Exception {
        // Get the execute
        restExecuteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExecute() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        int databaseSizeBeforeUpdate = executeRepository.findAll().size();

        // Update the execute
        Execute updatedExecute = executeRepository.findById(execute.getId()).get();
        // Disconnect from session so that the updates on updatedExecute are not directly saved in db
        em.detach(updatedExecute);
        updatedExecute.request(UPDATED_REQUEST).response(UPDATED_RESPONSE).startedAt(UPDATED_STARTED_AT).time(UPDATED_TIME);
        ExecuteDTO executeDTO = executeMapper.toDto(updatedExecute);

        restExecuteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, executeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
        Execute testExecute = executeList.get(executeList.size() - 1);
        assertThat(testExecute.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testExecute.getResponse()).isEqualTo(UPDATED_RESPONSE);
        assertThat(testExecute.getStartedAt()).isEqualTo(UPDATED_STARTED_AT);
        assertThat(testExecute.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void putNonExistingExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, executeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(executeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExecuteWithPatch() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        int databaseSizeBeforeUpdate = executeRepository.findAll().size();

        // Update the execute using partial update
        Execute partialUpdatedExecute = new Execute();
        partialUpdatedExecute.setId(execute.getId());

        partialUpdatedExecute.response(UPDATED_RESPONSE);

        restExecuteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExecute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExecute))
            )
            .andExpect(status().isOk());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
        Execute testExecute = executeList.get(executeList.size() - 1);
        assertThat(testExecute.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testExecute.getResponse()).isEqualTo(UPDATED_RESPONSE);
        assertThat(testExecute.getStartedAt()).isEqualTo(DEFAULT_STARTED_AT);
        assertThat(testExecute.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    void fullUpdateExecuteWithPatch() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        int databaseSizeBeforeUpdate = executeRepository.findAll().size();

        // Update the execute using partial update
        Execute partialUpdatedExecute = new Execute();
        partialUpdatedExecute.setId(execute.getId());

        partialUpdatedExecute.request(UPDATED_REQUEST).response(UPDATED_RESPONSE).startedAt(UPDATED_STARTED_AT).time(UPDATED_TIME);

        restExecuteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExecute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExecute))
            )
            .andExpect(status().isOk());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
        Execute testExecute = executeList.get(executeList.size() - 1);
        assertThat(testExecute.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testExecute.getResponse()).isEqualTo(UPDATED_RESPONSE);
        assertThat(testExecute.getStartedAt()).isEqualTo(UPDATED_STARTED_AT);
        assertThat(testExecute.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, executeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExecute() throws Exception {
        int databaseSizeBeforeUpdate = executeRepository.findAll().size();
        execute.setId(count.incrementAndGet());

        // Create the Execute
        ExecuteDTO executeDTO = executeMapper.toDto(execute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExecuteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(executeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Execute in the database
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExecute() throws Exception {
        // Initialize the database
        executeRepository.saveAndFlush(execute);

        int databaseSizeBeforeDelete = executeRepository.findAll().size();

        // Delete the execute
        restExecuteMockMvc
            .perform(delete(ENTITY_API_URL_ID, execute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Execute> executeList = executeRepository.findAll();
        assertThat(executeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
