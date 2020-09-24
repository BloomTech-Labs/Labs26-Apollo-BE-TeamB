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

        Context context = contextRepository.findContextByDescription(description.toLowerCase());
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

        if (context.getSurvey().getSurveyId() != 0) {
            Survey survey = surveyService.findById(context.getSurvey().getSurveyId());
            newContext.setSurvey(survey);
        } else {
            newContext.setSurvey(new Survey());
            for (Question q : context.getSurvey().getQuestions()) {
                newContext.getSurvey().addQuestion(new Question(q.getBody(), q.isLeader(), q.getType(),newContext.getSurvey()));
//                System.out.println("entered loop");
//                if (q.getQuestionId() != 0) {
//                    System.out.println("entered if");
//                    Question q1 = questionService.findById(q.getQuestionId());
//                    newContext.getSurvey().getQuestions().add(q1);
//                } else {
//                    System.out.println("entered else");
//                    System.out.println(newContext.getSurvey());
//                    newContext.getSurvey().addQuestion(new Question(q.getBody(), q.isLeader(), q.getType(),context.getSurvey()));
//                    System.out.println("finished adding question");
//                }
            }
        }

        return contextRepository.save(newContext);
    }

}
