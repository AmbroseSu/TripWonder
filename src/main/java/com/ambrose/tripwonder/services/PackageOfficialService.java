package com.ambrose.tripwonder.services;

import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.PackageOfficial;

import java.util.List;

public interface PackageOfficialService {
    List<PackageOfficialDTO> findAll(int sortBy);
    PackageOfficialDTO findOne(long id);
    List<PackageOfficialDTO> findAll();
    List<PackageOfficialDTO> findAll(int sort);
}
