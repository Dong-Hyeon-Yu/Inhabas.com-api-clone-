package com.inhabas.api.domain.wrapper;

import com.inhabas.api.domain.board.type.wrapper.CategoryName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryNameTest {

    @DisplayName("카테고리 이름으로 null 값은 허용 안됨.")
    @Test
    public void CategoryName_cannot_be_null() {
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CategoryName(null));
    }

    @DisplayName("카테고리 이름으로 빈 문자열은 허용 안됨.")
    @Test
    public void CategoryName_cannot_be_blank() {
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CategoryName("  "));
    }

    @DisplayName("카테고리 이름 10글자 이상은 허용 안됨.")
    @Test
    public void CategoryName_too_long() {
        //given
        String name = "이문장9글자입니다";

        //when : 9글자 정상 저장
        CategoryName categoryName = new CategoryName(name);
        assertThat(categoryName.getValue()).isEqualTo(name);

        //when : 10글자 저장 안됨.
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CategoryName(name+"."));
    }

}
