package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data

@Entity
@Table(name = "tbl_recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;

    @ManyToOne
    @JoinColumn(name = "packageTourId")
    @ToString.Exclude
    private PackageTour packageTour;

}
