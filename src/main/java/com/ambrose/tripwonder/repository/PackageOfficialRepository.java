package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.PackageTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageOfficialRepository extends JpaRepository<PackageTour, Long>, JpaSpecificationExecutor<PackageTour> {
    Page<PackageTour> findAll(Pageable pageable);
    Page<PackageTour> findAll(Specification<PackageTour> specification, Pageable pageable);

    PackageTour findPackageTourById(long packageOfficialId);
    @Query("SELECT t.id AS tourId, t.name AS tourName, AVG(r.rating) AS avgRating, g.imageUrl AS galleryUrl,t.price as price " +
            "FROM PackageTour t " +
            "JOIN RatingReview r ON t.id = r.packageTour.id " +
            "JOIN Gallery g ON g.packageTour.id = t.id " +
            "GROUP BY t.id, t.name, g.imageUrl " +
            "ORDER BY avgRating DESC")

    List<Object[]> findTop5ToursWithHighestAvgRating(Pageable pageable);
    //id anh ten ratting gia tien


}
