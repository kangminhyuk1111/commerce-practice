package com.commerce.controller.v1;

import com.commerce.controller.dto.request.AddReviewRequest;
import com.commerce.controller.dto.request.DeleteReviewRequest;
import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.request.UpdateReviewRequest;
import com.commerce.controller.dto.response.PageResponse;
import com.commerce.controller.dto.response.ReviewResponse;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import com.commerce.domain.review.application.ReviewService;
import com.commerce.support.response.ApiResponse;
import org.hibernate.mapping.Any;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(final ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/v1/reviews")
  public ApiResponse<PageResponse<ReviewResponse>> getReviews(
      @RequestParam ReviewTargetType targetType,
      @RequestParam Long targetId,
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "5") Integer limit,
      @RequestParam(defaultValue = "createdAt,desc") String sort
  ) {
    Page<Review> reviews = reviewService.findReviews(ReviewTarget.of(targetType, targetId), PageSize.of(offset, limit).toPageable(sort));
    Page<ReviewResponse> responses = reviews.map(ReviewResponse::of);
    return ApiResponse.success(PageResponse.of(responses));
  }

  @PostMapping("/v1/reviews")
  public ApiResponse<Any> addReview(
      @RequestBody AddReviewRequest request
  ) {
    reviewService.addReview(request.toTarget(), request.toContent(), request.userId());
    return ApiResponse.success();
  }

  @PutMapping("/v1/reviews/{reviewId}")
  public ApiResponse<Any> updateReview(
      @PathVariable Long reviewId,
      @RequestBody UpdateReviewRequest request
  ) {
    reviewService.updateReview(reviewId, request.userId(), request.toContent());
    return ApiResponse.success();
  }

  @DeleteMapping("/v1/reviews/{reviewId}")
  public ApiResponse<Any> deleteReview(
      @PathVariable Long reviewId,
      @RequestBody DeleteReviewRequest request
  ) {
    reviewService.deleteReview(reviewId, request.userId());
    return ApiResponse.success();
  }
}
