package com.ambrose.tripwonder.controller;

import java.util.Date;

import com.ambrose.tripwonder.services.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@RestController
@RequestMapping("/api/v1/checkout")
@CrossOrigin
@RequiredArgsConstructor
public class CheckoutController {
    
    private final CheckOutService checkoutService;
    
    @GetMapping("/success")
    public void success(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam long orderCode
    ){
        checkoutService.successfulCheckout(orderCode);
    }

    @GetMapping("/cancel")
    public void cancel(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam long orderCode
    ){
        checkoutService.failedCheckout(orderCode);
    }
    
    @PostMapping( "/linkPay")
    public ResponseEntity<?> checkout(HttpServletRequest request,long userId) throws Exception {
        return checkoutService.getLink(request, userId);
    }
    
}