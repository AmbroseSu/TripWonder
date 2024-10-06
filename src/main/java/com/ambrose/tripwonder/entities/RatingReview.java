package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "tbl_rating_review")
public class RatingReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feedback;
    private int rating;
    private Date ratingDate;

    @ManyToOne
    @JoinColumn(name = "packageOfficialId")
    private PackageOfficial packageOfficial;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
