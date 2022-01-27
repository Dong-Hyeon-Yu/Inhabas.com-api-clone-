package com.inhabas.api.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FileDBRepository<T> extends JpaRepository<T, Integer> {
}
