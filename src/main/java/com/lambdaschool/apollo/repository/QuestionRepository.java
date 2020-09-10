package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findAll();
}
