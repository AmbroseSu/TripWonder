package com.ambrose.tripwonder.controller;


import com.ambrose.tripwonder.services.CategoryService;
import com.ambrose.tripwonder.services.FavoritePackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favorite_package")
@RequiredArgsConstructor
@CrossOrigin
public class FavoritePackageController {

  private final FavoritePackageService favoritePackageService;

  @GetMapping("/get-all-favorite-package-by-user-id")
  public ResponseEntity<?> getAll(@RequestParam(value = "userId") Long userId, @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int limit){
    return favoritePackageService.getAllFavoritePackage(userId, page, limit);
  }

}
