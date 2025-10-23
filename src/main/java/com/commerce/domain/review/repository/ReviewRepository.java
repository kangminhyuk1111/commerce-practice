package com.commerce.domain.review.repository;

import com.commerce.domain.review.entity.Review;
import java.util.List;

public interface ReviewRepository {
  List<Review> findByProductId(Long productId);
  Boolean existByReviewKey(String reviewKey);
  Review save(Review review);
}
