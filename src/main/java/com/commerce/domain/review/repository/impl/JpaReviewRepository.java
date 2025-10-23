package com.commerce.domain.review.repository.impl;

import com.commerce.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProductId(Long productId);

  Boolean existsByReviewKey(String reviewKey);
}
