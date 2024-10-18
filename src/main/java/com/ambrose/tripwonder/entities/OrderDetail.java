package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data

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
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "packageId")
    @ToString.Exclude
    private PackageTour packageTour;


}
