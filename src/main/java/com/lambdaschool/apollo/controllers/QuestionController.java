package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.services.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    HelperFunctions helperFunctions;

    @ApiOperation(value = "List all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved lsit", response = Question.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionService.findAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping(value = "/leader/{surveyid}", produces = "application/json")
    public ResponseEntity<?> getLeaderQuestionsBySurveyId(@PathVariable
                                                          long surveyid) {
        List<Question> questions = helperFunctions.isLeaderQuestion(
                questionService.findAllBySurveyId(surveyid));
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

//    @DeleteMapping(value = "/question/{questionid}") <-- Ideally we will make this available once the service works
//    public ResponseEntity<?> deleteQuestionById(@PathVariable
//                                                long questionid) {
//        questionService.delete(questionid);
//        return new ResponseEntity<>(null, HttpStatus.GONE);
//    }
}
