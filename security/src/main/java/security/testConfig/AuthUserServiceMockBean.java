package security.testConfig;

import security.domain.AuthUserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class AuthUserServiceMockBean {

    @MockBean
    public AuthUserService authUserService;
}
