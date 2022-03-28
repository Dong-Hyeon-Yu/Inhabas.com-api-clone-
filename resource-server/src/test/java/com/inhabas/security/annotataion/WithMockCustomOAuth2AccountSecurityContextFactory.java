package com.inhabas.security.annotataion;

import com.inhabas.api.security.domain.authUser.AuthUserDetail;
import com.inhabas.api.security.utils.oauth2.CustomOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashMap;
import java.util.List;


/**
 * WithMockCustomOAuth2Account 어노테이션 정보를 기반으로 SecurityContext 를 설정한다.
 * @see WithMockCustomOAuth2Account
 */
public class WithMockCustomOAuth2AccountSecurityContextFactory
    implements WithSecurityContextFactory<WithMockCustomOAuth2Account> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2Account customOAuth2Account) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("username", customOAuth2Account.name());
        attributes.put("email", customOAuth2Account.email());
        attributes.put("picture", customOAuth2Account.picture());

        CustomOAuth2User principal = new CustomOAuth2User(
                List.of(new SimpleGrantedAuthority("USER")),
                attributes,
                "email",
                AuthUserDetail.builder()
                        .id(customOAuth2Account.authUserId())
                        .profileId(customOAuth2Account.profileId())
                        .role(null)
                        .isActive(customOAuth2Account.isActive())
                        .build());

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                principal,
                principal.getAuthorities(),
                customOAuth2Account.provider());

        context.setAuthentication(token);
        return context;
    }
}
