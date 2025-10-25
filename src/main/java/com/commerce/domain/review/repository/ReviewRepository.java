package com.commerce.domain.review.repository;

import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {
  Page<Review> findByReviewTargetTypeAndTargetId(ReviewTargetType targetType, Long targetId, Pageable pageable);
  List<Review> findByProductId(Long productId);
  Optional<Review> findByIdAndUserId(Long reviewId, Long userId);
  Boolean existByReviewKey(String reviewKey);
  Review save(Review review);
  void delete(Long reviewId);
}
