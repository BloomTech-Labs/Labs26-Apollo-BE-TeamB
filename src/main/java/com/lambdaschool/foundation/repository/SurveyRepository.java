package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
