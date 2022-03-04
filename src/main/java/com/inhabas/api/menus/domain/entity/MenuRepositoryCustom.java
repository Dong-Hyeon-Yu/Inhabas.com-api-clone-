package com.inhabas.api.menus.domain.entity;

import com.inhabas.api.menus.dto.MenuGroupDto;
import java.util.List;

public interface MenuRepositoryCustom {
    List<MenuGroupDto> findAllMenuByMenuGroup();
}
