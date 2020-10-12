package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.repository.AnswerRepository;
import com.lambdaschool.apollo.views.QuestionBody;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnswerServiceImplTest {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findById() {
        assertEquals(34, answerService.findById(34).getAnswerId());
    }

    @Test
    public void delete() {
        // answerService.delete(34);
        // assertEquals(0, answerService.findAllAnswers().size());
    }

    @Test
    public void save() {
        QuestionBody qb = new QuestionBody("test answer 2", 29);
        User u = userService.findUserById(4);
        answerService.save(qb, u);
        assertEquals(2, answerService.findAllAnswers().size());
    }

    @Test
    public void update() {
        assertNull(answerService.update(answerService.findById(34)));
    }

    @Test
    public void findAllAnswers() {
        assertEquals(1, answerService.findAllAnswers().size());
    }

    @Test
    public void findBySurveyId() {
        assertEquals(1, answerService.findBySurveyId(9).size());
    }
}