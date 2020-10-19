package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyServiceImplTest {
    @Autowired
    private SurveyService surveyService;

    @MockBean
    private SurveyRepository surveyRepository;

    private List<Survey> surveyList;

    @Before
    public void setUp() throws Exception {
        surveyList = new ArrayList<>();
        Survey s1 = new Survey();
        Topic t1 = new Topic("test topic", new User(), s1, TopicFrequency.DAILY);
        t1.setTopicId(1);
        s1.setTopic(t1);
        s1.setSurveyid(9);
        surveyList.add(s1);

        List<Question> questions = new ArrayList<>();
        Question q1 = new Question("test question", true, QuestionType.TEXT, s1);
        q1.setQuestionid(3);
        questions.add(q1);
        s1.setQuestions(questions);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findById() {
        Mockito.when(surveyRepository.findById(9L)).thenReturn(Optional.of(surveyList.get(0)));
        assertEquals("test topic", surveyService.findById(9L).getTopic().getTitle());
    }

    @Test
    public void b_delete() {
    }

    @Test
    public void c_save() {
        Survey newSurvey = new Survey();
        newSurvey.setSurveyid(10);
        Mockito.when(surveyRepository.save(any(Survey.class))).thenReturn(newSurvey);
        assertEquals(10, surveyService.save(newSurvey).getSurveyid());
    }

    @Test
    public void d_saveRequest() {
        // create survey questions list
        List<SurveyQuestion> surveyQuestions = new ArrayList<>();
        SurveyQuestion sq1 = new SurveyQuestion("test survey question", QuestionType.TEXT, true);
        surveyQuestions.add(sq1);
        // create survey for test and add questions list
        Survey newSurvey = new Survey();
        List<Question> questions = new ArrayList<>();
        Question q1 = new Question("test question", true, QuestionType.TEXT, newSurvey);
        questions.add(q1);
        newSurvey.setQuestions(questions);
        // get topic for test
        Topic t1 = surveyList.get(0).getTopic();

        Mockito.when(surveyRepository.save(any(Survey.class))).thenReturn(newSurvey);
        assertTrue(surveyService.saveRequest(surveyQuestions, t1).getQuestions().get(0).isLeader());

    }

    @Test
    public void e_findAllSurveys() {
        Mockito.when(surveyRepository.findAll()).thenReturn(surveyList);
        assertEquals(1, surveyService.findAllSurveys().size());
    }

    @Test
    public void f_removeQuestion() {
        Survey survey = surveyList.get(0);
        surveyService.removeQuestion(survey, 3);
        Mockito.when(surveyRepository.findById(9L)).thenReturn(Optional.of(surveyList.get(0)));
        assertEquals(0, surveyService.findById(9).getQuestions().size());
    }
}