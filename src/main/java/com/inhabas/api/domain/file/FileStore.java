package com.inhabas.api.domain.file;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
public interface FileStore {
    // 파일 경로반환
    List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException;
}
