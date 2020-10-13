package com.lambdaschool.apollo.handlers;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.QuestionService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains helper functions - functions that are needed throughout the application. The class can be autowired
 * into any class.
 */
@Component
public class HelperFunctions {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    /**
     * Checks to see if the authenticated user has access to modify the requested user's information
     *
     * @param username The user name of the user whose data is requested to be changed. This should either match the authenticated user
     *                 or the authenticate must have the role ADMIN
     * @return true if the user can make the modifications, otherwise an exception is thrown
     */
    public boolean isAuthorizedToMakeChange(String username) {
        // Check to see if the user whose information being requested is the current user
        // Check to see if the requesting user is an admin
        // if either is true, return true
        // otherwise stop the process and throw an exception
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName()
                .toLowerCase()) || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // this user can make this change
            return true;
        } else {
            // stop user is not authorized to make this change so stop the whole process and throw an exception
            throw new ResourceNotFoundException(authentication.getName() + " not authorized to make change");
        }
    }

    /**
     * Searches to see if the exception has any constraint violations to report
     *
     * @param cause the exception to search
     * @return constraint violations formatted for sending to the client
     */
    public List<ValidationError> getConstraintViolation(Throwable cause) {
        // Find any data violations that might be associated with the error and report them
        // data validations get wrapped in other exceptions as we work through the Spring
        // exception chain. Hence we have to search the entire Spring Exception Stack
        // to see if we have any violation constraints.
        while ((cause != null) && !(cause instanceof ConstraintViolationException)) {
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        // we know that cause either null or an instance of ConstraintViolationException
        if (cause != null) {
            ConstraintViolationException ex = (ConstraintViolationException) cause;
            for (ConstraintViolation cv : ex.getConstraintViolations()) {
                ValidationError newVe = new ValidationError();
                newVe.setCode(cv.getInvalidValue()
                        .toString());
                newVe.setMessage(cv.getMessage());
                listVE.add(newVe);
            }
        }
        return listVE;
    }

    public String getNewJoinCode() {

        // current timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // create new hash using secret HASHSALT
        Hashids hashids = new Hashids(System.getenv("HASHSALT"));
        // use current timestamp in millis as encode body
        String newJoinCode = hashids.encode(timestamp.getTime());

        return newJoinCode;
    }

    public void hasResponded(Survey survey, User user) {
        List<Question> questions;
        questions = questionService.findAllBySurveyId(survey.getSurveyid());

        for (Question q : questions) {
            for (Answer a : q.getAnswers()) {
                if (a.getUser() == user) {
                    survey.setHasResponded(true);
                }
            }
        }
    }

    public void hasResponded(List<Survey> surveys,  User user) {
        List<Question> questions;
        for (Survey s : surveys) {
            // only get questions that are associated with the survey
            questions = questionService.findAllBySurveyId(s.getSurveyid());
            // if the user has is tied to an answer that lives in questions than
            // has responded is set to true
            for (Question q : questions) {
                for (Answer a : q.getAnswers()) {
                    if (a.getUser() == user) {
                        s.setHasResponded(true);
                    }
                }
            }
        }
    }


    public List<Question> isLeaderQuestion(List<Question> questions) {
        List<Question> leaderQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.isLeader()) {
                leaderQuestions.add(q);
            }
        }
        return leaderQuestions;
    }

}
