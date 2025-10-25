package com.commerce.controller.dto.request;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.domain.Rating;

public record UpdateReviewRequest(
  Long userId, String title, String description, Rating rating
) {
  public ReviewContent toContent() {
    return new ReviewContent(title, description, rating);
  }
}
