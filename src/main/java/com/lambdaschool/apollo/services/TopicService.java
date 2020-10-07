package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;

import java.util.List;

public interface TopicService {

    Topic findTopicById(long id);

    List<Topic> findAllTopics();

    Topic findByJoinCode(String joincode);

    List<Topic> findTopicsByUser(String username);

    void delete(long id, User user);

    Topic save(Topic topic);

    Topic update(Topic topic, long id);

    void deleteTopicUser(long topicId, long userId);

    void addTopicUser(long topicId, long userId);

}
