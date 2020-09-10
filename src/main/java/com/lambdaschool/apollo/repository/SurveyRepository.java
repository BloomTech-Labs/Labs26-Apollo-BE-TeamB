package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
