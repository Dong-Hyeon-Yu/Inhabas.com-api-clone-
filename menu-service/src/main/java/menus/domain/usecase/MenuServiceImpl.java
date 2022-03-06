package menus.domain.usecase;

import menus.domain.data.jpa.MenuRepository;
import menus.domain.entity.Menu;
import menus.dto.MenuDto;
import menus.dto.MenuGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenuGroupDto> getAllMenuInfo() {
        return menuRepository.findAllMenuByMenuGroup();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDto getMenuInfoById(Integer menuId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(MenuNotExistException::new);

        return new MenuDto(menuId, menu.getPriority(), menu.getName(), menu.getType(), menu.getDescription());
    }
}
