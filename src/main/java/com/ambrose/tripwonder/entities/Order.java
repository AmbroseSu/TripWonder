package com.ambrose.tripwonder.entities;

import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
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
    @Setter(AccessLevel.NONE)
    private Double totalPrice = 0.0;
    private LocalDateTime orderDate;
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private Payment status;
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getTotalPrice();
            orderDetail.setOrder(this);  // Đặt mối quan hệ ngược lại
        }
    }

}
