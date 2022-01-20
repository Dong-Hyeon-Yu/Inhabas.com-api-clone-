package com.inhabas.api.domain;

import com.inhabas.api.config.JpaConfig;
import com.inhabas.api.domain.board.Category;
import com.inhabas.api.domain.board.CategoryRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import({JpaConfig.class, CategoryRepositoryImpl.class})
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepositoryImpl categoryRepository;

    @BeforeAll
    public void setUp() {
        categoryRepository.clearAll();
    }

    @DisplayName("신규 카테고리를 만든다.")
    @Test
    public void saveCategory() {
        //given
        Category newCategory = new Category("비밀게시판", "여긴 비밀입니다.");

        //when
        Category save = categoryRepository.save(newCategory);

        //then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getName()).isEqualTo("비밀게시판");
        assertThat(save.getDescription()).isEqualTo("여긴 비밀입니다.");
    }

    @DisplayName("게시글 카테고리를 모두 불러온다.")
    @Test
    public void findAllCategories() {
        //given
        categoryRepository.save(new Category("비밀게시판", "여긴 비밀입니다."));
        categoryRepository.save(new Category("잡담게시판", "여긴 잡담입니다."));
        categoryRepository.save(new Category("공지게시판", "여긴 공지입니다."));

        //when
        List<Category> all = categoryRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(3);
        assertThat(all).hasSize(3)
                .extracting(Category::getName, Category::getDescription)
                .contains(
                        tuple("비밀게시판", "여긴 비밀입니다."),
                        tuple("잡담게시판", "여긴 잡담입니다."),
                        tuple("공지게시판", "여긴 공지입니다.")
                );
    }

    @DisplayName("카테고리를 id로 찾아온다.")
    @Test
    public void findById() {
        //given
        Category save = categoryRepository.save(new Category("비밀게시판", "여긴 비밀입니다."));
        categoryRepository.save(new Category("잡담게시판", "여긴 잡담입니다."));
        categoryRepository.save(new Category("공지게시판", "여긴 공지입니다."));

        //when
        Optional<Category> find = categoryRepository.findById(save.getId());

        //then
        assertTrue(find.isPresent());
        assertThat(find.get().getId()).isNotNull();
        assertThat(find.get().getName()).isEqualTo("비밀게시판");
        assertThat(find.get().getDescription()).isEqualTo("여긴 비밀입니다.");
    }
}
