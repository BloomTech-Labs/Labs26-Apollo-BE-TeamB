package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.repository.AnswerRepository;
import com.lambdaschool.apollo.views.QuestionBody;
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
    public void save(QuestionBody qb, User user) {

        Answer newAnswer = new Answer();
        if (qb.getBody() != null) {
            newAnswer.setBody(qb.getBody());
        } else {
            throw new ResourceNotFoundException("Answer has no body!");
        }
        Question question = questionService.findById(qb.getQuestionid());
        if (question != null) {
            newAnswer.setQuestion(question);
        } else {
            throw new ResourceNotFoundException("Question Id " + qb.getQuestionid() + " Not Found");
        }
        if (user == userService.findUserById(user.getUserid())) {
            newAnswer.setUser(user);
        } else {
            throw new ResourceNotFoundException("User Id " + user.getUserid() + " Not Found");
        }
        Survey survey = question.getSurvey();
        if (survey != null) {
            newAnswer.setSurvey(survey);
        } else {
            throw new ResourceNotFoundException("Survey Id " + question.getSurvey().getSurveyid() + "Not Found");
        }

        answerRepository.save(newAnswer);

//        yes this is dead code but it will get implemented in an future save method overload
//        if (answer.getAnswerId() != 0) {
//            Answer oldAnswer = answerRepository.findById(answer.getAnswerId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Answer " + answer.getAnswerId() + " Not Found"));
//            newAnswer.setAnswerId(answer.getAnswerId());
//        }

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

    @Override
    public List<Answer> findBySurveyId(long surveyid) {
        List<Answer> answers = answerRepository.findBySurvey_surveyid(surveyid);
        return answers;
    }

    @Override
    public Answer findByQuestionIdAndUserId(long questionId, long userId) {
        Answer answer = answerRepository.findAnswerByQuestionIdAndUserId(questionId, userId);
        if (answer == null) {
            return null;
        } else {
            return answer;
        }
    }
}
