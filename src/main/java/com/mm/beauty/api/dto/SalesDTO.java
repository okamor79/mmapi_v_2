package com.mm.beauty.api.dto;

import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.enums.OStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SalesDTO {

    private Long id;

    private Long userId;

    private String fullUsername;

    private String courseName;

    private Long courseId;

    private String orderId;

    private double orderAmount;

    private LocalDateTime datePayment;

    private String checkCode;

    private String status;

    private LocalDateTime expireDate;

}
