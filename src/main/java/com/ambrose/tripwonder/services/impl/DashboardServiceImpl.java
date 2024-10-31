package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.Supplier;
import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.repository.OrderRepository;
import com.ambrose.tripwonder.repository.PackageTourRepository;
import com.ambrose.tripwonder.repository.SupplierRepository;
import com.ambrose.tripwonder.services.DashboardService;
import com.ambrose.tripwonder.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final PackageTourRepository packageTourRepository;
    
    @Override
    public Long totalRevenues() {
        List<Order> orders = orderRepository.findAllByStatus(Payment.PAID);
        Double total = 0.0;
        for (Order order : orders) {
            total += order.getTotalPrice();
        }

        return (long) Math.ceil((total * 5)/100);
    }
    
    public Long totalSupplier(){
        List<Supplier> suppliers = supplierRepository.findAllByStatus(true);
        return (long) suppliers.size();
    }
    
    public Long totalPackageTour(){
        List<PackageTour> packageTours = packageTourRepository.findAllByStatus(true);
        return (long) packageTours.size();
    }
    
    public Long totalOrder(){
        List<Order> orders = orderRepository.findAllByStatus(Payment.PAID);
        return (long) orders.size();
    }
}
