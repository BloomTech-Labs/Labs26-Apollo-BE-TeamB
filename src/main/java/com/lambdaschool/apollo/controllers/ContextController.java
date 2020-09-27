package com.lambdaschool.apollo.controllers;

import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.services.ContextService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contexts")
public class ContextController {

    @Autowired
    private ContextService contextService;

    @ApiOperation(value = "List all context types")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list", response = Context.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/contexts", produces = {"application/json"})
    public ResponseEntity<?> getAllContexts() {
        List<Context> myContexts = contextService.findAll();
        return new ResponseEntity<>(myContexts, HttpStatus.OK);
    }

    @ApiOperation(value = "Get context type by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved context type", response = Context.class),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/contexts/{contextId}", produces = {"application/json"})
    public ResponseEntity<?> getContextById(@PathVariable Long contextId) {
        Context context = contextService.findById(contextId);
        return new ResponseEntity<>(context, HttpStatus.OK);
    }

}
