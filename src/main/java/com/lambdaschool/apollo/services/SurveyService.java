package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.views.SurveyQuestion;

import java.util.List;

public interface SurveyService {

    Survey findById(long id);

    List<Survey> findAllSurveys();

    void delete(long id, User user);

    Survey save(Survey survey);

    Survey saveRequest(List<SurveyQuestion> questions, Topic topic);

    void removeQuestion(Survey survey, long id);
}
