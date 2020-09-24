package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Answer;

import java.util.List;

public interface AnswerService {

    Answer findById(long id);

    void delete(long id);

    Answer save(Answer answer, long questionId, long surveyId);

    Answer update(Answer answer);

    List<Answer> findAllAnswers();
}
