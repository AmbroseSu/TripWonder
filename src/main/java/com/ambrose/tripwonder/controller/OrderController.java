package com.ambrose.tripwonder.controller;

import com.ambrose.tripwonder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add/{userId}/{tourId}")
    public ResponseEntity<?> addOrder(
            @PathVariable("tourId") long tourId,
            @PathVariable long userId) {
        return orderService.addToCard(userId, tourId);
    }

    @GetMapping("/getall/{userId}")
    public ResponseEntity<?> getCart(
            @PathVariable(required = false) long userId,
            @RequestParam(defaultValue = "0") int Page,
            @RequestParam(defaultValue = "10") int PageSize
    ) {
        Pageable pageable = PageRequest.of(Page, PageSize);
        
        return orderService.getAllCart(userId, pageable);
    }

    @GetMapping("/getHistoryOrder")
    public ResponseEntity<?> getHistory() {
        return null;
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<?> deleteOne(
            @PathVariable("cartId") long cartId) {
        return orderService.deleteTourInCart(cartId);
    }

    @DeleteMapping("/deleteAll/{cardId}")
    public ResponseEntity<?> deleteAllCart(
            @PathVariable("cardId") long cardId
    ) {
        return orderService.deleteAllCart(cardId);
    }

    @GetMapping("/checkStatus")
    public ResponseEntity<?> checkStatus(@RequestParam long orderCode) {
        return orderService.getStatusOrder(orderCode);
    }


    @GetMapping("/getAllOrder")
    public ResponseEntity<?> getAllOrder(@RequestParam(required = false) Long userId,
                                         @RequestParam(defaultValue = "0") int Page,
                                         @RequestParam(defaultValue = "10") int PageSize) {
        Pageable pageable = PageRequest.of(Page, PageSize);
        if(userId != null) {
            return orderService.getAllOrder(userId);
        }
        else return orderService.getAllOrder(pageable); 
    }
//    @PostMapping("/checkout/{userId}")
//    public ResponseEntity<?> checkout(@PathVariable("userId") long userId, @RequestParam PaymentMethod paymentMethod) {
//        return orderService.checkOutCart(userId,paymentMethod);
//    }


}
