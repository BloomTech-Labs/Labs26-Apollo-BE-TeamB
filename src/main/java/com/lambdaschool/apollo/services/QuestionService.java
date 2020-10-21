package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.models.User;

import java.util.List;

public interface QuestionService {

    Question findById(long id);

    void delete(long id, User user);

    Question save(Question question);

    Question update(Question question);

    List<Question> findAllQuestions();

    List<Question> findAllBySurveyId(long surveyId);
}
