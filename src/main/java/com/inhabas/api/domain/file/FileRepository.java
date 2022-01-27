package com.inhabas.api.domain.file;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FileRepository<T> extends FileDBRepository<T> {
}
