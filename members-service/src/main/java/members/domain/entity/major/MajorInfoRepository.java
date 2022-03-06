package members.domain.entity.major;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorInfoRepository extends JpaRepository<MajorInfo, Integer> {
}
