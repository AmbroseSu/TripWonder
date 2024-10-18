package com.ambrose.tripwonder.repository;

import com.ambrose.tripwonder.entities.RatingReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingReviewRepository extends JpaRepository<RatingReview,Long> {
}
