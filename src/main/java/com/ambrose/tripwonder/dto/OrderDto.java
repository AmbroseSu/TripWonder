package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.Payment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Double totalPrice = 0.0;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private Payment status;
    @Setter(AccessLevel.NONE)
    private String name;

    public void setUser(User user) {
        name = user.getFullname();
    }
}
