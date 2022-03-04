package com.inhabas.api.boards.domain.entity.contest;

import com.inhabas.api.boards.dto.contest.DetailContestBoardDto;
import com.inhabas.api.boards.dto.contest.ListContestBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContestBoardRepositoryCustom {

    Optional<DetailContestBoardDto> findDtoById(Integer id);

    Page<ListContestBoardDto> findAllByMenuId(Integer menuId, Pageable pageable);

}
