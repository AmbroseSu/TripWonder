package com.ambrose.tripwonder.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void unzip(File file, String destinationDir)throws IOException;
}
