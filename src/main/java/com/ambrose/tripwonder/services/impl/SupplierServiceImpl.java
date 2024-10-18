package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.SupplierDTO;
import com.ambrose.tripwonder.entities.Supplier;
import com.ambrose.tripwonder.repository.specification.SupplierRepository;
import com.ambrose.tripwonder.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    
    private final SupplierRepository supplierRepository;
    private final GenericConverter<Supplier> supplierConverter;
    private final GenericConverter<SupplierDTO> supplierDTOConverter;

    @Override
    public ResponseEntity<?> create(SupplierDTO supplier) {
        
        return ResponseUtil.getObject(supplierRepository.save(supplierConverter.toEntity(supplier,Supplier.class)), HttpStatus.CREATED,"Create success");
    }

    @Override
    public ResponseEntity<?> update(SupplierDTO supplier) {
        Supplier supplierEntity = supplierRepository.findSuppliersById(supplier.getId());
        supplierEntity.setAddress( supplier.getAddress());
        supplierEntity.setName( supplier.getName());
        supplierEntity.setStatus(supplier.isStatus());
        supplierEntity.setContactEmail(supplier.getContactEmail());
        supplierEntity.setContactPhone(supplier.getContactPhone());
        supplierRepository.save(supplierEntity);
        return ResponseUtil.getObject(supplier, HttpStatus.CREATED,"Update success");
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        Supplier supplierEntity = supplierRepository.findSuppliersById(id);
        supplierEntity.setStatus(false);
        supplierRepository.save(supplierEntity);
        return ResponseEntity.ok("Delete success");
    }

    @Override
    public ResponseEntity<?> findOne(UUID id) {
        Supplier supplierEntity = supplierRepository.findSuppliersById(id);
        return ResponseUtil.getObject(supplierDTOConverter.toDTO(supplierEntity,SupplierDTO.class), HttpStatus.OK,"Find one success");
    }

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);
        Page<SupplierDTO> supplierDTOS = suppliers.map(supplier -> supplierDTOConverter.toDTO(supplier,SupplierDTO.class));
        for (SupplierDTO supplierDTO : supplierDTOS) {
            supplierDTO.setTours(new ArrayList<>());
        }
        return ResponseUtil.getCollection(supplierDTOS,HttpStatus.OK,"ok",pageable.getPageNumber(), pageable.getPageSize(), suppliers.getTotalElements());
    }
}
