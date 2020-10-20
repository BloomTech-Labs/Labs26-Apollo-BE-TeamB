package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.User;
import com.lambdaschool.apollo.services.AnswerService;
import com.lambdaschool.apollo.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Delete an answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete the answer"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/answer/{answerid}")
    public ResponseEntity<?> deleteAnswer(@PathVariable long answerid, Authentication authentication) {
        User user = userService.findByOKTAUserName(authentication.getName());
        answerService.delete(answerid, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
