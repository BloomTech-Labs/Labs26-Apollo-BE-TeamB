package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.repository.SurveyRepository;
import com.lambdaschool.apollo.repository.TopicRepository;
import com.lambdaschool.apollo.repository.UserRepository;
import com.lambdaschool.apollo.views.SurveyQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Survey findById(long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey " + id + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (surveyRepository.findById(id).isPresent()) {
            surveyRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Survey " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    @Override
    public Survey saveRequest(List<SurveyQuestion> questions, long topicId) {
        // We need to know what topic to add the survey to
        Topic topic  = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Hey, this topic doesn't exist"));
        // We need to know who is answering the leader questions
        User user = topic.getOwner();

        Survey newSurvey = new Survey(topic);

        for (SurveyQuestion q: questions) {
            // Create the question
            Question question = new Question(q.getBody(), q.getLeader(), q.getType(), newSurvey);
            // If it is a leader question, attach an answer
            if (question.isLeader()) {
                question.getAnswers().add(new Answer(q.getAnswer(), question, user, newSurvey));
            }
            // add question to survey
            newSurvey.addQuestion(question);
        }

        //Save
        return surveyRepository.save(newSurvey);
    }

    @Override
    public List<Survey> findAllSurveys() {
        List<Survey> list = new ArrayList<>();
        surveyRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }
}
