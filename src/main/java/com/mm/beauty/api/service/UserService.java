package com.mm.beauty.api.service;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.payload.request.SignupRequest;

import java.security.Principal;
import java.util.List;

public interface UserService {

    User createUser(SignupRequest userIn);

    User updateUser(UserDTO userDTO, Principal principal);

    User getUserByPrincipal(Principal principal);

    User getCurrentUser(Principal principal);

    List<User> getUserList();

    User getUserById(Long id);

}
