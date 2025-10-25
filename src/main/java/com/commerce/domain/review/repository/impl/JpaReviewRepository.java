package com.commerce.domain.review.repository.impl;

import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProductId(Long productId);
  Boolean existsByReviewKey(String reviewKey);
  Optional<Review> findByIdAndUserId(Long id, Long userId);
  Page<Review> findByReviewTargetTypeAndTargetId(ReviewTargetType reviewTargetType, Long targetId, Pageable pageable);
}
