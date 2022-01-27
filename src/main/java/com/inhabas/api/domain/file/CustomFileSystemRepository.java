package com.inhabas.api.domain.file;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@NoRepositoryBean
public interface CustomFileSystemRepository<T> extends Repository<T, Integer> {
    List<String> save(List<MultipartFile> multipartFiles);
}
