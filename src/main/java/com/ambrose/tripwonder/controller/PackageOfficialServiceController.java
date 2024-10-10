package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.entities.enums.FilterBy;
import com.ambrose.tripwonder.entities.enums.SortBy;
import com.ambrose.tripwonder.services.PackageOfficialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/packageOff")
@RequiredArgsConstructor
public class PackageOfficialServiceController {

    private final PackageOfficialService packageOfficialService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "datedesc") SortBy sortBy,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
//            case "attendanceasc":
//                    return SortBy.SORT_BY_NUM_ATTENDANCE_ASC;
//            case "attendancedesc":
//                    return SortBy.SORT_BY_NUM_ATTENDANCE_DESC;
//            case "dateasc":
//                    return SortBy.SORT_BY_DATE_ASC;
//            case "datedesc":
//                    return SortBy.SORT_BY_DATE_DESC;
//            case "priceasc":
//                    return SortBy.SORT_BY_PRICE_ASC;
//            case "pricedesc":
//                    return SortBy.SORT_BY_PRICE_DESC;
    ) {
        FilterBy filterBy = FilterBy.builder()
                .categoryId(category)
                .status(status)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        Sort sort = Sort.by(sortBy.getDirection(), sortBy.getField());
        Pageable pageable = PageRequest.of(page, size, sort);
        if (category != null && status != null && minPrice != 0.0 && maxPrice != Double.MAX_VALUE) {
            return  packageOfficialService.getFilteredTours(filterBy,pageable);
        }
        else
            return packageOfficialService.findAll(pageable);
        
    }

//    @GetMapping("/get")
//    public ResponseEntity<?> filterBy(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "datedesc") SortBy sortBy,
////            case "attendanceasc":
////                    return SortBy.SORT_BY_NUM_ATTENDANCE_ASC;
////            case "attendancedesc":
////                    return SortBy.SORT_BY_NUM_ATTENDANCE_DESC;
////            case "dateasc":
////                    return SortBy.SORT_BY_DATE_ASC;
////            case "datedesc":
////                    return SortBy.SORT_BY_DATE_DESC;
////            case "priceasc":
////                    return SortBy.SORT_BY_PRICE_ASC;
////            case "pricedesc":
////                    return SortBy.SORT_BY_PRICE_DESC;
//    ) {
//        
//        Sort sort = Sort.by(sortBy.getDirection(), sortBy.getField());
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return new ResponseEntity<>(packageOfficialService.getFilteredTours(filterBy,pageable), HttpStatus.OK);
//    }
}
