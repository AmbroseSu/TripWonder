package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageTourRepository extends JpaRepository<PackageTour, String> {

    PackageTour getPackageTourById(Long packageId);

    List<PackageTour> findAllByStatus(boolean status);
}
