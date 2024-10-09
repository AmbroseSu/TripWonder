package com.ambrose.tripwonder.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterBy {
    private Long categoryId;
    private String status;
    private Double minPrice = 0.0;
    private Double maxPrice = Double.MAX_VALUE;
}
