package com.inhabas.api.domain.board;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.inhabas.api.domain.board.QCategory.category;
import static com.inhabas.api.domain.board.QNormalBoard.normalBoard;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Category save(Category category) {
        em.persist(category);

        return em.find(Category.class, category.getId());
    }

    @Override
    public List<Tuple> findAll() {
        return queryFactory.from(category)
                .select(category.id, category.name, category.description)
                .fetch();
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        Category category = queryFactory.from(QCategory.category)
                .select(QCategory.category)
                .where(QCategory.category.id.eq(categoryId))
                .fetchOne();

        return Optional.ofNullable(category);
    }

    @Override
    public List<Tuple> getBoardCountGroupByCategoryId() {

        return queryFactory.from(normalBoard)
                .groupBy(normalBoard.category)
                .select(normalBoard.category.name, normalBoard.category.description, normalBoard.category.count())
                .fetch();
    }

    /**
     * this method is only for test case.
     * do not use outside!
     */
    @Transactional
    public void clearAll() {
        queryFactory.delete(category)
                .execute();
    }
}
