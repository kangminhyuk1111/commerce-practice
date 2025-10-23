package com.commerce.domain.review.service;

import com.commerce.domain.review.entity.RateSummary;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.support.validate.ReviewPolicyValidator;
import com.commerce.domain.review.support.key.ReviewKey;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewPolicyValidator reviewPolicyValidator;

  public ReviewService(final ReviewRepository reviewRepository, final ReviewPolicyValidator reviewPolicyValidator) {
    this.reviewRepository = reviewRepository;
    this.reviewPolicyValidator = reviewPolicyValidator;
  }

  public RateSummary findRateSummary(final Long productId) {
    final List<Review> reviews = reviewRepository.findByProductId(productId);

    final Double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    final Integer count = reviews.size();

    return new RateSummary(avg, count);
  }

  public Review addReview(final ReviewTargetType reviewTargetType, final Long userId, final Long targetId, final String title, final String description, final Rating rating) {
    final ReviewKey reviewKey = reviewPolicyValidator.addNewValidate(userId, targetId, reviewTargetType);
    final Review review = new Review(targetId, reviewKey.getReviewKey(), reviewTargetType, userId, title, description, rating);
    return reviewRepository.save(review);
  }
}
