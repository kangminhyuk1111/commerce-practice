package com.commerce.domain.review.repository;

import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import com.commerce.utils.BaseReflectionUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeReviewRepository implements ReviewRepository {

  private final Map<Long, Review> reviews = new HashMap<>();
  private Long idSequence = 1L;

  @Override
  public Page<Review> findByReviewTargetTypeAndTargetId(final ReviewTargetType targetType,
      final Long targetId,
      final Pageable pageable) {
    List<Review> filtered = reviews.values().stream()
        .filter(review -> review.getReviewTargetType().equals(targetType)
            && review.getTargetId().equals(targetId))
        .collect(Collectors.toList());

    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), filtered.size());

    List<Review> pageContent = filtered.subList(start, end);

    return new PageImpl<>(pageContent, pageable, filtered.size());
  }

  @Override
  public List<Review> findByProductId(Long productId) {
    return reviews.values().stream()
        .filter(review -> review.getTargetId().equals(productId))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Review> findByIdAndUserId(final Long reviewId, final Long userId) {
    return Optional.ofNullable(reviews.get(reviewId))
        .filter(review -> review.getUserId().equals(userId));
  }

  @Override
  public Boolean existByReviewKey(final String reviewKey) {
    return reviews.values().stream()
        .anyMatch(review -> review.getReviewKey().equals(reviewKey));
  }

  @Override
  public Review save(Review review) {
    if (review.getId() == null) {
      BaseReflectionUtils.setId(review, idSequence++);
      BaseReflectionUtils.setCreatedAt(review, LocalDateTime.now());
      BaseReflectionUtils.setUpdatedAt(review, LocalDateTime.now());
    }
    reviews.put(review.getId(), review);
    return review;
  }

  @Override
  public void delete(final Long reviewId) {
    reviews.remove(reviewId);
  }

  public void clear() {
    reviews.clear();
    idSequence = 1L;
  }
}