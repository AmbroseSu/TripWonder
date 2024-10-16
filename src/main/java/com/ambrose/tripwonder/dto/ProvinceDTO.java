package com.ambrose.tripwonder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceDTO {
  private Long id;
  private String name;
  private boolean status;
}
