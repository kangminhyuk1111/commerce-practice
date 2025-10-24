package com.commerce.controller.dto.response;

import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;

public record ReviewResponse(String title, String description, Integer rating) {
  public static ReviewResponse of(final Review review) {
    return new ReviewResponse(review.getTitle(), review.getDescription(), review.getRating());
  }
}
