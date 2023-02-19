package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.URoles;
import com.mm.beauty.api.entity.enums.UStatus;
import com.mm.beauty.api.exceptions.UserExistException;
import com.mm.beauty.api.payload.request.SignupRequest;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUserName(userIn.getUserName());
        user.setPhone(userIn.getPhone());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getUserRoles().add(URoles.ROLE_USER);
        user.getUserStatus().add(UStatus.USER_ENABLE);
        try {
            LOG.info("User created {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error user registration {}", e.getMessage());
            throw new UserExistException("User already exist");
        }
    }

    @Override
    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setPhone(userDTO.getPhone());
        user.setUserName(userDTO.getUserName());
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }
}
