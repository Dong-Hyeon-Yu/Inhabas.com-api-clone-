package boards.domain.data;

import boards.domain.entity.contest.ContestBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestBoardRepository extends JpaRepository<ContestBoard, Integer>, ContestBoardRepositoryCustom{
}