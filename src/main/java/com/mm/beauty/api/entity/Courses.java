package com.mm.beauty.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.CStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, nullable = false)
    private String uniqCode;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime registerDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime endDate;
    private int dayAccess;
    private String urlCourseVideo;
    private String urlCoursePreview;
    private Long avatarId;
    private double price;
    private double discount = 1;
    private String promoCode;
    @Column(columnDefinition = "text")
    private String fullDescription;
    private Set<CStatus> courseStatus = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.registerDate = LocalDateTime.now();
    }
}
