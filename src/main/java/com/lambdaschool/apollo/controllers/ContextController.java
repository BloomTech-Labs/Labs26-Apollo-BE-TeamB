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

}
