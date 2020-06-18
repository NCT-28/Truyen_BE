package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.UsermanagentApp;
import vn.com.truyen.usermanagent.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.usermanagent.domain.Functions;
import vn.com.truyen.usermanagent.domain.Role;
import vn.com.truyen.usermanagent.repository.FunctionsRepository;
import vn.com.truyen.usermanagent.service.FunctionsService;
import vn.com.truyen.usermanagent.service.dto.FunctionsDTO;
import vn.com.truyen.usermanagent.service.mapper.FunctionsMapper;
import vn.com.truyen.usermanagent.service.dto.FunctionsCriteria;
import vn.com.truyen.usermanagent.service.FunctionsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FunctionsResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, UsermanagentApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class FunctionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private FunctionsRepository functionsRepository;

    @Autowired
    private FunctionsMapper functionsMapper;

    @Autowired
    private FunctionsService functionsService;

    @Autowired
    private FunctionsQueryService functionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFunctionsMockMvc;

    private Functions functions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functions createEntity(EntityManager em) {
        Functions functions = new Functions()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        return functions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Functions createUpdatedEntity(EntityManager em) {
        Functions functions = new Functions()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        return functions;
    }

    @BeforeEach
    public void initTest() {
        functions = createEntity(em);
    }

    @Test
    @Transactional
    public void createFunctions() throws Exception {
        int databaseSizeBeforeCreate = functionsRepository.findAll().size();
        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);
        restFunctionsMockMvc.perform(post("/api/functions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeCreate + 1);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFunctions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFunctions.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createFunctionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = functionsRepository.findAll().size();

        // Create the Functions with an existing ID
        functions.setId(1L);
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFunctionsMockMvc.perform(post("/api/functions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = functionsRepository.findAll().size();
        // set the field null
        functions.setName(null);

        // Create the Functions, which fails.
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);


        restFunctionsMockMvc.perform(post("/api/functions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isBadRequest());

        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList
        restFunctionsMockMvc.perform(get("/api/functions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get the functions
        restFunctionsMockMvc.perform(get("/api/functions/{id}", functions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(functions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getFunctionsByIdFiltering() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        Long id = functions.getId();

        defaultFunctionsShouldBeFound("id.equals=" + id);
        defaultFunctionsShouldNotBeFound("id.notEquals=" + id);

        defaultFunctionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFunctionsShouldNotBeFound("id.greaterThan=" + id);

        defaultFunctionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFunctionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFunctionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name equals to DEFAULT_NAME
        defaultFunctionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the functionsList where name equals to UPDATED_NAME
        defaultFunctionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFunctionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name not equals to DEFAULT_NAME
        defaultFunctionsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the functionsList where name not equals to UPDATED_NAME
        defaultFunctionsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFunctionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFunctionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the functionsList where name equals to UPDATED_NAME
        defaultFunctionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFunctionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name is not null
        defaultFunctionsShouldBeFound("name.specified=true");

        // Get all the functionsList where name is null
        defaultFunctionsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllFunctionsByNameContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name contains DEFAULT_NAME
        defaultFunctionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the functionsList where name contains UPDATED_NAME
        defaultFunctionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFunctionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where name does not contain DEFAULT_NAME
        defaultFunctionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the functionsList where name does not contain UPDATED_NAME
        defaultFunctionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllFunctionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description equals to DEFAULT_DESCRIPTION
        defaultFunctionsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the functionsList where description equals to UPDATED_DESCRIPTION
        defaultFunctionsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFunctionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description not equals to DEFAULT_DESCRIPTION
        defaultFunctionsShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the functionsList where description not equals to UPDATED_DESCRIPTION
        defaultFunctionsShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFunctionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFunctionsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the functionsList where description equals to UPDATED_DESCRIPTION
        defaultFunctionsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFunctionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description is not null
        defaultFunctionsShouldBeFound("description.specified=true");

        // Get all the functionsList where description is null
        defaultFunctionsShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllFunctionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description contains DEFAULT_DESCRIPTION
        defaultFunctionsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the functionsList where description contains UPDATED_DESCRIPTION
        defaultFunctionsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFunctionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where description does not contain DEFAULT_DESCRIPTION
        defaultFunctionsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the functionsList where description does not contain UPDATED_DESCRIPTION
        defaultFunctionsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllFunctionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code equals to DEFAULT_CODE
        defaultFunctionsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the functionsList where code equals to UPDATED_CODE
        defaultFunctionsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFunctionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code not equals to DEFAULT_CODE
        defaultFunctionsShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the functionsList where code not equals to UPDATED_CODE
        defaultFunctionsShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFunctionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultFunctionsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the functionsList where code equals to UPDATED_CODE
        defaultFunctionsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFunctionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code is not null
        defaultFunctionsShouldBeFound("code.specified=true");

        // Get all the functionsList where code is null
        defaultFunctionsShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllFunctionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code contains DEFAULT_CODE
        defaultFunctionsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the functionsList where code contains UPDATED_CODE
        defaultFunctionsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllFunctionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        // Get all the functionsList where code does not contain DEFAULT_CODE
        defaultFunctionsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the functionsList where code does not contain UPDATED_CODE
        defaultFunctionsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllFunctionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);
        Role name = RoleResourceIT.createEntity(em);
        em.persist(name);
        em.flush();
        functions.addName(name);
        functionsRepository.saveAndFlush(functions);
        Long nameId = name.getId();

        // Get all the functionsList where name equals to nameId
        defaultFunctionsShouldBeFound("nameId.equals=" + nameId);

        // Get all the functionsList where name equals to nameId + 1
        defaultFunctionsShouldNotBeFound("nameId.equals=" + (nameId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFunctionsShouldBeFound(String filter) throws Exception {
        restFunctionsMockMvc.perform(get("/api/functions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(functions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restFunctionsMockMvc.perform(get("/api/functions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFunctionsShouldNotBeFound(String filter) throws Exception {
        restFunctionsMockMvc.perform(get("/api/functions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFunctionsMockMvc.perform(get("/api/functions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFunctions() throws Exception {
        // Get the functions
        restFunctionsMockMvc.perform(get("/api/functions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();

        // Update the functions
        Functions updatedFunctions = functionsRepository.findById(functions.getId()).get();
        // Disconnect from session so that the updates on updatedFunctions are not directly saved in db
        em.detach(updatedFunctions);
        updatedFunctions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        FunctionsDTO functionsDTO = functionsMapper.toDto(updatedFunctions);

        restFunctionsMockMvc.perform(put("/api/functions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isOk());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
        Functions testFunctions = functionsList.get(functionsList.size() - 1);
        assertThat(testFunctions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunctions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFunctions.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingFunctions() throws Exception {
        int databaseSizeBeforeUpdate = functionsRepository.findAll().size();

        // Create the Functions
        FunctionsDTO functionsDTO = functionsMapper.toDto(functions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFunctionsMockMvc.perform(put("/api/functions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(functionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Functions in the database
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFunctions() throws Exception {
        // Initialize the database
        functionsRepository.saveAndFlush(functions);

        int databaseSizeBeforeDelete = functionsRepository.findAll().size();

        // Delete the functions
        restFunctionsMockMvc.perform(delete("/api/functions/{id}", functions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Functions> functionsList = functionsRepository.findAll();
        assertThat(functionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
