package com.ambrose.tripwonder.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class OrderGetAllDto {
    private Long id;
    private String name;
    @Setter(AccessLevel.NONE)
    private String state;
    private LocalDateTime startTime;
    @Setter(AccessLevel.NONE)
    private LocalDateTime endTime;
    private CategoryDTO category;
    private Long orderCode;

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()); // Chuyển sang múi giờ hệ thống
        LocalDateTime adjustedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();

        if (adjustedDateTime.isAfter(endTime)) {
            state = "Done";
        } else {
            state = "Active";
        }
    }
}
