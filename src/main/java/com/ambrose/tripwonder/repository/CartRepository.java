package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.Cart;
import com.ambrose.tripwonder.entities.PackageTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserUserIdAndPackageTourId(Long userId, Long packageId);

    Page<Cart> findAllByUserUserId(Long userId, Pageable pageable);

    List<Cart> findAllByUserUserId(Long userId);

    Optional<Cart> findById(long id);

    void deleteByPackageTour(PackageTour packageTour);

}
