package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.LocationDto;
import com.ambrose.tripwonder.dto.PackageOfficialAdminDTO;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.dto.request.LocationRequest;
import com.ambrose.tripwonder.dto.request.PackageTourRequest;
import com.ambrose.tripwonder.entities.Gallery;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.RatingReview;
import com.ambrose.tripwonder.entities.TourLocation;
import com.ambrose.tripwonder.entities.enums.FilterBy;
import com.ambrose.tripwonder.repository.*;
import com.ambrose.tripwonder.repository.specification.PackageSpecification;
import com.ambrose.tripwonder.services.FirebaseService;
import com.ambrose.tripwonder.services.PackageOfficialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final GenericConverter<LocationDto> mapperLocationToDto;

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
        return ResponseUtil.getCollection(pageDTOS,
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

    private String getNameFile(String url) {
        String decodedUrl = url.replace("%2F", "/");

        // Sử dụng regex để tìm tên tệp
        String regex = "([^/]+\\.png)(?=[^/]*$)"; // Tìm 'cat4.png' trong đường dẫn
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(decodedUrl);
        String fileName;

        if (matcher.find()) {
            fileName = matcher.group(1);
        } else {
            fileName = UUID.randomUUID().toString();
        }
        return fileName;
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(PackageTourRequest packageTourRequest) throws IOException {

        List<String> galleryDtos = packageTourRequest.getGalleries();
        List<Gallery> galleries = new ArrayList<>();

        for (String file : galleryDtos) {
            Gallery gallery = new Gallery();
            gallery.setName(getNameFile(file));
            gallery.setImageUrl(file);
            gallery.setDeleted(false);
            galleries.add(gallery);
        }
        List<TourLocation> tourLocations = new ArrayList<>();
        for(LocationRequest locationRequest : packageTourRequest.getLocations()) {
            TourLocation tourLocation = getTourLocation(locationRequest);
            tourLocations.add(tourLocation);
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
                .tourLocations(tourLocations)
                .build();

        // Set liên kết tour trong Gallery và RatingReview
        for (Gallery gallery : galleries) {
            gallery.setPackageTour(packageTour);

        }
        for (TourLocation tourLocation : tourLocations) {
            tourLocation.setPackageTour(packageTour);
        }
        for (RatingReview review : ratingReviews) {
            review.setPackageTour(packageTour);
        }

        // Lưu tất cả cùng lúc
        PackageTour savedPackageTour = packageOfficialRepository.save(packageTour);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapperToDto.toDTO(savedPackageTour, PackageOfficialDTO.class));
    }

    private static TourLocation getTourLocation(LocationRequest locationRequest) {
        TourLocation tourLocation = new TourLocation();
        tourLocation.setLatitude(locationRequest.getLatitude());
        tourLocation.setLongitude(locationRequest.getLongitude());
        tourLocation.setName(locationRequest.getName());
        tourLocation.setFacilitate(locationRequest.getFacilitate());
        tourLocation.setEndDate(locationRequest.getEndTime().toLocalDate());
        tourLocation.setStartDate(locationRequest.getStartTime().toLocalDate());
        tourLocation.setEndTime(locationRequest.getEndTime().toLocalTime());
        tourLocation.setStartTime(locationRequest.getStartTime().toLocalTime());
        return tourLocation;
    }

    @Override
    public ResponseEntity<?> getPackageOfficialById(long packageOfficialId) {
        try {
            PackageTour packageTour = packageOfficialRepository.findPackageTourById(packageOfficialId);
            if (packageTour == null) {
                return ResponseUtil.error("Package Tour not exists", "Faild", HttpStatus.BAD_REQUEST);
            }
            PackageOfficialDTO packageOfficialDTO = mapperToDto.toDTO(packageTour, PackageOfficialDTO.class);
            return ResponseUtil.getObject(packageOfficialDTO, HttpStatus.CREATED, "Successfully Create");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseUtil.error(ex.getMessage(), "Failed", HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<?> getAllDetailTour(long tourId) {
        List<TourLocation> tourLocations = packageOfficialRepository.findAllTourLocations(tourId);
        PackageTour packageTour = packageOfficialRepository.findPackageTourById(tourId);
        LocalDateTime startTime = packageTour.getStartTime();
        LocalDateTime endTime = packageTour.getEndTime();
        long totalDay = ChronoUnit.DAYS.between(startTime, endTime);
        List<LocationDto> locationDtos = tourLocations.stream()
                .map(x -> mapperLocationToDto.toDTO(x, LocationDto.class)).toList();
        Map<Long, List<LocationDto>> listMap = new LinkedHashMap<>();
        for(long i =1;i<=totalDay;i++) {
            List<LocationDto> subLocationDtos = new ArrayList<>();
            for(LocationDto locationDto : locationDtos) {
                if(locationDto.getDays().contains((int)i)) {
                    locationDto.setDayString("Day:"+i);
                    
                    subLocationDtos.add(new LocationDto(locationDto));
                }
            }
            listMap.put(i,subLocationDtos);
        }
        return ResponseUtil.getCollection(listMap, HttpStatus.OK, "totalDay,List location", 0, 0, 0);
    }
}
    

