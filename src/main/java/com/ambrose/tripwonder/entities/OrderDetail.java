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
@Table(name = "tbl_order_detail")
public class OrderDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Float unitPrice;
  private int quantity;
  private boolean isDeleted;

  @ManyToOne
  @JoinColumn(name = "orderId")
  private Order order;
  @ManyToOne
  @JoinColumn(name = "packageOfficialId")
  private PackageOfficial packageOfficial;
}
