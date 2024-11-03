package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.CartDto;
import com.ambrose.tripwonder.dto.OrderGetAllDto;
import com.ambrose.tripwonder.entities.Cart;
import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.repository.*;
import com.ambrose.tripwonder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final PackageTourRepository packageTourRepository;
    private final UserRepository userRepository;

    private final GenericConverter<CartDto> cartDtoGenericConverter;
    private final GenericConverter<OrderGetAllDto> orderGetAllDtoGenericConverter;

    @Override
    public ResponseEntity<?> addToCard(Long userId, Long tourId) {
        User user = userRepository.findUserById(userId);
        PackageTour packageTour = packageTourRepository.getPackageTourById(tourId);
        Cart cart;
        try {
            cart = cartRepository.findByUserUserIdAndPackageTourId(userId, packageTour.getId());
        } catch (Exception e) {
            cart = new Cart();
            cart.setPackageTour(packageTour);
            cart.setUser(user);
            cart.setQuantity(cart.getQuantity() + 1);
        }
        if (cart == null) {
            cart = new Cart();
            cart.setPackageTour(packageTour);
            cart.setUser(user);
            cart.setQuantity(cart.getQuantity() + 1);
        } else {
            cart.setQuantity(cart.getQuantity() + 1);
        }
        cartRepository.save(cart);
        return ResponseEntity.ok("ok");
    }

    @Override
    public ResponseEntity<?> getAllCart(long userId, Pageable pageable) {
        Page<Cart> carts = cartRepository.findAllByUserUserId(userId, pageable);
        Page<CartDto> cartDtos = carts.map(cart -> cartDtoGenericConverter.toDTO(cart, CartDto.class));

        return ResponseUtil.getCollection(
                cartDtos,
                HttpStatus.OK,
                "",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                cartDtos.getTotalElements());
    }

    @Override
    public ResponseEntity<?> deleteTourInCart(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        cart.ifPresent(cart2 -> cart2.setQuantity(cart2.getQuantity() - 1));
        cartRepository.save(cart.get());
        return ResponseEntity.ok("Deleted");
    }

    @Override
    public ResponseEntity<?> deleteAllCart(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        cart.ifPresent(cartRepository::delete);
        return ResponseEntity.ok("Deleted");
    }

    @Override
    public ResponseEntity<?> getStatusOrder(Long orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElse(null);
        if (order == null) {
            return null;
        } else
            return ResponseUtil.getObject(order.getStatus(), HttpStatus.OK, "Status");
    }

    public ResponseEntity<?> getAllOrder(long userId) {
        List<Object[]> results = orderDetailRepository.findPackageToursByUserId(userId);
        List<Long> orderCodes = new ArrayList<>();
        List<PackageTour> packageTours = new ArrayList<>();
        for (Object[] result : results) {
            packageTours.add((PackageTour) result[0]);
            orderCodes.add((Long) result[1]);
        }
        List<OrderGetAllDto> orderGetAllDtos = packageTours.stream()
                .map(x -> orderGetAllDtoGenericConverter.toDTO(x, OrderGetAllDto.class)).toList();
        for (int i = 0; i < orderGetAllDtos.size(); i++) {
            orderGetAllDtos.get(i).setOrderCode(orderCodes.get(i));
        }
        return ResponseUtil.getCollection(orderGetAllDtos, HttpStatus.OK, "", 0, 0, 0);
    }

//    @Override
//    public ResponseEntity<?> checkOutCart(Long userId, PaymentMethod paymentMethod){
//        List<Cart> carts = cartRepository.findAllByUserUserId(userId);
//        User user = userRepository.findUserById(userId);
//        Order order = new Order();
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for(Cart cart : carts) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setPackageTour(cart.getPackageTour());
//            orderDetail.setQuantity(cart.getQuantity());
//            
//            orderDetails.add(orderDetail);
//            cartRepository.delete(cart);
//        }
//        order.setOrderDetails(orderDetails);
//        order.setOrderDate(LocalDateTime.now());
//        order.setStatus(Payment.WAITING);
//        order.setPaymentMethod(paymentMethod);
//        order.setUser(user);
//        
//        return ResponseEntity.ok(generateCode(orderRepository.save(order).getId()));
//    }

//    private String generateCode(Long id){
//        String header = "TW";
//        String hex = Long.toHexString(id);
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < 8-hex.length(); i++) {
//            result.append("0");
//        }
//        result.append(hex);
//        header += result;
//        return header;
//    }
}
