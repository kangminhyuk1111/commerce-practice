package com.commerce.domain.review.repository.impl;

import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import com.commerce.domain.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

  private final JpaReviewRepository jpaReviewRepository;

  public ReviewRepositoryImpl(final JpaReviewRepository jpaReviewRepository) {
    this.jpaReviewRepository = jpaReviewRepository;
  }

  @Override
  public Page<Review> findByReviewTargetTypeAndTargetId(final ReviewTargetType targetType, final Long targetId, final Pageable pageable) {
    return jpaReviewRepository.findByReviewTargetTypeAndTargetId(targetType, targetId, pageable);
  }

  @Override
  public List<Review> findByTargetId(final Long productId) {
    return jpaReviewRepository.findByTargetId(productId);
  }

  @Override
  public Optional<Review> findByIdAndUserId(final Long reviewId, final Long userId) {
    return jpaReviewRepository.findByIdAndUserId(reviewId, userId);
  }

  @Override
  public Boolean existByReviewKey(final String reviewKey) {
    return jpaReviewRepository.existsByReviewKey(reviewKey);
  }

  @Override
  public Review save(final Review review) {
    return jpaReviewRepository.save(review);
  }

  @Override
  public void delete(final Long reviewId) {

  }
}
