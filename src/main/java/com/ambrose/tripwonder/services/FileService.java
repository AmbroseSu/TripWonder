package com.ambrose.tripwonder.services;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void unzip(File file, String destinationDir) throws IOException;
}
