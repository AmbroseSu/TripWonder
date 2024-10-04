package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageOfficial;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageOfficialRepository extends JpaRepository<PackageOfficial, Long> {
    Page<PackageOfficial> findAllBy(Pageable pageable);
}
