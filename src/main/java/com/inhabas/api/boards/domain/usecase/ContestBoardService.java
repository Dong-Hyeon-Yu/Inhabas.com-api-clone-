package com.inhabas.api.boards.domain.usecase;

import com.inhabas.api.boards.dto.contest.DetailContestBoardDto;
import com.inhabas.api.boards.dto.contest.ListContestBoardDto;
import com.inhabas.api.boards.dto.contest.SaveContestBoardDto;
import com.inhabas.api.boards.dto.contest.UpdateContestBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestBoardService {
    Integer write(Integer memberId, SaveContestBoardDto dto);

    Integer update(Integer memberId, UpdateContestBoardDto dto);

    void delete(Integer id);

    DetailContestBoardDto getBoard(Integer id);

    Page<ListContestBoardDto> getBoardList(Integer menuId, Pageable pageable);

}
