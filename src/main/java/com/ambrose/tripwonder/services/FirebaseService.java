package com.ambrose.tripwonder.services;

import java.io.File;
import java.io.IOException;

public interface FirebaseService {
    String upload(File file) throws IOException;

}
