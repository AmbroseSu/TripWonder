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
@Table(name = "tbl_package_official")
public class PackageOfficial {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date date;
  private Float price;
  private int numberAttendance;
  private boolean isDeleted;

  @OneToOne
  @JoinColumn(name = "packageeId")
  private Packagee packagee;
  @OneToMany(mappedBy = "packageOfficial", cascade = CascadeType.ALL)
  private List<OrderDetail> orderDetails;
  @ManyToOne
  @JoinColumn(name = "recommendationId")
  private Recommendation recommendation;
}
