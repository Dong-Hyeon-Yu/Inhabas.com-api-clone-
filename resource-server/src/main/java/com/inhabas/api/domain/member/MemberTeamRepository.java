package com.inhabas.api.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTeamRepository extends JpaRepository<MemberTeam, Integer> {

    void deleteByMemberIdAndTeamId(Integer memberId, Integer teamId);
}
