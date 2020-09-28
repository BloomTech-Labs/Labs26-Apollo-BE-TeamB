package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Survey;

import java.util.List;

public interface SurveyService {

    Survey findById(long id);

    List<Survey> findAllSurveys();

    void delete(long id);

    Survey save(Survey survey);

    Survey saveRequest(Survey survey, long topicId);

}
