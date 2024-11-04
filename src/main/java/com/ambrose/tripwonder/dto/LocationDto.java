package com.ambrose.tripwonder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LocationDto {
    private Long id;
    private String name;
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private List<Integer> days;
    private String dayString;
    @Setter(AccessLevel.NONE)
    private LocalDate startDate;
    @Setter(AccessLevel.NONE)
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String facilitate;
    private Double latitude;
    private Double longitude;
    @Setter(AccessLevel.NONE)
    private TourInLocationDto packageTour;


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if (endDate != null && packageTour != null) {
            setDays();
        }
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        if (this.startDate != null && packageTour != null) {
            setDays();
        }
    }

    public void setPackageTour(TourInLocationDto packageTour) {
        this.packageTour = packageTour;
        if (startDate != null && endDate != null) {
            setDays();
        }
    }

    private void setDays() {
        LocalDate start = packageTour.getStartTime().toLocalDate();
        LocalDate end = packageTour.getEndTime().toLocalDate();
        long numberDayStart = ChronoUnit.DAYS.between(start, startDate)+1;
        long numberDay = ChronoUnit.DAYS.between(startDate, endDate)+1;
        days = new ArrayList<>();
        for (long i = 0; i < numberDay; i++) {
            days.add((int) (numberDayStart + i));
        }
    }

    public LocationDto(LocationDto other) {
        this.id = other.id;
        this.name = other.name;

        // Sao chép sâu cho danh sách days nếu không null
        if (other.days != null) {
            this.days = new ArrayList<>(other.days);
        } else {
            this.days = null;
        }

        this.dayString = other.dayString;
        this.startDate = other.startDate;
        this.endDate = other.endDate;
        this.startTime = other.startTime;
        this.endTime = other.endTime;
        this.facilitate = other.facilitate;
        this.latitude = other.latitude;
        this.longitude = other.longitude;

        // Sao chép sâu cho packageTour nếu không null
        if (other.packageTour != null) {
            this.packageTour = other.packageTour;
        } else {
            this.packageTour = null;
        }
    }
}
