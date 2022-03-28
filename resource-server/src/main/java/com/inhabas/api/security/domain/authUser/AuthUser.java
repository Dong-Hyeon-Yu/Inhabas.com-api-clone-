package com.inhabas.api.security.domain.authUser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "auth_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private AuthUserRole role;

    private LocalDateTime lastLogin;

    private LocalDateTime joinDate;

    public AuthUser(Integer profileId) {
        this.isActive = true;
        this.profileId = profileId;
        this.lastLogin = LocalDateTime.now();
        this.role = AuthUserRole.ANONYMOUS;
    }

    public void completeSignUp() {
        this.role = AuthUserRole.NOT_APPROVED_MEMBER;
    }

    public void setApproved() {
        this.role = AuthUserRole.BASIC_MEMBER;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
