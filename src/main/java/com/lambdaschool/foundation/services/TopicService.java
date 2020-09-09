package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Topic;

public interface TopicService {

    Topic findTopicById(long id);

    void delete(long id);

    Topic save(Topic topic);

    Topic update(Topic topic, long id);

    void deleteTopicUser(long topicId, long userId);

    void addTopicUser(long topicId, long userId);

}
