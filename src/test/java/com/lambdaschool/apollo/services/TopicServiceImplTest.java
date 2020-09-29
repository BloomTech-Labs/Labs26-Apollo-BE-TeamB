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
    public void findTopicById() {
        assertEquals("Topic 2", topicService.findTopicById(10).getTitle());
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAllTopics() {
    }

    @Test
    public void findTopicsByUser() {
    }

    @Test
    public void findByJoinCode() {
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteTopicUser() {
    }

    @Test
    public void addTopicUser() {
    }
}