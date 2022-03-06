package boards.domain.data;

import boards.domain.entity.comment.Comment;
import boards.domain.entity.comment.QComment;
import boards.dto.comment.CommentDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentDetailDto> findAllByParentBoardIdOrderByCreated(Integer boardId) {
        List<Comment> comments = queryFactory.selectFrom(QComment.comment)
                .innerJoin(QComment.comment.writer).fetchJoin()
                .leftJoin(QComment.comment.parentComment).fetchJoin()
                .where(QComment.comment.parentBoard.id.eq(boardId))
                .orderBy(QComment.comment.created.asc(), QComment.comment.parentComment.id.asc().nullsFirst(), QComment.comment.id.asc())
                .fetch();

        return convertToNestedStructure(comments);
    }

    private List<CommentDetailDto> convertToNestedStructure(List<Comment> commentList) {

        List<CommentDetailDto> result = new ArrayList<>();
        Map<Integer, CommentDetailDto> map = new HashMap<>();

        commentList.forEach(c -> {
            CommentDetailDto dto = CommentDetailDto.fromEntity(c);
            if (isRootComment(c)) {
                map.put(dto.getId(), dto);
                result.add(dto);
            }
            else {
                map.get(c.getParentComment().getId()).getChildren().add(dto);
            }
        });

        return result;
    }

    private boolean isRootComment(Comment c) {
        return Objects.isNull(c.getParentComment());
    }
}
