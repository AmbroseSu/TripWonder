package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_tour_location")
@NoArgsConstructor
@AllArgsConstructor
public class TourLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String facilitate;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "package_id")
    @ToString.Exclude
    private PackageTour packageTour;

    public List<String> getFacilitate() {
        String facArr = this.facilitate.trim();
        String[] facArrArr = facArr.split(",");
        return new ArrayList<>(Arrays.asList(facArrArr));
    }

    public void setFacilitate(List<String> facilitate) {
        StringBuilder sb = new StringBuilder();
        for (String fac : facilitate) {
            sb.append(fac);
            sb.append(",");
        }
        this.facilitate = sb.toString();
    }


}
/*
- name
- theo từng ngày
- start date - end date
- status //status pay
- ordercode
- tên từng địa điểm cụ thể
- start time - end time
- facilitate (tiện ích của từng địa điểm)
- latitude 
- longitude
*/