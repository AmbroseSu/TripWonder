package com.ambrose.tripwonder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageTourDTO {
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int attendance;
    private boolean status;
}
