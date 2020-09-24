package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.AnswerMinimum;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private AnswerService answerService;

    @PostMapping(value = "/new",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> createNewSurvey(
            HttpServletRequest httpServletRequest,
            @Valid
            @RequestBody
                Survey postedSurvey)
            throws
            URISyntaxException {
        Survey newSurvey = new Survey();

        newSurvey.setSurveyId(postedSurvey.getSurveyId());
        surveyService.save(newSurvey);

        return new ResponseEntity<>(newSurvey, HttpStatus.CREATED);
    }


    @PostMapping(value = "/response",
        consumes = {"application/json"},
        produces = {"application/json"})
    public ResponseEntity<?> createNewResponse(
            @Valid
            @RequestBody AnswerMinimum answer)
            throws URISyntaxException {
        Answer newAnswer = new Answer();
        newAnswer.setBody(answer.getBody());
        newAnswer.setAnswerId(0);
        answerService.save(newAnswer, answer.getQuestionId(), answer.getSurveyId());

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newResponseURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{responseid")
                .buildAndExpand(newAnswer.getAnswerId())
                .toUri();
        responseHeaders.setLocation(newResponseURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }




    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys() {
        List<Survey> surveys = surveyService.findAllSurveys();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }
    

}
