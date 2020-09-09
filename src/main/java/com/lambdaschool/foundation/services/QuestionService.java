package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Question;

public interface QuestionService {

    Question findById(long id);

    void delete(long id);

    Question save(Question question);

    Question update(Question question);
}
