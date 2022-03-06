package members.domain.usecase.login;

import security.domain.Role;
import security.domain.AuthUserDetail;
import security.domain.RefreshToken;
import security.domain.TokenService;
import security.jwtUtils.TokenDto;
import security.jwtUtils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 토큰을 uri parameter 로 넘김
 */
@Service
@RequiredArgsConstructor
public class LoginRedirectHeaderParamProvider implements LoginRedirectHeaderProvider {

    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final HttpOriginProvider originProvider;

    @Override
    public HttpHeaders prepareLoginRedirectHeader(HttpServletRequest request, String temporaryRedirectUri, String permanentRedirectUri, AuthUserDetail authUserDetail, String memberRole) throws URISyntaxException {
        TokenDto jwtToken = tokenProvider.createJwtToken(authUserDetail.getId(), memberRole, null); // 추후 팀 작업 시 변경해야함.
        tokenService.saveRefreshToken(new RefreshToken(jwtToken.getRefreshToken()));

        return getRedirectHttpHeaders(temporaryRedirectUri, originProvider.getOrigin(request),
                jwtToken.getAccessToken(), jwtToken.getRefreshToken(), String.valueOf(jwtToken.getExpiresIn()), authUserDetail.getProfileImageUrl(), permanentRedirectUri);
    }

    @Override
    public HttpHeaders prepareSignUpRedirectHeader(HttpServletRequest request, String temporaryRedirectUri, String permanentRedirectUri, AuthUserDetail authUserDetail) throws URISyntaxException {
        /* 회원가입 필요 */

        TokenDto jwtToken = tokenProvider.createJwtToken(authUserDetail.getId(), Role.ANONYMOUS.toString(), null);

        return getRedirectHttpHeaders(temporaryRedirectUri, originProvider.getOrigin(request),
                jwtToken.getAccessToken(), "", String.valueOf(jwtToken.getExpiresIn()), "", permanentRedirectUri);
    }

    private HttpHeaders getRedirectHttpHeaders(String redirectUrl, StringBuffer origin, String accessToken, String refreshToken, String expiresIn, String profileImageUrl, String permanentRedirectUri) throws URISyntaxException {

        URI redirectUri = new URI(
                String.format(redirectUrl, origin)
                        + String.format("?access_token=%s&refresh_token=%s&expires_in=%s&profile_image_url=%s&redirect_uri=%s", accessToken, refreshToken, expiresIn, profileImageUrl, permanentRedirectUri));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return httpHeaders;
    }
}
