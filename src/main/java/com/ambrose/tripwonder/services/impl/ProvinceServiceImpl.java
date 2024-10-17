package com.ambrose.tripwonder.services.impl;


import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.CategoryDTO;
import com.ambrose.tripwonder.dto.ProvinceDTO;
import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.entities.Province;
import com.ambrose.tripwonder.repository.CategoryRepository;
import com.ambrose.tripwonder.repository.ProvinceRepository;
import com.ambrose.tripwonder.services.ProvinceService;
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
public class ProvinceServiceImpl implements ProvinceService {
  private final ProvinceRepository provinceRepository;
  private final GenericConverter genericConverter;


  @Override
  public ResponseEntity<?> getAllProvince(int page, int limit) {
    try{
      Pageable pageable = PageRequest.of(page - 1, limit);
      List<Province> provinces = provinceRepository.getAllProvinceUsePageable(pageable);
      long count = provinces.stream().count();
      List<ProvinceDTO> provinceDTOS = convertProvincetoProvinceDTO(provinces);
      return ResponseUtil.getCollection(provinceDTOS, HttpStatus.OK, "Update Successfully", page, limit, count);
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> getNumberOfProvince() {
    try{
      List<Province> provinces = provinceRepository.getAllProvince();
      long count = provinces.stream().count();
      return ResponseUtil.getObject(count, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> createProvince(String name) {
    try{
      if(provinceRepository.getProvinceByName(name) != null){
        return ResponseUtil.error("Name exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      Province province = new Province();
      province.setName(name);
      province.setStatus(true);
      provinceRepository.save(province);
      ProvinceDTO result = (ProvinceDTO) genericConverter.toDTO(province, ProvinceDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Save Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> deleteProvince(long provinceId) {
    try{
      Province province = provinceRepository.getProvinceById(provinceId);
      if (province == null){
        return ResponseUtil.error("Category not exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      if(!province.isStatus()){
        province.setStatus(true);
      }else{
        province.setStatus(false);
      }
      provinceRepository.save(province);
      ProvinceDTO result = (ProvinceDTO) genericConverter.toDTO(province, ProvinceDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> updateProvince(long provinceId, String name) {
    try{
      Province province = provinceRepository.getProvinceById(provinceId);
      if (province == null){
        return ResponseUtil.error("Category not exists", "Faild", HttpStatus.BAD_REQUEST);
      }
      List<Province> provinces = provinceRepository.getAllProvince();

      for (Province province1 : provinces){
        if (province1.getName().equalsIgnoreCase(name)){
          return ResponseUtil.error("Category name exists", "Faild", HttpStatus.BAD_REQUEST);
        }
      }
      province.setName(name);
      provinceRepository.save(province);
      ProvinceDTO result = (ProvinceDTO) genericConverter.toDTO(province, ProvinceDTO.class);
      return ResponseUtil.getObject(result, HttpStatus.OK, "Update Successfully");
    }catch (Exception ex){
      ex.printStackTrace();
      return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
    }
  }

  public List<ProvinceDTO> convertProvincetoProvinceDTO(List<Province> provinces){
    List<ProvinceDTO> provinceDTOS = new ArrayList<>();
    for (Province province : provinces){
      ProvinceDTO result = (ProvinceDTO) genericConverter.toDTO(province, ProvinceDTO.class);
      provinceDTOS.add(result);
    }
    return provinceDTOS;
  }

}
