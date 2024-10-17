package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageTour;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTourRepository extends JpaRepository<PackageTour, String> {

  PackageTour getPackageTourById(Long packageId);

}
