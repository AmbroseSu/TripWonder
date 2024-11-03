package com.ambrose.tripwonder.dto.request;

import com.ambrose.tripwonder.dto.TourInLocationDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class LocationRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String facilitate;
    private Double latitude;
    private Double longitude;
}
