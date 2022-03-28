package com.inhabas.api.service.member;

import com.inhabas.api.domain.member.Member;
import com.inhabas.api.security.domain.authUser.AuthUserRole;
import com.inhabas.api.dto.member.ContactDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void save(Member member);

    List<Member> findMembers();

    Member findById(Integer memberId);

    Optional<Member> updateMember(Member member);

//    void changeRole(Integer memberId, AuthUserRole role);
//
//    ContactDto getChiefContact();
}
