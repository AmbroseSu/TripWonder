package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.OrderDetail;
import com.ambrose.tripwonder.entities.Packagee;
import com.ambrose.tripwonder.entities.RatingReview;
import com.ambrose.tripwonder.entities.Recommendation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.Date;
import java.util.List;

public class PackageOfficialDTO {
    private Long id;
    private Date date;
    private Float price;
    private int numberAttendance;
    private List<RatingReview> ratingReviews;
}
