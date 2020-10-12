package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    List<Answer> findBySurvey_surveyid(long surveyid);

    @Query(value = "SELECT * FROM Answers WHERE questionid = :questionid AND userid = :userid", nativeQuery = true)
    Answer findAnswerByQuestionIdAndUserId(long questionid, long userid);
}
