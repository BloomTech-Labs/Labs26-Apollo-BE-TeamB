package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

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


    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys() {
        List<Survey> surveys = surveyService.findAllSurveys();
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping(value = "/survey/{surveyid}", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys(@PathVariable long surveyid) {
        Survey survey = surveyService.findById(surveyid);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/topic/{topicid}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createSurveyRequest(@RequestBody Survey surveyRequest, Authentication authentication, @PathVariable long topicid) {
        //Check that the current use is thw owner of the topic for which they are trying to create a request
            // TO-DO
        // We do not accept existing surveys, Always save as a new survey
        surveyRequest.setSurveyId(0);
        surveyRequest = surveyService.save(surveyRequest);

        return new ResponseEntity<>(surveyRequest, HttpStatus.CREATED);
    }
    

}
