//package vn.com.truyen.web.rest;
//
//import vn.com.truyen.TruyenApp;
//import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
//import vn.com.truyen.domain.Category;
//import vn.com.truyen.domain.Truyen;
//import vn.com.truyen.repository.CategoryRepository;
//import vn.com.truyen.service.CategoryService;
//import vn.com.truyen.service.dto.CategoryDTO;
//import vn.com.truyen.service.mapper.CategoryMapper;
//import vn.com.truyen.service.dto.CategoryCriteria;
//import vn.com.truyen.service.CategoryQueryService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import javax.persistence.EntityManager;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link CategoryResource} REST controller.
// */
//@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
//@WithMockUser
//public class CategoryResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_LOCKED = false;
//    private static final Boolean UPDATED_LOCKED = true;
//
//    private static final String DEFAULT_CODE = "AAAAAAAAAA";
//    private static final String UPDATED_CODE = "BBBBBBBBBB";
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Mock
//    private CategoryRepository categoryRepositoryMock;
//
//    @Autowired
//    private CategoryMapper categoryMapper;
//
//    @Mock
//    private CategoryService categoryServiceMock;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private CategoryQueryService categoryQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restCategoryMockMvc;
//
//    private Category category;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Category createEntity(EntityManager em) {
//        Category category = new Category()
//            .name(DEFAULT_NAME)
//            .description(DEFAULT_DESCRIPTION)
//            .locked(DEFAULT_LOCKED)
//            .code(DEFAULT_CODE);
//        // Add required entity
//        Truyen truyen;
//        if (TestUtil.findAll(em, Truyen.class).isEmpty()) {
//            truyen = TruyenResourceIT.createEntity(em);
//            em.persist(truyen);
//            em.flush();
//        } else {
//            truyen = TestUtil.findAll(em, Truyen.class).get(0);
//        }
//        category.getTruyens().add(truyen);
//        return category;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Category createUpdatedEntity(EntityManager em) {
//        Category category = new Category()
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        // Add required entity
//        Truyen truyen;
//        if (TestUtil.findAll(em, Truyen.class).isEmpty()) {
//            truyen = TruyenResourceIT.createUpdatedEntity(em);
//            em.persist(truyen);
//            em.flush();
//        } else {
//            truyen = TestUtil.findAll(em, Truyen.class).get(0);
//        }
//        category.getTruyens().add(truyen);
//        return category;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        category = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createCategory() throws Exception {
//        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
//        // Create the Category
//        CategoryDTO categoryDTO = categoryMapper.toDto(category);
//        restCategoryMockMvc.perform(post("/api/categories").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Category in the database
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
//        Category testCategory = categoryList.get(categoryList.size() - 1);
//        assertThat(testCategory.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testCategory.isLocked()).isEqualTo(DEFAULT_LOCKED);
//        assertThat(testCategory.getCode()).isEqualTo(DEFAULT_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void createCategoryWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
//
//        // Create the Category with an existing ID
//        category.setId(1L);
//        CategoryDTO categoryDTO = categoryMapper.toDto(category);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCategoryMockMvc.perform(post("/api/categories").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Category in the database
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = categoryRepository.findAll().size();
//        // set the field null
//        category.setName(null);
//
//        // Create the Category, which fails.
//        CategoryDTO categoryDTO = categoryMapper.toDto(category);
//
//
//        restCategoryMockMvc.perform(post("/api/categories").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategories() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList
//        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//    }
//    
//    @SuppressWarnings({"unchecked"})
//    public void getAllCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
//        when(categoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//
//        restCategoryMockMvc.perform(get("/api/categories?eagerload=true"))
//            .andExpect(status().isOk());
//
//        verify(categoryServiceMock, times(1)).findAllWithEagerRelationships(any());
//    }
//
//    @SuppressWarnings({"unchecked"})
//    public void getAllCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
//        when(categoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//
//        restCategoryMockMvc.perform(get("/api/categories?eagerload=true"))
//            .andExpect(status().isOk());
//
//        verify(categoryServiceMock, times(1)).findAllWithEagerRelationships(any());
//    }
//
//    @Test
//    @Transactional
//    public void getCategory() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get the category
//        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
//    }
//
//
//    @Test
//    @Transactional
//    public void getCategoriesByIdFiltering() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        Long id = category.getId();
//
//        defaultCategoryShouldBeFound("id.equals=" + id);
//        defaultCategoryShouldNotBeFound("id.notEquals=" + id);
//
//        defaultCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultCategoryShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultCategoryShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultCategoryShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name equals to DEFAULT_NAME
//        defaultCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the categoryList where name equals to UPDATED_NAME
//        defaultCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name not equals to DEFAULT_NAME
//        defaultCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);
//
//        // Get all the categoryList where name not equals to UPDATED_NAME
//        defaultCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the categoryList where name equals to UPDATED_NAME
//        defaultCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name is not null
//        defaultCategoryShouldBeFound("name.specified=true");
//
//        // Get all the categoryList where name is null
//        defaultCategoryShouldNotBeFound("name.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllCategoriesByNameContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name contains DEFAULT_NAME
//        defaultCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the categoryList where name contains UPDATED_NAME
//        defaultCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where name does not contain DEFAULT_NAME
//        defaultCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the categoryList where name does not contain UPDATED_NAME
//        defaultCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description equals to DEFAULT_DESCRIPTION
//        defaultCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the categoryList where description equals to UPDATED_DESCRIPTION
//        defaultCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description not equals to DEFAULT_DESCRIPTION
//        defaultCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the categoryList where description not equals to UPDATED_DESCRIPTION
//        defaultCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionIsInShouldWork() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
//        defaultCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);
//
//        // Get all the categoryList where description equals to UPDATED_DESCRIPTION
//        defaultCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description is not null
//        defaultCategoryShouldBeFound("description.specified=true");
//
//        // Get all the categoryList where description is null
//        defaultCategoryShouldNotBeFound("description.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description contains DEFAULT_DESCRIPTION
//        defaultCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);
//
//        // Get all the categoryList where description contains UPDATED_DESCRIPTION
//        defaultCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByDescriptionNotContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where description does not contain DEFAULT_DESCRIPTION
//        defaultCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);
//
//        // Get all the categoryList where description does not contain UPDATED_DESCRIPTION
//        defaultCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByLockedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where locked equals to DEFAULT_LOCKED
//        defaultCategoryShouldBeFound("locked.equals=" + DEFAULT_LOCKED);
//
//        // Get all the categoryList where locked equals to UPDATED_LOCKED
//        defaultCategoryShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByLockedIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where locked not equals to DEFAULT_LOCKED
//        defaultCategoryShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);
//
//        // Get all the categoryList where locked not equals to UPDATED_LOCKED
//        defaultCategoryShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByLockedIsInShouldWork() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
//        defaultCategoryShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);
//
//        // Get all the categoryList where locked equals to UPDATED_LOCKED
//        defaultCategoryShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByLockedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where locked is not null
//        defaultCategoryShouldBeFound("locked.specified=true");
//
//        // Get all the categoryList where locked is null
//        defaultCategoryShouldNotBeFound("locked.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByCodeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code equals to DEFAULT_CODE
//        defaultCategoryShouldBeFound("code.equals=" + DEFAULT_CODE);
//
//        // Get all the categoryList where code equals to UPDATED_CODE
//        defaultCategoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByCodeIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code not equals to DEFAULT_CODE
//        defaultCategoryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);
//
//        // Get all the categoryList where code not equals to UPDATED_CODE
//        defaultCategoryShouldBeFound("code.notEquals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByCodeIsInShouldWork() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code in DEFAULT_CODE or UPDATED_CODE
//        defaultCategoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);
//
//        // Get all the categoryList where code equals to UPDATED_CODE
//        defaultCategoryShouldNotBeFound("code.in=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByCodeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code is not null
//        defaultCategoryShouldBeFound("code.specified=true");
//
//        // Get all the categoryList where code is null
//        defaultCategoryShouldNotBeFound("code.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllCategoriesByCodeContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code contains DEFAULT_CODE
//        defaultCategoryShouldBeFound("code.contains=" + DEFAULT_CODE);
//
//        // Get all the categoryList where code contains UPDATED_CODE
//        defaultCategoryShouldNotBeFound("code.contains=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByCodeNotContainsSomething() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        // Get all the categoryList where code does not contain DEFAULT_CODE
//        defaultCategoryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);
//
//        // Get all the categoryList where code does not contain UPDATED_CODE
//        defaultCategoryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllCategoriesByTruyenIsEqualToSomething() throws Exception {
//        // Get already existing entity
//        Truyen truyen = category.getTruyen();
//        categoryRepository.saveAndFlush(category);
//        Long truyenId = truyen.getId();
//
//        // Get all the categoryList where truyen equals to truyenId
//        defaultCategoryShouldBeFound("truyenId.equals=" + truyenId);
//
//        // Get all the categoryList where truyen equals to truyenId + 1
//        defaultCategoryShouldNotBeFound("truyenId.equals=" + (truyenId + 1));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultCategoryShouldBeFound(String filter) throws Exception {
//        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//
//        // Check, that the count call also returns 1
//        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultCategoryShouldNotBeFound(String filter) throws Exception {
//        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingCategory() throws Exception {
//        // Get the category
//        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateCategory() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
//
//        // Update the category
//        Category updatedCategory = categoryRepository.findById(category.getId()).get();
//        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
//        em.detach(updatedCategory);
//        updatedCategory
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        CategoryDTO categoryDTO = categoryMapper.toDto(updatedCategory);
//
//        restCategoryMockMvc.perform(put("/api/categories").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Category in the database
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
//        Category testCategory = categoryList.get(categoryList.size() - 1);
//        assertThat(testCategory.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testCategory.isLocked()).isEqualTo(UPDATED_LOCKED);
//        assertThat(testCategory.getCode()).isEqualTo(UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingCategory() throws Exception {
//        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();
//
//        // Create the Category
//        CategoryDTO categoryDTO = categoryMapper.toDto(category);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCategoryMockMvc.perform(put("/api/categories").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(categoryDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Category in the database
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteCategory() throws Exception {
//        // Initialize the database
//        categoryRepository.saveAndFlush(category);
//
//        int databaseSizeBeforeDelete = categoryRepository.findAll().size();
//
//        // Delete the category
//        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Category> categoryList = categoryRepository.findAll();
//        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
