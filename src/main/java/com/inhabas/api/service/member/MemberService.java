package com.inhabas.api.service.member;

import com.inhabas.api.domain.member.Member;
import com.inhabas.api.dto.signUp.StudentSignUpForm;
import com.inhabas.api.security.domain.AuthUser;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member signUp(AuthUser authUser, StudentSignUpForm signUpForm);

    StudentSignUpForm loadSignUpForm(Integer memberId, String email);

    List<Member> findMembers();

    Optional<Member> findById(Integer memberId);

    Optional<Member> updateMember(Member member);
}
