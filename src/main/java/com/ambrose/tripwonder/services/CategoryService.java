package com.ambrose.tripwonder.services;

import org.springframework.http.ResponseEntity;

public interface CategoryService {

  ResponseEntity<?> getAllCategory(int page, int limit);
  ResponseEntity<?> createCategory(String name);
  ResponseEntity<?> deleteCategory(long categoryId);
  ResponseEntity<?> updateCategory(long categoryId, String name);

}
