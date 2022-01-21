package com.inhabas.api.domain.board;

import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    List<Tuple> findAll();

    Optional<Category> findById(Integer categoryId);

    List<Tuple> getBoardCountGroupByCategoryId();
}
