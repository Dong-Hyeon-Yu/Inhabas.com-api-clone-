package com.inhabas.api.members.domain.usecase;

import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.type.wrapper.Phone;
import com.inhabas.api.members.domain.entity.member.type.wrapper.Role;
import com.inhabas.api.members.dto.DetailSignUpDto;
import com.inhabas.api.members.dto.ProfessorSignUpDto;
import com.inhabas.api.members.dto.StudentSignUpDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Member saveSignUpForm(StudentSignUpDto signUpForm);

    Member saveSignUpForm(ProfessorSignUpDto signUpForm);

    DetailSignUpDto loadSignUpForm(Integer memberId, String email);

    List<Member> findMembers();

    Member findById(Integer memberId);

    Optional<Member> updateMember(Member member);

    void changeRole(Integer memberId, Role role);

    boolean isDuplicatedId(Integer memberId);

    boolean isDuplicatedPhoneNumber(Phone phoneNumber);
}
