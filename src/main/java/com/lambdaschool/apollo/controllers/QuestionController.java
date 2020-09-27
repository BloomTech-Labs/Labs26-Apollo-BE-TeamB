package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Question;
import com.lambdaschool.apollo.services.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @ApiOperation(value = "List all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved lsit", response = Question.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/questions", produces = {"application/json"})
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionService.findAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
