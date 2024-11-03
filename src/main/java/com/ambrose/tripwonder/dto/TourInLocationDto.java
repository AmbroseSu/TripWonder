package com.ambrose.tripwonder.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TourInLocationDto {

    private Long id;
    private String name;
    private String shortDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CategoryDTO category;
    private List<GalleryDto> galleries;
    
    
     /*
    start day
    end day
    category
    name
    short description
     */
}
