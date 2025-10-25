package com.commerce.domain.review.application;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.domain.ReviewKey;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewManager {

  private final ReviewRepository reviewRepository;

  public ReviewManager(final ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public Page<Review> findReviews(final ReviewTarget reviewTarget, final Pageable pageable) {
    return reviewRepository.findByReviewTargetTypeAndTargetId(reviewTarget.reviewTargetType(), reviewTarget.targetId(), pageable);
  }

  public List<Review> findByProductId(final Long productId) {
    return reviewRepository.findByProductId(productId);
  }

  public Long add(final ReviewTarget target, final ReviewContent content, final ReviewKey key) {
    final Review review = new Review(
        target.targetId(),
        key.getReviewKey(),
        target.reviewTargetType(),
        key.getUserId(),
        content.title(),
        content.description(),
        content.rating()
    );

    final Review saved = reviewRepository.save(review);
    return saved.getId();
  }

  public void update(final ReviewContent content, final Long reviewId, final Long userId) {
    final Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
        .orElseThrow(() -> new CoreException(ErrorType.REVIEW_NOT_FOUND));

    review.updateContent(content);
  }

  public void delete(final Long reviewId, final Long userId) {
    final Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
        .orElseThrow(() -> new CoreException(ErrorType.REVIEW_NOT_FOUND));

    reviewRepository.delete(review.getId());
  }
}
