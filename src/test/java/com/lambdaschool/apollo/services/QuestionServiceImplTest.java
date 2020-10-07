package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
public class QuestionServiceImplTest {
    @Autowired
    private QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findById() {
        assertEquals("Leader Question 1", questionService.findById(29).getBody());
    }

    @Test
    public void findAllQuestions() {
        assertEquals(5, questionService.findAllQuestions().size());
    }

    @Test
    public void findAllBySurveyId() {
        assertEquals(3, questionService.findAllBySurveyId(9).size());
    }

    @Test
    public void delete() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }
}