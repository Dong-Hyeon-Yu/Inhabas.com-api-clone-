package com.inhabas.api.boards.domain.usecase;

import com.inhabas.api.boards.dto.comment.CommentDetailDto;
import com.inhabas.api.boards.dto.comment.CommentSaveDto;
import com.inhabas.api.boards.dto.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    List<CommentDetailDto> getComments(Integer boardId);

    Integer create(CommentSaveDto commentSaveDto);

    Integer update(CommentUpdateDto commentUpdateDto);

    void delete(Integer id);
}
