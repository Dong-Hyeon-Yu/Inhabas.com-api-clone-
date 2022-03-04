package com.inhabas.api.boards.domain.entity.contest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestBoardRepository extends JpaRepository<ContestBoard, Integer>, ContestBoardRepositoryCustom{
}