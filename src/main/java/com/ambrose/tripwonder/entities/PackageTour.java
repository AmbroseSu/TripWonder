package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "tbl_package_tour")
@NoArgsConstructor
@AllArgsConstructor
public class PackageTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String shortDescription;
    private double price;
    private Date startTime;
    private Date endTime;
    private Date date;
    private int attendance;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingReview> ratingReviews;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gallery> galleries;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

}
