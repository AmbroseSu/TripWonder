package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    Page<Supplier> findAll(Pageable pageable);
    Supplier findSuppliersById(UUID id);
}
