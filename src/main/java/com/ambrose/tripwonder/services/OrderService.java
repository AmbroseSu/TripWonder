package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> addToCard(Long userId, Long tourId);
    ResponseEntity<?> getAllCart(long userId, Pageable pageable);
    ResponseEntity<?> deleteTourInCart(Long cartId);
//    ResponseEntity<?> checkOutCart(Long userId, PaymentMethod paymentMethod);
    ResponseEntity<?> deleteAllCart(Long cartId);
    ResponseEntity<?> getStatusOrder(Long orderCode);
}
