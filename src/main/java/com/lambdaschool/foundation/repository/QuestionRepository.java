package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
