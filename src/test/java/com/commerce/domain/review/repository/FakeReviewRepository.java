package com.commerce.domain.review.repository;

import com.commerce.domain.review.entity.Review;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeReviewRepository implements ReviewRepository {

  private final Map<Long, Review> reviews = new HashMap<>();
  private Long idSequence = 1L;

  @Override
  public List<Review> findByProductId(Long productId) {
    return reviews.values().stream()
        .filter(review -> review.getTargetId().equals(productId))
        .collect(Collectors.toList());
  }

  @Override
  public Boolean existByReviewKey(final String reviewKey) {
    return null;
  }

  public Review save(Review review) {
    if (review.getId() == null) {
      setId(review, idSequence++);
    }
    return reviews.put(review.getId(), review);
  }

  public void clear() {
    reviews.clear();
    idSequence = 1L;
  }

  private void setId(Object entity, long id) {
    try {
      java.lang.reflect.Field field = entity.getClass().getSuperclass()
          .getDeclaredField("id");
      field.setAccessible(true);
      field.set(entity, id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}