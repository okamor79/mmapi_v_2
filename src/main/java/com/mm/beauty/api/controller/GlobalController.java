package com.mm.beauty.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class GlobalController {

    @GetMapping("/")
    public ResponseEntity<Object> getApiVersion() {
        String version = "Version 2.0.0\n\nModify date: 23.02.2023";
        return ResponseEntity.ok().body(version);
    }

}
