package com.ambrose.tripwonder.services;

import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.enums.SortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PackageOfficialService {
    PackageOfficialDTO findOne(long id);

    List<PackageOfficialDTO> findAll();

    ResponseEntity<?> findAll(Pageable pageable);
}
