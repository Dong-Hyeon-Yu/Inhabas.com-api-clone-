package com.inhabas.api.service.menu;

import com.inhabas.api.dto.menu.MenuDto;
import com.inhabas.api.dto.menu.MenuGroupDto;

import java.util.List;

public interface MenuService {

    List<MenuGroupDto> getAllMenuInfo();

    MenuDto getMenuInfoById(Integer menuId);
}