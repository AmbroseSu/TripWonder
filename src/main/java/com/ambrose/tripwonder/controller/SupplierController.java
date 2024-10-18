package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.dto.SupplierDTO;
import com.ambrose.tripwonder.entities.Supplier;
import com.ambrose.tripwonder.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
@CrossOrigin
public class SupplierController {
    
    private final SupplierService supplierService;
    
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(required = false) UUID id,
                                 @RequestParam(required = false,defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "10") int pageSize
                                 ) {
        
        if (id == null) {
            Pageable pageable = PageRequest.of(page,pageSize);
            return supplierService.findAll(pageable);
        }
        else {
            return supplierService.findOne(id);
        }
        
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SupplierDTO supplier) {
        
        return supplierService.create(supplier);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SupplierDTO supplier) {
        return supplierService.update(supplier);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        return supplierService.delete(id);
    }
}
