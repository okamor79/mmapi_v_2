package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.payload.request.SignupRequest;
import com.mm.beauty.api.service.CoursesService;
import com.mm.beauty.api.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/course")
@PreAuthorize("permitAll()")
public class CourseController {

    @Autowired
    private CoursesService coursesService;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;


    @PostMapping("/add")
    public ResponseEntity<Object> addCourse(@RequestBody CoursesDTO request, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        coursesService.createCourse(request, principal);
        return null;

    }
}
