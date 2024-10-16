package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Category;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

  @Query("SELECT ca FROM Category ca")
  List<Category> getAllCategory(Pageable pageable);

}
