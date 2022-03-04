package com.inhabas.api.members.domain.usecase.login.questionnaire;

import com.inhabas.api.members.dto.QuestionnaireDto;

import java.util.List;

public interface QuestionnaireService {

    List<QuestionnaireDto> getQuestionnaire();
}
