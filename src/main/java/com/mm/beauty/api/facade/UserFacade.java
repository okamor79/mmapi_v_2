package com.mm.beauty.api.facade;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO UsetToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setURoles(user.getUserRoles());
        userDTO.setUStatus(user.getUserStatus());
        return userDTO;
    }
}
