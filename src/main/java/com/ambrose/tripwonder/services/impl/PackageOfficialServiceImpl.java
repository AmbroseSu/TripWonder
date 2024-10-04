package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.PackageOfficial;
import com.ambrose.tripwonder.entities.enums.SortBy;
import com.ambrose.tripwonder.repository.PackageOfficialRepository;
import com.ambrose.tripwonder.services.PackageOfficialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PackageOfficialServiceImpl implements PackageOfficialService {
    private final PackageOfficialRepository packageOfficialRepository;
    private final GenericConverter<PackageOfficialDTO> mapperToDto;
    
    public List<PackageOfficialDTO> getSortedEvents(int page, int size, SortBy sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy.getDirection(), sortBy.getField()));
        return packageOfficialRepository.findAllBy(pageable).toList().stream()
                .map(packageOfficial -> mapperToDto.toDTO(packageOfficial,PackageOfficialDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PackageOfficialDTO findOne(long id) {
        return null;
    }

    @Override
    public List<PackageOfficialDTO> findAll() {
        return List.of();
    }
}
