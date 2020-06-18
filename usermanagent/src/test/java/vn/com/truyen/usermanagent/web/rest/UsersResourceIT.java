package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.UsermanagentApp;
import vn.com.truyen.usermanagent.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.domain.Role;
import vn.com.truyen.usermanagent.domain.GroupTranslate;
import vn.com.truyen.usermanagent.repository.UsersRepository;
import vn.com.truyen.usermanagent.service.UsersService;
import vn.com.truyen.usermanagent.service.dto.UsersDTO;
import vn.com.truyen.usermanagent.service.mapper.UsersMapper;
import vn.com.truyen.usermanagent.service.dto.UsersCriteria;
import vn.com.truyen.usermanagent.service.UsersQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static vn.com.truyen.usermanagent.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsersResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, UsermanagentApp.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsersResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "Fn@k.S";
    private static final String UPDATED_EMAIL = "_P@r^]%X.\"X).%";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final Boolean DEFAULT_LOCKED = false;
    private static final Boolean UPDATED_LOCKED = true;

    private static final Boolean DEFAULT_CAN_CHANGE = false;
    private static final Boolean UPDATED_CAN_CHANGE = true;

    private static final Boolean DEFAULT_MUST_CHANGE = false;
    private static final Boolean UPDATED_MUST_CHANGE = true;

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESET_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RESET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private UsersRepository usersRepository;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Autowired
    private UsersMapper usersMapper;

    @Mock
    private UsersService usersServiceMock;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersQueryService usersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersMockMvc;

    private Users users;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .login(DEFAULT_LOGIN)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activated(DEFAULT_ACTIVATED)
            .locked(DEFAULT_LOCKED)
            .canChange(DEFAULT_CAN_CHANGE)
            .mustChange(DEFAULT_MUST_CHANGE)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE)
            .code(DEFAULT_CODE);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        users.getRoles().add(role);
        return users;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createUpdatedEntity(EntityManager em) {
        Users users = new Users()
            .login(UPDATED_LOGIN)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .locked(UPDATED_LOCKED)
            .canChange(UPDATED_CAN_CHANGE)
            .mustChange(UPDATED_MUST_CHANGE)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .code(UPDATED_CODE);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createUpdatedEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        users.getRoles().add(role);
        return users;
    }

    @BeforeEach
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();
        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);
        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testUsers.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsers.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testUsers.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUsers.isLocked()).isEqualTo(DEFAULT_LOCKED);
        assertThat(testUsers.isCanChange()).isEqualTo(DEFAULT_CAN_CHANGE);
        assertThat(testUsers.isMustChange()).isEqualTo(DEFAULT_MUST_CHANGE);
        assertThat(testUsers.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testUsers.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testUsers.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testUsers.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId(1L);
        UsersDTO usersDTO = usersMapper.toDto(users);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setLogin(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setPasswordHash(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersRepository.findAll().size();
        // set the field null
        users.setEmail(null);

        // Create the Users, which fails.
        UsersDTO usersDTO = usersMapper.toDto(users);


        restUsersMockMvc.perform(post("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].canChange").value(hasItem(DEFAULT_CAN_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].mustChange").value(hasItem(DEFAULT_MUST_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(sameInstant(DEFAULT_RESET_DATE))))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(usersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsersMockMvc.perform(get("/api/users?eagerload=true"))
            .andExpect(status().isOk());

        verify(usersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsersMockMvc.perform(get("/api/users?eagerload=true"))
            .andExpect(status().isOk());

        verify(usersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
            .andExpect(jsonPath("$.canChange").value(DEFAULT_CAN_CHANGE.booleanValue()))
            .andExpect(jsonPath("$.mustChange").value(DEFAULT_MUST_CHANGE.booleanValue()))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY))
            .andExpect(jsonPath("$.resetDate").value(sameInstant(DEFAULT_RESET_DATE)))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getUsersByIdFiltering() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        Long id = users.getId();

        defaultUsersShouldBeFound("id.equals=" + id);
        defaultUsersShouldNotBeFound("id.notEquals=" + id);

        defaultUsersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsersShouldNotBeFound("id.greaterThan=" + id);

        defaultUsersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsersByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login equals to DEFAULT_LOGIN
        defaultUsersShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the usersList where login equals to UPDATED_LOGIN
        defaultUsersShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUsersByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login not equals to DEFAULT_LOGIN
        defaultUsersShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the usersList where login not equals to UPDATED_LOGIN
        defaultUsersShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUsersByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultUsersShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the usersList where login equals to UPDATED_LOGIN
        defaultUsersShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUsersByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login is not null
        defaultUsersShouldBeFound("login.specified=true");

        // Get all the usersList where login is null
        defaultUsersShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByLoginContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login contains DEFAULT_LOGIN
        defaultUsersShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the usersList where login contains UPDATED_LOGIN
        defaultUsersShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllUsersByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where login does not contain DEFAULT_LOGIN
        defaultUsersShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the usersList where login does not contain UPDATED_LOGIN
        defaultUsersShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllUsersByPasswordHashIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash equals to DEFAULT_PASSWORD_HASH
        defaultUsersShouldBeFound("passwordHash.equals=" + DEFAULT_PASSWORD_HASH);

        // Get all the usersList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultUsersShouldNotBeFound("passwordHash.equals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllUsersByPasswordHashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash not equals to DEFAULT_PASSWORD_HASH
        defaultUsersShouldNotBeFound("passwordHash.notEquals=" + DEFAULT_PASSWORD_HASH);

        // Get all the usersList where passwordHash not equals to UPDATED_PASSWORD_HASH
        defaultUsersShouldBeFound("passwordHash.notEquals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllUsersByPasswordHashIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash in DEFAULT_PASSWORD_HASH or UPDATED_PASSWORD_HASH
        defaultUsersShouldBeFound("passwordHash.in=" + DEFAULT_PASSWORD_HASH + "," + UPDATED_PASSWORD_HASH);

        // Get all the usersList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultUsersShouldNotBeFound("passwordHash.in=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllUsersByPasswordHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash is not null
        defaultUsersShouldBeFound("passwordHash.specified=true");

        // Get all the usersList where passwordHash is null
        defaultUsersShouldNotBeFound("passwordHash.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByPasswordHashContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash contains DEFAULT_PASSWORD_HASH
        defaultUsersShouldBeFound("passwordHash.contains=" + DEFAULT_PASSWORD_HASH);

        // Get all the usersList where passwordHash contains UPDATED_PASSWORD_HASH
        defaultUsersShouldNotBeFound("passwordHash.contains=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    public void getAllUsersByPasswordHashNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where passwordHash does not contain DEFAULT_PASSWORD_HASH
        defaultUsersShouldNotBeFound("passwordHash.doesNotContain=" + DEFAULT_PASSWORD_HASH);

        // Get all the usersList where passwordHash does not contain UPDATED_PASSWORD_HASH
        defaultUsersShouldBeFound("passwordHash.doesNotContain=" + UPDATED_PASSWORD_HASH);
    }


    @Test
    @Transactional
    public void getAllUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email equals to DEFAULT_EMAIL
        defaultUsersShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the usersList where email equals to UPDATED_EMAIL
        defaultUsersShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email not equals to DEFAULT_EMAIL
        defaultUsersShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the usersList where email not equals to UPDATED_EMAIL
        defaultUsersShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUsersShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the usersList where email equals to UPDATED_EMAIL
        defaultUsersShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email is not null
        defaultUsersShouldBeFound("email.specified=true");

        // Get all the usersList where email is null
        defaultUsersShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email contains DEFAULT_EMAIL
        defaultUsersShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the usersList where email contains UPDATED_EMAIL
        defaultUsersShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where email does not contain DEFAULT_EMAIL
        defaultUsersShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the usersList where email does not contain UPDATED_EMAIL
        defaultUsersShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllUsersByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultUsersShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the usersList where imageUrl equals to UPDATED_IMAGE_URL
        defaultUsersShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUsersByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultUsersShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the usersList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultUsersShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUsersByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultUsersShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the usersList where imageUrl equals to UPDATED_IMAGE_URL
        defaultUsersShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUsersByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl is not null
        defaultUsersShouldBeFound("imageUrl.specified=true");

        // Get all the usersList where imageUrl is null
        defaultUsersShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl contains DEFAULT_IMAGE_URL
        defaultUsersShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the usersList where imageUrl contains UPDATED_IMAGE_URL
        defaultUsersShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllUsersByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultUsersShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the usersList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultUsersShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllUsersByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activated equals to DEFAULT_ACTIVATED
        defaultUsersShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the usersList where activated equals to UPDATED_ACTIVATED
        defaultUsersShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllUsersByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activated not equals to DEFAULT_ACTIVATED
        defaultUsersShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the usersList where activated not equals to UPDATED_ACTIVATED
        defaultUsersShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllUsersByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultUsersShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the usersList where activated equals to UPDATED_ACTIVATED
        defaultUsersShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllUsersByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activated is not null
        defaultUsersShouldBeFound("activated.specified=true");

        // Get all the usersList where activated is null
        defaultUsersShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByLockedIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where locked equals to DEFAULT_LOCKED
        defaultUsersShouldBeFound("locked.equals=" + DEFAULT_LOCKED);

        // Get all the usersList where locked equals to UPDATED_LOCKED
        defaultUsersShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllUsersByLockedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where locked not equals to DEFAULT_LOCKED
        defaultUsersShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);

        // Get all the usersList where locked not equals to UPDATED_LOCKED
        defaultUsersShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllUsersByLockedIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
        defaultUsersShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);

        // Get all the usersList where locked equals to UPDATED_LOCKED
        defaultUsersShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
    }

    @Test
    @Transactional
    public void getAllUsersByLockedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where locked is not null
        defaultUsersShouldBeFound("locked.specified=true");

        // Get all the usersList where locked is null
        defaultUsersShouldNotBeFound("locked.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByCanChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where canChange equals to DEFAULT_CAN_CHANGE
        defaultUsersShouldBeFound("canChange.equals=" + DEFAULT_CAN_CHANGE);

        // Get all the usersList where canChange equals to UPDATED_CAN_CHANGE
        defaultUsersShouldNotBeFound("canChange.equals=" + UPDATED_CAN_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByCanChangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where canChange not equals to DEFAULT_CAN_CHANGE
        defaultUsersShouldNotBeFound("canChange.notEquals=" + DEFAULT_CAN_CHANGE);

        // Get all the usersList where canChange not equals to UPDATED_CAN_CHANGE
        defaultUsersShouldBeFound("canChange.notEquals=" + UPDATED_CAN_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByCanChangeIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where canChange in DEFAULT_CAN_CHANGE or UPDATED_CAN_CHANGE
        defaultUsersShouldBeFound("canChange.in=" + DEFAULT_CAN_CHANGE + "," + UPDATED_CAN_CHANGE);

        // Get all the usersList where canChange equals to UPDATED_CAN_CHANGE
        defaultUsersShouldNotBeFound("canChange.in=" + UPDATED_CAN_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByCanChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where canChange is not null
        defaultUsersShouldBeFound("canChange.specified=true");

        // Get all the usersList where canChange is null
        defaultUsersShouldNotBeFound("canChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByMustChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where mustChange equals to DEFAULT_MUST_CHANGE
        defaultUsersShouldBeFound("mustChange.equals=" + DEFAULT_MUST_CHANGE);

        // Get all the usersList where mustChange equals to UPDATED_MUST_CHANGE
        defaultUsersShouldNotBeFound("mustChange.equals=" + UPDATED_MUST_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByMustChangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where mustChange not equals to DEFAULT_MUST_CHANGE
        defaultUsersShouldNotBeFound("mustChange.notEquals=" + DEFAULT_MUST_CHANGE);

        // Get all the usersList where mustChange not equals to UPDATED_MUST_CHANGE
        defaultUsersShouldBeFound("mustChange.notEquals=" + UPDATED_MUST_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByMustChangeIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where mustChange in DEFAULT_MUST_CHANGE or UPDATED_MUST_CHANGE
        defaultUsersShouldBeFound("mustChange.in=" + DEFAULT_MUST_CHANGE + "," + UPDATED_MUST_CHANGE);

        // Get all the usersList where mustChange equals to UPDATED_MUST_CHANGE
        defaultUsersShouldNotBeFound("mustChange.in=" + UPDATED_MUST_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUsersByMustChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where mustChange is not null
        defaultUsersShouldBeFound("mustChange.specified=true");

        // Get all the usersList where mustChange is null
        defaultUsersShouldNotBeFound("mustChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByActivationKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey equals to DEFAULT_ACTIVATION_KEY
        defaultUsersShouldBeFound("activationKey.equals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the usersList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultUsersShouldNotBeFound("activationKey.equals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByActivationKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey not equals to DEFAULT_ACTIVATION_KEY
        defaultUsersShouldNotBeFound("activationKey.notEquals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the usersList where activationKey not equals to UPDATED_ACTIVATION_KEY
        defaultUsersShouldBeFound("activationKey.notEquals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByActivationKeyIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey in DEFAULT_ACTIVATION_KEY or UPDATED_ACTIVATION_KEY
        defaultUsersShouldBeFound("activationKey.in=" + DEFAULT_ACTIVATION_KEY + "," + UPDATED_ACTIVATION_KEY);

        // Get all the usersList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultUsersShouldNotBeFound("activationKey.in=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByActivationKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey is not null
        defaultUsersShouldBeFound("activationKey.specified=true");

        // Get all the usersList where activationKey is null
        defaultUsersShouldNotBeFound("activationKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByActivationKeyContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey contains DEFAULT_ACTIVATION_KEY
        defaultUsersShouldBeFound("activationKey.contains=" + DEFAULT_ACTIVATION_KEY);

        // Get all the usersList where activationKey contains UPDATED_ACTIVATION_KEY
        defaultUsersShouldNotBeFound("activationKey.contains=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByActivationKeyNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where activationKey does not contain DEFAULT_ACTIVATION_KEY
        defaultUsersShouldNotBeFound("activationKey.doesNotContain=" + DEFAULT_ACTIVATION_KEY);

        // Get all the usersList where activationKey does not contain UPDATED_ACTIVATION_KEY
        defaultUsersShouldBeFound("activationKey.doesNotContain=" + UPDATED_ACTIVATION_KEY);
    }


    @Test
    @Transactional
    public void getAllUsersByResetKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey equals to DEFAULT_RESET_KEY
        defaultUsersShouldBeFound("resetKey.equals=" + DEFAULT_RESET_KEY);

        // Get all the usersList where resetKey equals to UPDATED_RESET_KEY
        defaultUsersShouldNotBeFound("resetKey.equals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByResetKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey not equals to DEFAULT_RESET_KEY
        defaultUsersShouldNotBeFound("resetKey.notEquals=" + DEFAULT_RESET_KEY);

        // Get all the usersList where resetKey not equals to UPDATED_RESET_KEY
        defaultUsersShouldBeFound("resetKey.notEquals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByResetKeyIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey in DEFAULT_RESET_KEY or UPDATED_RESET_KEY
        defaultUsersShouldBeFound("resetKey.in=" + DEFAULT_RESET_KEY + "," + UPDATED_RESET_KEY);

        // Get all the usersList where resetKey equals to UPDATED_RESET_KEY
        defaultUsersShouldNotBeFound("resetKey.in=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByResetKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey is not null
        defaultUsersShouldBeFound("resetKey.specified=true");

        // Get all the usersList where resetKey is null
        defaultUsersShouldNotBeFound("resetKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByResetKeyContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey contains DEFAULT_RESET_KEY
        defaultUsersShouldBeFound("resetKey.contains=" + DEFAULT_RESET_KEY);

        // Get all the usersList where resetKey contains UPDATED_RESET_KEY
        defaultUsersShouldNotBeFound("resetKey.contains=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    public void getAllUsersByResetKeyNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetKey does not contain DEFAULT_RESET_KEY
        defaultUsersShouldNotBeFound("resetKey.doesNotContain=" + DEFAULT_RESET_KEY);

        // Get all the usersList where resetKey does not contain UPDATED_RESET_KEY
        defaultUsersShouldBeFound("resetKey.doesNotContain=" + UPDATED_RESET_KEY);
    }


    @Test
    @Transactional
    public void getAllUsersByResetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate equals to DEFAULT_RESET_DATE
        defaultUsersShouldBeFound("resetDate.equals=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate equals to UPDATED_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.equals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate not equals to DEFAULT_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.notEquals=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate not equals to UPDATED_RESET_DATE
        defaultUsersShouldBeFound("resetDate.notEquals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate in DEFAULT_RESET_DATE or UPDATED_RESET_DATE
        defaultUsersShouldBeFound("resetDate.in=" + DEFAULT_RESET_DATE + "," + UPDATED_RESET_DATE);

        // Get all the usersList where resetDate equals to UPDATED_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.in=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate is not null
        defaultUsersShouldBeFound("resetDate.specified=true");

        // Get all the usersList where resetDate is null
        defaultUsersShouldNotBeFound("resetDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate is greater than or equal to DEFAULT_RESET_DATE
        defaultUsersShouldBeFound("resetDate.greaterThanOrEqual=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate is greater than or equal to UPDATED_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.greaterThanOrEqual=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate is less than or equal to DEFAULT_RESET_DATE
        defaultUsersShouldBeFound("resetDate.lessThanOrEqual=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate is less than or equal to SMALLER_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.lessThanOrEqual=" + SMALLER_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsLessThanSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate is less than DEFAULT_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.lessThan=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate is less than UPDATED_RESET_DATE
        defaultUsersShouldBeFound("resetDate.lessThan=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    public void getAllUsersByResetDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where resetDate is greater than DEFAULT_RESET_DATE
        defaultUsersShouldNotBeFound("resetDate.greaterThan=" + DEFAULT_RESET_DATE);

        // Get all the usersList where resetDate is greater than SMALLER_RESET_DATE
        defaultUsersShouldBeFound("resetDate.greaterThan=" + SMALLER_RESET_DATE);
    }


    @Test
    @Transactional
    public void getAllUsersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code equals to DEFAULT_CODE
        defaultUsersShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the usersList where code equals to UPDATED_CODE
        defaultUsersShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code not equals to DEFAULT_CODE
        defaultUsersShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the usersList where code not equals to UPDATED_CODE
        defaultUsersShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code in DEFAULT_CODE or UPDATED_CODE
        defaultUsersShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the usersList where code equals to UPDATED_CODE
        defaultUsersShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code is not null
        defaultUsersShouldBeFound("code.specified=true");

        // Get all the usersList where code is null
        defaultUsersShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsersByCodeContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code contains DEFAULT_CODE
        defaultUsersShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the usersList where code contains UPDATED_CODE
        defaultUsersShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllUsersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList where code does not contain DEFAULT_CODE
        defaultUsersShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the usersList where code does not contain UPDATED_CODE
        defaultUsersShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllUsersByRoleIsEqualToSomething() throws Exception {
        // Get already existing entity
        Role role = users.getRole();
        usersRepository.saveAndFlush(users);
        Long roleId = role.getId();

        // Get all the usersList where role equals to roleId
        defaultUsersShouldBeFound("roleId.equals=" + roleId);

        // Get all the usersList where role equals to roleId + 1
        defaultUsersShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }


    @Test
    @Transactional
    public void getAllUsersByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);
        GroupTranslate login = GroupTranslateResourceIT.createEntity(em);
        em.persist(login);
        em.flush();
        users.addLogin(login);
        usersRepository.saveAndFlush(users);
        Long loginId = login.getId();

        // Get all the usersList where login equals to loginId
        defaultUsersShouldBeFound("loginId.equals=" + loginId);

        // Get all the usersList where login equals to loginId + 1
        defaultUsersShouldNotBeFound("loginId.equals=" + (loginId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsersShouldBeFound(String filter) throws Exception {
        restUsersMockMvc.perform(get("/api/users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].canChange").value(hasItem(DEFAULT_CAN_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].mustChange").value(hasItem(DEFAULT_MUST_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(sameInstant(DEFAULT_RESET_DATE))))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restUsersMockMvc.perform(get("/api/users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsersShouldNotBeFound(String filter) throws Exception {
        restUsersMockMvc.perform(get("/api/users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsersMockMvc.perform(get("/api/users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        // Disconnect from session so that the updates on updatedUsers are not directly saved in db
        em.detach(updatedUsers);
        updatedUsers
            .login(UPDATED_LOGIN)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .locked(UPDATED_LOCKED)
            .canChange(UPDATED_CAN_CHANGE)
            .mustChange(UPDATED_MUST_CHANGE)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .code(UPDATED_CODE);
        UsersDTO usersDTO = usersMapper.toDto(updatedUsers);

        restUsersMockMvc.perform(put("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testUsers.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsers.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testUsers.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUsers.isLocked()).isEqualTo(UPDATED_LOCKED);
        assertThat(testUsers.isCanChange()).isEqualTo(UPDATED_CAN_CHANGE);
        assertThat(testUsers.isMustChange()).isEqualTo(UPDATED_MUST_CHANGE);
        assertThat(testUsers.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testUsers.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testUsers.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testUsers.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc.perform(put("/api/users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
