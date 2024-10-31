package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.config.ResponseUtil;
import com.ambrose.tripwonder.entities.*;
import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.entities.enums.PaymentMethod;
import com.ambrose.tripwonder.repository.CartRepository;
import com.ambrose.tripwonder.repository.OrderRepository;
import com.ambrose.tripwonder.repository.UserRepository;
import com.ambrose.tripwonder.services.CheckOutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl implements CheckOutService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PayOS payOS;
    private final OrderRepository orderRepository;
    
    @Override
    @Transactional
    public ResponseEntity<?> successfulCheckout(long orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElse(null);
        if (order != null) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                cartRepository.deleteByPackageTour(orderDetail.getPackageTour());
            }
            order.setPaymentDate(LocalDateTime.now());
            order.setStatus(Payment.PAID);
            orderRepository.save(order);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> failedCheckout(long orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElse(null);
        if (order != null) {
            order.setStatus(Payment.CANCELLED);
            orderRepository.save(order);
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().build();    
    }

    @Override
    public ResponseEntity<?> getLink(HttpServletRequest request, long userId) throws Exception {
        final String baseUrl = getBaseUrl(request);
        final String returnUrl = baseUrl + "/success";
        final String cancelUrl = baseUrl + "/cancel";

        List<Cart> cartList = cartRepository.findAllByUserUserId(userId);
        List<ItemData> itemDataList = new ArrayList<>();
        int total = 0;
        for (Cart cart : cartList) {
            ItemData itemData = ItemData.builder().name(cart.getPackageTour().getName()).quantity(cart.getQuantity()).price((int) Math.round(cart.getTotalPrice())).build();
            itemDataList.add(itemData);
            total += (int) Math.round(cart.getTotalPrice());
        }
        String currentTimeString = String.valueOf(new Date().getTime());
        long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
        PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(total).description("Thanh toan Trip Wonder")
                .returnUrl(returnUrl).cancelUrl(cancelUrl).build();
        paymentData.setItems(itemDataList);
        
        CheckoutResponseData data = payOS.createPaymentLink(paymentData);

        User user = userRepository.findUserById(userId);
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPackageTour(cart.getPackageTour());
            orderDetail.setQuantity(cart.getQuantity());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Payment.PENDING);
        order.setPaymentMethod(PaymentMethod.QRCODE);
        order.setUser(user);
        order.setOrderCode(data.getOrderCode());
        orderRepository.save(order);
        return ResponseUtil.getObject(data, HttpStatus.OK,"URL payment link");
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
