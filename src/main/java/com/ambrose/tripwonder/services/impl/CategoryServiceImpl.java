package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.repository.CategoryRepository;
import com.ambrose.tripwonder.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final GenericConverter genericConverter;
  @Override
  public ResponseEntity<?> getAllCategory(int page, int limit) {
    try{
      Pageable pageable = PageRequest.of(page - 1, limit);
      List<Category> categories = categoryRepository.getAllCategory(pageable);
      long count = categories.stream().count();
      return ResponseUtil.getCollection(categories, HttpStatus.OK, "Update Successfully", page, limit, count);
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }
}
