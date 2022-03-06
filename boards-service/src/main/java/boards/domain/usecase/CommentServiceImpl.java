package boards.domain.usecase;

import boards.domain.entity.board.NormalBoard;
import boards.domain.data.NormalBoardRepository;
import boards.domain.entity.comment.Comment;
import boards.domain.data.CommentRepository;
import members.domain.entity.member.Member;
import members.domain.entity.member.MemberRepository;
import boards.dto.comment.CommentDetailDto;
import boards.dto.comment.CommentSaveDto;
import boards.dto.comment.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final NormalBoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentDetailDto> getComments(Integer boardId) {
        return commentRepository.findAllByParentBoardIdOrderByCreated(boardId);
    }

    @Transactional
    public Integer create(CommentSaveDto commentSaveDto) {
        Member writer = getWriterFrom(commentSaveDto);
        NormalBoard board = getBoardFrom(commentSaveDto);

        Comment newComment = new Comment(commentSaveDto.getContents())
                .toBoard(board)
                .writtenBy(writer);

        return commentRepository.save(newComment).getId();
    }

    private NormalBoard getBoardFrom(CommentSaveDto commentSaveDto) {
        return boardRepository.getById(commentSaveDto.getBoardId());
    }

    private Member getWriterFrom(CommentSaveDto commentSaveDto) {
        return memberRepository.getById(commentSaveDto.getWriterId());
    }

    @Transactional
    public Integer update(CommentUpdateDto commentUpdateDto) {

        Integer id = commentUpdateDto.getId();
        Comment OldComment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Integer updaterId = commentUpdateDto.getWriterId();
        if (OldComment.isWrittenBy(updaterId)) {
            OldComment.setContents(commentUpdateDto.getContents());
            commentRepository.save(OldComment);

            return id;
        }
        else {
            throw new RuntimeException("작성자만 수정 가능합니다.");
        }
    }

    @Transactional
    public void delete(Integer id) {
        // 향후 로그인 유저 받아서 처리해야함.
        commentRepository.deleteById(id);
    }
}
