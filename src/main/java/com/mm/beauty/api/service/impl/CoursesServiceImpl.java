package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.dto.CoursesDTO;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.repository.AvatarCoursesRepository;
import com.mm.beauty.api.repository.CoursesRepository;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.CoursesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CoursesServiceImpl implements CoursesService {

    public static final Logger LOG = LoggerFactory.getLogger(CoursesServiceImpl.class);

    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    private final AvatarCoursesRepository avatarCoursesRepository;

    @Autowired
    public CoursesServiceImpl(UserRepository userRepository, CoursesRepository coursesRepository, AvatarCoursesRepository avatarCoursesRepository) {
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
        this.avatarCoursesRepository = avatarCoursesRepository;
    }

    @Override
    public Courses createCourse(CoursesDTO coursesDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Courses courses = new Courses();
        return null;
    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }


}
