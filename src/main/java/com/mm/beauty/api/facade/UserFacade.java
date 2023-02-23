package com.mm.beauty.api.facade;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhone(user.getPhone());
        userDTO.setURoles(user.getUserRoles());
//        userDTO.setUStatus(user.getUserStatus());
        return userDTO;
    }
}
