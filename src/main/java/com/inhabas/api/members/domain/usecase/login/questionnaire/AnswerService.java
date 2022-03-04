package com.inhabas.api.members.domain.usecase.login.questionnaire;

import com.inhabas.api.members.domain.entity.questionaire.Answer;
import com.inhabas.api.members.dto.AnswerDto;

import java.util.List;

public interface AnswerService {

    List<Answer> saveAnswers(List<AnswerDto> submittedAnswers, Integer memberId);

    List<AnswerDto> getAnswers(Integer memberId);
}
