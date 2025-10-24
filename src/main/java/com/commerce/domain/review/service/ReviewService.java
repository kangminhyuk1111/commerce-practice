package com.commerce.domain.review.service;

import com.commerce.controller.dto.request.PageSize;
import com.commerce.domain.review.dto.RateSummary;
import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.support.persistence.ReviewManager;
import com.commerce.domain.review.support.validate.ReviewPolicyValidator;
import com.commerce.domain.review.support.key.ReviewKey;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

  private final ReviewManager reviewManager;
  private final ReviewPolicyValidator reviewPolicyValidator;

  public ReviewService(final ReviewManager reviewManager, final ReviewPolicyValidator reviewPolicyValidator) {
    this.reviewManager = reviewManager;
    this.reviewPolicyValidator = reviewPolicyValidator;
  }

  public Page<Review> findReviews(final ReviewTarget reviewTarget, final Pageable pageable) {
    return reviewManager.findReviews(reviewTarget, pageable);
  }

  public RateSummary findRateSummary(final Long productId) {
    final List<Review> reviews = reviewManager.findByProductId(productId);

    final Double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    final Integer count = reviews.size();

    return new RateSummary(avg, count);
  }

  public void addReview(final ReviewTarget reviewTarget, final ReviewContent reviewContent, final Long userId) {
    final ReviewKey reviewKey = reviewPolicyValidator.addNewValidate(userId, reviewTarget.targetId(), reviewTarget.reviewTargetType());
    reviewManager.add(reviewTarget, reviewContent, reviewKey);
  }

  @Transactional
  public void updateReview(final Long reviewId, final Long userId, final ReviewContent reviewContent) {
    reviewPolicyValidator.updateValidate(reviewId, userId);
    reviewManager.update(reviewContent, reviewId, userId);
  }

  @Transactional
  public void deleteReview(final Long reviewId, final Long userId) {
    reviewManager.delete(userId, reviewId);
  }
}
