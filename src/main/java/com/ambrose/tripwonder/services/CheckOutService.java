package com.ambrose.tripwonder.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CheckOutService {
    ResponseEntity<?> getLink(HttpServletRequest request, long userId) throws Exception;

    ResponseEntity<?> successfulCheckout(long orderCode);

    ResponseEntity<?> failedCheckout(long orderCode);
}
