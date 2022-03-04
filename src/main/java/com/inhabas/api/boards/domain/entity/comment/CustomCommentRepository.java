package com.inhabas.api.boards.domain.entity.comment;

import com.inhabas.api.boards.dto.comment.CommentDetailDto;

import java.util.List;

public interface CustomCommentRepository {

    List<CommentDetailDto> findAllByParentBoardIdOrderByCreated(Integer boardId);
}
