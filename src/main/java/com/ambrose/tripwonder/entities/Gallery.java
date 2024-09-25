package com.ambrose.tripwonder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
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
  @JoinColumn(name = "tourServiceId")
  private TourService tourService;
  @ManyToOne
  @JoinColumn(name = "destinationId")
  private Destination destination;
}
