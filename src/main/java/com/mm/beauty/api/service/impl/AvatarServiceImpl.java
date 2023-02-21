package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.repository.AvatarCoursesRepository;
import com.mm.beauty.api.repository.CoursesRepository;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.AvatarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AvatarServiceImpl implements AvatarService {
    public static final Logger LOG = LoggerFactory.getLogger(AvatarServiceImpl.class);


    private AvatarCoursesRepository avatarCoursesRepository;
    private UserRepository userRepository;
    private CoursesRepository coursesRepository;

    @Autowired
    public AvatarServiceImpl(AvatarCoursesRepository avatarCoursesRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.avatarCoursesRepository = avatarCoursesRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }


}
