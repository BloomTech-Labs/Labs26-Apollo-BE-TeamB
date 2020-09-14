package com.lambdaschool.apollo.controllers;


import com.lambdaschool.apollo.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;


//    @ApiOperation(value = "returns all Topics",
//            response = Topic.class,
//            responseContainer = "List")
//    /** Will add this back in when i know how this works
//     * @PreAuthorize("hasAnyRole('USER')")
//     */
//    @GetMapping(value = "/topics",
//            produces = {"applicattion/json"})
//
//    public ResponseEntity<?> listAllTopics() {
//        List<Topic> myTopics
//    }


}
