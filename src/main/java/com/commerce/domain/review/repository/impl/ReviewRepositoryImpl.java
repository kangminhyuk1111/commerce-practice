package com.commerce.domain.review.repository.impl;

import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.repository.ReviewRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

  private final JpaReviewRepository jpaReviewRepository;

  public ReviewRepositoryImpl(final JpaReviewRepository jpaReviewRepository) {
    this.jpaReviewRepository = jpaReviewRepository;
  }

  @Override
  public List<Review> findByProductId(final Long productId) {
    return jpaReviewRepository.findByProductId(productId);
  }

  @Override
  public Boolean existByReviewKey(final String reviewKey) {
    return jpaReviewRepository.existsByReviewKey(reviewKey);
  }

  @Override
  public Review save(final Review review) {
    return jpaReviewRepository.save(review);
  }
}
