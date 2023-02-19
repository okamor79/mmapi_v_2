package com.mm.beauty.api.entity;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class Comment {

    private Long id;
    private Long userId;
    private Long courseId;
    private String message;
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
