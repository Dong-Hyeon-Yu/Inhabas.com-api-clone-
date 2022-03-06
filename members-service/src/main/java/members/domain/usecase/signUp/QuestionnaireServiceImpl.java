package members.domain.usecase.signUp;

import members.domain.entity.questionaire.QuestionnaireRepository;
import members.dto.QuestionnaireDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    @Transactional(readOnly = true)
    public List<QuestionnaireDto> getQuestionnaire() {

        return questionnaireRepository.findAll().stream()
                .map(q->new QuestionnaireDto(q.getNo(), q.getItem()))
                .collect(Collectors.toList());
    }
}
