package com.ambrose.tripwonder.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

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

  @OneToMany(mappedBy = "packagee", cascade = CascadeType.ALL)
  private List<RatingReview> ratingReviews;
  @OneToMany(mappedBy = "packagee", cascade = CascadeType.ALL)
  private List<FavoritePackage> favoritePackages;
  @ManyToOne
  @JoinColumn(name = "categoryId")
  private Category category;
  @ManyToOne
  @JoinColumn(name = "provinceId")
  private Province province;
  @OneToOne
  @JoinColumn(name = "tourServiceId")
  private TourService tourService;


}
