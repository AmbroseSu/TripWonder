package com.ambrose.tripwonder.config.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


@Configuration
public class MultipartFileToFileConverter implements Converter<MultipartFile, File> {
    @Override
    public File convert(MultipartFile source) {
        String fileName = source.getOriginalFilename();
        fileName = UUID.randomUUID().toString().concat(fileName.substring(fileName.lastIndexOf(".")));  // to generated random string values for file name.
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(source.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempFile;
    }
}
