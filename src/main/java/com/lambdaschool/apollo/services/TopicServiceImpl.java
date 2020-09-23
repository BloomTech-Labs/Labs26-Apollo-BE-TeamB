package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceFoundException;
import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private HelperFunctions helperFunctions;

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

    @Override
    public List<Topic> findAllTopics() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().iterator().forEachRemaining(topics::add);
        return topics;
    }

    @Override
    public List<Topic> findTopicsByUser(User user) {
        List<Topic> topics = new ArrayList<>();
               topics = topicRepository.findByUsers_userContaining(user);

        return topics;
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
            // use existing joincode when updating existing topic
            newTopic.setJoincode(oldTopic.getJoincode());
            newTopic.setTopicId(oldTopic.getTopicId());
        } else {
            // generate new joincode when creating new topic
            newTopic.setJoincode(helperFunctions.getNewJoinCode());
        }
        newTopic.setTitle(topic.getTitle());

        User owner = userService.findByName(topic.getOwner().getUsername());
        if (owner != null) {
            newTopic.setOwner(owner);
        } else {
            throw new ResourceNotFoundException("User " + topic.getOwner().getUsername() + " Not Found");
        }

        newTopic.setFrequency(topic.getFrequency());

        newTopic.setDefaultsurvey(new Survey(newTopic));
        for (Question sq : topic.getDefaultsurvey().getQuestions()) {
            newTopic.getDefaultsurvey().addQuestion(new Question(sq.getBody(), sq.isLeader(),sq.getType(),newTopic.getDefaultsurvey()));
        }

        newTopic.getUsers().clear();

        for (TopicUsers tu : topic.getUsers()) {
            newTopic.getUsers().add(new TopicUsers(newTopic, tu.getUser()));
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
