package vn.com.truyen.web.rest;

import vn.com.truyen.TruyenApp;
import vn.com.truyen.config.SecurityBeanOverrideConfiguration;
import vn.com.truyen.domain.Feedback;
import vn.com.truyen.repository.FeedbackRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static vn.com.truyen.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FeedbackResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TruyenApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class FeedbackResourceIT {

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_USER_SEND = "AAAAAAAAAA";
    private static final String UPDATED_NAME_USER_SEND = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "~SVfr@JKRoYw.J~[";
    private static final String UPDATED_EMAIL = "0G$@`C*^J\\.B^u";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREADED_DAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREADED_DAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .topic(DEFAULT_TOPIC)
            .nameUserSend(DEFAULT_NAME_USER_SEND)
            .email(DEFAULT_EMAIL)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS)
            .code(DEFAULT_CODE)
            .creadedDay(DEFAULT_CREADED_DAY);
        return feedback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feedback createUpdatedEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .topic(UPDATED_TOPIC)
            .nameUserSend(UPDATED_NAME_USER_SEND)
            .email(UPDATED_EMAIL)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .creadedDay(UPDATED_CREADED_DAY);
        return feedback;
    }

    @BeforeEach
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();
        // Create the Feedback
        restFeedbackMockMvc.perform(post("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testFeedback.getNameUserSend()).isEqualTo(DEFAULT_NAME_USER_SEND);
        assertThat(testFeedback.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFeedback.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFeedback.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFeedback.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFeedback.getCreadedDay()).isEqualTo(DEFAULT_CREADED_DAY);
    }

    @Test
    @Transactional
    public void createFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback with an existing ID
        feedback.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc.perform(post("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setTopic(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setEmail(null);

        // Create the Feedback, which fails.


        restFeedbackMockMvc.perform(post("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)))
            .andExpect(jsonPath("$.[*].nameUserSend").value(hasItem(DEFAULT_NAME_USER_SEND)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].creadedDay").value(hasItem(sameInstant(DEFAULT_CREADED_DAY))));
    }
    
    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC))
            .andExpect(jsonPath("$.nameUserSend").value(DEFAULT_NAME_USER_SEND))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.creadedDay").value(sameInstant(DEFAULT_CREADED_DAY)));
    }
    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findById(feedback.getId()).get();
        // Disconnect from session so that the updates on updatedFeedback are not directly saved in db
        em.detach(updatedFeedback);
        updatedFeedback
            .topic(UPDATED_TOPIC)
            .nameUserSend(UPDATED_NAME_USER_SEND)
            .email(UPDATED_EMAIL)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .creadedDay(UPDATED_CREADED_DAY);

        restFeedbackMockMvc.perform(put("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedback)))
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testFeedback.getNameUserSend()).isEqualTo(UPDATED_NAME_USER_SEND);
        assertThat(testFeedback.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFeedback.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFeedback.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFeedback.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFeedback.getCreadedDay()).isEqualTo(UPDATED_CREADED_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedbackMockMvc.perform(put("/api/feedbacks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Delete the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
