package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.repository.SurveyRepository;
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

    @Override
    public Survey findById(long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey " + id + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long id, User user) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey " + id + " Not Found"));

        // if deleting a survey request
        if (survey.getTopic() != null) {
            // delete the survey if the associated topic owner is the current user
            if (user.getUserid() == survey.getTopic().getOwner().getUserid()) {
                surveyRepository.delete(survey);
            } else {
                throw new ResourceNotFoundException("Not authorized to perform this action");
            }
        } else {
            throw new ResourceNotFoundException("Cannot delete a default survey");
        }

    }

    @Transactional
    @Override
    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    @Override
    public Survey saveRequest(List<SurveyQuestion> questions, Topic topic) {
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

    @Override
    public void removeQuestion(Survey survey, long id) {
        for (Question q : survey.getQuestions()) {
            if (q.getQuestionid() == id) {
                survey.getQuestions().remove(q);
                return;
            }
        }
        throw new ResourceNotFoundException("Question " + id + " Not Found");
    }
}
