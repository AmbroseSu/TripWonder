package com.ambrose.tripwonder.services;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> addToCard(Long userId, Long tourId);

    ResponseEntity<?> getAllCart(long userId, Pageable pageable);

    ResponseEntity<?> deleteTourInCart(Long cartId);

    //    ResponseEntity<?> checkOutCart(Long userId, PaymentMethod paymentMethod);
    ResponseEntity<?> deleteAllCart(Long cartId);

    ResponseEntity<?> getStatusOrder(Long orderCode);

    ResponseEntity<?> getAllOrder(long userId);
    ResponseEntity<?> getAllOrder(Pageable pageable);
    ResponseEntity<?> getOrderDetailsByOrderId(Long orderId);
}
