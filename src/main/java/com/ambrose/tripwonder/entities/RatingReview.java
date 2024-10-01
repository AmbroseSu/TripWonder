package com.ambrose.tripwonder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

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
