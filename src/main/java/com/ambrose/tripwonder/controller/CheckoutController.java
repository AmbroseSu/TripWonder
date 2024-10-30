package com.ambrose.tripwonder.controller;

import java.util.Date;

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
    private final PayOS payOS;
    
    @GetMapping("/success")
    public ResponseEntity<?> success(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam String orderCode
    ){
        return ResponseEntity.ok(code + " " + id + " " + cancel + " " + status + " " + orderCode);
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancel(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam String orderCode
    ){
        return ResponseEntity.ok(code + " " + id + " " + cancel + " " + status + " " + orderCode);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/create-payment-link")
    public ResponseEntity<?> checkout(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
            final String baseUrl = getBaseUrl(request);
            final String productName = "Mì tôm hảo hảo ly";
            final String description = "Thanh toan don hang";
            final String returnUrl = baseUrl + "/success";
            final String cancelUrl = baseUrl + "/cancel";
            final int price = 2000;
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
            ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();
            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(price).description(description)
                    .returnUrl(returnUrl).cancelUrl(cancelUrl).item(item).build();
            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            String checkoutUrl = data.getCheckoutUrl();

            httpServletResponse.setHeader("Location", checkoutUrl);
            httpServletResponse.setStatus(302);
            return ResponseEntity.ok(data);
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        
        String url = scheme + "://" + serverName + (serverPort > 0 ? ":"+ serverPort  : "") + contextPath+"/api/v1/checkout";
       
        return url;
    }
}