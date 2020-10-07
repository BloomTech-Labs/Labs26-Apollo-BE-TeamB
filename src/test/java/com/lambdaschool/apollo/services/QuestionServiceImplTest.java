package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.views.QuestionType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void a_findById() {
        assertEquals("Leader Question 1", questionService.findById(29).getBody());
    }

    @Test
    public void b_findAllQuestions() {
        assertEquals(5, questionService.findAllQuestions().size());
    }

    @Test
    public void c_findAllBySurveyId() {
        assertEquals(3, questionService.findAllBySurveyId(9).size());
    }

    @Test
    public void d_delete() {
        questionService.delete(29);
        assertEquals(4, questionService.findAllQuestions().size());
    }

    @Test
    public void e_save() {
        Survey s1 = new Survey();
        s1.setSurveyId(9);
        Question newQuestion = new Question("Test Question", false, QuestionType.TEXT, s1);
        questionService.save(newQuestion);
        assertEquals(6, questionService.findAllQuestions().size());
    }

    @Test
    public void f_update() {
        // asserted to be null until update function is actually added
        assertNull(questionService.update(new Question()));
    }
}