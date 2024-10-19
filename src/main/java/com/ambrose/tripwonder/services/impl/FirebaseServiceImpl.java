package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.services.FirebaseService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;
import com.google.firebase.cloud.StorageClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private Storage storage;


    @Override
    public String upload(File file) throws IOException {
        BlobId blobId = BlobId.of("tripwonder-image-86819.appspot.com", file.getName()); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        String firebaseConfig = System.getenv("FIREBASE_CONFIG");

        // Tạo InputStream từ chuỗi JSON
        InputStream inputStream = new ByteArrayInputStream(firebaseConfig.getBytes());

//        InputStream inputStream = FirebaseService.class.getClassLoader().getResourceAsStream("firebase-private-key.json"); // change the file name with your one
        assert inputStream != null;
        FirebaseOptions options = new FirebaseOptions.Builder()

                .setCredentials(GoogleCredentials.fromStream(inputStream))

                .build();


        FirebaseApp.initializeApp(options);

//        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        inputStream.close();

        // Initialize Storage instance with a new InputStream
//        InputStream storageStream = FirebaseService.class.getClassLoader().getResourceAsStream("firebase-private-key.json");
        InputStream storageStream = new ByteArrayInputStream(firebaseConfig.getBytes());
        
        assert storageStream != null;
        this.storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(storageStream))
                .build()
                .getService();
        storageStream.close();      
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/tripwonder-image-86819.appspot.com/o/%s?alt=media";
        file.delete();
        return String.format(DOWNLOAD_URL, URLEncoder.encode(file.getName(), StandardCharsets.UTF_8));
    }
}
