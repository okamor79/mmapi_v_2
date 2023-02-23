package com.mm.beauty.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
@PreAuthorize("permitAll()")
public class GlobalController {

    @GetMapping("/")
    public ResponseEntity<Object> getApiVersion() {
        String version = "Version 2.0.0\n\nModify date: 23.02.2023";
        return ResponseEntity.ok().body(version);
    }

}
