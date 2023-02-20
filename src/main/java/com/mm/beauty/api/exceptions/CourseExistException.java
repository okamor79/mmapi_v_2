package com.mm.beauty.api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseExistException extends RuntimeException {
    public CourseExistException(String message) {
        super(message);
    }
}