package com.ambrose.tripwonder.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void unzip(MultipartFile file, String destinationDir)throws IOException;
}
