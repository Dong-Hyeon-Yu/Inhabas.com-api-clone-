package boards;

import boards.web.BoardController;
import com.inhabas.api.boards.domain.entity.board.NormalBoard;
import boards.dto.board.BoardDto;
import boards.dto.board.SaveBoardDto;
import boards.dto.board.UpdateBoardDto;
import com.inhabas.api.boards.domain.usecase.BoardService;
import com.inhabas.api.members.domain.usecase.MemberService;
import com.inhabas.api.security.annotataion.WithMockJwtAuthenticationToken;
import com.inhabas.testConfig.DefaultWebMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DefaultWebMvcTest(BoardController.class)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BoardController boardController;

    @MockBean
    BoardService boardService;

    @MockBean
    MemberService memberService;

    @MockBean
    NormalBoard normalBoard;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(boardController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @DisplayName("게시글 저장을 요청한다.")
    @Test
    @WithMockJwtAuthenticationToken
    public void addNewBoard() throws Exception {
        //given
        SaveBoardDto saveBoardDto = new SaveBoardDto("This is title", "This is contents", 1);
        BDDMockito.given(boardService.write(ArgumentMatchers.any(), ArgumentMatchers.any(SaveBoardDto.class))).willReturn(1);

        // when
        mvc.perform(MockMvcRequestBuilders.post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "1")
                        .content(objectMapper.writeValueAsString(saveBoardDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @DisplayName("게시글 수정을 요청한다.")
    @Test
    @WithMockJwtAuthenticationToken
    public void updateBoard() throws Exception{
        //given
        UpdateBoardDto updateBoardDto = new UpdateBoardDto(1, "제목을 수정하였습니다.", "내용을 수정하였습니다.");
        BDDMockito.given(boardService.update(ArgumentMatchers.any(), ArgumentMatchers.any(UpdateBoardDto.class))).willReturn(1);

        // when
        mvc.perform(MockMvcRequestBuilders.put("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBoardDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @DisplayName("게시글 삭제를 요청한다.")
    @Test
    public void deleteBoard() throws Exception{
        //given
        Mockito.doNothing().when(boardService).delete(ArgumentMatchers.anyInt());

        // when
        mvc.perform(MockMvcRequestBuilders.delete("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @DisplayName("게시글 목록 조회를 요청한다.")
    @Test
    public void getBoardList() throws Exception {
        PageRequest pageable = PageRequest.of(0,10, Sort.Direction.DESC, "id");

        List<BoardDto> results = new ArrayList<>();
        results.add(new BoardDto(1, "Shown Title1", null, "Mingyeom", 2, LocalDateTime.now(), null));
        results.add(new BoardDto(2, "Shown Title2", null, "Mingyeom", 2, LocalDateTime.now(), null));
        results.add(new BoardDto(3, "Shown Title3", null, "Mingyeom", 2, LocalDateTime.now(), null));

        Page<BoardDto> expectedBoardDto = new PageImpl<>(results,pageable, results.size());

        BDDMockito.given(boardService.getBoardList(ArgumentMatchers.anyInt(), ArgumentMatchers.any())).willReturn(expectedBoardDto);

        // when
        String responseBody = mvc.perform(MockMvcRequestBuilders.get("/board/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("menuId", "2")
                        .param("page", "2")
                        .param("size", "1")
                        .param("sort", "ASC")
                        .param("properties", "id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        Assertions.assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expectedBoardDto));
    }

    @DisplayName("게시글 단일 조회를 요청한다.")
    @Test
    public void getBoardDetail() throws Exception{
        //given
        BoardDto boardDto = new BoardDto(1, "Shown Title", "Shown Contents", "Mingyeom", 1, LocalDateTime.now(), null);
        BDDMockito.given(boardService.getBoard(ArgumentMatchers.anyInt())).willReturn(boardDto);

        // when
        String responseBody = mvc.perform(MockMvcRequestBuilders.get("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        Assertions.assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(boardDto));

    }

    @DisplayName("게시글 작성 시 Title의 길이가 범위를 초과해 오류 발생")
    @Test
    @WithMockJwtAuthenticationToken
    public void TitleIsTooLongError() throws Exception {
        //given
        SaveBoardDto saveBoardDto = new SaveBoardDto("title".repeat(20) + ".", "contents", 1);

        // when
        String errorMessage = Objects.requireNonNull(
                mvc.perform(MockMvcRequestBuilders.post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveBoardDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResolvedException())
                .getMessage();

        // then
        Assertions.assertThat(errorMessage).isNotBlank();
        Assertions.assertThat(errorMessage).contains("제목은 최대 100자입니다.");
    }

    @DisplayName("게시글 작성 시 Contents가 null인 경우 오류 발생")
    @Test
    @WithMockJwtAuthenticationToken
    public void ContentIsNullError() throws Exception {
        //given
        SaveBoardDto saveBoardDto = new SaveBoardDto("title", "   ", 1);

        // when
        String errorMessage = Objects.requireNonNull(
                mvc.perform(MockMvcRequestBuilders.post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveBoardDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn()
                .getResolvedException())
                .getMessage();

        // then
        Assertions.assertThat(errorMessage).isNotBlank();
        Assertions.assertThat(errorMessage).contains("본문을 입력하세요.");
    }
}
