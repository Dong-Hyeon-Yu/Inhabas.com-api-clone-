package boards;

import com.inhabas.api.members.MemberTest;
import com.inhabas.api.boards.domain.entity.board.NormalBoard;
import com.inhabas.api.boards.domain.entity.board.NormalBoardRepository;
import com.inhabas.api.boards.domain.entity.comment.Comment;
import com.inhabas.api.boards.domain.entity.comment.CommentRepository;
import com.inhabas.api.members.domain.entity.member.Member;
import com.inhabas.api.members.domain.entity.member.MemberRepository;
import boards.dto.comment.CommentSaveDto;
import boards.dto.comment.CommentUpdateDto;
import com.inhabas.api.boards.domain.usecase.CommentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private NormalBoardRepository boardRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Member proxyWriter;
    private NormalBoard proxyBoard;

    @BeforeEach
    public void setUpMocking() {
        proxyWriter = MemberTest.getTestMember(12171652);
        proxyBoard = NormalBoardTest.getTestBoard(12);
    }

    @DisplayName("새로운 댓글을 저장한다.")
    @Test
    public void SaveNewCommentTest() {
        //mocking
        BDDMockito.given(memberRepository.getById(12171652)).willReturn(proxyWriter);
        BDDMockito.given(boardRepository.getById(12)).willReturn(proxyBoard);
        BDDMockito.given(commentRepository.save(ArgumentMatchers.any(Comment.class)))
                .willReturn(new Comment(ArgumentMatchers.any(Integer.class), "이야 이게 댓글 기능이라고??"));

        //given
        CommentSaveDto newComment = new CommentSaveDto(12171652, "이야 이게 댓글 기능이라고??", 12);

        //when
        Integer returnId = commentService.create(newComment);

        //then
        Assertions.assertThat(returnId).isNotNull();
        Mockito.verify(commentRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Comment.class));
    }

    @DisplayName("댓글을 성공적으로 수정한다.")
    @Test
    public void UpdateCommentTest() {
        //mocking
        Integer commentId = 1;
        BDDMockito.given(commentRepository.findById(commentId))
                .willReturn(expectedCommentAfterFind(commentId, proxyWriter, proxyBoard));
        BDDMockito.given(commentRepository.save(ArgumentMatchers.any(Comment.class)))
                .willAnswer(i -> i.getArguments()[0]);

        //given
        CommentUpdateDto param = new CommentUpdateDto(1, 12171652, "내용 수정 좀 할게요.", 12);

        //when
        Integer returnId = commentService.update(param);

        //then
        Assertions.assertThat(returnId).isNotNull();
        Mockito.verify(commentRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Comment.class));
    }

    private Optional<Comment> expectedCommentAfterFind(Integer commentId, Member proxyWriter, NormalBoard proxyBoard) {
        return Optional.of(new Comment(commentId, "이야 이게 댓글 기능이라고??")
                .writtenBy(proxyWriter)
                .toBoard(proxyBoard));
    }

    @DisplayName("다른 유저가 댓글 수정을 시도하면 오류")
    @Test
    public void IllegalUpdateTest() {
        //mocking
        Integer commentId = 1;
        BDDMockito.given(commentRepository.findById(commentId))
                .willReturn(expectedCommentAfterFind(commentId, proxyWriter, proxyBoard));

        //given
        CommentUpdateDto param = new CommentUpdateDto(1, 12170000, "내용 수정 좀 할게요.", 12);

        //when
        Assertions.assertThrows(RuntimeException.class,
                () -> commentService.update(param));
    }

    @DisplayName("댓글 리스트를 찾는 메소드를 호출한다.")
    @Test
    public void getComments() {
        //when
        commentService.getComments(proxyBoard.getId());

        //then
        Mockito.verify(commentRepository, Mockito.times(1))
                .findAllByParentBoardIdOrderByCreated(proxyBoard.getId());
    }

    @DisplayName("댓글을 성공적으로 삭제한다.")
    @Test
    public void deleteComment() {
        //given
        Mockito.doNothing().when(commentRepository).deleteById(ArgumentMatchers.isA(Integer.class));

        //when
        commentService.delete(1);

        //then
        Mockito.verify(commentRepository, Mockito.times(1))
                .deleteById(1);
    }
}