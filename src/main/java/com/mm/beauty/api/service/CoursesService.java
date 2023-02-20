package com.mm.beauty.api.service;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;

import java.security.Principal;
import java.util.List;

public interface CoursesService {

    Courses createCourse(CoursesDTO coursesDTO);

    List<Courses> getAllCourse();

}
