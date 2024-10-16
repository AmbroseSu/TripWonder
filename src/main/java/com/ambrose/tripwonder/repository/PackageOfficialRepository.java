package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageOfficialRepository extends JpaRepository<PackageTour, Long>, JpaSpecificationExecutor<PackageTour> {
    Page<PackageTour> findAll(Pageable pageable);
    Page<PackageTour> findAll(Specification specification, Pageable pageable);
    
    @Query(value = "select p from PackageTour p where p.name like %?1%")
    List<PackageTour> search(String keyword);
}
 