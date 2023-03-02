package com.mm.beauty.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.CStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoursesDTO {

    private Long id;
    private String uniqueCode;
    private String courseName;
    private String description;
    private String fullDescription;
    private Long avatarId;
    private String urlCourseVideo;
    private String urlCoursePreview;
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int dayAccess;

    private CStatus status;

}
