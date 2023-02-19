package com.mm.beauty.api.payload.request;

import com.mm.beauty.api.annotation.PasswordMatches;
import com.mm.beauty.api.annotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "User need email")
    @NotBlank
    @ValidEmail
    private String email;
    @NotEmpty
    private String name;
    @NotBlank
    private String phone;
    @NotEmpty
    @Size(min = 6)
    private String password;
    private String confirmPassword;

}
