package com.inhabas.security.argumentResolver;

import com.inhabas.api.security.utils.argumentResolver.AuthUserArgumentResolver;
import com.inhabas.api.security.domain.authUser.AuthUser;
import com.inhabas.api.security.domain.authUser.AuthUserDetail;
import com.inhabas.api.security.utils.jwtUtils.JwtAuthenticationToken;
import com.inhabas.api.security.utils.oauth2.CustomOAuth2User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ArgumentResolverTest {

    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

    @InjectMocks
    private AuthUserArgumentResolver authUserArgumentResolver;

    @AfterEach
    public void clearSecurityContest() {
        SecurityContextHolder.clearContext();
    }


    @DisplayName("인증이 이루어지지 않았으면, null을 반환한다.")
    @Test
    public void returnNullIfNotAuthenticated() {
        //given
        doReturn(AuthUserDetail.class).when(parameter).getParameterType();

        //when
        AuthUserDetail invalidUser = (AuthUserDetail) authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        //then
        assertThat(invalidUser).isNull();
    }

    @DisplayName("Jwt token 인증 후 authUserDetail 을 컨트롤러 파라미터로 주입한다.")
    @Test
    public void successToInjectJwtTokenAuthenticatedAuthUserIntoArguments() {
        //given
        //기존 회원 정보
        Integer authUserId = 1;
        AuthUser expectedUser = new AuthUser(12171652);
        ReflectionTestUtils.setField(expectedUser, "id", authUserId);

        // jwt 토큰 인증 결과
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(AuthUserDetail.convert(expectedUser), Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")));

        //authentication 객체를 컨텍스트에 설정. 최종 인증 끝
        SecurityContextHolder.getContext().setAuthentication(authentication);

        doReturn(AuthUserDetail.class).when(parameter).getParameterType();

        //when
        AuthUserDetail authenticatedUser = (AuthUserDetail) authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        //then
        assertThat(authenticatedUser).isNotNull();
        assertThat(authenticatedUser.getId()).isEqualTo(authUserId);
    }

    @DisplayName("OAuth2 인증 후 authUserDetail 를 컨트롤러 파라미터로 주입한다.")
    @Test
    public void successToInjectOAuth2AuthenticatedAuthUserIntoArguments() {

        //given
        //기존 회원 정보
        Integer authUserId = 1;
        HashMap<String, Object> attributes = new HashMap<>() {{
            put("name", "김아무개");
            put("email", "my@email.com");
            put("picture", "https://my_photo.com");
        }};
        //OAuth2 인증 결과
        CustomOAuth2User principal = new CustomOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email",
                AuthUserDetail.builder()
                        .id(authUserId)
                        .profileId(12171652)
                        .hasJoined(true)
                        .isActive(true)
                        .build());
        //OAuth2 인증결과를 authentication 객체에 담는다.
        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                principal,
                principal.getAuthorities(),
                "google");
        //authentication 객체를 컨텍스트에 설정. 최종 인증 끝
        SecurityContextHolder.getContext().setAuthentication(token);

        doReturn(AuthUserDetail.class).when(parameter).getParameterType();

        //when
        AuthUserDetail authenticatedUser = (AuthUserDetail) authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        //then
        assertThat(authenticatedUser).isNotNull();
        assertThat(authenticatedUser.getId()).isEqualTo(authUserId);
    }

    @DisplayName("Integer Id를 받아온 경우, 인증이 이루어지지 않았으면 null을 반환한다.")
    @Test
    public void returnNullIfNotAuthenticatedWhenIntegerParameter() {
        //given
        doReturn(Integer.class).when(parameter).getParameterType();

        //when
        Object invalidUser = authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        //then
        assertThat(invalidUser).isNull();
    }

    @DisplayName("Jwt 토큰 인증 후 Profile Id를 반환한다.")
    @Test
    public void successToInjectJwtTokenIntegerIdIntoArguments() {
        //given
        //기존 회원 정보
        Integer authUserId = 1;
        AuthUser expectedUser = new AuthUser(12171652);
        ReflectionTestUtils.setField(expectedUser, "id", authUserId);

        // jwt 토큰 인증 결과
        JwtAuthenticationToken authentication =
                new JwtAuthenticationToken(AuthUserDetail.convert(expectedUser), Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")));

        //authentication 객체를 컨텍스트에 설정. 최종 인증 끝
        SecurityContextHolder.getContext().setAuthentication(authentication);

        doReturn(Integer.class).when(parameter).getParameterType();

        //when
        Object profileId = authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        // then
        assertThat(profileId).isNotNull();
        assertThat(profileId).isEqualTo(12171652);
        assertThat(profileId).isInstanceOf(Integer.class);
    }

    @DisplayName("OAuth2 인증 후 Profile Id를 반환한다.")
    @Test
    public void successToInjectIntegerIdIntoArgumentsAndReturnProfileId() {
        //given
        //기존 회원 정보
        Integer authUserId = 1;
        HashMap<String, Object> attributes = new HashMap<>() {{
            put("name", "김아무개");
            put("email", "my@email.com");
            put("picture", "https://my_photo.com");
        }};
        //OAuth2 인증 결과
        CustomOAuth2User principal = new CustomOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email",
                AuthUserDetail.builder()
                        .id(authUserId)
                        .profileId(12171652)
                        .hasJoined(true)
                        .isActive(true)
                        .build());
        //OAuth2 인증결과를 authentication 객체에 담는다.
        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                principal,
                principal.getAuthorities(),
                "google");
        //authentication 객체를 컨텍스트에 설정. 최종 인증 끝
        SecurityContextHolder.getContext().setAuthentication(token);

        doReturn(Integer.class).when(parameter).getParameterType();

        // when
        Object profileId = authUserArgumentResolver.resolveArgument(parameter, null, request, null);

        // then
        assertThat(profileId).isNotNull();
        assertThat(profileId).isEqualTo(principal.getAuthUserDetail().getProfileId());
        assertThat(profileId).isInstanceOf(Integer.class);
    }
}
