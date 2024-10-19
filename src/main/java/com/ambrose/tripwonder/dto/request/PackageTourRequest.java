package com.ambrose.tripwonder.dto.request;

import com.ambrose.tripwonder.dto.GalleryDto;
import com.ambrose.tripwonder.entities.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageTourRequest {

    private String name;
    private String description;
    private String shortDescription;
    private double price;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")   
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private LocalDateTime endTime;
    private int attendance;
    private boolean status;
    private Long categoryId;

    private Long provinceId;

    private int ratingReviews;
    
    private List<String> galleries;
    
    private UUID supplierId; // Quan hệ 1 Tour thuộc về 1 Supplier
    private Long staffId;
}
