package com.inhabas.api.domain.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileSystemRepository {
    List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException;
}
