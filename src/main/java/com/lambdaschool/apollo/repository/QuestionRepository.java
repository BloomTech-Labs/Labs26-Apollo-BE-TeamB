package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
