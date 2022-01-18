package com.inhabas.api.domain.file;

import com.inhabas.api.domain.board.BaseBoard;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoardFileRepository extends CrudRepository<BoardFile, Integer> {

    List<BoardFile> findAllByParentBoard(BaseBoard baseBoard);
}
