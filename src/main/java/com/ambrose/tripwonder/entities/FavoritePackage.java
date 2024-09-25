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
@Table(name = "tbl_favorite_package")
public class FavoritePackage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private boolean idDeleted;

  @ManyToOne
  @JoinColumn(name = "packageId")
  private Packagee packagee;
  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
}
