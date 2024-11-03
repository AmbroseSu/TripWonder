package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int limit) {
        return categoryService.getAllCategory(page, limit);
    }

    @GetMapping("/get-number-of-category")
    public ResponseEntity<?> getNumberOfCategory() {
        return categoryService.getNumberOfCategory();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestParam(value = "name") String name) {
        return categoryService.createCategory(name);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name) {
        return categoryService.updateCategory(id, name);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam(value = "id") long id) {
        return categoryService.deleteCategory(id);
    }

}
