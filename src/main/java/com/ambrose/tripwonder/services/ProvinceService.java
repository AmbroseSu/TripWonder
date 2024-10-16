package com.ambrose.tripwonder.services;

import org.springframework.http.ResponseEntity;

public interface ProvinceService {

  ResponseEntity<?> getAllProvince(int page, int limit);
  ResponseEntity<?> createProvince(String name);
  ResponseEntity<?> deleteProvince(long provinceId);
  ResponseEntity<?> updateProvince(long provinceId, String name);

}
