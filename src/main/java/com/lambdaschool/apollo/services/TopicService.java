package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;

import java.util.List;

public interface TopicService {

    Topic findTopicById(long id);

    List<Topic> findAllTopics();

    List<Topic> findTopicsByUser(User user);

    void delete(long id);

    Topic save(Topic topic);

    Topic update(Topic topic, long id);

    void deleteTopicUser(long topicId, long userId);

    void addTopicUser(long topicId, long userId);

}
