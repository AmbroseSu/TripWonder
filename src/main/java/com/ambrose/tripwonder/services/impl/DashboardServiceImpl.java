package com.ambrose.tripwonder.services.impl;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.OrderDto;
import com.ambrose.tripwonder.dto.PackageTourDTO;
import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.PackageTour;
import com.ambrose.tripwonder.entities.Supplier;
import com.ambrose.tripwonder.entities.User;
import com.ambrose.tripwonder.entities.enums.Gender;
import com.ambrose.tripwonder.entities.enums.Payment;
import com.ambrose.tripwonder.repository.*;
import com.ambrose.tripwonder.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final PackageTourRepository packageTourRepository;
    private final UserRepository userRepository;
    private final GenericConverter<OrderDto> mapperOrderDto;
    private final PackageOfficialRepository packageOfficialRepository;
    private final GenericConverter<PackageTourDTO> mapperPackageDto;
    
    @Override
    public Long totalRevenues() {
        List<Order> orders = orderRepository.findAllByStatus(Payment.PAID);
        Double total = 0.0;
        for (Order order : orders) {
            total += order.getTotalPrice();
        }

        return (long) Math.ceil((total * 5)/100);
    }
    
    @Override
    public Long totalSupplier(){
        List<Supplier> suppliers = supplierRepository.findAllByStatus(true);
        return (long) suppliers.size();
    }
    
    @Override
    public Long totalPackageTour(){
        List<PackageTour> packageTours = packageTourRepository.findAllByStatus(true);
        return (long) packageTours.size();
    }
    
    @Override
    public Long totalOrder(){
        List<Order> orders = orderRepository.findAllByStatus(Payment.PAID);
        return (long) orders.size();
    }
    
    @Override
    public Map<String,Integer> getGenders() {
        List<User> males = userRepository.findAlLGender(Gender.MALE);
        List<User> females = userRepository.findAlLGender(Gender.FEMALE);
        List<User> others = userRepository.findAlLGender(Gender.OTHER);
        Map<String,Integer> genders = new HashMap<>();
        genders.put("Male", males.size());
        genders.put("Female", females.size());
        genders.put("Other", others.size());
        return genders;
    }
    
    @Override
    public List<OrderDto> getTopFiveOrders() {
        List<Order> orders = orderRepository.findTop5Records(PageRequest.of(0,5));
        return orders.stream().map(x -> mapperOrderDto.toDTO(x, OrderDto.class)).toList();
    }
    
    @Override
    public List<Object[]> getTopFivePackageTours() {
        return packageOfficialRepository.findTop5ToursWithHighestAvgRating(PageRequest.of(0,5));
    }
}
