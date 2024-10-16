package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
@CrossOrigin
public class ImageController {
    private final FirebaseService imageService;

    @PostMapping
    public String upload(@RequestParam("file") File multipartFile) throws IOException {
        return imageService.upload(multipartFile);
    }
}
