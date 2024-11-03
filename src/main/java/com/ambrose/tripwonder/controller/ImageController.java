package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
