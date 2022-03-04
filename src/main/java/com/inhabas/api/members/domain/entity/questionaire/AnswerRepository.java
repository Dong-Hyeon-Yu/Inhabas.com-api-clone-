package com.inhabas.api.members.domain.entity.questionaire;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByMember_Id(Integer memberId);
}
