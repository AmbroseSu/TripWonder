package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.dto.OrderUserDto;
import com.ambrose.tripwonder.entities.Order;
import com.ambrose.tripwonder.entities.OrderDetail;
import com.ambrose.tripwonder.entities.enums.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderCode(Long orderCode);

    List<Order> findAllByStatus(Payment status);

    @Query("SELECT e FROM Order e ORDER BY e.orderDate DESC")
    List<Order> findTop5Records(Pageable pageable);
    
    @Query("select new com.ambrose.tripwonder.dto.OrderUserDto(o.id,o.orderDate,o.status,o.totalPrice,o.user.userId,o.user.fullname )from Order o join User u on u.userId = o.user.userId")
    Page<OrderUserDto> getAll(Pageable pageable);
    
    
}
