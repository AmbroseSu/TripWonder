package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.dto.SupplierDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface SupplierService {
    ResponseEntity<?> create(SupplierDTO supplier); 
    ResponseEntity<?> update(SupplierDTO supplier);
    ResponseEntity<?> delete(UUID id);
    ResponseEntity<?> findOne(UUID id);
    ResponseEntity<?> findAll(Pageable pageable);
}
