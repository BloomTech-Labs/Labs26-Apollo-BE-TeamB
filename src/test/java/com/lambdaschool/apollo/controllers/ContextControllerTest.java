package com.lambdaschool.apollo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.services.ContextService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class ContextControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContextService contextService;

    private List<Context> contextList;

    @Before
    public void setUp() throws Exception {

        contextList = new ArrayList<>();

        Context context1 = new Context("Product Leadership", new Survey());
        contextList.add(context1);
        context1.setContextId(101);

        Context context2 = new Context("Delivery Management", new Survey());
        contextList.add(context2);
        context2.setContextId(102);

        Context context3 = new Context("Project Management", new Survey());
        contextList.add(context3);
        context3.setContextId(103);

        Context context4 = new Context("Design Leadership", new Survey());
        contextList.add(context4);
        context4.setContextId(104);

        Context context5 = new Context("Engineering Leadership", new Survey());
        contextList.add(context5);
        context5.setContextId(105);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllContexts() throws Exception {
        String apiUrl = "/contexts/contexts";

        Mockito.when(contextService.findAll())
                .thenReturn(contextList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult result = mockMvc.perform(requestBuilder).andReturn(); // this could throw an exception
        String actual = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(contextList);

        System.out.println("Expect: " + expected);
        System.out.println("Actual: " + actual);

        assertEquals("Rest API Returns List", expected, actual);
    }

    @Test
    public void getContextById() throws Exception {
        String apiUrl = "/contexts/contexts/101";

        Mockito.when(contextService.findById(101))
                .thenReturn(contextList.get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String actual = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(contextList.get(0));

        System.out.println("Expect: " + expected);
        System.out.println("Actual: " + actual);

        assertEquals("Rest API Returns List", expected, actual);
    }

    @Test
    public void createContext() throws Exception {
        String apiUrl = "/contexts/new";

        Context newContext = new Context("New Context", new Survey());

        Mockito.when(contextService.save(any(Context.class)))
                .thenReturn(newContext);

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(newContext);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(expected);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}