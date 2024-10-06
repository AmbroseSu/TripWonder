package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.RatingReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageOfficialDTO {
    private Long id;
    private Date date;
    private Float price;
    private int numberAttendance;
    private List<RatingReview> ratingReviews;
}
