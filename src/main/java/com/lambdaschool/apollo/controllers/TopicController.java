package com.lambdaschool.apollo.controllers;
import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
