package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceFoundException;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Survey;
import com.lambdaschool.foundation.models.Topic;
import com.lambdaschool.foundation.models.TopicUsers;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "topicService")
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public Topic findTopicById(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic " + id + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long id) {

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic " + id + " Not Found"));
        topicRepository.delete(topic);

    }

    @Transactional
    @Override
    public Topic save(Topic topic) {

        Topic newTopic = new Topic();

        if (topic.getTopicId() != 0) {
            Topic oldTopic = topicRepository.findById(topic.getTopicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Topic " + topic.getTopicId() + " Not Found"));

            // delete the users for the old topic we are replacing
            for (TopicUsers tu : oldTopic.getUsers()) {
                deleteTopicUser(tu.getTopic().getTopicId(), tu.getUser().getUserid());
            }
            newTopic.setTopicId(oldTopic.getTopicId());
        }

        newTopic.setTitle(topic.getTitle());

        User owner = userService.findByName(topic.getOwner().getUsername());
        if (owner != null) {
            newTopic.setOwner(owner);
        } else {
            throw new ResourceNotFoundException("User " + topic.getOwner().getUsername() + " Not Found");
        }

        Survey survey = surveyService.findById(topic.getSurvey().getSurveyId());
        if (survey != null) {
            newTopic.setSurvey(survey);
        } else {
            throw new ResourceNotFoundException("Survey Id " + topic.getSurvey().getSurveyId() + " Not Found");
        }

        newTopic.getUsers().clear();
        if (topic.getTopicId() == 0) {
            for (TopicUsers tu : topic.getUsers()) {
                User newUser = userService.findByName(tu.getUser().getUsername());
                newTopic.addUser(newUser);
            }
        } else {
            for (TopicUsers tu : topic.getUsers()) {
                addTopicUser(newTopic.getTopicId(), tu.getUser().getUserid());
            }
        }

        return topicRepository.save(newTopic);

    }

    @Override
    public Topic update(Topic topic, long id) {
        return null;
    }

    @Transactional
    @Override
    public void deleteTopicUser(long topicId, long userId) {

        topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic " + topicId + " Not Found"));
        userService.findUserById(userId);

        if (topicRepository.checkTopicUsersCombo(topicId, userId).getCount() > 0) {
            topicRepository.deleteTopicUsers(topicId, userId);
        } else {
            throw new ResourceNotFoundException("Topic and User Combination Does Not Exist");
        }

    }

    @Transactional
    @Override
    public void addTopicUser(long topicId, long userId) {

        topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic " + topicId + " Not Found"));
        userService.findUserById(userId);

        if (topicRepository.checkTopicUsersCombo(topicId, userId).getCount() <= 0) {
            topicRepository.insertTopicUsers(userAuditing.getCurrentAuditor().get(), topicId, userId);
        } else {
            throw new ResourceFoundException("Topic and User Combination Already Exists");
        }
    }
}
