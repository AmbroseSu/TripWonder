package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "tbl_favorite_package")
public class FavoritePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean idDeleted;

    @ManyToOne
    @JoinColumn(name = "packageId")
    private PackageTour packageId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
