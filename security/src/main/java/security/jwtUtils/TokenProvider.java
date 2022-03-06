package security.jwtUtils;

import io.jsonwebtoken.JwtException;
import security.jwtUtils.TokenDecodedInfo;
import security.jwtUtils.TokenDto;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface TokenProvider {

    TokenDecodedInfo authenticate(String token) throws JwtException;

    String resolveToken(HttpServletRequest request) ;

    TokenDto reissueAccessTokenUsing(String refreshToken) throws JwtException;

    TokenDto createJwtToken(Integer authUserId, String role, Set<String> teams);
}
