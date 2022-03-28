package com.inhabas.api.security.domain.socialAccount;

import com.inhabas.api.security.domain.authUser.AuthUser;
import com.inhabas.api.security.domain.socialAccount.type.Provider;
import com.inhabas.api.security.domain.socialAccount.type.UID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "social_account",
        uniqueConstraints = { @UniqueConstraint(name = "unique_socialaccount", columnNames = {"provider", "uid"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Provider provider;

    @Embedded
    private UID uid;

    private LocalDateTime lastLogin;

    private LocalDateTime connectDate;

    private String extraData;

    @Column(length = 1000)
    private String profileImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id", foreignKey = @ForeignKey(name = "fk_to_auth_user"))
    private AuthUser authUser;

    public SocialAccount(Provider provider, UID uid, LocalDateTime lastLogin, String extraData) {
        this.provider = provider;
        this.uid = uid;
        this.lastLogin = lastLogin;
        this.extraData = extraData;
    }

    public SocialAccount(OAuth2UserRequest request) {

    }

    public SocialAccount connectTo(AuthUser authUser) {
        this.connectDate = LocalDateTime.now();
        this.authUser = authUser;
        return this;
    }

    public String getUid() {
        return uid.getValue();
    }

    public String getProvider() {
        return provider.getValue();
    }

    public SocialAccount setLastLoginTime(LocalDateTime time) {
        this.lastLogin = time;
        return this;
    }
}
