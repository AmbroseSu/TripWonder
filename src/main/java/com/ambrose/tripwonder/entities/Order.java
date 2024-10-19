package com.ambrose.tripwonder.entities;

import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data

@Entity
@Table(name = "tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float totalPrice;
    private LocalDateTime orderDate;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private boolean isDeleted;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;
}
