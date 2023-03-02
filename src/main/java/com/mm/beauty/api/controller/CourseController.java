package com.mm.beauty.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.facade.CourseFacade;
import com.mm.beauty.api.payload.response.MessageResponse;
import com.mm.beauty.api.service.CoursesService;
import com.mm.beauty.api.service.SaleService;
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
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/course")
@PreAuthorize("permitAll()")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private CourseFacade courseFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    private SaleService saleService;

    @GetMapping("/all")
    public ResponseEntity<List<CoursesDTO>> getAllCourse() {
        List<CoursesDTO> userDTOList = coursesService.getAllCourse().stream()
                .map(courseFacade::CourseToCourseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCourse(@RequestBody CoursesDTO coursesDTO, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidator.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Courses courses = coursesService.createCourse(coursesDTO, principal);
        CoursesDTO addedCourse = courseFacade.CourseToCourseDTO(courses);
        return new ResponseEntity<>(  addedCourse, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/changeStatus")
    public ResponseEntity<Object> changeStatus(@PathVariable("courseId") String courseId) {
        coursesService.changeStatus(Long.parseLong(courseId));
        return  ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/{courseId}/info")
    public ResponseEntity<CoursesDTO> getCourseById(@PathVariable("courseId") String courseId) {
        CoursesDTO course = courseFacade.CourseToCourseDTO(coursesService.getCourseById(Long.parseLong(courseId)));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/genbut")
    public ResponseEntity<Object> generateOrderButton(@PathVariable("courseId") String courseId, Principal principal) throws JsonProcessingException {
        return new ResponseEntity<>(saleService.newOrder(Long.parseLong(courseId), principal), HttpStatus.OK);
    }
}
