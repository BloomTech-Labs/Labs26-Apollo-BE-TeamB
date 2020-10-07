package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.views.TopicFrequency;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

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
    public void findById() {
        assertEquals(9, surveyService.findById(9).getSurveyId());
    }

    @Test
    public void delete() {
        // surveyService.delete(23);
        // assertEquals(10, surveyService.findAllSurveys().size());
    }

    @Test
    public void save() {
    }

    @Test
    public void saveRequest() {
    }

    @Test
    public void findAllSurveys() {
        assertEquals(11, surveyService.findAllSurveys().size());
    }
}