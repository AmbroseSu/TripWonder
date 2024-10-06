package com.ambrose.tripwonder.services;

import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.enums.SortBy;

import java.util.List;

public interface PackageOfficialService {
    PackageOfficialDTO findOne(long id);

    List<PackageOfficialDTO> findAll();

    List<PackageOfficialDTO> findAll(SortBy sortBy);
}
