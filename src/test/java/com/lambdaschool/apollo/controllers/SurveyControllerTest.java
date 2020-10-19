package com.lambdaschool.apollo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.SurveyService;
import com.lambdaschool.apollo.services.UserService;
import com.lambdaschool.apollo.views.QuestionBody;
import com.lambdaschool.apollo.views.QuestionType;
import com.lambdaschool.apollo.views.SurveyQuestion;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApolloApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class SurveyControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    private List<Survey> surveyList;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        surveyList = new ArrayList<>();
        testUser = new User();
        testUser.setUsername("admin");
        testUser.setUserid(4);

        Topic t1 = new Topic(); // id - 10
        t1.setOwner(testUser);
        t1.setTopicId(10);

        Survey s1 = new Survey(new Topic()); // id - 9
        s1.setSurveyid(9);
        s1.setTopic(t1);
        surveyList.add(s1);

        Question question1 = new Question("Leader Question 1", true, QuestionType.TEXT, s1);
        question1.setQuestionid(29);
        Question question2 = new Question("Leader Question 2", true, QuestionType.TEXT, s1);
        question2.setQuestionid(30);
        List<Answer> answers1 = new ArrayList<>();
        Answer testAnswer = new Answer("test answer 1", question1, testUser, s1);
        testAnswer.setAnswerId(34);
        answers1.add(testAnswer);
        question1.setAnswers(answers1);
        List<Question> qList = new ArrayList<>();
        qList.add(question1);
        qList.add(question2);
        s1.setQuestions(qList);

        Survey s2 = new Survey(new Topic()); // id - 11
        s2.setSurveyid(11);
        surveyList.add(s2);

        Survey s3 = new Survey(new Topic()); // id - 13
        s3.setSurveyid(13);
        surveyList.add(s3);

        Survey s4 = new Survey(new Topic()); // id - 15
        s4.setSurveyid(15);
        surveyList.add(s4);

        Survey s5 = new Survey(new Topic()); // id - 17
        s5.setSurveyid(17);
        surveyList.add(s5);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createNewSurvey() throws Exception {
        String apiUrl = "/surveys/new";

        Mockito.when(surveyService.save(any(Survey.class))).thenReturn(surveyList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"surveyid\": 36, \"topic\": null, \"questions\": [], \"responded\": false, \"surveyId\": 36}");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewResponse() throws Exception {
        String apiUrl = "/surveys/response";

        Mockito.when(surveyService.findById(9L)).thenReturn(surveyList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("[{\"questionid\":30, \"body\": \"test response\"}]");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllSurveys() throws Exception {
        String apiUrl = "/surveys/all";

        Mockito.when(surveyService.findAllSurveys()).thenReturn(surveyList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(surveyList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getSurveyById() throws Exception {
        String apiUrl = "/surveys/survey/17";

        Mockito.when(surveyService.findById(17)).thenReturn(surveyList.get(4));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(surveyList.get(4));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void createSurveyRequest() throws Exception {
        String apiUrl = "/surveys/topic/10";

        Mockito.when(surveyService.saveRequest(Mockito.<SurveyQuestion>anyList(), any(Topic.class))).thenReturn(surveyList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("[{\"body\": \"test survey question\", \"type\": \"TEXT\", \"leader\": false, \"answer\": \"\"}]");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getResponses() throws Exception {
        String apiUrl = "/surveys/survey/9/responses";

        Survey survey = surveyList.get(0);
        List<Answer> responses = survey.getQuestions().get(0).getAnswers();

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(responses);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }
}