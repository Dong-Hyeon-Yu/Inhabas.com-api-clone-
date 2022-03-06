package boards.domain.data;

import boards.dto.comment.CommentDetailDto;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentDetailDto> findAllByParentBoardIdOrderByCreated(Integer boardId);
}
