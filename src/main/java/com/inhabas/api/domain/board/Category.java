package com.inhabas.api.domain.board;

import com.inhabas.api.domain.board.type.wrapper.CategoryName;
import com.inhabas.api.domain.board.type.wrapper.Description;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * This is for NormalBoard
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "normal_board_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CategoryName name;

    @Embedded
    private Description description;

    public Category(String name, String description) {
        this.name = new CategoryName(name);
        this.description = new Description(description);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }
}
