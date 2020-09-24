package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "answerService")
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    SurveyService surveyService;

    @Override
    public Answer findById(long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer " + id + " Not Found"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer " + id + " Not Found"));
        answerRepository.delete(answer);
    }

    @Transactional
    @Override
    public Answer save(Answer answer, long questionId, String username) {
        Answer newAnswer = new Answer();

        if (answer.getAnswerId() != 0) {
            Answer oldAnswer = answerRepository.findById(answer.getAnswerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Answer " + answer.getAnswerId() + " Not Found"));
            newAnswer.setAnswerId(answer.getAnswerId());
        }

        newAnswer.setBody(answer.getBody());
        Question question = questionService.findById(questionId);
        if (question != null) {
            newAnswer.setQuestion(question);
        } else {
            throw new ResourceNotFoundException("Question Id " + answer.getQuestion().getQuestionId() + " Not Found");
        }

        User user = userService.findByOKTAUserName(username);
        if (user != null) {
            newAnswer.setUser(user);
        } else {
            throw new ResourceNotFoundException("User Id " + answer.getUser().getUserid() + " Not Found");
        }

        Survey survey = question.getSurvey();
        if (survey != null) {
            newAnswer.setSurvey(survey);
        } else {
            throw new ResourceNotFoundException("Survey Id " + answer.getSurvey().getSurveyId() + "Nor Found");
        }

        return answerRepository.save(newAnswer);
    }

    @Transactional
    @Override
    public Answer update(Answer answer) {
        return null;
    }

    @Override
    public List<Answer> findAllAnswers() {
        List<Answer> a = new ArrayList<>();

        answerRepository.findAll().iterator().forEachRemaining(a::add);
        return a;
    }
}
