package menus.domain.data.jpa;

import menus.dto.MenuDto;
import menus.dto.MenuGroupDto;
import menus.domain.data.jpa.MenuRepositoryCustom;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


import static menus.domain.entity.QMenu.menu;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<MenuGroupDto> findAllMenuByMenuGroup() {
        return jpaQueryFactory
                .from(menu)
                .orderBy(menu.menuGroup.id.asc())
                .orderBy(menu.priority.asc())
                .transform(GroupBy.groupBy(menu.menuGroup)
                        .as(list(Projections.constructor(MenuDto.class,
                                menu.id,
                                menu.priority,
                                menu.name.value,
                                menu.type, // enum type
                                menu.description.value)))
                ).entrySet().stream()
                .map(entry -> new MenuGroupDto(entry.getKey().getId(), entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());
    }
}