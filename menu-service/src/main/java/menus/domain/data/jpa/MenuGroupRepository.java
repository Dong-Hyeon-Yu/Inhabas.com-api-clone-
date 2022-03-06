package menus.domain.data.jpa;

import menus.domain.entity.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Integer> {
}
