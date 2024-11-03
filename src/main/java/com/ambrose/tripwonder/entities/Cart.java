package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tbl_cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter(AccessLevel.NONE)
    private Double totalPrice;
    @Setter(AccessLevel.NONE)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "packageId")
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private PackageTour packageTour;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

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
