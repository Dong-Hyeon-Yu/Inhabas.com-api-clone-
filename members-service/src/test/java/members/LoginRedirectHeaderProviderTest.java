package members;

import security.domain.Role;
import members.domain.usecase.login.HttpOriginProvider;
import members.domain.usecase.login.LoginRedirectHeaderParamProvider;
import org.mockito.*;
import security.domain.AuthUserDetail;
import security.domain.TokenService;
import security.jwtUtils.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LoginRedirectHeaderProviderTest {

    @InjectMocks
    private LoginRedirectHeaderParamProvider provider;

    @Mock
    private HttpOriginProvider httpOriginProvider;

    @Spy
    private JwtTokenProvider tokenProvider;

    @Mock
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private AuthUserDetail authUserDetail;

    private static final String TEMPORARY_REDIRECT_URL = "%s/login/success";
    private static final String LOGIN_SUCCESS_REDIRECT_URI = "/";
    private static final String SIGNUP_REQUIRED_REDIRECT_URI = "/signUp";


    @Test
    public void prepareLoginRedirectHeaderTest() throws URISyntaxException {

        given(httpOriginProvider.getOrigin(any(HttpServletRequest.class))).willReturn(new StringBuffer("https://www.inhabas.com"));

        //when
        HttpHeaders httpHeaders = provider.prepareLoginRedirectHeader(request, TEMPORARY_REDIRECT_URL, LOGIN_SUCCESS_REDIRECT_URI, authUserDetail, Role.BASIC_MEMBER.toString());

        //then
        assertThat(httpHeaders.getLocation().getPath()).isEqualTo("/login/success");
        assertThat(httpHeaders.getLocation().getQuery()).contains("access_token=", "refresh_token=", "expires_in=", "profile_image_url=", "redirect_uri=/");
    }

    @Test
    public void prepareSignUpRedirectHeaderTest() throws URISyntaxException {

        given(httpOriginProvider.getOrigin(any(HttpServletRequest.class))).willReturn(new StringBuffer("https://www.inhabas.com"));

        //when
        HttpHeaders httpHeaders = provider.prepareSignUpRedirectHeader(request, TEMPORARY_REDIRECT_URL, SIGNUP_REQUIRED_REDIRECT_URI, authUserDetail);

        //then
        assertThat(httpHeaders.getLocation().getPath()).isEqualTo("/login/success");
        assertThat(httpHeaders.getLocation().getQuery()).contains("access_token=", "refresh_token=&", "expires_in=", "profile_image_url=&", "redirect_uri=/signUp");
    }

}