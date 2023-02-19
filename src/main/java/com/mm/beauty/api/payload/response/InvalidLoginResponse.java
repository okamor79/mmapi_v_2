package com.mm.beauty.api.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String email;
    private String password;

    public InvalidLoginResponse() {
        this.email = "Invaild email";
        this.password = "Invalid password";
    }

}
