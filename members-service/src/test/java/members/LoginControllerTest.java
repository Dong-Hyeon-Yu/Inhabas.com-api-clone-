package members;

import com.inhabas.api.members.domain.usecase.login.LoginService;
import com.inhabas.api.security.annotataion.WithMockCustomOAuth2Account;
import com.inhabas.testConfig.DefaultWebMvcTest;
import members.web.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DefaultWebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    @WithMockCustomOAuth2Account
    public void OAuth2_인증이_성공적으로_완료됐다() throws Exception {

        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("http://localhost:8080/login/success"));
        BDDMockito.given(loginService.prepareRedirectHeader(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(httpHeaders);

        //when
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/login/test-success"))
                .andExpect(MockMvcResultMatchers.status().isSeeOther())  // 303
                .andExpect(MockMvcResultMatchers.header().string("location", "http://localhost:8080/login/success"))
                .andReturn();

        BDDMockito.then(loginService).should(Mockito.times(1)).prepareRedirectHeader(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

}
