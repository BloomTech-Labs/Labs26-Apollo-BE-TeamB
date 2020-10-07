package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.exceptions.ResourceFoundException;
import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.*;
import com.lambdaschool.apollo.services.*;
import com.lambdaschool.apollo.views.QuestionBody;
import com.lambdaschool.apollo.views.SurveyQuestion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/surveys")
@Api(value = "Operations pertaining to topics")
public class SurveyController {

    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "Create new survey ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new survey", response = Survey.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
    public ResponseEntity<?> createNewResponse(Authentication authentication,
            @Valid
            @RequestBody List<QuestionBody> myList)
            throws URISyntaxException {
        User user = userService.findByOKTAUserName(authentication.getName());
        for (QuestionBody qb: myList) {
            Question question = questionService.findById(qb.getQuestionid());
            for (Answer a: question.getAnswers()) {
                if (user.getUserid() == a.getUser().getUserid()) {
                    throw new ResourceFoundException("Current user already answered question with id - " + question.getQuestionId());
                }
            }
            Survey survey = surveyService.findById(question.getSurvey().getSurveyid());
            Topic topic = topicService.findTopicById(survey.getTopic().getTopicId());
            List<TopicUsers> topicUsers = topic.getUsers();
            List<User> users = new ArrayList<>();
            for (TopicUsers tu: topicUsers) users.add(tu.getUser());
            if (question.isLeader()) {
                if (user.getUserid() != topic.getOwner().getUserid()) {
                    throw new ResourceFoundException("Current user not owner of topic with id - " + topic.getTopicId());
                }
            } else {
                if (user.getUserid() == topic.getOwner().getUserid()) {
                    throw new ResourceFoundException("Topic owner cannot answer request questions");
                } else if (!users.contains(user)) {
                    throw new ResourceFoundException("Current user not a member of topic with id - " + topic.getTopicId());
                }
            }
            answerService.save(qb, user);
        }
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @ApiOperation(value = "List all surveys ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Survey.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAllSurveys(Authentication authentication) {
        List<Survey> surveys = surveyService.findAllSurveys();
        helperFunctions.hasResponded(surveys, userService.findByOKTAUserName(authentication.getName()));

        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @GetMapping(value = "/survey/{surveyid}", produces = {"application/json"})
    public ResponseEntity<?> getSurveyById(Authentication authentication, @PathVariable long surveyid) {
        Survey survey = surveyService.findById(surveyid);

        helperFunctions.hasResponded(survey, userService.findByOKTAUserName(authentication.getName()));
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/topic/{topicid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createSurveyRequest(@RequestBody @NotNull List<SurveyQuestion> questions, Authentication authentication, @PathVariable long topicid) {
        //Check that the current use is the owner of the topic for which they are trying to create a request
        User u = userService.findByOKTAUserName(authentication.getName());
        //Get topic from url path variable
        Topic topic  = topicService.findTopicById(topicid);
        //if user is owner of topic return new survey otherwise throw exception saying user is not authorized
        if (u.getUserid() == topic.getOwner().getUserid()) {
            Survey survey = surveyService.saveRequest(questions, topic);
            return new ResponseEntity<>(survey, HttpStatus.CREATED);
        } else {
            throw new ResourceFoundException("Current user not authorized to make this request");
        }
    }

    @GetMapping(value = "/survey/{surveyid}/responses", produces = {"application/json"})
    public ResponseEntity<?> getResponses(Authentication authentication, @PathVariable long surveyid) {
        Survey survey = surveyService.findById(surveyid);
        List<Answer> responses = answerService.findBySurveyId(surveyid);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
