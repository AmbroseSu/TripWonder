package com.ambrose.tripwonder.controller;

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
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam SortBy sortBy
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
        Sort sort = Sort.by(sortBy.getDirection(), sortBy.getField());
        Pageable pageable = PageRequest.of(page, size, sort);
        return new ResponseEntity<>(packageOfficialService.findAll(pageable), HttpStatus.OK);
    }
}
