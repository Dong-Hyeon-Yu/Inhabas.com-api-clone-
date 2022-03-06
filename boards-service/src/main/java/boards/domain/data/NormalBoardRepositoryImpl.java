package boards.domain.data;

import boards.domain.entity.board.QNormalBoard;
import boards.dto.board.BoardDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class NormalBoardRepositoryImpl implements NormalBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardDto> findAllByMenuId(Integer menuId, Pageable pageable) {
        List<BoardDto> results = queryFactory.select(Projections.constructor(BoardDto.class,
                        QNormalBoard.normalBoard.id,
                        QNormalBoard.normalBoard.title.value,
                        Expressions.asString("").as("contents"),
                        Expressions.asNumber(menuId).as("menuId"),
                        QNormalBoard.normalBoard.created,
                        QNormalBoard.normalBoard.updated))
                .from(QNormalBoard.normalBoard)
                .where(menuEq(menuId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    private BooleanExpression menuEq(Integer categoryId) {
        return QNormalBoard.normalBoard.menu.id.eq(categoryId);
    }

    @Override
    public Optional<BoardDto> findDtoById(Integer id) {

        BoardDto target = queryFactory.select(Projections.constructor(BoardDto.class,
                        Expressions.asNumber(id).as("id"),
                        QNormalBoard.normalBoard.title.value,
                        QNormalBoard.normalBoard.contents.value,
                        QNormalBoard.normalBoard.menu.id,
                        QNormalBoard.normalBoard.created,
                        QNormalBoard.normalBoard.updated))
                .from(QNormalBoard.normalBoard)
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(target);
    }
}