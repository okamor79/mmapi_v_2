package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.CStatus;
import com.mm.beauty.api.exceptions.CourseExistException;
import com.mm.beauty.api.repository.ImageModelRepository;
import com.mm.beauty.api.repository.CoursesRepository;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.CoursesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CoursesServiceImpl implements CoursesService {

    public static final Logger LOG = LoggerFactory.getLogger(CoursesServiceImpl.class);

    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;

    public CoursesServiceImpl(UserRepository userRepository, CoursesRepository coursesRepository) {
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public Courses createCourse(CoursesDTO coursesDTO, Principal principal) {
        Courses courses = new Courses();
        User user = getUserByPrincipal(principal);
//        courses.setUser(user);
        courses.setUniqueCode(coursesDTO.getUniqueCode());
        courses.setName(coursesDTO.getCourseName());
        courses.setDescription(coursesDTO.getDescription());
        courses.setFullDescription(coursesDTO.getFullDescription());
        courses.setPrice(coursesDTO.getPrice());
        courses.setStartDate(coursesDTO.getStartDate());
        courses.setEndDate(coursesDTO.getEndDate());
        courses.setUrlCourseVideo(coursesDTO.getUrlCourseVideo());
        courses.setUrlCoursePreview(coursesDTO.getUrlCoursePreview());
        courses.setStatus(CStatus.COURSE_ENABLE);
        courses.setDayAccess(coursesDTO.getDayAccess());
        try {
            LOG.info("Course added {}", coursesDTO.getUniqueCode() + " - " + coursesDTO.getCourseName());
            return coursesRepository.save(courses);
        } catch (Exception e) {
            LOG.error("Error course added {}", e.getMessage());
            throw new CourseExistException("Course " + coursesDTO.getUniqueCode() + " - " + coursesDTO.getCourseName() + " already exist");
        }
    }

    @Override
    public List<Courses> getAllCourse() {
        return coursesRepository.findAll();
    }

    @Override
    public Courses changeStatus(Long id) {
        Courses course = coursesRepository.findCoursesById(id);
        switch (course.getStatus()) {
            case COURSE_ENABLE : {
                course.setStatus(CStatus.COURSE_DISABLE);
                break;
            }
            case COURSE_DISABLE : {
                course.setStatus(CStatus.COURSE_ENABLE);
                break;
            }
        }
        return coursesRepository.save(course);
    }

    @Override
    public Courses getCourseById(Long id) {
        return coursesRepository.findCoursesById(id);
    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }


}
