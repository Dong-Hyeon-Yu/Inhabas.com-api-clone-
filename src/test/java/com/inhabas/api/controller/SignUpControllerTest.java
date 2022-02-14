package com.inhabas.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inhabas.api.annotataion.WithMockJwtAuthenticationToken;
import com.inhabas.api.domain.member.type.wrapper.Role;
import com.inhabas.api.dto.signUp.StudentSignUpForm;
import com.inhabas.api.service.member.MemberService;
import com.inhabas.api.testConfig.DefaultWebMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DefaultWebMvcTest(SignUpController.class)
public class SignUpControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("회원가입 도중 개인정보를 저장한다.")
    @Test
    @WithMockJwtAuthenticationToken
    public void 회원가입_도중_개인정보를_저장한다() throws Exception {
        //given
        StudentSignUpForm signUpForm = StudentSignUpForm.builder()
                .name("유동현")
                .grade(3)
                .semester(2)
                .email("my@email.com")
                .major("컴퓨터공학과")
                .phoneNumber("010-0000-1111")
                .studentId(11112222)
                .isProfessor(false)
                .build();

        mvc.perform(post("/signUp/student")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpForm)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @DisplayName("임시 저장했던 개인정보를 불러온다.")
    @Test
    @WithMockJwtAuthenticationToken(memberId = 12171652, memberRole = Role.ANONYMOUS)
    public void 임시저장했던_개인정보를_불러온다() throws Exception {
        //given
        StudentSignUpForm expectedSavedForm = StudentSignUpForm.builder()
                .studentId(12171652)
                .name("홍길동")
                .grade(1)
                .semester(1)
                .major("의예과")
                .phoneNumber("010-1234-5678")
                .isProfessor(false)
                .email("my@email.com")
                .build();

        given(memberService.loadSignUpForm(12171652, "my@email.com")).willReturn(expectedSavedForm);

        //when
        String response = mvc.perform(get("/signUp/student"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        //then
        assertThat(response).isEqualTo(objectMapper.writeValueAsString(expectedSavedForm));

    }
}
