package com.ambrose.tripwonder.config.converter;

import com.ambrose.tripwonder.entities.enums.FilterBy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Configuration
public class FilterByToStringConverter implements Converter<String, FilterBy> {
    @Override
    public FilterBy convert(String source) {
        return null;
    }
}
