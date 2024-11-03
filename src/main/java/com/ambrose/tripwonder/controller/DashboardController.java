package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/totalRevenues")
    public ResponseEntity<?> totalRevenues() {
        return ResponseUtil.getObject(dashboardService.totalRevenues(), HttpStatus.OK, "Total Revenues");
    }

    @GetMapping("/totalSuppliers")
    public ResponseEntity<?> totalSuppliers() {
        return ResponseUtil.getObject(dashboardService.totalSupplier(), HttpStatus.OK, "Total Suppliers");
    }

    @GetMapping("/totalTours")
    public ResponseEntity<?> totalTours() {
        return ResponseUtil.getObject(dashboardService.totalPackageTour(), HttpStatus.OK, "Total Tours");
    }

    @GetMapping("/totalOrders")
    public ResponseEntity<?> totalOrders() {
        return ResponseUtil.getObject(dashboardService.totalOrder(), HttpStatus.OK, "Total Orders");
    }

    @GetMapping("/totalGender")
    public ResponseEntity<?> totalGender() {
        return ResponseUtil.getObject(dashboardService.getGenders(), HttpStatus.OK, "Total Gender");
    }

    @GetMapping("/topFiveOrder")
    public ResponseEntity<?> topFiveOrder() {
        return ResponseUtil.getObject(dashboardService.getTopFiveOrders(), HttpStatus.OK, "Top Five Orders");
    }

    @GetMapping("/topFiveTours")
    public ResponseEntity<?> topFiveTours() {
        return ResponseUtil.getObject(dashboardService.getTopFivePackageTours(), HttpStatus.OK, "Top Five Tours");
    }
}
