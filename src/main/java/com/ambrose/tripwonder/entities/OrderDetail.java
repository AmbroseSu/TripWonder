package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data

@Entity
@Table(name = "tbl_order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter(AccessLevel.NONE)
    private Double totalPrice;
    @Setter(AccessLevel.NONE)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "packageId")
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private PackageTour packageTour;


    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (packageTour != null) {
            totalPrice = packageTour.getPrice() * quantity;
        }
    }

    public void setPackageTour(PackageTour packageTour) {
        this.packageTour = packageTour;
        if (quantity != 0) totalPrice = packageTour.getPrice() * quantity;
    }

}
