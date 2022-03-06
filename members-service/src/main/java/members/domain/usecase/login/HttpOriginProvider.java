package members.domain.usecase.login;

import javax.servlet.http.HttpServletRequest;

public interface HttpOriginProvider {

    StringBuffer getOrigin(HttpServletRequest request);

}
