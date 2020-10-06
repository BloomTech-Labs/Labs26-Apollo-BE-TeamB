package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findBySurvey_surveyid(long surveyid);
}
