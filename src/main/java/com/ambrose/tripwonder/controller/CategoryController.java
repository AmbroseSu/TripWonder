package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.services.CategoryService;
import com.ambrose.tripwonder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/get-all")
  public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int limit){
    return categoryService.getAllCategory(page, limit);
  }

  @PostMapping("/create")
  public ResponseEntity<?> createCategory(@RequestParam(value = "name") String name){
    return categoryService.createCategory(name);
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateCategory(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name){
    return categoryService.updateCategory(id, name);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteCategory(@RequestParam(value = "id") long id){
    return categoryService.deleteCategory(id);
  }

}
