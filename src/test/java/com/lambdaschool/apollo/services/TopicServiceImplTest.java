package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopicServiceImplTest {

    @Autowired
    private TopicService topicService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findTopicById() {
        assertEquals("Topic 2", topicService.findTopicById(10).getTitle());
    }

    @Test
    public void e_delete() {
    }

    @Test
    public void b_findAllTopics() {
        assertEquals(5, topicService.findAllTopics().size());
    }

    @Test
    public void c_findTopicsByUser() {
    }

    @Test
    public void d_findByJoinCode() {
    }

    @Test
    public void f_save() {
    }

    @Test
    public void g_update() {
    }

    @Test
    public void h_deleteTopicUser() {
    }

    @Test
    public void i_addTopicUser() {
    }
}