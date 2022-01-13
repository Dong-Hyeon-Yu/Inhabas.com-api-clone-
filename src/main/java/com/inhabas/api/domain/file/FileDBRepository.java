package com.inhabas.api.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository<T> extends JpaRepository<T, Integer> {
}
