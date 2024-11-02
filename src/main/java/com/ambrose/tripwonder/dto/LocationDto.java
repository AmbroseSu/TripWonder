package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.PackageTour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class LocationDto {
    private Long id;
    private String name;
    @Setter(AccessLevel.NONE)
    private List<Integer> days;
    @Setter(AccessLevel.NONE)
    private LocalDate startDate;
    @Setter(AccessLevel.NONE)
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> facilitate;
    private Double latitude;
    private Double longitude;
    @Setter(AccessLevel.NONE)
    private TourInLocationDto packageTour;
    
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if(endDate != null && packageTour != null) {
           setDays();
        }
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if(this.startDate != null && packageTour != null) {
            setDays();
        }
    }
    
    public void setPackageTour(TourInLocationDto packageTour) {
        this.packageTour = packageTour;
        if(startDate != null && endDate != null && packageTour != null) {
            setDays();
        }
    }
    
    private void setDays(){
        LocalDate start = packageTour.getStartTime().toLocalDate();
        LocalDate end = packageTour.getEndTime().toLocalDate();
        long numberDayStart = ChronoUnit.DAYS.between(start, startDate);
        long numberDay = ChronoUnit.DAYS.between(startDate, endDate);
        days = new ArrayList<>();
        for(long i = 0; i<numberDay; i++) {
            days.add((int)(numberDayStart+i));
        }
    }
   
}
