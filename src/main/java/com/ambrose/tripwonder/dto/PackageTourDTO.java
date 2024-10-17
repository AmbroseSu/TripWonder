package com.ambrose.tripwonder.dto;

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
  private Date startTime;
  private Date endTime;
  private Date date;
  private int attendance;
  private boolean status;
}
