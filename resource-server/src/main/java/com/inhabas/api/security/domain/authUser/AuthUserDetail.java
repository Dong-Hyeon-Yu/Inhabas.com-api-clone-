package com.inhabas.api.security.domain.authUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This instance includes exactly the same data of AuthUser!
 * After finishing authentication, AuthUser's data is saved as AuthUserDetail into SecurityContext.
 * It's risky to use directly AuthUser because AuthUser is JPA Entity.
 * (whenever use JPA Entity, we should make sure to know what the Entity's status is in PersistenceContext at that time).
 *
 * we never intend to modify AuthUser Entity in normal business logic.
 * (such as "Controllers", "Services", etc.).
 *
 */
@Setter @Getter
public class AuthUserDetail {

    private Integer id;

    private Integer profileId;

    private boolean isActive;

    private AuthUserRole role;

    private String profileImageUrl;

    @Builder
    public AuthUserDetail(Integer id, Integer profileId, boolean isActive, AuthUserRole role) {
        this.id = id;
        this.profileId = profileId;
        this.isActive = isActive;
        this.role = role;
    }

    public static AuthUserDetail convert(AuthUser authUser) {
        return AuthUserDetail.builder()
                .id(authUser.getId())
                .profileId(authUser.getProfileId())
                .isActive(authUser.isActive())
                .role(authUser.getRole())
                .build();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isJoined() {
        return this.role != AuthUserRole.ANONYMOUS && this.isActive;
    }
}
