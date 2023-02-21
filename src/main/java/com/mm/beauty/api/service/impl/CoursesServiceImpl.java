package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.CStatus;
import com.mm.beauty.api.exceptions.CourseExistException;
import com.mm.beauty.api.repository.AvatarCoursesRepository;
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
    private final AvatarCoursesRepository avatarCoursesRepository;

    public CoursesServiceImpl(UserRepository userRepository, CoursesRepository coursesRepository, AvatarCoursesRepository avatarCoursesRepository) {
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
        this.avatarCoursesRepository = avatarCoursesRepository;
    }

    @Override
    public Courses createCourse(CoursesDTO coursesDTO, Principal principal) {
        Courses courses = new Courses();
        User user = getUserByPrincipal(principal);
        courses.setUser(user);
        courses.setUniqueCode(coursesDTO.getUniqueCode());
        courses.setName(coursesDTO.getCourseName());
        courses.setDescription(coursesDTO.getDescription());
        courses.setFullDescription(coursesDTO.getFullDescription());
        courses.setPrice(coursesDTO.getPrice());
        courses.setStartDate(coursesDTO.getStartDate());
        courses.setEndDate(coursesDTO.getEndDate());
        courses.setUrlCourseVideo(coursesDTO.getUrlCourseVideo());
        courses.setUrlCoursePreview(coursesDTO.getUrlCoursePreview());
        courses.getCourseStatus().add(CStatus.COURSE_ENABLE);
        courses.setAvatarId(coursesDTO.getAvatarId());
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
    public Courses getCourseById(Long id) {
        return coursesRepository.findCoursesById(id);
    }

//    public Courses getCourseById(Long id, Principal principal) {
//        User user = getUserByPrincipal(principal);
//        return coursesRepository.findCoursesById(id);
//    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }


}
