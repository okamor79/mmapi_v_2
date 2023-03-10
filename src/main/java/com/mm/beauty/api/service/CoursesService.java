package com.mm.beauty.api.service;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;

import java.security.Principal;
import java.util.List;

public interface CoursesService {

    Courses createCourse(CoursesDTO coursesDTO, Principal principal);

    List<Courses> getAllCourse();

    Courses changeStatus(Long id);

    Courses getCourseById(Long id);

}
