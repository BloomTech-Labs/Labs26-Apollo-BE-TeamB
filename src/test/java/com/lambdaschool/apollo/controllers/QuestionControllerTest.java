package com.lambdaschool.apollo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.services.QuestionService;
import com.lambdaschool.apollo.views.QuestionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApolloApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HelperFunctions helperFunctions;

    @MockBean
    private QuestionService questionService;

    private List<Question> questionList;
    private List<Question> leaderQuestionList;

    @Before
    public void setUp() throws Exception {
        questionList = new ArrayList<>();
        leaderQuestionList = new ArrayList<>();

        Survey s1 = new Survey();
        s1.setSurveyId(9);

        Survey s2 = new Survey();
        s2.setSurveyId(11);

        Question question1 = new Question("Leader Question 1", true, QuestionType.TEXT, s1);
        questionList.add(question1);
        leaderQuestionList.add(question1);
        question1.setQuestionId(29);

        Question question2 = new Question("Leader Question 2", true, QuestionType.TEXT, s1);
        questionList.add(question2);
        leaderQuestionList.add(question2);
        question2.setQuestionId(30);

        Question question3 = new Question("Member Question 1", false, QuestionType.TEXT, s2);
        questionList.add(question3);
        question3.setQuestionId(31);

        Question question4 = new Question("Member Question 2", false, QuestionType.TEXT, s2);
        questionList.add(question4);
        question4.setQuestionId(32);

        Question question5 = new Question("Member Question 3", false, QuestionType.TEXT, s2);
        questionList.add(question5);
        question5.setQuestionId(33);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllQuestions() throws Exception {
        String apiUrl = "/questions/all";

        Mockito.when(questionService.findAllQuestions()).thenReturn(questionList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(questionList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getLeaderQuestionsBySurveyId() throws Exception {
        String apiUrl = "/questions/leader/9";

        Mockito.when(helperFunctions.isLeaderQuestion(questionService.findAllBySurveyId(9)))
            .thenReturn(leaderQuestionList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(leaderQuestionList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }
}