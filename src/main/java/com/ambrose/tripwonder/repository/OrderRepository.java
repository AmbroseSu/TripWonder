package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.enums.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(Long orderCode);

    List<Order> findAllByStatus(Payment status);

    @Query("SELECT e FROM Order e ORDER BY e.orderDate DESC")
    List<Order> findTop5Records(Pageable pageable);
}
