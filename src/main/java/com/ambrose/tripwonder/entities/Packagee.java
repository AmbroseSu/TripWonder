package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "tbl_packagee")
public class Packagee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private Date startTime;
    private Date endTime;
    private Float price;
    private boolean isDelete;
    private boolean status;


    @OneToMany(mappedBy = "packagee", cascade = CascadeType.ALL)
    private List<FavoritePackage> favoritePackages;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;
    @OneToMany(mappedBy = "packagee", cascade = CascadeType.ALL)
    private List<Gallery> galleries;


}
