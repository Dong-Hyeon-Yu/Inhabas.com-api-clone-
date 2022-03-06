package boards.domain.data;

import boards.domain.entity.board.NormalBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalBoardRepository extends JpaRepository<NormalBoard, Integer>, NormalBoardRepositoryCustom {
}