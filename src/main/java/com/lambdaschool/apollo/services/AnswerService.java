package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.views.QuestionBody;

import java.util.List;

public interface AnswerService {

    Answer findById(long id);

    void delete(long id);

    void save(List<QuestionBody> l, User user);

    Answer update(Answer answer);

    List<Answer> findAllAnswers();
}
