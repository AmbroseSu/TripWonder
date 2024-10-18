package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.FavoritePackage;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.FilterBy;
import com.ambrose.tripwonder.repository.PackageOfficialRepository;
import com.ambrose.tripwonder.repository.specification.PackageSpecification;
import com.ambrose.tripwonder.services.PackageOfficialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return packageOfficialRepository.findAll().stream()
                .map(packageOfficial -> mapperToDto.toDTO(packageOfficial, PackageOfficialDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<PackageTour> packageOfficials = packageOfficialRepository.findAll(pageable);
        Page<PackageOfficialDTO> packageOfficialDTOS = packageOfficials.map(obj -> mapperToDto.toDTO(obj, PackageOfficialDTO.class));
        return ResponseUtil.getCollection(packageOfficialDTOS,
                HttpStatus.OK,
                "ok",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                packageOfficials.getTotalElements());
    }

    @Override
    public ResponseEntity<?> getFilteredTours(FilterBy filterBy, Pageable pageable) {
        Specification<PackageTour> specification = Specification
                .where(PackageSpecification.hasCategory(filterBy.getCategoryId()))    // Lọc theo category
                .and(PackageSpecification.hasStatus(filterBy.getStatus()))            // Lọc theo status
                .and(PackageSpecification.priceBetween(filterBy.getMinPrice(), filterBy.getMaxPrice())); // Lọc theo khoảng giá
        Page<PackageTour> page = packageOfficialRepository.findAll(specification, pageable);
        Page<PackageOfficialDTO> pageDTOS = page.map(obj -> mapperToDto.toDTO(obj, PackageOfficialDTO.class));
        
        // Trả về dữ liệu phân trang và lọc theo điều kiện
        return  ResponseUtil.getCollection(pageDTOS,
                HttpStatus.OK,
                "ok",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                packageOfficialRepository.findAll(specification, pageable).getTotalElements());
    }

    @Override
    public ResponseEntity<?> search(String query, Pageable pageable) {
        Specification<PackageTour> specification = Specification
                .where(PackageSpecification.hasNameLike(query));
        Page<PackageTour> page = packageOfficialRepository.findAll(specification, pageable);
        Page<PackageOfficialDTO> packageOfficialDTOS = page.map(obj -> mapperToDto.toDTO(obj, PackageOfficialDTO.class));
        
        return ResponseUtil.getCollection(
                packageOfficialDTOS,
                HttpStatus.OK,
                "ok",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                page.getTotalElements()
        );
    }

    @Override
    public ResponseEntity<?> create(File file) {
        return null;
    }

    @Override
    public ResponseEntity<?> getPackageOfficialById(long packageOfficialId) {
        try{
            PackageTour packageTour = packageOfficialRepository.findPackageTourById(packageOfficialId);
            if (packageTour == null){
                return ResponseUtil.error("Package Tour not exists", "Faild", HttpStatus.BAD_REQUEST);
            }
            PackageOfficialDTO packageOfficialDTO = mapperToDto.toDTO(packageTour, PackageOfficialDTO.class);
            return ResponseUtil.getObject(packageOfficialDTO, HttpStatus.CREATED, "Successfully Create");
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseUtil.error(ex.getMessage(),"Failed", HttpStatus.BAD_REQUEST);
        }
    }


}
    

