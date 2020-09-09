package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Survey;

public interface SurveyService {

    Survey findById(long id);

    void delete(long id);

    Survey save(Survey survey);

}
