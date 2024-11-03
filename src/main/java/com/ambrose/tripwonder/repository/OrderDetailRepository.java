package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od.packageTour,o.orderCode from OrderDetail od " +
            "join Order o on od.order.id = o.id " +
            "where o.status = 'PAID' and o.user.userId = :userId")
    List<Object[]> findPackageToursByUserId(Long userId);
}
