package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.repository.SurveyRepository;
import com.lambdaschool.apollo.views.QuestionType;
import com.lambdaschool.apollo.views.SurveyQuestion;
import com.lambdaschool.apollo.views.TopicFrequency;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyServiceImplTest {
    @Autowired
    private SurveyService surveyService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findById() {
        assertEquals(9, surveyService.findById(9).getSurveyId());
    }

    @Test
    public void b_delete() {
        // surveyService.delete(23);
        // assertEquals(10, surveyService.findAllSurveys().size());
    }

    @Test
    public void c_save() {
        surveyService.save(new Survey());
        assertEquals(12, surveyService.findAllSurveys().size());
    }

    @Test
    public void d_saveRequest() {
        // Topic saveTopic = surveyService.findById(9).getTopic();
        // Topic saveTopic = new Topic("test topic", userService.findUserById(4), surveyService.findById(9), TopicFrequency.DAILY);
        // List<SurveyQuestion> newQuestions = new ArrayList<>();
        // newQuestions.add(new SurveyQuestion("tesy survey question", QuestionType.TEXT, false));
        // // System.out.println(saveTopic);
        // // System.out.println(newQuestions);
        // surveyService.saveRequest(newQuestions, saveTopic);
        // assertEquals(1, surveyService.findById(9).getTopic().getSurveysrequests().size());
    }

    @Test
    public void e_findAllSurveys() {
        assertEquals(12, surveyService.findAllSurveys().size());
    }
}