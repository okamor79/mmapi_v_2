package com.mm.beauty.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.OStatus;
import lombok.*;

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
public class OrdersList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Courses courseId;

    private String orderId;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime datePayment;

    private boolean payCheck;

    private String checkCode;

    @ElementCollection(targetClass = OStatus.class)
    @CollectionTable(name = "order_status", joinColumns = @JoinColumn("order_id"))
    private Set<OStatus> status = new HashSet<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private LocalDateTime expireDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

}
