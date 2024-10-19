package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_package_tour")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String shortDescription;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int attendance;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    @ToString.Exclude
    private Province province;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingReview> ratingReviews;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gallery> galleries;

    @OneToMany(mappedBy = "packageTour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    @ToString.Exclude
    private Supplier supplier; // Quan hệ 1 Tour thuộc về 1 Supplier

}
