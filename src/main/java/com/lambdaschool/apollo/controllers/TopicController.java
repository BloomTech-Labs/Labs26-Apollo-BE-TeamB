package com.lambdaschool.apollo.controllers;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.services.TopicService;
import com.lambdaschool.apollo.services.UserService;
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


    @GetMapping(value = "/topics",
            produces = {"application/json"})

    public ResponseEntity<?> listUserTopics(Authentication authentication) {
        User user = userService.findByOKTAUserName(authentication.getName());
        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findTopicsByUser(user);
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }

    @GetMapping(value = "/all",
            produces = {"application/json"})

    public ResponseEntity<?> listAllTopics() {

        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findAllTopics();
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }


    @GetMapping(value = "/topic/{topicid}", produces = "application/json")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicid) {
        Topic myTopic = topicService.findTopicById(topicid);
        return new ResponseEntity<>(myTopic, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTopic(@RequestBody Topic newtopic) throws URISyntaxException {
        newtopic.setTopicId(0);
        newtopic = topicService.save(newtopic);
        return new ResponseEntity<>(newtopic, HttpStatus.CREATED);
    }
}
