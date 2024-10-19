package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.PackageOfficialAdminDTO;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.dto.request.PackageTourRequest;
import com.ambrose.tripwonder.entities.Gallery;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.RatingReview;
import com.ambrose.tripwonder.entities.FavoritePackage;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.FilterBy;
import com.ambrose.tripwonder.repository.*;
import com.ambrose.tripwonder.repository.specification.PackageSpecification;
import com.ambrose.tripwonder.services.FirebaseService;
import com.ambrose.tripwonder.services.PackageOfficialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackageOfficialServiceImpl implements PackageOfficialService {
    private final PackageOfficialRepository packageOfficialRepository;
    private final GenericConverter<PackageOfficialDTO> mapperToDto;
    private final CategoryRepository categoryRepository;
    private final ProvinceRepository provinceRepository;
    private final SupplierRepository supplierRepository;
    private final FirebaseService firebaseService;
    private final GalleryRepository galleryRepository;
    private final RatingReviewRepository ratingReviewRepository;
    private final UserRepository userRepository;
    private final GenericConverter<PackageOfficialAdminDTO> mapperAdminToDto;
    
    
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
    @Transactional
    public ResponseEntity<?> create(PackageTourRequest packageTourRequest) throws IOException {
        
        List<File> files = packageTourRequest.getGalleries();
        List<Gallery> galleries = new ArrayList<>();
        
        for (File file : files) {
            Gallery gallery = new Gallery();
            gallery.setName(file.getName());
            gallery.setImageUrl(firebaseService.upload(file));
            gallery.setDeleted(false);
            galleries.add(gallery);
        }

        RatingReview ratingReview = new RatingReview();
        ratingReview.setRating(packageTourRequest.getRatingReviews());
        ratingReview.setFeedback("");
        ratingReview.setUser(userRepository.findUserById(packageTourRequest.getStaffId()));
        List<RatingReview> ratingReviews = new ArrayList<>();
        ratingReviews.add(ratingReview);
        PackageTour packageTour = PackageTour.builder()
                .name(packageTourRequest.getName())
                .price(packageTourRequest.getPrice())
                .category(categoryRepository.getCategoryById(packageTourRequest.getCategoryId()))
                .description(packageTourRequest.getDescription())
                .attendance(packageTourRequest.getAttendance())
                .endTime(packageTourRequest.getEndTime())
                .province(provinceRepository.getProvinceById(packageTourRequest.getProvinceId()))
                .startTime(packageTourRequest.getStartTime())
                .supplier(supplierRepository.findSuppliersById(packageTourRequest.getSupplierId()))
                .shortDescription(packageTourRequest.getShortDescription())
                .status(true)
                .galleries(galleries)  // Thêm galleries trực tiếp vào đây
                .ratingReviews(ratingReviews) // Thêm reviews trực tiếp vào đây
                .build();

        // Set liên kết tour trong Gallery và RatingReview
        for (Gallery gallery : galleries) {
            gallery.setPackageTour(packageTour);
            
        }
        for (RatingReview review : ratingReviews) {
            review.setPackageTour(packageTour);
        }

        // Lưu tất cả cùng lúc
        PackageTour savedPackageTour = packageOfficialRepository.save(packageTour);
        for(File file : packageTourRequest.getGalleries()) {
            if (file.exists()) {
                // Xóa file
                file.delete();
            } 
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperToDto.toDTO(savedPackageTour, PackageOfficialDTO.class));
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

    @Override
    public ResponseEntity<?> findAllAdmin(Pageable pageable) {
        Page<PackageTour> packageOfficials = packageOfficialRepository.findAll(pageable);
        Page<PackageOfficialAdminDTO> packageOfficialDTOS = packageOfficials.map(obj -> mapperAdminToDto.toDTO(obj, PackageOfficialAdminDTO.class));
        return ResponseUtil.getCollection(packageOfficialDTOS,
                HttpStatus.OK,
                "ok",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                packageOfficials.getTotalElements());
    }

}
    

