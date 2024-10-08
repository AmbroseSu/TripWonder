package com.ambrose.tripwonder.entities;

import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float totalPrice;
    private Date orderDate;
    private Date paymentDate;
    private PaymentMethod paymentMethod;
    private boolean isDeleted;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
