//package vn.com.truyen.web.rest;
//
//import vn.com.truyen.TruyenApp;
//import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
//import vn.com.truyen.domain.View;
//import vn.com.truyen.domain.Truyen;
//import vn.com.truyen.repository.ViewRepository;
//import vn.com.truyen.service.ViewService;
//import vn.com.truyen.service.dto.ViewDTO;
//import vn.com.truyen.service.mapper.ViewMapper;
//import vn.com.truyen.service.dto.ViewCriteria;
//import vn.com.truyen.service.ViewQueryService;
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
//import java.time.Instant;
//import java.time.ZonedDateTime;
//import java.time.ZoneOffset;
//import java.time.ZoneId;
//import java.util.List;
//
//import static vn.com.truyen.web.rest.TestUtil.sameInstant;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link ViewResource} REST controller.
// */
//@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
//@AutoConfigureMockMvc
//@WithMockUser
//public class ViewResourceIT {
//
//    private static final ZonedDateTime DEFAULT_DAY_VIEW = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_DAY_VIEW = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//    private static final ZonedDateTime SMALLER_DAY_VIEW = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);
//
//    private static final Integer DEFAULT_VIEWS = 1;
//    private static final Integer UPDATED_VIEWS = 2;
//    private static final Integer SMALLER_VIEWS = 1 - 1;
//
//    @Autowired
//    private ViewRepository viewRepository;
//
//    @Autowired
//    private ViewMapper viewMapper;
//
//    @Autowired
//    private ViewService viewService;
//
//    @Autowired
//    private ViewQueryService viewQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restViewMockMvc;
//
//    private View view;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static View createEntity(EntityManager em) {
//        View view = new View()
//            .dayView(DEFAULT_DAY_VIEW)
//            .views(DEFAULT_VIEWS);
//        return view;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static View createUpdatedEntity(EntityManager em) {
//        View view = new View()
//            .dayView(UPDATED_DAY_VIEW)
//            .views(UPDATED_VIEWS);
//        return view;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        view = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createView() throws Exception {
//        int databaseSizeBeforeCreate = viewRepository.findAll().size();
//        // Create the View
//        ViewDTO viewDTO = viewMapper.toDto(view);
//        restViewMockMvc.perform(post("/api/views").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(viewDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the View in the database
//        List<View> viewList = viewRepository.findAll();
//        assertThat(viewList).hasSize(databaseSizeBeforeCreate + 1);
//        View testView = viewList.get(viewList.size() - 1);
//        assertThat(testView.getDayView()).isEqualTo(DEFAULT_DAY_VIEW);
//        assertThat(testView.getViews()).isEqualTo(DEFAULT_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void createViewWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = viewRepository.findAll().size();
//
//        // Create the View with an existing ID
//        view.setId(1L);
//        ViewDTO viewDTO = viewMapper.toDto(view);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restViewMockMvc.perform(post("/api/views").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(viewDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the View in the database
//        List<View> viewList = viewRepository.findAll();
//        assertThat(viewList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllViews() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList
//        restViewMockMvc.perform(get("/api/views?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(view.getId().intValue())))
//            .andExpect(jsonPath("$.[*].dayView").value(hasItem(sameInstant(DEFAULT_DAY_VIEW))))
//            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)));
//    }
//    
//    @Test
//    @Transactional
//    public void getView() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get the view
//        restViewMockMvc.perform(get("/api/views/{id}", view.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(view.getId().intValue()))
//            .andExpect(jsonPath("$.dayView").value(sameInstant(DEFAULT_DAY_VIEW)))
//            .andExpect(jsonPath("$.views").value(DEFAULT_VIEWS));
//    }
//
//
//    @Test
//    @Transactional
//    public void getViewsByIdFiltering() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        Long id = view.getId();
//
//        defaultViewShouldBeFound("id.equals=" + id);
//        defaultViewShouldNotBeFound("id.notEquals=" + id);
//
//        defaultViewShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultViewShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultViewShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultViewShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView equals to DEFAULT_DAY_VIEW
//        defaultViewShouldBeFound("dayView.equals=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView equals to UPDATED_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.equals=" + UPDATED_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView not equals to DEFAULT_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.notEquals=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView not equals to UPDATED_DAY_VIEW
//        defaultViewShouldBeFound("dayView.notEquals=" + UPDATED_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsInShouldWork() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView in DEFAULT_DAY_VIEW or UPDATED_DAY_VIEW
//        defaultViewShouldBeFound("dayView.in=" + DEFAULT_DAY_VIEW + "," + UPDATED_DAY_VIEW);
//
//        // Get all the viewList where dayView equals to UPDATED_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.in=" + UPDATED_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView is not null
//        defaultViewShouldBeFound("dayView.specified=true");
//
//        // Get all the viewList where dayView is null
//        defaultViewShouldNotBeFound("dayView.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView is greater than or equal to DEFAULT_DAY_VIEW
//        defaultViewShouldBeFound("dayView.greaterThanOrEqual=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView is greater than or equal to UPDATED_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.greaterThanOrEqual=" + UPDATED_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView is less than or equal to DEFAULT_DAY_VIEW
//        defaultViewShouldBeFound("dayView.lessThanOrEqual=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView is less than or equal to SMALLER_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.lessThanOrEqual=" + SMALLER_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsLessThanSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView is less than DEFAULT_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.lessThan=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView is less than UPDATED_DAY_VIEW
//        defaultViewShouldBeFound("dayView.lessThan=" + UPDATED_DAY_VIEW);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByDayViewIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where dayView is greater than DEFAULT_DAY_VIEW
//        defaultViewShouldNotBeFound("dayView.greaterThan=" + DEFAULT_DAY_VIEW);
//
//        // Get all the viewList where dayView is greater than SMALLER_DAY_VIEW
//        defaultViewShouldBeFound("dayView.greaterThan=" + SMALLER_DAY_VIEW);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views equals to DEFAULT_VIEWS
//        defaultViewShouldBeFound("views.equals=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views equals to UPDATED_VIEWS
//        defaultViewShouldNotBeFound("views.equals=" + UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views not equals to DEFAULT_VIEWS
//        defaultViewShouldNotBeFound("views.notEquals=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views not equals to UPDATED_VIEWS
//        defaultViewShouldBeFound("views.notEquals=" + UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsInShouldWork() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views in DEFAULT_VIEWS or UPDATED_VIEWS
//        defaultViewShouldBeFound("views.in=" + DEFAULT_VIEWS + "," + UPDATED_VIEWS);
//
//        // Get all the viewList where views equals to UPDATED_VIEWS
//        defaultViewShouldNotBeFound("views.in=" + UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views is not null
//        defaultViewShouldBeFound("views.specified=true");
//
//        // Get all the viewList where views is null
//        defaultViewShouldNotBeFound("views.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views is greater than or equal to DEFAULT_VIEWS
//        defaultViewShouldBeFound("views.greaterThanOrEqual=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views is greater than or equal to UPDATED_VIEWS
//        defaultViewShouldNotBeFound("views.greaterThanOrEqual=" + UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views is less than or equal to DEFAULT_VIEWS
//        defaultViewShouldBeFound("views.lessThanOrEqual=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views is less than or equal to SMALLER_VIEWS
//        defaultViewShouldNotBeFound("views.lessThanOrEqual=" + SMALLER_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsLessThanSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views is less than DEFAULT_VIEWS
//        defaultViewShouldNotBeFound("views.lessThan=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views is less than UPDATED_VIEWS
//        defaultViewShouldBeFound("views.lessThan=" + UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void getAllViewsByViewsIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        // Get all the viewList where views is greater than DEFAULT_VIEWS
//        defaultViewShouldNotBeFound("views.greaterThan=" + DEFAULT_VIEWS);
//
//        // Get all the viewList where views is greater than SMALLER_VIEWS
//        defaultViewShouldBeFound("views.greaterThan=" + SMALLER_VIEWS);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllViewsByTruyenIsEqualToSomething() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//        Truyen truyen = TruyenResourceIT.createEntity(em);
//        em.persist(truyen);
//        em.flush();
//        view.setTruyen(truyen);
//        viewRepository.saveAndFlush(view);
//        Long truyenId = truyen.getId();
//
//        // Get all the viewList where truyen equals to truyenId
//        defaultViewShouldBeFound("truyenId.equals=" + truyenId);
//
//        // Get all the viewList where truyen equals to truyenId + 1
//        defaultViewShouldNotBeFound("truyenId.equals=" + (truyenId + 1));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultViewShouldBeFound(String filter) throws Exception {
//        restViewMockMvc.perform(get("/api/views?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(view.getId().intValue())))
//            .andExpect(jsonPath("$.[*].dayView").value(hasItem(sameInstant(DEFAULT_DAY_VIEW))))
//            .andExpect(jsonPath("$.[*].views").value(hasItem(DEFAULT_VIEWS)));
//
//        // Check, that the count call also returns 1
//        restViewMockMvc.perform(get("/api/views/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultViewShouldNotBeFound(String filter) throws Exception {
//        restViewMockMvc.perform(get("/api/views?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restViewMockMvc.perform(get("/api/views/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingView() throws Exception {
//        // Get the view
//        restViewMockMvc.perform(get("/api/views/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateView() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        int databaseSizeBeforeUpdate = viewRepository.findAll().size();
//
//        // Update the view
//        View updatedView = viewRepository.findById(view.getId()).get();
//        // Disconnect from session so that the updates on updatedView are not directly saved in db
//        em.detach(updatedView);
//        updatedView
//            .dayView(UPDATED_DAY_VIEW)
//            .views(UPDATED_VIEWS);
//        ViewDTO viewDTO = viewMapper.toDto(updatedView);
//
//        restViewMockMvc.perform(put("/api/views").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(viewDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the View in the database
//        List<View> viewList = viewRepository.findAll();
//        assertThat(viewList).hasSize(databaseSizeBeforeUpdate);
//        View testView = viewList.get(viewList.size() - 1);
//        assertThat(testView.getDayView()).isEqualTo(UPDATED_DAY_VIEW);
//        assertThat(testView.getViews()).isEqualTo(UPDATED_VIEWS);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingView() throws Exception {
//        int databaseSizeBeforeUpdate = viewRepository.findAll().size();
//
//        // Create the View
//        ViewDTO viewDTO = viewMapper.toDto(view);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restViewMockMvc.perform(put("/api/views").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(viewDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the View in the database
//        List<View> viewList = viewRepository.findAll();
//        assertThat(viewList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteView() throws Exception {
//        // Initialize the database
//        viewRepository.saveAndFlush(view);
//
//        int databaseSizeBeforeDelete = viewRepository.findAll().size();
//
//        // Delete the view
//        restViewMockMvc.perform(delete("/api/views/{id}", view.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<View> viewList = viewRepository.findAll();
//        assertThat(viewList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
