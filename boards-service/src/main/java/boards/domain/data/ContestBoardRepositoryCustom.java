package boards.domain.data;

import boards.dto.contest.DetailContestBoardDto;
import boards.dto.contest.ListContestBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContestBoardRepositoryCustom {

    Optional<DetailContestBoardDto> findDtoById(Integer id);

    Page<ListContestBoardDto> findAllByMenuId(Integer menuId, Pageable pageable);

}
