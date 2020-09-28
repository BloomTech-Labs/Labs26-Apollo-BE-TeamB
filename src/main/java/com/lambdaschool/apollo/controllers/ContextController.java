package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.services.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/contexts")
public class ContextController {

    @Autowired
    private ContextService contextService;

    @GetMapping(value = "/contexts", produces = {"application/json"})
    public ResponseEntity<?> getAllContexts() {
        List<Context> myContexts = contextService.findAll();
        return new ResponseEntity<>(myContexts,HttpStatus.OK);
    }

    @GetMapping(value = "/contexts/{contextId}", produces = {"application/json"})
    public ResponseEntity<?> getContextById(@PathVariable Long contextId) {
        Context context = contextService.findById(contextId);
        return new ResponseEntity<>(context, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createContext(@RequestBody Context context, Authentication authentication) {
        context.setContextId(0);
        context.getSurvey().setSurveyId(0);
        context = contextService.save(context);
        return new ResponseEntity<>(context, HttpStatus.CREATED);
    }

}
