package vn.com.truyen.usermanagent.web.rest;

import vn.com.truyen.usermanagent.UsermanagentApp;
import vn.com.truyen.usermanagent.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.usermanagent.domain.UserInfo;
import vn.com.truyen.usermanagent.domain.Users;
import vn.com.truyen.usermanagent.repository.UserInfoRepository;
import vn.com.truyen.usermanagent.service.UserInfoService;
import vn.com.truyen.usermanagent.service.dto.UserInfoDTO;
import vn.com.truyen.usermanagent.service.mapper.UserInfoMapper;
import vn.com.truyen.usermanagent.service.dto.UserInfoCriteria;
import vn.com.truyen.usermanagent.service.UserInfoQueryService;

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
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, UsermanagentApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserInfoResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INITIALS = "AAAAAAAAAA";
    private static final String UPDATED_INITIALS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;
    private static final Integer SMALLER_MOBILE = 1 - 1;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoQueryService userInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .fullName(DEFAULT_FULL_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .initials(DEFAULT_INITIALS)
            .comment(DEFAULT_COMMENT)
            .mobile(DEFAULT_MOBILE);
        // Add required entity
        Users users;
        if (TestUtil.findAll(em, Users.class).isEmpty()) {
            users = UsersResourceIT.createEntity(em);
            em.persist(users);
            em.flush();
        } else {
            users = TestUtil.findAll(em, Users.class).get(0);
        }
        userInfo.setUser(users);
        return userInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initials(UPDATED_INITIALS)
            .comment(UPDATED_COMMENT)
            .mobile(UPDATED_MOBILE);
        // Add required entity
        Users users;
        if (TestUtil.findAll(em, Users.class).isEmpty()) {
            users = UsersResourceIT.createUpdatedEntity(em);
            em.persist(users);
            em.flush();
        } else {
            users = TestUtil.findAll(em, Users.class).get(0);
        }
        userInfo.setUser(users);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();
        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);
        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUserInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserInfo.getInitials()).isEqualTo(DEFAULT_INITIALS);
        assertThat(testUserInfo.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserInfo.getMobile()).isEqualTo(DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    public void createUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo with an existing ID
        userInfo.setId(1L);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setFullName(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setFirstName(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setLastName(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitialsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setInitials(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setComment(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setMobile(null);

        // Create the UserInfo, which fails.
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);


        restUserInfoMockMvc.perform(post("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].initials").value(hasItem(DEFAULT_INITIALS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)));
    }
    
    @Test
    @Transactional
    public void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.initials").value(DEFAULT_INITIALS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE));
    }


    @Test
    @Transactional
    public void getUserInfosByIdFiltering() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        Long id = userInfo.getId();

        defaultUserInfoShouldBeFound("id.equals=" + id);
        defaultUserInfoShouldNotBeFound("id.notEquals=" + id);

        defaultUserInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultUserInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserInfosByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName equals to DEFAULT_FULL_NAME
        defaultUserInfoShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the userInfoList where fullName equals to UPDATED_FULL_NAME
        defaultUserInfoShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName not equals to DEFAULT_FULL_NAME
        defaultUserInfoShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the userInfoList where fullName not equals to UPDATED_FULL_NAME
        defaultUserInfoShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultUserInfoShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the userInfoList where fullName equals to UPDATED_FULL_NAME
        defaultUserInfoShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName is not null
        defaultUserInfoShouldBeFound("fullName.specified=true");

        // Get all the userInfoList where fullName is null
        defaultUserInfoShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByFullNameContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName contains DEFAULT_FULL_NAME
        defaultUserInfoShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the userInfoList where fullName contains UPDATED_FULL_NAME
        defaultUserInfoShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where fullName does not contain DEFAULT_FULL_NAME
        defaultUserInfoShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the userInfoList where fullName does not contain UPDATED_FULL_NAME
        defaultUserInfoShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName equals to DEFAULT_FIRST_NAME
        defaultUserInfoShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the userInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultUserInfoShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName not equals to DEFAULT_FIRST_NAME
        defaultUserInfoShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the userInfoList where firstName not equals to UPDATED_FIRST_NAME
        defaultUserInfoShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultUserInfoShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the userInfoList where firstName equals to UPDATED_FIRST_NAME
        defaultUserInfoShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName is not null
        defaultUserInfoShouldBeFound("firstName.specified=true");

        // Get all the userInfoList where firstName is null
        defaultUserInfoShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName contains DEFAULT_FIRST_NAME
        defaultUserInfoShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the userInfoList where firstName contains UPDATED_FIRST_NAME
        defaultUserInfoShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where firstName does not contain DEFAULT_FIRST_NAME
        defaultUserInfoShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the userInfoList where firstName does not contain UPDATED_FIRST_NAME
        defaultUserInfoShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName equals to DEFAULT_LAST_NAME
        defaultUserInfoShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the userInfoList where lastName equals to UPDATED_LAST_NAME
        defaultUserInfoShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName not equals to DEFAULT_LAST_NAME
        defaultUserInfoShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the userInfoList where lastName not equals to UPDATED_LAST_NAME
        defaultUserInfoShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultUserInfoShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the userInfoList where lastName equals to UPDATED_LAST_NAME
        defaultUserInfoShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName is not null
        defaultUserInfoShouldBeFound("lastName.specified=true");

        // Get all the userInfoList where lastName is null
        defaultUserInfoShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByLastNameContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName contains DEFAULT_LAST_NAME
        defaultUserInfoShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the userInfoList where lastName contains UPDATED_LAST_NAME
        defaultUserInfoShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where lastName does not contain DEFAULT_LAST_NAME
        defaultUserInfoShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the userInfoList where lastName does not contain UPDATED_LAST_NAME
        defaultUserInfoShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByInitialsIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials equals to DEFAULT_INITIALS
        defaultUserInfoShouldBeFound("initials.equals=" + DEFAULT_INITIALS);

        // Get all the userInfoList where initials equals to UPDATED_INITIALS
        defaultUserInfoShouldNotBeFound("initials.equals=" + UPDATED_INITIALS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByInitialsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials not equals to DEFAULT_INITIALS
        defaultUserInfoShouldNotBeFound("initials.notEquals=" + DEFAULT_INITIALS);

        // Get all the userInfoList where initials not equals to UPDATED_INITIALS
        defaultUserInfoShouldBeFound("initials.notEquals=" + UPDATED_INITIALS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByInitialsIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials in DEFAULT_INITIALS or UPDATED_INITIALS
        defaultUserInfoShouldBeFound("initials.in=" + DEFAULT_INITIALS + "," + UPDATED_INITIALS);

        // Get all the userInfoList where initials equals to UPDATED_INITIALS
        defaultUserInfoShouldNotBeFound("initials.in=" + UPDATED_INITIALS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByInitialsIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials is not null
        defaultUserInfoShouldBeFound("initials.specified=true");

        // Get all the userInfoList where initials is null
        defaultUserInfoShouldNotBeFound("initials.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByInitialsContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials contains DEFAULT_INITIALS
        defaultUserInfoShouldBeFound("initials.contains=" + DEFAULT_INITIALS);

        // Get all the userInfoList where initials contains UPDATED_INITIALS
        defaultUserInfoShouldNotBeFound("initials.contains=" + UPDATED_INITIALS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByInitialsNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where initials does not contain DEFAULT_INITIALS
        defaultUserInfoShouldNotBeFound("initials.doesNotContain=" + DEFAULT_INITIALS);

        // Get all the userInfoList where initials does not contain UPDATED_INITIALS
        defaultUserInfoShouldBeFound("initials.doesNotContain=" + UPDATED_INITIALS);
    }


    @Test
    @Transactional
    public void getAllUserInfosByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment equals to DEFAULT_COMMENT
        defaultUserInfoShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the userInfoList where comment equals to UPDATED_COMMENT
        defaultUserInfoShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment not equals to DEFAULT_COMMENT
        defaultUserInfoShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the userInfoList where comment not equals to UPDATED_COMMENT
        defaultUserInfoShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultUserInfoShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the userInfoList where comment equals to UPDATED_COMMENT
        defaultUserInfoShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment is not null
        defaultUserInfoShouldBeFound("comment.specified=true");

        // Get all the userInfoList where comment is null
        defaultUserInfoShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserInfosByCommentContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment contains DEFAULT_COMMENT
        defaultUserInfoShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the userInfoList where comment contains UPDATED_COMMENT
        defaultUserInfoShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where comment does not contain DEFAULT_COMMENT
        defaultUserInfoShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the userInfoList where comment does not contain UPDATED_COMMENT
        defaultUserInfoShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllUserInfosByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile equals to DEFAULT_MOBILE
        defaultUserInfoShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile equals to UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile not equals to DEFAULT_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile not equals to UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the userInfoList where mobile equals to UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is not null
        defaultUserInfoShouldBeFound("mobile.specified=true");

        // Get all the userInfoList where mobile is null
        defaultUserInfoShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is greater than or equal to DEFAULT_MOBILE
        defaultUserInfoShouldBeFound("mobile.greaterThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile is greater than or equal to UPDATED_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.greaterThanOrEqual=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is less than or equal to DEFAULT_MOBILE
        defaultUserInfoShouldBeFound("mobile.lessThanOrEqual=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile is less than or equal to SMALLER_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.lessThanOrEqual=" + SMALLER_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is less than DEFAULT_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.lessThan=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile is less than UPDATED_MOBILE
        defaultUserInfoShouldBeFound("mobile.lessThan=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMobileIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where mobile is greater than DEFAULT_MOBILE
        defaultUserInfoShouldNotBeFound("mobile.greaterThan=" + DEFAULT_MOBILE);

        // Get all the userInfoList where mobile is greater than SMALLER_MOBILE
        defaultUserInfoShouldBeFound("mobile.greaterThan=" + SMALLER_MOBILE);
    }


    @Test
    @Transactional
    public void getAllUserInfosByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        Users user = userInfo.getUser();
        userInfoRepository.saveAndFlush(userInfo);
        Long userId = user.getId();

        // Get all the userInfoList where user equals to userId
        defaultUserInfoShouldBeFound("userId.equals=" + userId);

        // Get all the userInfoList where user equals to userId + 1
        defaultUserInfoShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserInfoShouldBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].initials").value(hasItem(DEFAULT_INITIALS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)));

        // Check, that the count call also returns 1
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserInfoShouldNotBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .fullName(UPDATED_FULL_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initials(UPDATED_INITIALS)
            .comment(UPDATED_COMMENT)
            .mobile(UPDATED_MOBILE);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(updatedUserInfo);

        restUserInfoMockMvc.perform(put("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUserInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserInfo.getInitials()).isEqualTo(UPDATED_INITIALS);
        assertThat(testUserInfo.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserInfo.getMobile()).isEqualTo(UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc.perform(put("/api/user-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc.perform(delete("/api/user-infos/{id}", userInfo.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
