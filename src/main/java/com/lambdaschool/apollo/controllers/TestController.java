package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.Answer;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.SurveyService;
import com.lambdaschool.apollo.services.TopicService;
import com.lambdaschool.apollo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
@Transactional
public class TestController {


    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;


    @Autowired
    private AnswerService answerService;

    @Autowired
    private HelperFunctions helperFunctions;



    @GetMapping(value = "/topics/topics", produces = {"application/json"})
    public ResponseEntity<?> listUserTopics() {
        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findTopicsByUser("llama001@maildrop.cc");
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }

    @GetMapping(value = "/topics/topic/{topicid}", produces = "application/json")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicid) {
        Topic myTopic = topicService.findTopicById(topicid);
        return new ResponseEntity<>(myTopic, HttpStatus.OK);
    }

    @GetMapping(value = "/surveys/survey/{surveyid}/responses", produces = {"application/json"})
    public ResponseEntity<?> getResponses( @PathVariable long surveyid) {
        Survey survey = surveyService.findById(surveyid);
        List<Answer> responses = answerService.findBySurveyId(surveyid);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(value = "/surveys/all", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys() {
        List<Survey> surveys = surveyService.findAllSurveys();
        helperFunctions.hasResponded(surveys, userService.findByOKTAUserName("llama001@maildrop.cc"));

        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping(value = "/surveys/survey/{surveyid}", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys(@PathVariable long surveyid) {
        Survey survey = surveyService.findById(surveyid);

        helperFunctions.hasResponded(survey, userService.findByOKTAUserName("llama001@maildrop.cc"));
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }
}
