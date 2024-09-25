package com.ambrose.tripwonder.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "tbl_recommendation")
public class Recommendation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL)
  private List<PackageOfficial> packageOfficials;
  @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL)
  private List<Destination> destinations;

}
