package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.OrderDetail;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSuperDetailDto {
    private Long id;
    private Double totalPrice;
    private int quantity;
    private Long packageId;
    private String packageName;
    private Double packagePrice;
    
    public OrderSuperDetailDto(OrderDetail orderDetail) {
        id = orderDetail.getId();
        totalPrice = orderDetail.getTotalPrice();
        quantity = orderDetail.getQuantity();
        packageId = orderDetail.getPackageId();
        packageName = orderDetail.getPackageTour().getName();
        packagePrice = orderDetail.getPackageTour().getPrice();
    }
}
