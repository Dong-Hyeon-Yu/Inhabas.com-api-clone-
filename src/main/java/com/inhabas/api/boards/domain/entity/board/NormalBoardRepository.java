package com.inhabas.api.boards.domain.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalBoardRepository extends JpaRepository<NormalBoard, Integer>, NormalBoardRepositoryCustom {
}