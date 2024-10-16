package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.CategoryDTO;
import com.ambrose.tripwonder.dto.UpsertUserDTO;
import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.repository.CategoryRepository;
import com.ambrose.tripwonder.services.CategoryService;
import java.util.ArrayList;
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
      List<CategoryDTO> categoryDTOS = convertCategorytoCategoryDTO(categories);
      return ResponseUtil.getCollection(categoryDTOS, HttpStatus.OK, "Update Successfully", page, limit, count);
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> createCategory(String name) {
    try{
      if(categoryRepository.getCategoryByName(name) != null){
        return ResponseUtil.error("Name exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      Category category = new Category();
      category.setName(name);
      category.setDelete(false);
      categoryRepository.save(category);
      CategoryDTO result = (CategoryDTO) genericConverter.toDTO(category, CategoryDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Save Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> deleteCategory(long categoryId) {
    try{
      Category category = categoryRepository.getCategoryById(categoryId);
      if (category == null){
        return ResponseUtil.error("Category not exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      if(!category.isDelete()){
        category.setDelete(true);
      }else{
        category.setDelete(false);
      }
      categoryRepository.save(category);
      CategoryDTO result = (CategoryDTO) genericConverter.toDTO(category, CategoryDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> updateCategory(long categoryId, String name) {
    try{
      Category category = categoryRepository.getCategoryById(categoryId);
      if (category == null){
        return ResponseUtil.error("Category not exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      List<Category> categories = categoryRepository.getAllCategory();

      for (Category category1 : categories){
        if (category1.getName().equalsIgnoreCase(category.getName())){
          return ResponseUtil.error("Category name exists", "Faild", HttpStatus.BAD_REQUEST);
        }
      }
      category.setName(name);
      categoryRepository.save(category);
      CategoryDTO result = (CategoryDTO) genericConverter.toDTO(category, CategoryDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }


  public List<CategoryDTO> convertCategorytoCategoryDTO(List<Category> categories){
    List<CategoryDTO> categoryDTOS = new ArrayList<>();
    for (Category category : categories){
      CategoryDTO result = (CategoryDTO) genericConverter.toDTO(category, CategoryDTO.class);
      categoryDTOS.add(result);
    }
    return categoryDTOS;
  }




}
