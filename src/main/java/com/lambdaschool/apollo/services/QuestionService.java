package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Question;

public interface QuestionService {

    Question findById(long id);

    void delete(long id);

    Question save(Question question);

    Question update(Question question);
}
