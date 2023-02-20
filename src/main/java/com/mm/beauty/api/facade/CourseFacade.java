package com.mm.beauty.api.facade;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import org.springframework.stereotype.Component;

@Component
public class CourseFacade {

    public CoursesDTO CourseToCourseDTO(Courses courses) {
        CoursesDTO coursesDTO = new CoursesDTO();
        coursesDTO.setId(courses.getId());
        coursesDTO.setCourseName(courses.getName());
        coursesDTO.setDescription(courses.getDescription());
        coursesDTO.setUniqueCode(courses.getUniqCode());
        coursesDTO.setFullDescription(courses.getFullDescription());
        coursesDTO.setPrice(courses.getPrice());
        coursesDTO.setAvatarId(courses.getAvatarId());
        coursesDTO.setUrlCoursePreview(courses.getUrlCoursePreview());
        coursesDTO.setUrlCourseVideo(courses.getUrlCourseVideo());
        coursesDTO.setDayAccess(courses.getDayAccess());
        coursesDTO.setStartDate(courses.getStartDate());
        coursesDTO.setEndDate(courses.getEndDate());
        return coursesDTO;

    }

}
