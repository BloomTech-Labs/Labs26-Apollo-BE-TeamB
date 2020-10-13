package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "contextService")
public class ContextServiceImpl implements ContextService {

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Override
    public List<Context> findAll() {

        List<Context> contextList = new ArrayList<>();
        contextRepository.findAll().iterator().forEachRemaining(contextList::add);
        return contextList;
    }

    @Override
    public Context findById(long id) {

        return contextRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Context " + id + " Not Found"));
    }

    @Override
    public Context findByDescription(String description) {

        Context context = contextRepository.findContextByDescription(description);
        if (context == null) {
            throw new ResourceNotFoundException("Context Description: " + description + " Not Found");
        }
        return context;
    }


    @Transactional
    @Override
    public Context save(Context context) {

        Context newContext = new Context();

        if (context.getContextId() != 0) {
            Context oldContext = contextRepository.findById(context.getContextId())
                    .orElseThrow(() -> new ResourceNotFoundException("Context Id " + context.getContextId() + " Not Found"));
            newContext.setContextId(context.getContextId());
        }

        newContext.setDescription(context.getDescription());

        // If saving existing context with a survey, go get the survey by id
        if (context.getSurvey().getSurveyId() != 0) {
            Survey survey = surveyService.findById(context.getSurvey().getSurveyId());
            newContext.setSurvey(survey);
        // New context, new survey
        } else {
            newContext.setSurvey(new Survey());
            // A survey needs questions, add them
            for (Question q : context.getSurvey().getQuestions()) {
                // Hey, an existing question
                if (q.getQuestionid() != 0) {
                    Question q1 = questionService.findById(q.getQuestionid());
                    newContext.getSurvey().addQuestion(q1);
                // Cool, a new question. I can create that
                } else {
                    newContext.getSurvey().addQuestion(new Question(q.getBody(), q.isLeader(), q.getType(),newContext.getSurvey()));
                }
            }
        }

        return contextRepository.save(newContext);
    }

}
