package com.mm.beauty.api.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invaild username";
        this.password = "Invalid password";
    }

}
