package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.entities.enums.SortBy;
import com.ambrose.tripwonder.services.PackageOfficialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ) {
        Sort sort = Sort.by(sortBy.getDirection(), sortBy.getField());
        Pageable pageable = PageRequest.of(page, size,sort);
        return new ResponseEntity<>(packageOfficialService.findAll(pageable), HttpStatus.OK);
    }
}
