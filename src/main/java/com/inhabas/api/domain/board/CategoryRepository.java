package com.inhabas.api.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findById(Integer categoryId);
}
