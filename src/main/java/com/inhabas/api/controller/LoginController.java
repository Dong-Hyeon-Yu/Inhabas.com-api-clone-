package com.inhabas.api.controller;

import com.inhabas.api.security.domain.RefreshToken;
import com.inhabas.api.security.domain.RefreshTokenService;
import com.inhabas.api.security.jwtUtils.TokenDto;
import com.inhabas.api.security.jwtUtils.TokenProvider;
import com.inhabas.api.security.oauth2.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private static final String LOGIN_SUCCESS_REDIRECT_URL = "%s/login/success?accessToken=%s&refreshToken=%s&expiresIn=%d";
    private static final String SIGNUP_REQUIRED_REDIRECT_URL = "%s/signUp?provider=%s&email=%s";

    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    /* token authentication */

    @GetMapping("${authenticate.oauth2-success-handle-url}")
    @Operation(description = "로그인 성공하여 최종적으로 accessToken, refreshToken 을 발행한다.", hidden = true)
    public void successLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal) throws IOException {

        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) principal;
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        StringBuffer origin = this.getOrigin(request);

        if (oAuth2User.isAlreadyJoined()) {

            /* 유저 권한 들고오기 (추후 작업이 필요함.)
            - role 은 (미승인회원, 일반회원, 교수, 회장단, 회장) 과 같이 수직구조의 권한 => 상대적으로 덜 변함. => enum 타입
            - team 은 (총무팀, 운영팀, 기획팀, IT팀, 회계) 등 과 같은 수평구조의 권한 => 시간에 따라 더 변하기 쉬움 => db 연동
            */
            TokenDto jwtToken = tokenProvider.createJwtToken(oAuth2User.getAuthUserId(), "ROLE_MEMBER", null);
            refreshTokenService.save(new RefreshToken(jwtToken.getRefreshToken()));

            request.getSession().invalidate(); // 프론트 단에서 브라우저 쿠키 JSESSIONID, XSRF-TOKEN 지우는 게 좋을 듯. 상관없긴 한디.

            response.sendRedirect(
                    String.format(LOGIN_SUCCESS_REDIRECT_URL, origin,
                            jwtToken.getAccessToken(), jwtToken.getRefreshToken(), jwtToken.getExpiresIn()));
        }
        else {

            /* 회원가입 필요 */

            String provider = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            String email = principal.getName();

            response.sendRedirect(
                    String.format(SIGNUP_REQUIRED_REDIRECT_URL, origin, provider, email));
        }
    }


    @GetMapping("${authenticate.oauth2-failure-handle-url}")
    @Operation(hidden = true)
    public ResponseEntity<?> failToLogin() {
        Map<String, String> message = new HashMap<>() {{
            put("message","fail_to_social_login");
        }};
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }



    private StringBuffer getOrigin(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            // Work around java.net.URL bug
            port = 80;
        }

        url.append(scheme);
        url.append("://");
        url.append(request.getRemoteHost()); // 배포시에는 getServerName 사용해야함
        if ((scheme.equals("http") && (port != 80))
                || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }

        return url;
    }
}