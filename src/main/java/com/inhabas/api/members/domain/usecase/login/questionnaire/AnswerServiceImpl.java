package com.inhabas.api.members.domain.usecase.login.questionnaire;

import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.MemberRepository;
import com.inhabas.api.members.domain.entity.questionaire.Answer;
import com.inhabas.api.members.domain.entity.questionaire.AnswerRepository;
import com.inhabas.api.members.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    public List<Answer> saveAnswers(List<AnswerDto> submittedAnswers, Integer memberId) {

        Member currentMember = memberRepository.getById(memberId);

        List<Answer> answers = submittedAnswers.stream()
                .map(a -> new Answer(currentMember, a.getQuestionNo(), a.getAnswer()))
                .collect(Collectors.toList());

        return answerRepository.saveAll(answers);
    }

    public List<AnswerDto> getAnswers(Integer memberId) {

        return answerRepository.findByMember_Id(memberId).stream()
                .map(a-> new AnswerDto(a.getQuestionNo(), a.getAnswer()))
                .collect(Collectors.toList());
    }
}
