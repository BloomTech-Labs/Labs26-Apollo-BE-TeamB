package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.handlers.HelperFunctions;
import com.lambdaschool.apollo.models.Survey;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.services.TopicService;
import com.lambdaschool.apollo.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/topics")
@Transactional
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @ApiOperation(value = "Get all topics of current user ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Topic.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/topics",
            produces = {"application/json"})

    public ResponseEntity<?> listUserTopics(Authentication authentication) {
        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findTopicsByUser(authentication.getName());
        List<Survey> surveyRequests = new ArrayList<>();
        myTopics.iterator().forEachRemaining(topic -> {
            surveyRequests.addAll(topic.getSurveysrequests());
        });
        for (Survey surveyRequest : surveyRequests) {
            helperFunctions.hasResponded(surveyRequest, userService.findByOKTAUserName(authentication.getName()));
        }
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }

    @ApiOperation(value = "List all topics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Topic.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/all",
            produces = {"application/json"})

    public ResponseEntity<?> listAllTopics() {

        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findAllTopics();
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }

    @ApiOperation(value = "Get topic by Id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved topic", response = Topic.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/topic/{topicid}", produces = "application/json")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicid) {
        Topic myTopic = topicService.findTopicById(topicid);
        return new ResponseEntity<>(myTopic, HttpStatus.OK);
    }

    @ApiOperation(value = "Join topic by the join code")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully joined topic", response = Topic.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 404, message = "Topic Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional
    @PostMapping(value = "/topic/{code}", produces = "application/json")
    public ResponseEntity<?> userJoinTopic(@PathVariable String code, Authentication authentication) throws URISyntaxException {

        Topic topic = topicService.findByJoinCode(code);

        User user = userService.findByOKTAUserName(authentication.getName());

        topicService.addTopicUser(topic.getTopicId(), user.getUserid());

        return new ResponseEntity<>(topic, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Create new topic for current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a new topic", response = Topic.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional
    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTopic(@RequestBody Topic newtopic, Authentication authentication) throws URISyntaxException {
        newtopic.setTopicId(0);
        User user = userService.findByOKTAUserName(authentication.getName());
        newtopic.setOwner(user);
        newtopic.getDefaultsurvey().setSurveyid(0);
        newtopic = topicService.save(newtopic);

        return new ResponseEntity<>(newtopic, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping(value = "/leave/{topicid}")
    public ResponseEntity<?> leaveTopic(@PathVariable long topicid,
                                        Authentication authentication) {
        topicService.deleteTopicUser(topicid, userService.findByOKTAUserName(authentication.getName()).getUserid());
        return new ResponseEntity<>(null, HttpStatus.GONE);
    }

    @ApiOperation(value = "Delete topic by topic id (## This also delete all surveys, questions, and responses associated with this topic ##)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the topic"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional
    @DeleteMapping(value = "/topic/{topicid}")
    public ResponseEntity<?> deleteTopic(@PathVariable long topicid,
                                         Authentication authentication) {
        User user = userService.findByOKTAUserName(authentication.getName());
        topicService.delete(topicid, user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}