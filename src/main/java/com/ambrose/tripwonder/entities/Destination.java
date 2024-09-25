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
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "tbl_destination")
public class Destination {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String address;
  private String description;
  private String shortDescription;
  private String activities;
  private boolean isDeleted;

  @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
  private List<Gallery> galleries;
  @ManyToOne
  @JoinColumn(name = "provinceId")
  private Province province;
  @ManyToOne
  @JoinColumn(name = "recommendationId")
  private Recommendation recommendation;
}
