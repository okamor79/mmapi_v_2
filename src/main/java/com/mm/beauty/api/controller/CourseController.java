package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.facade.CourseFacade;
import com.mm.beauty.api.service.CoursesService;
import com.mm.beauty.api.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/course")
@PreAuthorize("permitAll()")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CourseFacade courseFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @GetMapping("/all")
    public ResponseEntity<List<CoursesDTO>> getAllCourse() {
        List<CoursesDTO> userDTOList = coursesService.getAllCourse().stream()
                .map(courseFacade::CourseToCourseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addCourse(@RequestBody CoursesDTO request, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        coursesService.createCourse(request, principal);
        return null;

    }
}
