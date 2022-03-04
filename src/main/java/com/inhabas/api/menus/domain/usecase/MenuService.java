package com.inhabas.api.menus.domain.usecase;

import com.inhabas.api.menus.dto.MenuDto;
import com.inhabas.api.menus.dto.MenuGroupDto;

import java.util.List;

public interface MenuService {

    List<MenuGroupDto> getAllMenuInfo();

    MenuDto getMenuInfoById(Integer menuId);
}
