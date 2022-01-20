package com.inhabas.api.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.inhabas.api.domain.board.QCategory.category;

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
    public List<Category> findAll() {
        return queryFactory
                .selectFrom(category)
                .fetch();
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        Category category = queryFactory.selectFrom(QCategory.category)
                .where(QCategory.category.id.eq(categoryId))
                .fetchOne();

        return Optional.ofNullable(category);
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
