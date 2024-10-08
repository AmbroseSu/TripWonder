package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageOfficialRepository extends JpaRepository<PackageTour, Long> {
    Page<PackageTour> findAll(Pageable pageable);
}
