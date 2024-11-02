package com.ambrose.tripwonder.dto;

import com.ambrose.tripwonder.entities.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class OrderGetAllDto {
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
        if(LocalDateTime.now().isAfter(endTime)) {
            state = "Done";
        }
        else {
            state = "Active";
        }
    }
}
