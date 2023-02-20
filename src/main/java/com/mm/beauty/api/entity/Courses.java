package com.mm.beauty.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.CStatus;
import lombok.*;

import com.mm.beauty.api.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Courses {

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
    @Column(columnDefinition = "text")
    private String urlCourseVideo;
    @Column(columnDefinition = "text")
    private String urlCoursePreview;
    private Long avatarId;
    private double price;
    private double discount = 1;
    private String promoCode;
    @Column(columnDefinition = "text")
    private String fullDescription;

    private User user;

    @ElementCollection(targetClass = CStatus.class)
    @CollectionTable(name = "course_status", joinColumns = @JoinColumn(name = "courses_id"))
    private Set<CStatus> courseStatus = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.registerDate = LocalDateTime.now();
    }
}
