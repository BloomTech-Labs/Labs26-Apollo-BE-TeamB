package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.SurveyService;
import com.lambdaschool.apollo.views.QuestionBody;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApolloApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class SurveyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    private List<Survey> surveyList;

    @Before
    public void setUp() throws Exception {
        surveyList = new ArrayList<>();

        Survey s1 = new Survey(new Topic());
        surveyList.add(s1);

        Survey s2 = new Survey(new Topic());
        surveyList.add(s2);

        Survey s3 = new Survey(new Topic());
        surveyList.add(s3);

        Survey s4 = new Survey(new Topic());
        surveyList.add(s4);

        Survey s5 = new Survey(new Topic());
        surveyList.add(s5);
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
        // String apiUrl = "/surveys/response";

        // Mockito.when(answerService.save(any(QuestionBody.class), any(User.class))).thenReturn(null);

        // for (Survey s: surveyService.findAllSurveys()) {
        //     System.out.println(s.getSurveyId());
        // }

        // RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .accept(MediaType.APPLICATION_JSON)
        //     .content("[{\"questionid\":60, \"body\": \"test response\"}]");
        //
        // mockMvc.perform(rb)
        //     .andExpect(status().isCreated())
        //     .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllSurveys() throws Exception {
    }

    @Test
    public void testGetAllSurveys() throws Exception {
    }

    @Test
    public void createSurveyRequest() throws Exception {
    }

    @Test
    public void getResponses() throws Exception {
    }
}