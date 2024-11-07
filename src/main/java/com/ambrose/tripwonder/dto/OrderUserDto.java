package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.enums.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUserDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Payment orderStatus;
    private Double totalPrice;
    private Long userId;
    private String userName;
}
