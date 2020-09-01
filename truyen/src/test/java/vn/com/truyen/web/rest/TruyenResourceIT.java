//package vn.com.truyen.web.rest;
//
//import vn.com.truyen.TruyenApp;
//import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
//import vn.com.truyen.domain.Truyen;
//import vn.com.truyen.domain.Chuong;
//import vn.com.truyen.domain.View;
//import vn.com.truyen.domain.Feedback;
//import vn.com.truyen.domain.Author;
//import vn.com.truyen.domain.Category;
//import vn.com.truyen.repository.TruyenRepository;
//import vn.com.truyen.service.TruyenService;
//import vn.com.truyen.service.dto.TruyenDTO;
//import vn.com.truyen.service.mapper.TruyenMapper;
//import vn.com.truyen.service.dto.TruyenCriteria;
//import vn.com.truyen.service.TruyenQueryService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Base64Utils;
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link TruyenResource} REST controller.
// */
//@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
//@AutoConfigureMockMvc
//@WithMockUser
//public class TruyenResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_SO_CHUONG = 1;
//    private static final Integer UPDATED_SO_CHUONG = 2;
//    private static final Integer SMALLER_SO_CHUONG = 1 - 1;
//
//    private static final String DEFAULT_NGUON = "AAAAAAAAAA";
//    private static final String UPDATED_NGUON = "BBBBBBBBBB";
//
//    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";
//
//    private static final Boolean DEFAULT_FULLS = false;
//    private static final Boolean UPDATED_FULLS = true;
//
//    private static final Boolean DEFAULT_HOT = false;
//    private static final Boolean UPDATED_HOT = true;
//
//    private static final Boolean DEFAULT_NEWS = false;
//    private static final Boolean UPDATED_NEWS = true;
//
//    private static final Boolean DEFAULT_LOCKED = false;
//    private static final Boolean UPDATED_LOCKED = true;
//
//    private static final String DEFAULT_CODE = "AAAAAAAAAA";
//    private static final String UPDATED_CODE = "BBBBBBBBBB";
//
//    @Autowired
//    private TruyenRepository truyenRepository;
//
//    @Autowired
//    private TruyenMapper truyenMapper;
//
//    @Autowired
//    private TruyenService truyenService;
//
//    @Autowired
//    private TruyenQueryService truyenQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restTruyenMockMvc;
//
//    private Truyen truyen;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Truyen createEntity(EntityManager em) {
//        Truyen truyen = new Truyen()
//            .name(DEFAULT_NAME)
//            .description(DEFAULT_DESCRIPTION)
//            .soChuong(DEFAULT_SO_CHUONG)
//            .nguon(DEFAULT_NGUON)
//            .image(DEFAULT_IMAGE)
//            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
//            .fulls(DEFAULT_FULLS)
//            .hot(DEFAULT_HOT)
//            .news(DEFAULT_NEWS)
//            .locked(DEFAULT_LOCKED)
//            .code(DEFAULT_CODE);
//        // Add required entity
//        Chuong chuong;
//        if (TestUtil.findAll(em, Chuong.class).isEmpty()) {
//            chuong = ChuongResourceIT.createEntity(em);
//            em.persist(chuong);
//            em.flush();
//        } else {
//            chuong = TestUtil.findAll(em, Chuong.class).get(0);
//        }
//        truyen.getChuongs().add(chuong);
//        // Add required entity
//        View view;
//        if (TestUtil.findAll(em, View.class).isEmpty()) {
//            view = ViewResourceIT.createEntity(em);
//            em.persist(view);
//            em.flush();
//        } else {
//            view = TestUtil.findAll(em, View.class).get(0);
//        }
//        truyen.getViews().add(view);
//        // Add required entity
//        Feedback feedback;
//        if (TestUtil.findAll(em, Feedback.class).isEmpty()) {
//            feedback = FeedbackResourceIT.createEntity(em);
//            em.persist(feedback);
//            em.flush();
//        } else {
//            feedback = TestUtil.findAll(em, Feedback.class).get(0);
//        }
//        truyen.getFeadbacks().add(feedback);
//        // Add required entity
//        Author author;
//        if (TestUtil.findAll(em, Author.class).isEmpty()) {
//            author = AuthorResourceIT.createEntity(em);
//            em.persist(author);
//            em.flush();
//        } else {
//            author = TestUtil.findAll(em, Author.class).get(0);
//        }
//        truyen.setAuthor(author);
//        return truyen;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Truyen createUpdatedEntity(EntityManager em) {
//        Truyen truyen = new Truyen()
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .soChuong(UPDATED_SO_CHUONG)
//            .nguon(UPDATED_NGUON)
//            .image(UPDATED_IMAGE)
//            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
//            .fulls(UPDATED_FULLS)
//            .hot(UPDATED_HOT)
//            .news(UPDATED_NEWS)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        // Add required entity
//        Chuong chuong;
//        if (TestUtil.findAll(em, Chuong.class).isEmpty()) {
//            chuong = ChuongResourceIT.createUpdatedEntity(em);
//            em.persist(chuong);
//            em.flush();
//        } else {
//            chuong = TestUtil.findAll(em, Chuong.class).get(0);
//        }
//        truyen.getChuongs().add(chuong);
//        // Add required entity
//        View view;
//        if (TestUtil.findAll(em, View.class).isEmpty()) {
//            view = ViewResourceIT.createUpdatedEntity(em);
//            em.persist(view);
//            em.flush();
//        } else {
//            view = TestUtil.findAll(em, View.class).get(0);
//        }
//        truyen.getViews().add(view);
//        // Add required entity
//        Feedback feedback;
//        if (TestUtil.findAll(em, Feedback.class).isEmpty()) {
//            feedback = FeedbackResourceIT.createUpdatedEntity(em);
//            em.persist(feedback);
//            em.flush();
//        } else {
//            feedback = TestUtil.findAll(em, Feedback.class).get(0);
//        }
//        truyen.getFeadbacks().add(feedback);
//        // Add required entity
//        Author author;
//        if (TestUtil.findAll(em, Author.class).isEmpty()) {
//            author = AuthorResourceIT.createUpdatedEntity(em);
//            em.persist(author);
//            em.flush();
//        } else {
//            author = TestUtil.findAll(em, Author.class).get(0);
//        }
//        truyen.setAuthor(author);
//        return truyen;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        truyen = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createTruyen() throws Exception {
//        int databaseSizeBeforeCreate = truyenRepository.findAll().size();
//        // Create the Truyen
//        TruyenDTO truyenDTO = truyenMapper.toDto(truyen);
//        restTruyenMockMvc.perform(post("/api/truyens").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(truyenDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Truyen in the database
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeCreate + 1);
//        Truyen testTruyen = truyenList.get(truyenList.size() - 1);
//        assertThat(testTruyen.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testTruyen.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testTruyen.getSoChuong()).isEqualTo(DEFAULT_SO_CHUONG);
//        assertThat(testTruyen.getNguon()).isEqualTo(DEFAULT_NGUON);
//        assertThat(testTruyen.getImage()).isEqualTo(DEFAULT_IMAGE);
//        assertThat(testTruyen.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
//        assertThat(testTruyen.isFulls()).isEqualTo(DEFAULT_FULLS);
//        assertThat(testTruyen.isHot()).isEqualTo(DEFAULT_HOT);
//        assertThat(testTruyen.isNews()).isEqualTo(DEFAULT_NEWS);
//        assertThat(testTruyen.isLocked()).isEqualTo(DEFAULT_LOCKED);
//        assertThat(testTruyen.getCode()).isEqualTo(DEFAULT_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void createTruyenWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = truyenRepository.findAll().size();
//
//        // Create the Truyen with an existing ID
//        truyen.setId(1L);
//        TruyenDTO truyenDTO = truyenMapper.toDto(truyen);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restTruyenMockMvc.perform(post("/api/truyens").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(truyenDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Truyen in the database
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = truyenRepository.findAll().size();
//        // set the field null
//        truyen.setName(null);
//
//        // Create the Truyen, which fails.
//        TruyenDTO truyenDTO = truyenMapper.toDto(truyen);
//
//
//        restTruyenMockMvc.perform(post("/api/truyens").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(truyenDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyens() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList
//        restTruyenMockMvc.perform(get("/api/truyens?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(truyen.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].soChuong").value(hasItem(DEFAULT_SO_CHUONG)))
//            .andExpect(jsonPath("$.[*].nguon").value(hasItem(DEFAULT_NGUON)))
//            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
//            .andExpect(jsonPath("$.[*].fulls").value(hasItem(DEFAULT_FULLS.booleanValue())))
//            .andExpect(jsonPath("$.[*].hot").value(hasItem(DEFAULT_HOT.booleanValue())))
//            .andExpect(jsonPath("$.[*].news").value(hasItem(DEFAULT_NEWS.booleanValue())))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//    }
//    
//    @Test
//    @Transactional
//    public void getTruyen() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get the truyen
//        restTruyenMockMvc.perform(get("/api/truyens/{id}", truyen.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(truyen.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.soChuong").value(DEFAULT_SO_CHUONG))
//            .andExpect(jsonPath("$.nguon").value(DEFAULT_NGUON))
//            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
//            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
//            .andExpect(jsonPath("$.fulls").value(DEFAULT_FULLS.booleanValue()))
//            .andExpect(jsonPath("$.hot").value(DEFAULT_HOT.booleanValue()))
//            .andExpect(jsonPath("$.news").value(DEFAULT_NEWS.booleanValue()))
//            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
//    }
//
//
//    @Test
//    @Transactional
//    public void getTruyensByIdFiltering() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        Long id = truyen.getId();
//
//        defaultTruyenShouldBeFound("id.equals=" + id);
//        defaultTruyenShouldNotBeFound("id.notEquals=" + id);
//
//        defaultTruyenShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultTruyenShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultTruyenShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultTruyenShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name equals to DEFAULT_NAME
//        defaultTruyenShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the truyenList where name equals to UPDATED_NAME
//        defaultTruyenShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name not equals to DEFAULT_NAME
//        defaultTruyenShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);
//
//        // Get all the truyenList where name not equals to UPDATED_NAME
//        defaultTruyenShouldBeFound("name.notEquals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultTruyenShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the truyenList where name equals to UPDATED_NAME
//        defaultTruyenShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name is not null
//        defaultTruyenShouldBeFound("name.specified=true");
//
//        // Get all the truyenList where name is null
//        defaultTruyenShouldNotBeFound("name.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllTruyensByNameContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name contains DEFAULT_NAME
//        defaultTruyenShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the truyenList where name contains UPDATED_NAME
//        defaultTruyenShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where name does not contain DEFAULT_NAME
//        defaultTruyenShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the truyenList where name does not contain UPDATED_NAME
//        defaultTruyenShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByDescriptionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description equals to DEFAULT_DESCRIPTION
//        defaultTruyenShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the truyenList where description equals to UPDATED_DESCRIPTION
//        defaultTruyenShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByDescriptionIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description not equals to DEFAULT_DESCRIPTION
//        defaultTruyenShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the truyenList where description not equals to UPDATED_DESCRIPTION
//        defaultTruyenShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByDescriptionIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
//        defaultTruyenShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);
//
//        // Get all the truyenList where description equals to UPDATED_DESCRIPTION
//        defaultTruyenShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByDescriptionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description is not null
//        defaultTruyenShouldBeFound("description.specified=true");
//
//        // Get all the truyenList where description is null
//        defaultTruyenShouldNotBeFound("description.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllTruyensByDescriptionContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description contains DEFAULT_DESCRIPTION
//        defaultTruyenShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);
//
//        // Get all the truyenList where description contains UPDATED_DESCRIPTION
//        defaultTruyenShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByDescriptionNotContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where description does not contain DEFAULT_DESCRIPTION
//        defaultTruyenShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);
//
//        // Get all the truyenList where description does not contain UPDATED_DESCRIPTION
//        defaultTruyenShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong equals to DEFAULT_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.equals=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong equals to UPDATED_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.equals=" + UPDATED_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong not equals to DEFAULT_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.notEquals=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong not equals to UPDATED_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.notEquals=" + UPDATED_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong in DEFAULT_SO_CHUONG or UPDATED_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.in=" + DEFAULT_SO_CHUONG + "," + UPDATED_SO_CHUONG);
//
//        // Get all the truyenList where soChuong equals to UPDATED_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.in=" + UPDATED_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong is not null
//        defaultTruyenShouldBeFound("soChuong.specified=true");
//
//        // Get all the truyenList where soChuong is null
//        defaultTruyenShouldNotBeFound("soChuong.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong is greater than or equal to DEFAULT_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.greaterThanOrEqual=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong is greater than or equal to UPDATED_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.greaterThanOrEqual=" + UPDATED_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong is less than or equal to DEFAULT_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.lessThanOrEqual=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong is less than or equal to SMALLER_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.lessThanOrEqual=" + SMALLER_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsLessThanSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong is less than DEFAULT_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.lessThan=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong is less than UPDATED_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.lessThan=" + UPDATED_SO_CHUONG);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensBySoChuongIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where soChuong is greater than DEFAULT_SO_CHUONG
//        defaultTruyenShouldNotBeFound("soChuong.greaterThan=" + DEFAULT_SO_CHUONG);
//
//        // Get all the truyenList where soChuong is greater than SMALLER_SO_CHUONG
//        defaultTruyenShouldBeFound("soChuong.greaterThan=" + SMALLER_SO_CHUONG);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNguonIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon equals to DEFAULT_NGUON
//        defaultTruyenShouldBeFound("nguon.equals=" + DEFAULT_NGUON);
//
//        // Get all the truyenList where nguon equals to UPDATED_NGUON
//        defaultTruyenShouldNotBeFound("nguon.equals=" + UPDATED_NGUON);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNguonIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon not equals to DEFAULT_NGUON
//        defaultTruyenShouldNotBeFound("nguon.notEquals=" + DEFAULT_NGUON);
//
//        // Get all the truyenList where nguon not equals to UPDATED_NGUON
//        defaultTruyenShouldBeFound("nguon.notEquals=" + UPDATED_NGUON);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNguonIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon in DEFAULT_NGUON or UPDATED_NGUON
//        defaultTruyenShouldBeFound("nguon.in=" + DEFAULT_NGUON + "," + UPDATED_NGUON);
//
//        // Get all the truyenList where nguon equals to UPDATED_NGUON
//        defaultTruyenShouldNotBeFound("nguon.in=" + UPDATED_NGUON);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNguonIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon is not null
//        defaultTruyenShouldBeFound("nguon.specified=true");
//
//        // Get all the truyenList where nguon is null
//        defaultTruyenShouldNotBeFound("nguon.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllTruyensByNguonContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon contains DEFAULT_NGUON
//        defaultTruyenShouldBeFound("nguon.contains=" + DEFAULT_NGUON);
//
//        // Get all the truyenList where nguon contains UPDATED_NGUON
//        defaultTruyenShouldNotBeFound("nguon.contains=" + UPDATED_NGUON);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNguonNotContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where nguon does not contain DEFAULT_NGUON
//        defaultTruyenShouldNotBeFound("nguon.doesNotContain=" + DEFAULT_NGUON);
//
//        // Get all the truyenList where nguon does not contain UPDATED_NGUON
//        defaultTruyenShouldBeFound("nguon.doesNotContain=" + UPDATED_NGUON);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByFullsIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where fulls equals to DEFAULT_FULLS
//        defaultTruyenShouldBeFound("fulls.equals=" + DEFAULT_FULLS);
//
//        // Get all the truyenList where fulls equals to UPDATED_FULLS
//        defaultTruyenShouldNotBeFound("fulls.equals=" + UPDATED_FULLS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByFullsIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where fulls not equals to DEFAULT_FULLS
//        defaultTruyenShouldNotBeFound("fulls.notEquals=" + DEFAULT_FULLS);
//
//        // Get all the truyenList where fulls not equals to UPDATED_FULLS
//        defaultTruyenShouldBeFound("fulls.notEquals=" + UPDATED_FULLS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByFullsIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where fulls in DEFAULT_FULLS or UPDATED_FULLS
//        defaultTruyenShouldBeFound("fulls.in=" + DEFAULT_FULLS + "," + UPDATED_FULLS);
//
//        // Get all the truyenList where fulls equals to UPDATED_FULLS
//        defaultTruyenShouldNotBeFound("fulls.in=" + UPDATED_FULLS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByFullsIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where fulls is not null
//        defaultTruyenShouldBeFound("fulls.specified=true");
//
//        // Get all the truyenList where fulls is null
//        defaultTruyenShouldNotBeFound("fulls.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByHotIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where hot equals to DEFAULT_HOT
//        defaultTruyenShouldBeFound("hot.equals=" + DEFAULT_HOT);
//
//        // Get all the truyenList where hot equals to UPDATED_HOT
//        defaultTruyenShouldNotBeFound("hot.equals=" + UPDATED_HOT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByHotIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where hot not equals to DEFAULT_HOT
//        defaultTruyenShouldNotBeFound("hot.notEquals=" + DEFAULT_HOT);
//
//        // Get all the truyenList where hot not equals to UPDATED_HOT
//        defaultTruyenShouldBeFound("hot.notEquals=" + UPDATED_HOT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByHotIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where hot in DEFAULT_HOT or UPDATED_HOT
//        defaultTruyenShouldBeFound("hot.in=" + DEFAULT_HOT + "," + UPDATED_HOT);
//
//        // Get all the truyenList where hot equals to UPDATED_HOT
//        defaultTruyenShouldNotBeFound("hot.in=" + UPDATED_HOT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByHotIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where hot is not null
//        defaultTruyenShouldBeFound("hot.specified=true");
//
//        // Get all the truyenList where hot is null
//        defaultTruyenShouldNotBeFound("hot.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNewsIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where news equals to DEFAULT_NEWS
//        defaultTruyenShouldBeFound("news.equals=" + DEFAULT_NEWS);
//
//        // Get all the truyenList where news equals to UPDATED_NEWS
//        defaultTruyenShouldNotBeFound("news.equals=" + UPDATED_NEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNewsIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where news not equals to DEFAULT_NEWS
//        defaultTruyenShouldNotBeFound("news.notEquals=" + DEFAULT_NEWS);
//
//        // Get all the truyenList where news not equals to UPDATED_NEWS
//        defaultTruyenShouldBeFound("news.notEquals=" + UPDATED_NEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNewsIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where news in DEFAULT_NEWS or UPDATED_NEWS
//        defaultTruyenShouldBeFound("news.in=" + DEFAULT_NEWS + "," + UPDATED_NEWS);
//
//        // Get all the truyenList where news equals to UPDATED_NEWS
//        defaultTruyenShouldNotBeFound("news.in=" + UPDATED_NEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNewsIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where news is not null
//        defaultTruyenShouldBeFound("news.specified=true");
//
//        // Get all the truyenList where news is null
//        defaultTruyenShouldNotBeFound("news.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByLockedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where locked equals to DEFAULT_LOCKED
//        defaultTruyenShouldBeFound("locked.equals=" + DEFAULT_LOCKED);
//
//        // Get all the truyenList where locked equals to UPDATED_LOCKED
//        defaultTruyenShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByLockedIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where locked not equals to DEFAULT_LOCKED
//        defaultTruyenShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);
//
//        // Get all the truyenList where locked not equals to UPDATED_LOCKED
//        defaultTruyenShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByLockedIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
//        defaultTruyenShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);
//
//        // Get all the truyenList where locked equals to UPDATED_LOCKED
//        defaultTruyenShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByLockedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where locked is not null
//        defaultTruyenShouldBeFound("locked.specified=true");
//
//        // Get all the truyenList where locked is null
//        defaultTruyenShouldNotBeFound("locked.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByCodeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code equals to DEFAULT_CODE
//        defaultTruyenShouldBeFound("code.equals=" + DEFAULT_CODE);
//
//        // Get all the truyenList where code equals to UPDATED_CODE
//        defaultTruyenShouldNotBeFound("code.equals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByCodeIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code not equals to DEFAULT_CODE
//        defaultTruyenShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);
//
//        // Get all the truyenList where code not equals to UPDATED_CODE
//        defaultTruyenShouldBeFound("code.notEquals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByCodeIsInShouldWork() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code in DEFAULT_CODE or UPDATED_CODE
//        defaultTruyenShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);
//
//        // Get all the truyenList where code equals to UPDATED_CODE
//        defaultTruyenShouldNotBeFound("code.in=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByCodeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code is not null
//        defaultTruyenShouldBeFound("code.specified=true");
//
//        // Get all the truyenList where code is null
//        defaultTruyenShouldNotBeFound("code.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllTruyensByCodeContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code contains DEFAULT_CODE
//        defaultTruyenShouldBeFound("code.contains=" + DEFAULT_CODE);
//
//        // Get all the truyenList where code contains UPDATED_CODE
//        defaultTruyenShouldNotBeFound("code.contains=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTruyensByCodeNotContainsSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        // Get all the truyenList where code does not contain DEFAULT_CODE
//        defaultTruyenShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);
//
//        // Get all the truyenList where code does not contain UPDATED_CODE
//        defaultTruyenShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByChuongIsEqualToSomething() throws Exception {
//        // Get already existing entity
//        Chuong chuong = truyen.getChuong();
//        truyenRepository.saveAndFlush(truyen);
//        Long chuongId = chuong.getId();
//
//        // Get all the truyenList where chuong equals to chuongId
//        defaultTruyenShouldBeFound("chuongId.equals=" + chuongId);
//
//        // Get all the truyenList where chuong equals to chuongId + 1
//        defaultTruyenShouldNotBeFound("chuongId.equals=" + (chuongId + 1));
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByViewIsEqualToSomething() throws Exception {
//        // Get already existing entity
//        View view = truyen.getView();
//        truyenRepository.saveAndFlush(truyen);
//        Long viewId = view.getId();
//
//        // Get all the truyenList where view equals to viewId
//        defaultTruyenShouldBeFound("viewId.equals=" + viewId);
//
//        // Get all the truyenList where view equals to viewId + 1
//        defaultTruyenShouldNotBeFound("viewId.equals=" + (viewId + 1));
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByFeadbackIsEqualToSomething() throws Exception {
//        // Get already existing entity
//        Feedback feadback = truyen.getFeadback();
//        truyenRepository.saveAndFlush(truyen);
//        Long feadbackId = feadback.getId();
//
//        // Get all the truyenList where feadback equals to feadbackId
//        defaultTruyenShouldBeFound("feadbackId.equals=" + feadbackId);
//
//        // Get all the truyenList where feadback equals to feadbackId + 1
//        defaultTruyenShouldNotBeFound("feadbackId.equals=" + (feadbackId + 1));
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByAuthorIsEqualToSomething() throws Exception {
//        // Get already existing entity
//        Author author = truyen.getAuthor();
//        truyenRepository.saveAndFlush(truyen);
//        Long authorId = author.getId();
//
//        // Get all the truyenList where author equals to authorId
//        defaultTruyenShouldBeFound("authorId.equals=" + authorId);
//
//        // Get all the truyenList where author equals to authorId + 1
//        defaultTruyenShouldNotBeFound("authorId.equals=" + (authorId + 1));
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllTruyensByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//        Category name = CategoryResourceIT.createEntity(em);
//        em.persist(name);
//        em.flush();
//        truyen.addName(name);
//        truyenRepository.saveAndFlush(truyen);
//        Long nameId = name.getId();
//
//        // Get all the truyenList where name equals to nameId
//        defaultTruyenShouldBeFound("nameId.equals=" + nameId);
//
//        // Get all the truyenList where name equals to nameId + 1
//        defaultTruyenShouldNotBeFound("nameId.equals=" + (nameId + 1));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultTruyenShouldBeFound(String filter) throws Exception {
//        restTruyenMockMvc.perform(get("/api/truyens?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(truyen.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].soChuong").value(hasItem(DEFAULT_SO_CHUONG)))
//            .andExpect(jsonPath("$.[*].nguon").value(hasItem(DEFAULT_NGUON)))
//            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
//            .andExpect(jsonPath("$.[*].fulls").value(hasItem(DEFAULT_FULLS.booleanValue())))
//            .andExpect(jsonPath("$.[*].hot").value(hasItem(DEFAULT_HOT.booleanValue())))
//            .andExpect(jsonPath("$.[*].news").value(hasItem(DEFAULT_NEWS.booleanValue())))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//
//        // Check, that the count call also returns 1
//        restTruyenMockMvc.perform(get("/api/truyens/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultTruyenShouldNotBeFound(String filter) throws Exception {
//        restTruyenMockMvc.perform(get("/api/truyens?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restTruyenMockMvc.perform(get("/api/truyens/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingTruyen() throws Exception {
//        // Get the truyen
//        restTruyenMockMvc.perform(get("/api/truyens/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateTruyen() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        int databaseSizeBeforeUpdate = truyenRepository.findAll().size();
//
//        // Update the truyen
//        Truyen updatedTruyen = truyenRepository.findById(truyen.getId()).get();
//        // Disconnect from session so that the updates on updatedTruyen are not directly saved in db
//        em.detach(updatedTruyen);
//        updatedTruyen
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .soChuong(UPDATED_SO_CHUONG)
//            .nguon(UPDATED_NGUON)
//            .image(UPDATED_IMAGE)
//            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
//            .fulls(UPDATED_FULLS)
//            .hot(UPDATED_HOT)
//            .news(UPDATED_NEWS)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        TruyenDTO truyenDTO = truyenMapper.toDto(updatedTruyen);
//
//        restTruyenMockMvc.perform(put("/api/truyens").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(truyenDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Truyen in the database
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeUpdate);
//        Truyen testTruyen = truyenList.get(truyenList.size() - 1);
//        assertThat(testTruyen.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testTruyen.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testTruyen.getSoChuong()).isEqualTo(UPDATED_SO_CHUONG);
//        assertThat(testTruyen.getNguon()).isEqualTo(UPDATED_NGUON);
//        assertThat(testTruyen.getImage()).isEqualTo(UPDATED_IMAGE);
//        assertThat(testTruyen.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
//        assertThat(testTruyen.isFulls()).isEqualTo(UPDATED_FULLS);
//        assertThat(testTruyen.isHot()).isEqualTo(UPDATED_HOT);
//        assertThat(testTruyen.isNews()).isEqualTo(UPDATED_NEWS);
//        assertThat(testTruyen.isLocked()).isEqualTo(UPDATED_LOCKED);
//        assertThat(testTruyen.getCode()).isEqualTo(UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingTruyen() throws Exception {
//        int databaseSizeBeforeUpdate = truyenRepository.findAll().size();
//
//        // Create the Truyen
//        TruyenDTO truyenDTO = truyenMapper.toDto(truyen);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restTruyenMockMvc.perform(put("/api/truyens").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(truyenDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Truyen in the database
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteTruyen() throws Exception {
//        // Initialize the database
//        truyenRepository.saveAndFlush(truyen);
//
//        int databaseSizeBeforeDelete = truyenRepository.findAll().size();
//
//        // Delete the truyen
//        restTruyenMockMvc.perform(delete("/api/truyens/{id}", truyen.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Truyen> truyenList = truyenRepository.findAll();
//        assertThat(truyenList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
