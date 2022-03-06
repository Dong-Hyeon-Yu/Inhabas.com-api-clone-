package members.domain.entity.member;

import members.domain.entity.member.type.wrapper.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByPhone(Phone phone);
}
