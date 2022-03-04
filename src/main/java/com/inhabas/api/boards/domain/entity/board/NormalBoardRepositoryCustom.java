package com.inhabas.api.boards.domain.entity.board;

import com.inhabas.api.boards.dto.board.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NormalBoardRepositoryCustom {

    Page<BoardDto> findAllByMenuId(Integer menuId, Pageable pageable);
    Optional<BoardDto> findDtoById(Integer id);
}
