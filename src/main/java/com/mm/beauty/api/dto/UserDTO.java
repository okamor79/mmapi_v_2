package com.mm.beauty.api.dto;

import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.enums.URoles;
//import com.mm.beauty.api.entity.enums.UStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String phone;

    private List<GrantedAuthority> authorities;
}
