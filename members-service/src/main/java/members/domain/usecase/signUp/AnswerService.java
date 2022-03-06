package members.domain.usecase.signUp;

import members.domain.entity.questionaire.Answer;
import members.dto.AnswerDto;

import java.util.List;

public interface AnswerService {

    List<Answer> saveAnswers(List<AnswerDto> submittedAnswers, Integer memberId);

    List<AnswerDto> getAnswers(Integer memberId);
}
