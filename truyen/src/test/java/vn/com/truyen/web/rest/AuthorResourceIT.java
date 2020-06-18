//package vn.com.truyen.web.rest;
//
//import vn.com.truyen.TruyenApp;
//import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
//import vn.com.truyen.domain.Author;
//import vn.com.truyen.repository.AuthorRepository;
//import vn.com.truyen.service.AuthorService;
//import vn.com.truyen.service.dto.AuthorDTO;
//import vn.com.truyen.service.mapper.AuthorMapper;
//import vn.com.truyen.service.dto.AuthorCriteria;
//import vn.com.truyen.service.AuthorQueryService;
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
// * Integration tests for the {@link AuthorResource} REST controller.
// */
//@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
//@AutoConfigureMockMvc
//@WithMockUser
//public class AuthorResourceIT {
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
//    private AuthorRepository authorRepository;
//
//    @Autowired
//    private AuthorMapper authorMapper;
//
//    @Autowired
//    private AuthorService authorService;
//
//    @Autowired
//    private AuthorQueryService authorQueryService;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restAuthorMockMvc;
//
//    private Author author;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Author createEntity(EntityManager em) {
//        Author author = new Author()
//            .name(DEFAULT_NAME)
//            .description(DEFAULT_DESCRIPTION)
//            .locked(DEFAULT_LOCKED)
//            .code(DEFAULT_CODE);
//        return author;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Author createUpdatedEntity(EntityManager em) {
//        Author author = new Author()
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        return author;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        author = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createAuthor() throws Exception {
//        int databaseSizeBeforeCreate = authorRepository.findAll().size();
//        // Create the Author
//        AuthorDTO authorDTO = authorMapper.toDto(author);
//        restAuthorMockMvc.perform(post("/api/authors").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(authorDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Author in the database
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
//        Author testAuthor = authorList.get(authorList.size() - 1);
//        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testAuthor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testAuthor.isLocked()).isEqualTo(DEFAULT_LOCKED);
//        assertThat(testAuthor.getCode()).isEqualTo(DEFAULT_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void createAuthorWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = authorRepository.findAll().size();
//
//        // Create the Author with an existing ID
//        author.setId(1L);
//        AuthorDTO authorDTO = authorMapper.toDto(author);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restAuthorMockMvc.perform(post("/api/authors").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(authorDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Author in the database
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = authorRepository.findAll().size();
//        // set the field null
//        author.setName(null);
//
//        // Create the Author, which fails.
//        AuthorDTO authorDTO = authorMapper.toDto(author);
//
//
//        restAuthorMockMvc.perform(post("/api/authors").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(authorDTO)))
//            .andExpect(status().isBadRequest());
//
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthors() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList
//        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//    }
//    
//    @Test
//    @Transactional
//    public void getAuthor() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get the author
//        restAuthorMockMvc.perform(get("/api/authors/{id}", author.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(author.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.locked").value(DEFAULT_LOCKED.booleanValue()))
//            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
//    }
//
//
//    @Test
//    @Transactional
//    public void getAuthorsByIdFiltering() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        Long id = author.getId();
//
//        defaultAuthorShouldBeFound("id.equals=" + id);
//        defaultAuthorShouldNotBeFound("id.notEquals=" + id);
//
//        defaultAuthorShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultAuthorShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultAuthorShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultAuthorShouldNotBeFound("id.lessThan=" + id);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name equals to DEFAULT_NAME
//        defaultAuthorShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the authorList where name equals to UPDATED_NAME
//        defaultAuthorShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name not equals to DEFAULT_NAME
//        defaultAuthorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);
//
//        // Get all the authorList where name not equals to UPDATED_NAME
//        defaultAuthorShouldBeFound("name.notEquals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultAuthorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the authorList where name equals to UPDATED_NAME
//        defaultAuthorShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name is not null
//        defaultAuthorShouldBeFound("name.specified=true");
//
//        // Get all the authorList where name is null
//        defaultAuthorShouldNotBeFound("name.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllAuthorsByNameContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name contains DEFAULT_NAME
//        defaultAuthorShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the authorList where name contains UPDATED_NAME
//        defaultAuthorShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where name does not contain DEFAULT_NAME
//        defaultAuthorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the authorList where name does not contain UPDATED_NAME
//        defaultAuthorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description equals to DEFAULT_DESCRIPTION
//        defaultAuthorShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the authorList where description equals to UPDATED_DESCRIPTION
//        defaultAuthorShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description not equals to DEFAULT_DESCRIPTION
//        defaultAuthorShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);
//
//        // Get all the authorList where description not equals to UPDATED_DESCRIPTION
//        defaultAuthorShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionIsInShouldWork() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
//        defaultAuthorShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);
//
//        // Get all the authorList where description equals to UPDATED_DESCRIPTION
//        defaultAuthorShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description is not null
//        defaultAuthorShouldBeFound("description.specified=true");
//
//        // Get all the authorList where description is null
//        defaultAuthorShouldNotBeFound("description.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description contains DEFAULT_DESCRIPTION
//        defaultAuthorShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);
//
//        // Get all the authorList where description contains UPDATED_DESCRIPTION
//        defaultAuthorShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByDescriptionNotContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where description does not contain DEFAULT_DESCRIPTION
//        defaultAuthorShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);
//
//        // Get all the authorList where description does not contain UPDATED_DESCRIPTION
//        defaultAuthorShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByLockedIsEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where locked equals to DEFAULT_LOCKED
//        defaultAuthorShouldBeFound("locked.equals=" + DEFAULT_LOCKED);
//
//        // Get all the authorList where locked equals to UPDATED_LOCKED
//        defaultAuthorShouldNotBeFound("locked.equals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByLockedIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where locked not equals to DEFAULT_LOCKED
//        defaultAuthorShouldNotBeFound("locked.notEquals=" + DEFAULT_LOCKED);
//
//        // Get all the authorList where locked not equals to UPDATED_LOCKED
//        defaultAuthorShouldBeFound("locked.notEquals=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByLockedIsInShouldWork() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where locked in DEFAULT_LOCKED or UPDATED_LOCKED
//        defaultAuthorShouldBeFound("locked.in=" + DEFAULT_LOCKED + "," + UPDATED_LOCKED);
//
//        // Get all the authorList where locked equals to UPDATED_LOCKED
//        defaultAuthorShouldNotBeFound("locked.in=" + UPDATED_LOCKED);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByLockedIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where locked is not null
//        defaultAuthorShouldBeFound("locked.specified=true");
//
//        // Get all the authorList where locked is null
//        defaultAuthorShouldNotBeFound("locked.specified=false");
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByCodeIsEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code equals to DEFAULT_CODE
//        defaultAuthorShouldBeFound("code.equals=" + DEFAULT_CODE);
//
//        // Get all the authorList where code equals to UPDATED_CODE
//        defaultAuthorShouldNotBeFound("code.equals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByCodeIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code not equals to DEFAULT_CODE
//        defaultAuthorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);
//
//        // Get all the authorList where code not equals to UPDATED_CODE
//        defaultAuthorShouldBeFound("code.notEquals=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByCodeIsInShouldWork() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code in DEFAULT_CODE or UPDATED_CODE
//        defaultAuthorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);
//
//        // Get all the authorList where code equals to UPDATED_CODE
//        defaultAuthorShouldNotBeFound("code.in=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByCodeIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code is not null
//        defaultAuthorShouldBeFound("code.specified=true");
//
//        // Get all the authorList where code is null
//        defaultAuthorShouldNotBeFound("code.specified=false");
//    }
//                @Test
//    @Transactional
//    public void getAllAuthorsByCodeContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code contains DEFAULT_CODE
//        defaultAuthorShouldBeFound("code.contains=" + DEFAULT_CODE);
//
//        // Get all the authorList where code contains UPDATED_CODE
//        defaultAuthorShouldNotBeFound("code.contains=" + UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void getAllAuthorsByCodeNotContainsSomething() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        // Get all the authorList where code does not contain DEFAULT_CODE
//        defaultAuthorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);
//
//        // Get all the authorList where code does not contain UPDATED_CODE
//        defaultAuthorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultAuthorShouldBeFound(String filter) throws Exception {
//        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].locked").value(hasItem(DEFAULT_LOCKED.booleanValue())))
//            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
//
//        // Check, that the count call also returns 1
//        restAuthorMockMvc.perform(get("/api/authors/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultAuthorShouldNotBeFound(String filter) throws Exception {
//        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restAuthorMockMvc.perform(get("/api/authors/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingAuthor() throws Exception {
//        // Get the author
//        restAuthorMockMvc.perform(get("/api/authors/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateAuthor() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
//
//        // Update the author
//        Author updatedAuthor = authorRepository.findById(author.getId()).get();
//        // Disconnect from session so that the updates on updatedAuthor are not directly saved in db
//        em.detach(updatedAuthor);
//        updatedAuthor
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .locked(UPDATED_LOCKED)
//            .code(UPDATED_CODE);
//        AuthorDTO authorDTO = authorMapper.toDto(updatedAuthor);
//
//        restAuthorMockMvc.perform(put("/api/authors").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(authorDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Author in the database
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
//        Author testAuthor = authorList.get(authorList.size() - 1);
//        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testAuthor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testAuthor.isLocked()).isEqualTo(UPDATED_LOCKED);
//        assertThat(testAuthor.getCode()).isEqualTo(UPDATED_CODE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingAuthor() throws Exception {
//        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
//
//        // Create the Author
//        AuthorDTO authorDTO = authorMapper.toDto(author);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restAuthorMockMvc.perform(put("/api/authors").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(authorDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Author in the database
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteAuthor() throws Exception {
//        // Initialize the database
//        authorRepository.saveAndFlush(author);
//
//        int databaseSizeBeforeDelete = authorRepository.findAll().size();
//
//        // Delete the author
//        restAuthorMockMvc.perform(delete("/api/authors/{id}", author.getId()).with(csrf())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Author> authorList = authorRepository.findAll();
//        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
