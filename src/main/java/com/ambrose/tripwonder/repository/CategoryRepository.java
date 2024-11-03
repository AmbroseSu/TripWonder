package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT ca FROM Category ca")
    List<Category> getAllCategoryUsePageable(Pageable pageable);

    @Query("SELECT ca FROM Category ca")
    List<Category> getAllCategory();

    Category getCategoryById(long categoryId);

    Category getCategoryByName(String name);

}
