package members.domain.usecase.signUp;

import members.dto.QuestionnaireDto;

import java.util.List;

public interface QuestionnaireService {

    List<QuestionnaireDto> getQuestionnaire();
}
