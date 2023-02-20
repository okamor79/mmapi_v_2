package com.mm.beauty.api.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String userName;
    private String password;

    public InvalidLoginResponse() {
        this.userName = "Invaild username";
        this.password = "Invalid password";
    }

}
