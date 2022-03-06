package boards;

import com.inhabas.api.boards.domain.entity.board.NormalBoard;
import com.inhabas.api.boards.domain.entity.board.NormalBoardRepository;
import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.MemberRepository;
import com.inhabas.api.menus.domain.entity.Menu;
import com.inhabas.api.menus.domain.entity.MenuRepository;
import boards.dto.board.BoardDto;
import boards.dto.board.SaveBoardDto;
import boards.dto.board.UpdateBoardDto;
import com.inhabas.api.boards.domain.usecase.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    BoardServiceImpl boardService;

    @Mock
    NormalBoardRepository boardRepository;

    @Mock
    MenuRepository menuRepository;

    @Mock
    MemberRepository memberRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(boardService).build();
    }

    @DisplayName("게시판을 성공적으로 생성한다.")
    @Test
    public void createBoard() {
        //given
        SaveBoardDto saveBoardDto = new SaveBoardDto("title", "contents", 1);
        NormalBoard normalBoard = new NormalBoard(1, "title", "contents");
        Menu menu = new Menu(null, 1, null, "name", "description");
        Member member = new Member(12201863, "mingyeom", "010-0000-0000","picture", null, null);
        BDDMockito.given(boardRepository.save(ArgumentMatchers.any())).willReturn(normalBoard);
        BDDMockito.given(menuRepository.getById(ArgumentMatchers.any())).willReturn(menu);
        BDDMockito.given(memberRepository.getById(ArgumentMatchers.any())).willReturn(member);

        // when
        Integer returnedId = boardService.write(12201863, saveBoardDto);

        // then
        BDDMockito.then(boardRepository).should(Mockito.times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(returnedId).isNotNull();
        Assertions.assertThat(returnedId).isEqualTo(normalBoard.getId());
    }

    @DisplayName("게시판의 목록을 조회한다.")
    @Test
    public void getBoardList() {
        //given
        PageRequest pageable = PageRequest.of(0,10, Sort.Direction.ASC, "created");

        BoardDto boardDto1 = new BoardDto(1, "title", "contents", "mingyeom",1, LocalDateTime.now(), LocalDateTime.now() );
        BoardDto boardDto2 = new BoardDto(2, "title", "contents", "minji",1, LocalDateTime.now(), LocalDateTime.now() );

        List<BoardDto> results = new ArrayList<>();
        results.add(boardDto1);
        results.add(boardDto2);
        Page<BoardDto> expectedBoardDto = new PageImpl<> (results, pageable, results.size());

        BDDMockito.given(boardRepository.findAllByMenuId(ArgumentMatchers.any(), ArgumentMatchers.any())).willReturn(expectedBoardDto);

        //when
        Page<BoardDto> returnedBoardList = boardService.getBoardList(1, pageable);

        //then
        BDDMockito.then(boardRepository).should(Mockito.times(1)).findAllByMenuId(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertThat(returnedBoardList).isEqualTo(expectedBoardDto);
    }

    @DisplayName("게시글 단일 조회에 성공한다.")
    @Test
    public void getDetailBoard() {
        //given
        BoardDto boardDto = new BoardDto(1, "title", "contents", "김민겸", 1, LocalDateTime.now() , null);
        BDDMockito.given(boardRepository.findDtoById(ArgumentMatchers.any())).willReturn(Optional.of(boardDto));

        // when
        boardService.getBoard(1);

        // then
        BDDMockito.then(boardRepository).should(Mockito.times(1)).findDtoById(ArgumentMatchers.any());
    }

    @DisplayName("게시글을 성공적으로 삭제한다.")
    @Test
    public void deleteBoard() {
        //given
        Mockito.doNothing().when(boardRepository).deleteById(ArgumentMatchers.any());

        // when
        boardService.delete(1);

        // then
        BDDMockito.then(boardRepository).should(Mockito.times(1)).deleteById(ArgumentMatchers.any());
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    public void updateBoard() {
        //given
        Member entityMember = new Member(12201863, "mingyeom", "010-0000-0000", "picture", null, null);
        NormalBoard savedNormalBoard = new NormalBoard(1, "Origin Title", "Origin Contents").writtenBy(entityMember);
        NormalBoard updatedNormalBoard = new NormalBoard(1, "Title", "Contents").writtenBy(entityMember);

        BDDMockito.given(boardRepository.findById(ArgumentMatchers.anyInt())).willReturn(Optional.of(savedNormalBoard));
        BDDMockito.given(boardRepository.save(ArgumentMatchers.any())).willReturn(updatedNormalBoard);

        UpdateBoardDto updateBoardDto = new UpdateBoardDto(1, "수정된 제목", "수정된 내용");

        // when
        Integer returnedId = boardService.update(12201863, updateBoardDto);

        // then
        BDDMockito.then(boardRepository).should(Mockito.times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(returnedId).isNotNull();
        Assertions.assertThat(returnedId).isEqualTo(updatedNormalBoard.getId());
    }

    @DisplayName("게시글을 생성한 유저와 일치하지 않아 게시글 수정에 실패한다.")
    @Test
    public void failToUpdateBoard() {
        //given

        // when

        // then
    }

}
