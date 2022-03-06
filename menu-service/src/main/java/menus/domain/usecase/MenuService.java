package menus.domain.usecase;

import menus.dto.MenuDto;
import menus.dto.MenuGroupDto;

import java.util.List;

public interface MenuService {

    List<MenuGroupDto> getAllMenuInfo();

    MenuDto getMenuInfoById(Integer menuId);
}
