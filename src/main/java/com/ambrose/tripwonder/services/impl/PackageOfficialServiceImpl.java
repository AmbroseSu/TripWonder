package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.PackageOfficial;
import com.ambrose.tripwonder.entities.enums.SortBy;
import com.ambrose.tripwonder.repository.PackageOfficialRepository;
import com.ambrose.tripwonder.services.PackageOfficialService;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PackageOfficialServiceImpl implements PackageOfficialService {
    private final PackageOfficialRepository packageOfficialRepository;
    private final GenericConverter<PackageOfficialDTO> mapperToDto;

    @Override
    public PackageOfficialDTO findOne(long id) {
        return null;
    }

    @Override
    public List<PackageOfficialDTO> findAll() {
        return List.of();
    }
    
    @Override
    public List<PackageOfficialDTO> findAll(SortBy sortBy) {
        List<PackageOfficial> packageOfficials = packageOfficialRepository.findAll();

        switch (sortBy) {
            case SORT_BY_NUM_ATTENDANCE_ASC -> packageOfficials.sort(Comparator.comparingInt(PackageOfficial::getNumberAttendance));
            case SORT_BY_NUM_ATTENDANCE_DESC -> packageOfficials.sort((o1, o2) -> Integer.compare(o2.getNumberAttendance(), o1.getNumberAttendance()));
            case SORT_BY_PRICE_ASC -> packageOfficials.sort((o1, o2) -> Float.compare(o1.getPrice(), o2.getPrice()));
            case SORT_BY_PRICE_DESC -> packageOfficials.sort((o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice()));
            case SORT_BY_DATE_ASC -> packageOfficials.sort(Comparator.comparing(PackageOfficial::getDate));
            case SORT_BY_DATE_DESC -> packageOfficials.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }
        return packageOfficials.stream()
                .map(packageOfficial -> mapperToDto.toDTO(packageOfficial, PackageOfficialDTO.class))
                .collect(Collectors.toList());
    }


}
    

