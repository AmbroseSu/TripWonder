package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Category;
import com.ambrose.tripwonder.entities.Province;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
  @Query("SELECT pr FROM Province pr")
  List<Province> getAllProvinceUsePageable(Pageable pageable);

  @Query("SELECT pr FROM Province pr")
  List<Province> getAllProvince();
  Province getProvinceById(long provinceId);

  Province getProvinceByName(String name);
}
