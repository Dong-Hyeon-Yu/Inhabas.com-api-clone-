package boards.domain.usecase;

import boards.dto.comment.CommentDetailDto;
import boards.dto.comment.CommentSaveDto;
import boards.dto.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    List<CommentDetailDto> getComments(Integer boardId);

    Integer create(CommentSaveDto commentSaveDto);

    Integer update(CommentUpdateDto commentUpdateDto);

    void delete(Integer id);
}
