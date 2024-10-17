package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.CategoryService;
import com.ambrose.tripwonder.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/province")
@RequiredArgsConstructor
@CrossOrigin
public class ProvinceController {

  private final ProvinceService provinceService;

  @GetMapping("/get-all")
  public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int limit){
    return provinceService.getAllProvince(page, limit);
  }

  @GetMapping("/get-number-of-province")
  public ResponseEntity<?> getNumberOfProvince(){
    return provinceService.getNumberOfProvince();
  }

  @PostMapping("/create")
  public ResponseEntity<?> createProvince(@RequestParam(value = "name") String name){
    return provinceService.createProvince(name);
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateProvince(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name){
    return provinceService.updateProvince(id, name);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteProvince(@RequestParam(value = "id") long id){
    return provinceService.deleteProvince(id);
  }

}
