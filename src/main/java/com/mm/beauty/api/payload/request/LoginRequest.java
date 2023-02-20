package com.mm.beauty.api.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Email cannot be empty")
    private String userName;
    @NotEmpty(message = "Password cannot be empty")
    private String password;


}
