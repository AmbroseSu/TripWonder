package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileServiceImpl implements FileService {
    public void unzip(MultipartFile file, String destinationDir) throws IOException {
        // Tạo thư mục đích nếu chưa tồn tại
        File dir = new File(destinationDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Giải nén file ZIP
        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                File newFile = new File(destinationDir, entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // Tạo file mới
                    Files.copy(zipInputStream, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                zipInputStream.closeEntry();
            }
        }
    }
}
