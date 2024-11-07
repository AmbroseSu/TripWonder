package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private String orderDetailId;
    private Integer quantity;
    private PackageOfficialAdminDTO packageOfficialAdmin;
    private Long orderId;
    
    public OrderDetailDto(String orderDetailId, Integer quantity,Long packageId, Long orderId) {
        
    }
}
