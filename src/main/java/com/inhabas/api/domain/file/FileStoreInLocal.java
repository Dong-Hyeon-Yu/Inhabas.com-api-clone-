package com.inhabas.api.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FileStoreInLocal implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty())
                storeFileResult.add(storeFile(multipartFile));
        }

        return storeFileResult;
    }


    private String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty())
            return null;

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fullPath = getFullPath(storeFileName);
        multipartFile.transferTo(new File(fullPath));

        return fullPath;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        assert originalFilename != null;
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
