package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.repository.AnswerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnswerServiceImplTest {
    @Autowired
    private AnswerService answerService;

    @MockBean
    private AnswerRepository answerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findById() {
        Answer newAnswer = new Answer();
        newAnswer.setAnswerId(3);
        answerRepository.save(newAnswer);
        assertEquals(3, answerService.findById(3).getAnswerId());
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

    @Test
    public void findAllAnswers() {
    }

    @Test
    public void findBySurveyId() {
    }
}