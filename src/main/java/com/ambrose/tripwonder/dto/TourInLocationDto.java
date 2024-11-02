package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourInLocationDto {

    private Long id;
    private String name;
    private String shortDescription;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CategoryDTO category;
    
    
     /*
    start day
    end day
    category
    name
    short description
     */
}
