package com.inhabas.api.security.domain.authUser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(nullable = false)
    private boolean hasJoined;

    @Column(nullable = false)
    private boolean isActive;

    private LocalDateTime lastLogin;

    private LocalDateTime joinDate;

    public AuthUser(Integer profileId) {
        this.hasJoined = false;
        this.isActive = true;
        this.profileId = profileId;
        this.lastLogin = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean hasJoined() {
        return this.hasJoined;
    }

    public void setJoinFlag() {
        this.hasJoined = true;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
