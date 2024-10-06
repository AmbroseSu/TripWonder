package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "tbl_package_official")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageOfficial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Float price;
    private int numberAttendance;
    private boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "packageeId")
    private Packagee packagee;
    @OneToMany(mappedBy = "packageOfficial", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
    @OneToOne
    @JoinColumn(name = "recommendationId")
    private Recommendation recommendation;
    @OneToMany(mappedBy = "packageOfficial", cascade = CascadeType.ALL)
    private List<RatingReview> ratingReviews;
}
