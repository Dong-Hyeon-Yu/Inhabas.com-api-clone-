package boards.domain.usecase;

import boards.domain.entity.board.NormalBoard;
import boards.domain.data.NormalBoardRepository;
import boards.domain.entity.board.BoardNotFoundException;
import members.domain.entity.member.Member;
import members.domain.entity.member.MemberRepository;
import menus.domain.entity.Menu;
import menus.domain.data.jpa.MenuRepository;
import boards.dto.board.BoardDto;

import boards.dto.board.SaveBoardDto;
import boards.dto.board.UpdateBoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final NormalBoardRepository boardRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;

    @Override
    public Integer write(Integer memberId, SaveBoardDto saveBoardDto) {
        Menu menu = menuRepository.getById(saveBoardDto.getMenuId());
        Member writer = memberRepository.getById(memberId);
        NormalBoard normalBoard = new NormalBoard(saveBoardDto.getTitle(), saveBoardDto.getContents())
                .inMenu(menu)
                .writtenBy(writer);
        return boardRepository.save(normalBoard).getId();
    }

    @Override
    public Integer update(Integer memberId, UpdateBoardDto updateBoardDto) {
        Member writer = memberRepository.getById(memberId);
        NormalBoard savedBoard = boardRepository.findById(updateBoardDto.getId())
                .orElseThrow(BoardNotFoundException::new);
        NormalBoard updatedBoard = new NormalBoard(updateBoardDto.getId(), updateBoardDto.getTitle(), updateBoardDto.getContents())
                .writtenBy(writer)
                .inMenu(savedBoard.getMenu());
        return boardRepository.save(updatedBoard).getId();
    }

    @Override
    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDto getBoard(Integer id) {
        return boardRepository.findDtoById(id)
                .orElseThrow(BoardNotFoundException::new);
    }

    @Override
    public Page<BoardDto> getBoardList(Integer menuId, Pageable pageable) {
            return boardRepository.findAllByMenuId(menuId, pageable);
    }
}
