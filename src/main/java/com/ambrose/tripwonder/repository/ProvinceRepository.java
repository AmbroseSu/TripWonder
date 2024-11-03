package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    @Query("SELECT pr FROM Province pr")
    List<Province> getAllProvinceUsePageable(Pageable pageable);

    @Query("SELECT pr FROM Province pr")
    List<Province> getAllProvince();

    Province getProvinceById(long provinceId);

    Province getProvinceByName(String name);
}
