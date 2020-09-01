//package vn.com.truyen.web.rest;
//
//import vn.com.truyen.TruyenApp;
//import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
//import vn.com.truyen.domain.Chuong;
//import vn.com.truyen.domain.Truyen;
//import vn.com.truyen.repository.ChuongRepository;
//import vn.com.truyen.service.ChuongService;
//import vn.com.truyen.service.dto.ChuongDTO;
//import vn.com.truyen.service.mapper.ChuongMapper;
//import vn.com.truyen.service.dto.ChuongCriteria;
//import vn.com.truyen.service.ChuongQueryService;
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
// * Integration tests for the {@link ChuongResource} REST controller.
// */
//@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
//@AutoConfigureMockMvc
//@WithMockUser
//public class ChuongResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
//    private static final String UPDATED_CONTENT = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_LOCKED = false;
//    private static final Boolean UPDATED_LOCKED = true;
//
//    private static final String DEFAULT_CODE = "AAAAAAAAAA";
//    private static final String UPDATED_CODE = "BBBBBBBBBB";
//
//    @Autowired
//    private ChuongRepository chuongRepository;
//
//    @Autowired
//    private ChuongMapper chuongMapper;
//
//    @Autowired
//    private ChuongService chuongService;
//
//    @Autowired
//    private ChuongQueryService chuongQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restChuongMockMvc;
//
//    private Chuong chuong;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Chuong createEntity(EntityManager em) {
//        Chuong chuong = new Chuong()
//            .name(DEFAULT_NAME)
//            .content(DEFAULT_CONTENT)
//            .locked(DEFAULT_LOCKED)
//            .code(DEFAULT_CODE);
//        return chuong;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Chuong createUpdatedEntity(EntityManager em) {
//        Chuong chuong = new Chuong()
//            .name(UPDATED_NAME)
//            .content(UPDATED_CONTENT)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        return chuong;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        chuong = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createChuong() throws Exception {
//        int databaseSizeBeforeCreate = chuongRepository.findAll().size();
//        // Create the Chuong
//        ChuongDTO chuongDTO = chuongMapper.toDto(chuong);
//        restChuongMockMvc.perform(post("/api/chuongs").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(chuongDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Chuong in the database
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeCreate + 1);
//        Chuong testChuong = chuongList.get(chuongList.size() - 1);
//        assertThat(testChuong.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testChuong.getContent()).isEqualTo(DEFAULT_CONTENT);
//        assertThat(testChuong.isLocked()).isEqualTo(DEFAULT_LOCKED);
//        assertThat(testChuong.getCode()).isEqualTo(DEFAULT_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void createChuongWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = chuongRepository.findAll().size();
//
//        // Create the Chuong with an existing ID
//        chuong.setId(1L);
//        ChuongDTO chuongDTO = chuongMapper.toDto(chuong);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restChuongMockMvc.perform(post("/api/chuongs").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(chuongDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Chuong in the database
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = chuongRepository.findAll().size();
//        // set the field null
//        chuong.setName(null);
//
//        // Create the Chuong, which fails.
//        ChuongDTO chuongDTO = chuongMapper.toDto(chuong);
//
//
//        restChuongMockMvc.perform(post("/api/chuongs").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(chuongDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongs() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList
//        restChuongMockMvc.perform(get("/api/chuongs?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(chuong.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//    }
//    
//    @Test
//    @Transactional
//    public void getChuong() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get the chuong
//        restChuongMockMvc.perform(get("/api/chuongs/{id}", chuong.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(chuong.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
//            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
//    }
//
//
//    @Test
//    @Transactional
//    public void getChuongsByIdFiltering() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        Long id = chuong.getId();
//
//        defaultChuongShouldBeFound("id.equals=" + id);
//        defaultChuongShouldNotBeFound("id.notEquals=" + id);
//
//        defaultChuongShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultChuongShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultChuongShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultChuongShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllChuongsByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name equals to DEFAULT_NAME
//        defaultChuongShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the chuongList where name equals to UPDATED_NAME
//        defaultChuongShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name not equals to DEFAULT_NAME
//        defaultChuongShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);
//
//        // Get all the chuongList where name not equals to UPDATED_NAME
//        defaultChuongShouldBeFound("name.notEquals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultChuongShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the chuongList where name equals to UPDATED_NAME
//        defaultChuongShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name is not null
//        defaultChuongShouldBeFound("name.specified=true");
//
//        // Get all the chuongList where name is null
//        defaultChuongShouldNotBeFound("name.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllChuongsByNameContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name contains DEFAULT_NAME
//        defaultChuongShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the chuongList where name contains UPDATED_NAME
//        defaultChuongShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where name does not contain DEFAULT_NAME
//        defaultChuongShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the chuongList where name does not contain UPDATED_NAME
//        defaultChuongShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllChuongsByContentIsEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content equals to DEFAULT_CONTENT
//        defaultChuongShouldBeFound("content.equals=" + DEFAULT_CONTENT);
//
//        // Get all the chuongList where content equals to UPDATED_CONTENT
//        defaultChuongShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByContentIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content not equals to DEFAULT_CONTENT
//        defaultChuongShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);
//
//        // Get all the chuongList where content not equals to UPDATED_CONTENT
//        defaultChuongShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByContentIsInShouldWork() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content in DEFAULT_CONTENT or UPDATED_CONTENT
//        defaultChuongShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);
//
//        // Get all the chuongList where content equals to UPDATED_CONTENT
//        defaultChuongShouldNotBeFound("content.in=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByContentIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content is not null
//        defaultChuongShouldBeFound("content.specified=true");
//
//        // Get all the chuongList where content is null
//        defaultChuongShouldNotBeFound("content.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllChuongsByContentContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content contains DEFAULT_CONTENT
//        defaultChuongShouldBeFound("content.contains=" + DEFAULT_CONTENT);
//
//        // Get all the chuongList where content contains UPDATED_CONTENT
//        defaultChuongShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByContentNotContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where content does not contain DEFAULT_CONTENT
//        defaultChuongShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);
//
//        // Get all the chuongList where content does not contain UPDATED_CONTENT
//        defaultChuongShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllChuongsByLockedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where locked equals to DEFAULT_LOCKED
//        defaultChuongShouldBeFound("locked.equals=" + DEFAULT_LOCKED);
//
//        // Get all the chuongList where locked equals to UPDATED_LOCKED
//        defaultChuongShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByLockedIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where locked not equals to DEFAULT_LOCKED
//        defaultChuongShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);
//
//        // Get all the chuongList where locked not equals to UPDATED_LOCKED
//        defaultChuongShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByLockedIsInShouldWork() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
//        defaultChuongShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);
//
//        // Get all the chuongList where locked equals to UPDATED_LOCKED
//        defaultChuongShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByLockedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where locked is not null
//        defaultChuongShouldBeFound("locked.specified=true");
//
//        // Get all the chuongList where locked is null
//        defaultChuongShouldNotBeFound("locked.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByCodeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code equals to DEFAULT_CODE
//        defaultChuongShouldBeFound("code.equals=" + DEFAULT_CODE);
//
//        // Get all the chuongList where code equals to UPDATED_CODE
//        defaultChuongShouldNotBeFound("code.equals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByCodeIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code not equals to DEFAULT_CODE
//        defaultChuongShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);
//
//        // Get all the chuongList where code not equals to UPDATED_CODE
//        defaultChuongShouldBeFound("code.notEquals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByCodeIsInShouldWork() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code in DEFAULT_CODE or UPDATED_CODE
//        defaultChuongShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);
//
//        // Get all the chuongList where code equals to UPDATED_CODE
//        defaultChuongShouldNotBeFound("code.in=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByCodeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code is not null
//        defaultChuongShouldBeFound("code.specified=true");
//
//        // Get all the chuongList where code is null
//        defaultChuongShouldNotBeFound("code.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllChuongsByCodeContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code contains DEFAULT_CODE
//        defaultChuongShouldBeFound("code.contains=" + DEFAULT_CODE);
//
//        // Get all the chuongList where code contains UPDATED_CODE
//        defaultChuongShouldNotBeFound("code.contains=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllChuongsByCodeNotContainsSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        // Get all the chuongList where code does not contain DEFAULT_CODE
//        defaultChuongShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);
//
//        // Get all the chuongList where code does not contain UPDATED_CODE
//        defaultChuongShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllChuongsByTruyenIsEqualToSomething() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//        Truyen truyen = TruyenResourceIT.createEntity(em);
//        em.persist(truyen);
//        em.flush();
//        chuong.setTruyen(truyen);
//        chuongRepository.saveAndFlush(chuong);
//        Long truyenId = truyen.getId();
//
//        // Get all the chuongList where truyen equals to truyenId
//        defaultChuongShouldBeFound("truyenId.equals=" + truyenId);
//
//        // Get all the chuongList where truyen equals to truyenId + 1
//        defaultChuongShouldNotBeFound("truyenId.equals=" + (truyenId + 1));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultChuongShouldBeFound(String filter) throws Exception {
//        restChuongMockMvc.perform(get("/api/chuongs?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(chuong.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//
//        // Check, that the count call also returns 1
//        restChuongMockMvc.perform(get("/api/chuongs/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultChuongShouldNotBeFound(String filter) throws Exception {
//        restChuongMockMvc.perform(get("/api/chuongs?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restChuongMockMvc.perform(get("/api/chuongs/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingChuong() throws Exception {
//        // Get the chuong
//        restChuongMockMvc.perform(get("/api/chuongs/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateChuong() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        int databaseSizeBeforeUpdate = chuongRepository.findAll().size();
//
//        // Update the chuong
//        Chuong updatedChuong = chuongRepository.findById(chuong.getId()).get();
//        // Disconnect from session so that the updates on updatedChuong are not directly saved in db
//        em.detach(updatedChuong);
//        updatedChuong
//            .name(UPDATED_NAME)
//            .content(UPDATED_CONTENT)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        ChuongDTO chuongDTO = chuongMapper.toDto(updatedChuong);
//
//        restChuongMockMvc.perform(put("/api/chuongs").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(chuongDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Chuong in the database
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeUpdate);
//        Chuong testChuong = chuongList.get(chuongList.size() - 1);
//        assertThat(testChuong.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testChuong.getContent()).isEqualTo(UPDATED_CONTENT);
//        assertThat(testChuong.isLocked()).isEqualTo(UPDATED_LOCKED);
//        assertThat(testChuong.getCode()).isEqualTo(UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingChuong() throws Exception {
//        int databaseSizeBeforeUpdate = chuongRepository.findAll().size();
//
//        // Create the Chuong
//        ChuongDTO chuongDTO = chuongMapper.toDto(chuong);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restChuongMockMvc.perform(put("/api/chuongs").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(chuongDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Chuong in the database
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteChuong() throws Exception {
//        // Initialize the database
//        chuongRepository.saveAndFlush(chuong);
//
//        int databaseSizeBeforeDelete = chuongRepository.findAll().size();
//
//        // Delete the chuong
//        restChuongMockMvc.perform(delete("/api/chuongs/{id}", chuong.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Chuong> chuongList = chuongRepository.findAll();
//        assertThat(chuongList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
