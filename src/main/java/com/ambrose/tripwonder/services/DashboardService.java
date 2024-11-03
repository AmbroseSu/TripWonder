package com.ambrose.tripwonder.services;


import com.ambrose.tripwonder.dto.OrderDto;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    Long totalRevenues();

    Long totalSupplier();

    Long totalPackageTour();

    Long totalOrder();

    Map<String, Integer> getGenders();

    List<OrderDto> getTopFiveOrders();

    List<Object[]> getTopFivePackageTours();
}
