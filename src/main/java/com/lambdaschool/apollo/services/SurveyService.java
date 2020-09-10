package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Survey;

public interface SurveyService {

    Survey findById(long id);

    void delete(long id);

    Survey save(Survey survey);

}
