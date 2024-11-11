package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.OrderDetail;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    
    private GenericConverter<UserDTO> userConverter;
    
    private Long id;
    private Double totalPrice = 0.0;
    private LocalDateTime orderDate;
    private Long orderCode;
    private LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;
    private Payment status;
    private Boolean isDeleted = false;
    private List<OrderSuperDetailDto> orderDetails;
    private UserDTO user;
    
    public OrderDetailDto(Order order,List<OrderDetail> orderDetails) {
        id = order.getId();
        orderDate = order.getOrderDate();
        orderCode = order.getOrderCode();
        paymentDate = order.getPaymentDate();
        paymentMethod = order.getPaymentMethod();
        status = order.getStatus();
        isDeleted = order.isDeleted();
        totalPrice = order.getTotalPrice();
        user = userConverter.toDTO(order.getUser(), UserDTO.class);
        this.orderDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetails.add(new OrderSuperDetailDto(orderDetail));
        }
    }
}
