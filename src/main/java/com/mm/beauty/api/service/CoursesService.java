package com.mm.beauty.api.service;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;

import java.security.Principal;

public interface CoursesService {

    Courses createCourse(CoursesDTO coursesDTO, Principal principal);




}
