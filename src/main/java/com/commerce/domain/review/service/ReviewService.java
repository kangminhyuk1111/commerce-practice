package com.commerce.domain.review.service;

import com.commerce.domain.review.entity.RateSummary;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.repository.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewService(final ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  public RateSummary findRateSummary(final Long productId) {
    final List<Review> reviews = reviewRepository.findByProductId(productId);

    final Double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    final Integer count = reviews.size();

    return new RateSummary(avg, count);
  }
}
