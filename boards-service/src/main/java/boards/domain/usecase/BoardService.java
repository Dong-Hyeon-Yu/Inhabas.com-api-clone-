package boards.domain.usecase;

import boards.dto.board.BoardDto;
import boards.dto.board.SaveBoardDto;
import boards.dto.board.UpdateBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    Integer write(Integer memberId, SaveBoardDto saveBoardDto);

    Integer update(Integer memberId, UpdateBoardDto updateBoardDto);

    void delete(Integer id);

    BoardDto getBoard(Integer boardId);

    Page<BoardDto> getBoardList(Integer menuId, Pageable pageable);

}
