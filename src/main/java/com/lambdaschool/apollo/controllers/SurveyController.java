package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.SurveyService;
import com.lambdaschool.apollo.services.UserService;
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
import java.util.List;

@RestController
@RequestMapping("/surveys")
@Api(value = "Operations pertaining to topics")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

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

        answerService.save(myList, userService.findByOKTAUserName(authentication.getName()));

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
  
    @ApiOperation(value = "List all surveys ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Survey.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })

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
    public ResponseEntity<?> createSurveyRequest(@RequestBody @NotNull List<SurveyQuestion> questions, Authentication authentication, @PathVariable long topicid) {
        //Check that the current use is thw owner of the topic for which they are trying to create a request
            // TO-DO
        // We do not accept existing surveys, Always save as a new survey
        // surveyRequest.setSurveyId(0);
        Survey survey = surveyService.saveRequest(questions, topicid);
        // for (SurveyQuestion q: questions) {
        //     System.out.println(q);
        // }



        return new ResponseEntity<>(survey, HttpStatus.CREATED);
    }

}
