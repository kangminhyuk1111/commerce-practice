package com.commerce.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.domain.product.entity.Product;
import com.commerce.domain.review.entity.RateSummary;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.repository.FakeReviewRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewServiceTest {

  private ReviewService reviewService;
  private FakeReviewRepository fakeReviewRepository;
  private Product product;

  @BeforeEach
  void setUp() {
    fakeReviewRepository = new FakeReviewRepository();
    reviewService = new ReviewService(fakeReviewRepository);

    product = new Product("상품1", "상품1 입니다.", BigDecimal.valueOf(1000000), "http://localhost:33/1", "상품 디테일 설명 페이지");
    setId(product, 1L);

    Review review1 = new Review(product, "리뷰 1", "리뷰 설명 1", Rating.FIVE);
    Review review2 = new Review(product, "리뷰 2", "리뷰 설명 2", Rating.FOUR);
    Review review3 = new Review(product, "리뷰 3", "리뷰 설명 3", Rating.THREE);

    fakeReviewRepository.save(review1);
    fakeReviewRepository.save(review2);
    fakeReviewRepository.save(review3);
  }

  @Nested
  class 조회_테스트 {

    @Test
    void 상품의_리뷰에_대한_통계_조회() {
      // given & when
      final RateSummary rateSummary = reviewService.findRateSummary(product.getId());

      // then
      assertThat(rateSummary.getReviewAvg()).isEqualTo(4.0);
      assertThat(rateSummary.getReviewCount()).isEqualTo(3);
    }
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