package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.CheckOutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@CrossOrigin
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckOutService checkoutService;
    @Value("${fe.success}")
    public String successUrl;
    @Value("${fe.cancel}")
    public String cancelUrl;

    @GetMapping("/success")
    public void success(
            HttpServletResponse response,
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam long orderCode
    ) {
        response.setHeader("Location", successUrl);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        checkoutService.successfulCheckout(orderCode);
    }

    @GetMapping("/cancel")
    public void cancel(
            HttpServletResponse response,
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam long orderCode
    ) {
        response.setHeader("Location", cancelUrl);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        checkoutService.failedCheckout(orderCode);
    }

    @PostMapping("/linkPay")
    public ResponseEntity<?> checkout(HttpServletRequest request, long userId) throws Exception {
        return checkoutService.getLink(request, userId);
    }

}