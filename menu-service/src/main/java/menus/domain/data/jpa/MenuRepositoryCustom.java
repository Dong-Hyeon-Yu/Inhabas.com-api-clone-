package menus.domain.data.jpa;

import menus.dto.MenuGroupDto;
import java.util.List;

public interface MenuRepositoryCustom {
    List<MenuGroupDto> findAllMenuByMenuGroup();
}
