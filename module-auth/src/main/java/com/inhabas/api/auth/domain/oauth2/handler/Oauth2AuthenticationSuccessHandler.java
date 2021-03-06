package com.inhabas.api.auth.domain.oauth2.handler;

import static com.inhabas.api.auth.domain.oauth2.cookie.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URL_PARAM_COOKIE_NAME;

import com.inhabas.api.auth.AuthProperties;
import com.inhabas.api.auth.domain.exception.UnauthorizedRedirectUrlException;
import com.inhabas.api.auth.domain.oauth2.cookie.CookieUtils;
import com.inhabas.api.auth.domain.oauth2.cookie.HttpCookieOAuth2AuthorizationRequestRepository;
import com.inhabas.api.auth.domain.oauth2.userInfo.OAuth2UserInfoFactory;
import com.inhabas.api.auth.domain.token.TokenProvider;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AuthProperties authProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = this.determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        this.clearAuthenticationAttributes(request);
        this.httpCookieOAuth2AuthorizationRequestRepository.clearCookies(request, response);
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * @param authentication ?????? ????????? ??????
     * @return ?????? ????????? ???????????? access ????????? ????????????, ????????? ???????????? ?????? redirect_uri(??????????????? ????????? ???)??? ????????? ??????.
     * ???????????? ????????? ????????????({@link AuthProperties})??? ????????? default redirect url ??? ??????
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String targetUrl = CookieUtils.resolveCookie(request, REDIRECT_URL_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(authProperties.getOauth2().getDefaultRedirectUri());

        if (notAuthorized(targetUrl)) {
            /* ????????? AuthenticationException ??? ???????????? ????????? AbstractAuthenticationProcessingFilter.doFilter ?????? ????????????.
             *   - AbstractAuthenticationProcessingFilter.doFilter ????????? try~ catch~ ?????? ??????.
             *   -    -> AbstractAuthenticationProcessingFilter.unsuccessfulAuthentication()
             *   -    -> Oauth2AuthenticationFailureHandler().onAuthenticationFailure()
             * */
            throw new UnauthorizedRedirectUrlException();
        }

        String imageUrl = OAuth2UserInfoFactory.getOAuth2UserInfo((OAuth2AuthenticationToken) authentication)
                .getImageUrl();

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", tokenProvider.createAccessToken(authentication))
                .queryParam("refresh_token", tokenProvider.createRefreshToken(authentication))
                .queryParam("expires_in", tokenProvider.getExpiration())
                .queryParam("image_url", imageUrl)
                .build().toUriString();
    }

    private boolean notAuthorized(String redirectUrl) {
        return !redirectUrl.isBlank() &&
                !authProperties.getOauth2().isAuthorizedRedirectUri(redirectUrl);
    }
}
