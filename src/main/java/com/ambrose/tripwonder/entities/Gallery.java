package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data

@Entity
@Table(name = "tbl_gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String name;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "packageId")
    @ToString.Exclude
    private PackageTour packageTour;

}
