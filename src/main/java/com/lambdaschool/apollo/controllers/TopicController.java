package com.lambdaschool.apollo.controllers;


import com.lambdaschool.apollo.models.Topic;
import com.lambdaschool.apollo.services.TopicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;


    @ApiOperation(value = "returns all Topics",
            response = Topic.class,
            responseContainer = "List")
    /** Will add this back in when i know how this works
     * @PreAuthorize("hasAnyRole('USER')")
     */
    @GetMapping(value = "/topics",
            produces = {"applicattion/json"})

    public ResponseEntity<?> listAllTopics() {
        List<Topic> myTopics
    }




}
