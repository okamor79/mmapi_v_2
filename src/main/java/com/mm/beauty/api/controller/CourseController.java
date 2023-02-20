package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.facade.CourseFacade;
import com.mm.beauty.api.payload.response.MessageResponse;
import com.mm.beauty.api.service.CoursesService;
import com.mm.beauty.api.validations.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Object> addCourse(@Valid @RequestBody CoursesDTO coursesDTO, BindingResult result) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Courses courses = coursesService.createCourse(coursesDTO);
        CoursesDTO addedCourse = courseFacade.CourseToCourseDTO(courses);
        return new ResponseEntity<>(  addedCourse, HttpStatus.OK);

    }
}
