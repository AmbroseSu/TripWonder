package com.ambrose.tripwonder.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "tbl_tour_service")
public class TourService {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date startTime;
  private Date endTime;
  private Float price;
  private String type;
  private String name;
  private String description;
  private String shortDescription;
  private boolean isDeleted;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
  @OneToMany(mappedBy = "tourService", cascade = CascadeType.ALL)
  private List<Gallery> galleries;
}
