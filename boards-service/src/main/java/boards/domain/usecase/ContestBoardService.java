package boards.domain.usecase;

import boards.dto.contest.DetailContestBoardDto;
import boards.dto.contest.ListContestBoardDto;
import boards.dto.contest.SaveContestBoardDto;
import boards.dto.contest.UpdateContestBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestBoardService {
    Integer write(Integer memberId, SaveContestBoardDto dto);

    Integer update(Integer memberId, UpdateContestBoardDto dto);

    void delete(Integer id);

    DetailContestBoardDto getBoard(Integer id);

    Page<ListContestBoardDto> getBoardList(Integer menuId, Pageable pageable);

}
