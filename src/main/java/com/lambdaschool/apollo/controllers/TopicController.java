package com.lambdaschool.apollo.controllers;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;


    @GetMapping(value = "/topics",
            produces = {"application/json"})

    public ResponseEntity<?> listAllTopics() {

        List<Topic> myTopics = new ArrayList<>();
        myTopics = topicService.findAllTopics();
        return new ResponseEntity<>(myTopics, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userid}", produces = {"application/json"})
    public ResponseEntity<?> getTopicsByUserId(@PathVariable Long userid){
        List<Topic> userTopics = topicService.findTopicsByUser(userid);

        return new ResponseEntity<>(userTopics, HttpStatus.OK);
    }

    @GetMapping(value = "/topic/{topicid}", produces = "application/json")
    public ResponseEntity<?> getTopicById(@PathVariable Long topicid) {
        Topic myTopic = topicService.findTopicById(topicid);
        return new ResponseEntity<>(myTopic, HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createTopic(@Valid @RequestBody Topic newtopic) throws URISyntaxException {
        newtopic.setTopicId(0);
        newtopic = topicService.save(newtopic);
        return new ResponseEntity<>(newtopic, HttpStatus.CREATED);
    }
}
