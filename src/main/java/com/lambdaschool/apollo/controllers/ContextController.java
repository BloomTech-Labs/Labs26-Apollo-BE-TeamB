package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.services.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/contexts")
public class ContextController {

    @Autowired
    private ContextService contextService;

    @GetMapping(value = "/all",
            produces = {"application/json"})
    public ResponseEntity<?> getAllContexts() {
        List<Context> allContexts = contextService.findAllContexts();
        return new ResponseEntity<>(allContexts,
                HttpStatus.OK);
    }

    @GetMapping(value = "/contextid={contextid}/questions",
            produces = {"application/json"})
    public ResponseEntity<?> getQuestionsByContextId(@PathVariable Long contextid)
    {
        Context context = contextService.findContextById(contextid);
        return new ResponseEntity<>(context.getContextquestions(), HttpStatus.OK);
    }

}
