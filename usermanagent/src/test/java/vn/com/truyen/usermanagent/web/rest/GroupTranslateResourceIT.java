package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.UsermanagentApp;
import vn.com.truyen.usermanagent.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.usermanagent.domain.GroupTranslate;
import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.domain.Role;
import vn.com.truyen.usermanagent.repository.GroupTranslateRepository;
import vn.com.truyen.usermanagent.service.GroupTranslateService;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateDTO;
import vn.com.truyen.usermanagent.service.mapper.GroupTranslateMapper;
import vn.com.truyen.usermanagent.service.dto.GroupTranslateCriteria;
import vn.com.truyen.usermanagent.service.GroupTranslateQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupTranslateResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, UsermanagentApp.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupTranslateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private GroupTranslateRepository groupTranslateRepository;

    @Mock
    private GroupTranslateRepository groupTranslateRepositoryMock;

    @Autowired
    private GroupTranslateMapper groupTranslateMapper;

    @Mock
    private GroupTranslateService groupTranslateServiceMock;

    @Autowired
    private GroupTranslateService groupTranslateService;

    @Autowired
    private GroupTranslateQueryService groupTranslateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupTranslateMockMvc;

    private GroupTranslate groupTranslate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupTranslate createEntity(EntityManager em) {
        GroupTranslate groupTranslate = new GroupTranslate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        // Add required entity
        Users users;
        if (TestUtil.findAll(em, Users.class).isEmpty()) {
            users = UsersResourceIT.createEntity(em);
            em.persist(users);
            em.flush();
        } else {
            users = TestUtil.findAll(em, Users.class).get(0);
        }
        groupTranslate.getUsers().add(users);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        groupTranslate.getRoles().add(role);
        return groupTranslate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupTranslate createUpdatedEntity(EntityManager em) {
        GroupTranslate groupTranslate = new GroupTranslate()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        // Add required entity
        Users users;
        if (TestUtil.findAll(em, Users.class).isEmpty()) {
            users = UsersResourceIT.createUpdatedEntity(em);
            em.persist(users);
            em.flush();
        } else {
            users = TestUtil.findAll(em, Users.class).get(0);
        }
        groupTranslate.getUsers().add(users);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createUpdatedEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        groupTranslate.getRoles().add(role);
        return groupTranslate;
    }

    @BeforeEach
    public void initTest() {
        groupTranslate = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupTranslate() throws Exception {
        int databaseSizeBeforeCreate = groupTranslateRepository.findAll().size();
        // Create the GroupTranslate
        GroupTranslateDTO groupTranslateDTO = groupTranslateMapper.toDto(groupTranslate);
        restGroupTranslateMockMvc.perform(post("/api/group-translates").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTranslateDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupTranslate in the database
        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeCreate + 1);
        GroupTranslate testGroupTranslate = groupTranslateList.get(groupTranslateList.size() - 1);
        assertThat(testGroupTranslate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupTranslate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGroupTranslate.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createGroupTranslateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupTranslateRepository.findAll().size();

        // Create the GroupTranslate with an existing ID
        groupTranslate.setId(1L);
        GroupTranslateDTO groupTranslateDTO = groupTranslateMapper.toDto(groupTranslate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupTranslateMockMvc.perform(post("/api/group-translates").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTranslateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupTranslate in the database
        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupTranslateRepository.findAll().size();
        // set the field null
        groupTranslate.setName(null);

        // Create the GroupTranslate, which fails.
        GroupTranslateDTO groupTranslateDTO = groupTranslateMapper.toDto(groupTranslate);


        restGroupTranslateMockMvc.perform(post("/api/group-translates").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTranslateDTO)))
            .andExpect(status().isBadRequest());

        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupTranslates() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList
        restGroupTranslateMockMvc.perform(get("/api/group-translates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupTranslate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGroupTranslatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupTranslateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupTranslateMockMvc.perform(get("/api/group-translates?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupTranslateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupTranslatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupTranslateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupTranslateMockMvc.perform(get("/api/group-translates?eagerload=true"))
            .andExpect(status().isOk());

        verify(groupTranslateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupTranslate() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get the groupTranslate
        restGroupTranslateMockMvc.perform(get("/api/group-translates/{id}", groupTranslate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupTranslate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getGroupTranslatesByIdFiltering() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        Long id = groupTranslate.getId();

        defaultGroupTranslateShouldBeFound("id.equals=" + id);
        defaultGroupTranslateShouldNotBeFound("id.notEquals=" + id);

        defaultGroupTranslateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGroupTranslateShouldNotBeFound("id.greaterThan=" + id);

        defaultGroupTranslateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGroupTranslateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGroupTranslatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name equals to DEFAULT_NAME
        defaultGroupTranslateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the groupTranslateList where name equals to UPDATED_NAME
        defaultGroupTranslateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name not equals to DEFAULT_NAME
        defaultGroupTranslateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the groupTranslateList where name not equals to UPDATED_NAME
        defaultGroupTranslateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGroupTranslateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the groupTranslateList where name equals to UPDATED_NAME
        defaultGroupTranslateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name is not null
        defaultGroupTranslateShouldBeFound("name.specified=true");

        // Get all the groupTranslateList where name is null
        defaultGroupTranslateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupTranslatesByNameContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name contains DEFAULT_NAME
        defaultGroupTranslateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the groupTranslateList where name contains UPDATED_NAME
        defaultGroupTranslateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where name does not contain DEFAULT_NAME
        defaultGroupTranslateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the groupTranslateList where name does not contain UPDATED_NAME
        defaultGroupTranslateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description equals to DEFAULT_DESCRIPTION
        defaultGroupTranslateShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the groupTranslateList where description equals to UPDATED_DESCRIPTION
        defaultGroupTranslateShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description not equals to DEFAULT_DESCRIPTION
        defaultGroupTranslateShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the groupTranslateList where description not equals to UPDATED_DESCRIPTION
        defaultGroupTranslateShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGroupTranslateShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the groupTranslateList where description equals to UPDATED_DESCRIPTION
        defaultGroupTranslateShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description is not null
        defaultGroupTranslateShouldBeFound("description.specified=true");

        // Get all the groupTranslateList where description is null
        defaultGroupTranslateShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description contains DEFAULT_DESCRIPTION
        defaultGroupTranslateShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the groupTranslateList where description contains UPDATED_DESCRIPTION
        defaultGroupTranslateShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where description does not contain DEFAULT_DESCRIPTION
        defaultGroupTranslateShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the groupTranslateList where description does not contain UPDATED_DESCRIPTION
        defaultGroupTranslateShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllGroupTranslatesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code equals to DEFAULT_CODE
        defaultGroupTranslateShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the groupTranslateList where code equals to UPDATED_CODE
        defaultGroupTranslateShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code not equals to DEFAULT_CODE
        defaultGroupTranslateShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the groupTranslateList where code not equals to UPDATED_CODE
        defaultGroupTranslateShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code in DEFAULT_CODE or UPDATED_CODE
        defaultGroupTranslateShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the groupTranslateList where code equals to UPDATED_CODE
        defaultGroupTranslateShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code is not null
        defaultGroupTranslateShouldBeFound("code.specified=true");

        // Get all the groupTranslateList where code is null
        defaultGroupTranslateShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupTranslatesByCodeContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code contains DEFAULT_CODE
        defaultGroupTranslateShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the groupTranslateList where code contains UPDATED_CODE
        defaultGroupTranslateShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupTranslatesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        // Get all the groupTranslateList where code does not contain DEFAULT_CODE
        defaultGroupTranslateShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the groupTranslateList where code does not contain UPDATED_CODE
        defaultGroupTranslateShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllGroupTranslatesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Users user = groupTranslate.getUser();
        groupTranslateRepository.saveAndFlush(groupTranslate);
        Long userId = user.getId();

        // Get all the groupTranslateList where user equals to userId
        defaultGroupTranslateShouldBeFound("userId.equals=" + userId);

        // Get all the groupTranslateList where user equals to userId + 1
        defaultGroupTranslateShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupTranslatesByRoleIsEqualToSomething() throws Exception {
        // Get already existing entity
        Role role = groupTranslate.getRole();
        groupTranslateRepository.saveAndFlush(groupTranslate);
        Long roleId = role.getId();

        // Get all the groupTranslateList where role equals to roleId
        defaultGroupTranslateShouldBeFound("roleId.equals=" + roleId);

        // Get all the groupTranslateList where role equals to roleId + 1
        defaultGroupTranslateShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupTranslateShouldBeFound(String filter) throws Exception {
        restGroupTranslateMockMvc.perform(get("/api/group-translates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupTranslate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restGroupTranslateMockMvc.perform(get("/api/group-translates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupTranslateShouldNotBeFound(String filter) throws Exception {
        restGroupTranslateMockMvc.perform(get("/api/group-translates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupTranslateMockMvc.perform(get("/api/group-translates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGroupTranslate() throws Exception {
        // Get the groupTranslate
        restGroupTranslateMockMvc.perform(get("/api/group-translates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupTranslate() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        int databaseSizeBeforeUpdate = groupTranslateRepository.findAll().size();

        // Update the groupTranslate
        GroupTranslate updatedGroupTranslate = groupTranslateRepository.findById(groupTranslate.getId()).get();
        // Disconnect from session so that the updates on updatedGroupTranslate are not directly saved in db
        em.detach(updatedGroupTranslate);
        updatedGroupTranslate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        GroupTranslateDTO groupTranslateDTO = groupTranslateMapper.toDto(updatedGroupTranslate);

        restGroupTranslateMockMvc.perform(put("/api/group-translates").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTranslateDTO)))
            .andExpect(status().isOk());

        // Validate the GroupTranslate in the database
        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeUpdate);
        GroupTranslate testGroupTranslate = groupTranslateList.get(groupTranslateList.size() - 1);
        assertThat(testGroupTranslate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupTranslate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroupTranslate.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupTranslate() throws Exception {
        int databaseSizeBeforeUpdate = groupTranslateRepository.findAll().size();

        // Create the GroupTranslate
        GroupTranslateDTO groupTranslateDTO = groupTranslateMapper.toDto(groupTranslate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupTranslateMockMvc.perform(put("/api/group-translates").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupTranslateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupTranslate in the database
        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupTranslate() throws Exception {
        // Initialize the database
        groupTranslateRepository.saveAndFlush(groupTranslate);

        int databaseSizeBeforeDelete = groupTranslateRepository.findAll().size();

        // Delete the groupTranslate
        restGroupTranslateMockMvc.perform(delete("/api/group-translates/{id}", groupTranslate.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupTranslate> groupTranslateList = groupTranslateRepository.findAll();
        assertThat(groupTranslateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
