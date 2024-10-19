package com.ambrose.tripwonder.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageTourDTO {
  private Long id;
  private String name;
  private String description;
  private String shortDescription;
  private double price;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private int attendance;
  private boolean status;
}
