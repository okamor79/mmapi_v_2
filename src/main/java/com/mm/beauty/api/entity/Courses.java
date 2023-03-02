package com.mm.beauty.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.CStatus;
import lombok.*;

import com.mm.beauty.api.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name="courses")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, nullable = false)
    private String uniqueCode;
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
    @Lob
    private String urlCourseVideo;
    @Lob
    private String urlCoursePreview;

    private double price;
    private double discount = 1;
    private String promoCode;
    @Column(columnDefinition = "text")
    private String fullDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private CStatus status = CStatus.COURSE_ENABLE;

    @PrePersist
    protected void onCreate() {
        this.registerDate = LocalDateTime.now();
    }
}
