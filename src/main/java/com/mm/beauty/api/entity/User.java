package com.mm.beauty.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mm.beauty.api.entity.enums.URoles;
import com.mm.beauty.api.entity.enums.UStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    @Column(nullable = false, name = "user_name")
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = UStatus.class)
    @CollectionTable(name = "user_status", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UStatus> userStatus = new HashSet<>();

    @ElementCollection(targetClass = URoles.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<URoles> userRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Sales> orderList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Courses> CoursesList = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    /**
     * SECURITY
     */

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
