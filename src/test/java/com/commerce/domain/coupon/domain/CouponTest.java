package com.commerce.domain.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 테스트")
class CouponTest {

  @Test
  @DisplayName("쿠폰 생성")
  void createCoupon() {
    // given
    String name = "10% 할인 쿠폰";
    CouponType couponType = CouponType.FIXED;
    BigDecimal discount = new BigDecimal("5000");
    LocalDateTime expiredAt = LocalDateTime.now().plusDays(30);
    ApplyTargetType applyTargetType = ApplyTargetType.PRODUCT;
    Long applyTargetId = 1L;

    Coupon coupon = new Coupon(name, couponType, discount, expiredAt, applyTargetType, applyTargetId);

    // then
    assertAll(() -> {
      assertThat(coupon.getName()).isEqualTo(name);
      assertThat(coupon.getCouponType()).isEqualTo(couponType);
      assertThat(coupon.getDiscount()).isEqualTo(discount);
      assertThat(coupon.getExpiredAt()).isEqualTo(expiredAt);
      assertThat(coupon.getApplyTargetType()).isEqualTo(applyTargetType);
      assertThat(coupon.getApplyTargetId()).isEqualTo(applyTargetId);
    });
  }

  @Test
  @DisplayName("카테고리 적용 쿠폰 생성")
  void createCouponForCategory() {
    // given
    String name = "전자제품 10000원 할인";
    CouponType couponType = CouponType.FIXED;
    BigDecimal discount = new BigDecimal("5000");
    LocalDateTime expiredAt = LocalDateTime.now().plusDays(14);
    ApplyTargetType applyTargetType = ApplyTargetType.CATEGORY;
    Long categoryId = 5L;

    Coupon coupon = new Coupon(name, couponType, discount, expiredAt, applyTargetType, categoryId);

    // then
    assertAll(() -> {
      assertThat(coupon.getName()).isEqualTo(name);
      assertThat(coupon.getCouponType()).isEqualTo(CouponType.FIXED);
      assertThat(coupon.getApplyTargetType()).isEqualTo(ApplyTargetType.CATEGORY);
      assertThat(coupon.getApplyTargetId()).isEqualTo(categoryId);
    });
  }

  @Test
  @DisplayName("전체 상품 적용 쿠폰")
  void createCouponForAllProducts() {
    // given
    String name = "전사 할인 쿠폰";
    CouponType couponType = CouponType.FIXED;
    BigDecimal discount = new BigDecimal("5000");
    LocalDateTime expiredAt = LocalDateTime.now().plusDays(7);
    ApplyTargetType applyTargetType = ApplyTargetType.ALL;

    Coupon coupon = new Coupon(name, couponType, discount, expiredAt, applyTargetType, null);

    // then
    assertAll(() -> {
      assertThat(coupon.getName()).isEqualTo(name);
      assertThat(coupon.getApplyTargetType()).isEqualTo(ApplyTargetType.ALL);
      assertThat(coupon.getApplyTargetId()).isNull();
    });
  }
}