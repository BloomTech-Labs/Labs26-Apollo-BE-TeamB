package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
