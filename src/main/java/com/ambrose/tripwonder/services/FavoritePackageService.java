package com.ambrose.tripwonder.services;

import org.springframework.http.ResponseEntity;

public interface FavoritePackageService {
  ResponseEntity<?> getAllFavoritePackage(Long userId, int page, int limit);
}
