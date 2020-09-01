package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.UsermanagentApp;
import vn.com.truyen.usermanagent.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.usermanagent.domain.Role;
import vn.com.truyen.usermanagent.domain.Functions;
import vn.com.truyen.usermanagent.domain.GroupTranslate;
import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.repository.RoleRepository;
import vn.com.truyen.usermanagent.service.RoleService;
import vn.com.truyen.usermanagent.service.dto.RoleDTO;
import vn.com.truyen.usermanagent.service.mapper.RoleMapper;
import vn.com.truyen.usermanagent.service.dto.RoleCriteria;
import vn.com.truyen.usermanagent.service.RoleQueryService;

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
 * Integration tests for the {@link RoleResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, UsermanagentApp.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LOCKED = false;
    private static final Boolean UPDATED_LOCKED = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private RoleRepository roleRepository;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Autowired
    private RoleMapper roleMapper;

    @Mock
    private RoleService roleServiceMock;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleQueryService roleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleMockMvc;

    private Role role;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .locked(DEFAULT_LOCKED)
            .code(DEFAULT_CODE);
        // Add required entity
        Functions functions;
        if (TestUtil.findAll(em, Functions.class).isEmpty()) {
            functions = FunctionsResourceIT.createEntity(em);
            em.persist(functions);
            em.flush();
        } else {
            functions = TestUtil.findAll(em, Functions.class).get(0);
        }
        role.getFunctions().add(functions);
        return role;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createUpdatedEntity(EntityManager em) {
        Role role = new Role()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .locked(UPDATED_LOCKED)
            .code(UPDATED_CODE);
        // Add required entity
        Functions functions;
        if (TestUtil.findAll(em, Functions.class).isEmpty()) {
            functions = FunctionsResourceIT.createUpdatedEntity(em);
            em.persist(functions);
            em.flush();
        } else {
            functions = TestUtil.findAll(em, Functions.class).get(0);
        }
        role.getFunctions().add(functions);
        return role;
    }

    @BeforeEach
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    public void createRole() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();
        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);
        restRoleMockMvc.perform(post("/api/roles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isCreated());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate + 1);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRole.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testRole.isLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testRole.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleRepository.findAll().size();

        // Create the Role with an existing ID
        role.setId(1L);
        RoleDTO roleDTO = roleMapper.toDto(role);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleMockMvc.perform(post("/api/roles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleRepository.findAll().size();
        // set the field null
        role.setName(null);

        // Create the Role, which fails.
        RoleDTO roleDTO = roleMapper.toDto(role);


        restRoleMockMvc.perform(post("/api/roles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRolesWithEagerRelationshipsIsEnabled() throws Exception {
        when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRoleMockMvc.perform(get("/api/roles?eagerload=true"))
            .andExpect(status().isOk());

        verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRolesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(roleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRoleMockMvc.perform(get("/api/roles?eagerload=true"))
            .andExpect(status().isOk());

        verify(roleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getRolesByIdFiltering() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        Long id = role.getId();

        defaultRoleShouldBeFound("id.equals=" + id);
        defaultRoleShouldNotBeFound("id.notEquals=" + id);

        defaultRoleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoleShouldNotBeFound("id.greaterThan=" + id);

        defaultRoleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name equals to DEFAULT_NAME
        defaultRoleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the roleList where name equals to UPDATED_NAME
        defaultRoleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name not equals to DEFAULT_NAME
        defaultRoleShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the roleList where name not equals to UPDATED_NAME
        defaultRoleShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRoleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the roleList where name equals to UPDATED_NAME
        defaultRoleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name is not null
        defaultRoleShouldBeFound("name.specified=true");

        // Get all the roleList where name is null
        defaultRoleShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllRolesByNameContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name contains DEFAULT_NAME
        defaultRoleShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the roleList where name contains UPDATED_NAME
        defaultRoleShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where name does not contain DEFAULT_NAME
        defaultRoleShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the roleList where name does not contain UPDATED_NAME
        defaultRoleShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllRolesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content equals to DEFAULT_CONTENT
        defaultRoleShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the roleList where content equals to UPDATED_CONTENT
        defaultRoleShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllRolesByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content not equals to DEFAULT_CONTENT
        defaultRoleShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the roleList where content not equals to UPDATED_CONTENT
        defaultRoleShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllRolesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultRoleShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the roleList where content equals to UPDATED_CONTENT
        defaultRoleShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllRolesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content is not null
        defaultRoleShouldBeFound("content.specified=true");

        // Get all the roleList where content is null
        defaultRoleShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllRolesByContentContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content contains DEFAULT_CONTENT
        defaultRoleShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the roleList where content contains UPDATED_CONTENT
        defaultRoleShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllRolesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where content does not contain DEFAULT_CONTENT
        defaultRoleShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the roleList where content does not contain UPDATED_CONTENT
        defaultRoleShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }


    @Test
    @Transactional
    public void getAllRolesByLockedIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where locked equals to DEFAULT_LOCKED
        defaultRoleShouldBeFound("locked.equals=" + DEFAULT_LOCKED);

        // Get all the roleList where locked equals to UPDATED_LOCKED
        defaultRoleShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllRolesByLockedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where locked not equals to DEFAULT_LOCKED
        defaultRoleShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);

        // Get all the roleList where locked not equals to UPDATED_LOCKED
        defaultRoleShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllRolesByLockedIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
        defaultRoleShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);

        // Get all the roleList where locked equals to UPDATED_LOCKED
        defaultRoleShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllRolesByLockedIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where locked is not null
        defaultRoleShouldBeFound("locked.specified=true");

        // Get all the roleList where locked is null
        defaultRoleShouldNotBeFound("locked.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code equals to DEFAULT_CODE
        defaultRoleShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the roleList where code equals to UPDATED_CODE
        defaultRoleShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code not equals to DEFAULT_CODE
        defaultRoleShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the roleList where code not equals to UPDATED_CODE
        defaultRoleShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code in DEFAULT_CODE or UPDATED_CODE
        defaultRoleShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the roleList where code equals to UPDATED_CODE
        defaultRoleShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code is not null
        defaultRoleShouldBeFound("code.specified=true");

        // Get all the roleList where code is null
        defaultRoleShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllRolesByCodeContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code contains DEFAULT_CODE
        defaultRoleShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the roleList where code contains UPDATED_CODE
        defaultRoleShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where code does not contain DEFAULT_CODE
        defaultRoleShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the roleList where code does not contain UPDATED_CODE
        defaultRoleShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllRolesByFunctionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Functions function = role.getFunction();
        roleRepository.saveAndFlush(role);
        Long functionId = function.getId();

        // Get all the roleList where function equals to functionId
        defaultRoleShouldBeFound("functionId.equals=" + functionId);

        // Get all the roleList where function equals to functionId + 1
        defaultRoleShouldNotBeFound("functionId.equals=" + (functionId + 1));
    }


    @Test
    @Transactional
    public void getAllRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);
        GroupTranslate name = GroupTranslateResourceIT.createEntity(em);
        em.persist(name);
        em.flush();
        role.addName(name);
        roleRepository.saveAndFlush(role);
        Long nameId = name.getId();

        // Get all the roleList where name equals to nameId
        defaultRoleShouldBeFound("nameId.equals=" + nameId);

        // Get all the roleList where name equals to nameId + 1
        defaultRoleShouldNotBeFound("nameId.equals=" + (nameId + 1));
    }


    @Test
    @Transactional
    public void getAllRolesByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);
        Users login = UsersResourceIT.createEntity(em);
        em.persist(login);
        em.flush();
        role.addLogin(login);
        roleRepository.saveAndFlush(role);
        Long loginId = login.getId();

        // Get all the roleList where login equals to loginId
        defaultRoleShouldBeFound("loginId.equals=" + loginId);

        // Get all the roleList where login equals to loginId + 1
        defaultRoleShouldNotBeFound("loginId.equals=" + (loginId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoleShouldBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoleShouldNotBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Update the role
        Role updatedRole = roleRepository.findById(role.getId()).get();
        // Disconnect from session so that the updates on updatedRole are not directly saved in db
        em.detach(updatedRole);
        updatedRole
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .locked(UPDATED_LOCKED)
            .code(UPDATED_CODE);
        RoleDTO roleDTO = roleMapper.toDto(updatedRole);

        restRoleMockMvc.perform(put("/api/roles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isOk());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
        Role testRole = roleList.get(roleList.size() - 1);
        assertThat(testRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRole.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testRole.isLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testRole.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRole() throws Exception {
        int databaseSizeBeforeUpdate = roleRepository.findAll().size();

        // Create the Role
        RoleDTO roleDTO = roleMapper.toDto(role);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleMockMvc.perform(put("/api/roles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Role in the database
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        int databaseSizeBeforeDelete = roleRepository.findAll().size();

        // Delete the role
        restRoleMockMvc.perform(delete("/api/roles/{id}", role.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Role> roleList = roleRepository.findAll();
        assertThat(roleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
