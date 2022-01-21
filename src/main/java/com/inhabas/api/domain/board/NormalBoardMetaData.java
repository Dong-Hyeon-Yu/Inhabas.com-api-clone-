package com.inhabas.api.domain.board;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class NormalBoardMetaData {

    private final CategoryRepository categoryRepository;

    private static List<Tuple> CATEGORY_LIST;
    private static ConcurrentHashMap<Category, Integer> COUNT_OF_BOARDS_IN;

    @PostConstruct
    private void loadMetaData() {
        CATEGORY_LIST = categoryRepository.findAll();
    }

}
