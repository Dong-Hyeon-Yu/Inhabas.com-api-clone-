package menus.domain.data.jpa;

import menus.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer>, MenuRepositoryCustom {
}
