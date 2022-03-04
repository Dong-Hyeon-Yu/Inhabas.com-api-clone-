package com.inhabas.api.menus.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer>, MenuRepositoryCustom {
}
