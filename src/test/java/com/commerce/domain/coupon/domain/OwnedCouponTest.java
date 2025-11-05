package com.commerce.domain.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OwnedCoupon 테스트")
class OwnedCouponTest {

  @Test
  @DisplayName("소유 쿠폰 생성")
  void createOwnedCoupon() {
    // given
    Long userId = 1L;
    Coupon coupon = createTestCoupon();
    OwnedCouponState state = OwnedCouponState.DOWNLOADED;
    LocalDateTime issuedAt = LocalDateTime.now();
    LocalDateTime expiredAt = LocalDateTime.now().plusDays(30);
    LocalDateTime usedAt = null;

    OwnedCoupon ownedCoupon = new OwnedCoupon(userId, coupon, state, issuedAt, expiredAt, usedAt);

    // then
    assertThat(ownedCoupon.getUserId()).isEqualTo(userId);
    assertThat(ownedCoupon.getCoupon()).isEqualTo(coupon);
    assertThat(ownedCoupon.getState()).isEqualTo(state);
    assertThat(ownedCoupon.getIssuedAt()).isEqualTo(issuedAt);
    assertThat(ownedCoupon.getExpiredAt()).isEqualTo(expiredAt);
    assertThat(ownedCoupon.getUsedAt()).isNull();
  }

  @Test
  @DisplayName("사용된 쿠폰 생성")
  void createUsedOwnedCoupon() {
    // given
    Long userId = 2L;
    Coupon coupon = createTestCoupon();
    OwnedCouponState state = OwnedCouponState.USED;
    LocalDateTime issuedAt = LocalDateTime.now().minusDays(10);
    LocalDateTime expiredAt = LocalDateTime.now().plusDays(20);
    LocalDateTime usedAt = LocalDateTime.now();

    OwnedCoupon ownedCoupon = new OwnedCoupon(userId, coupon, state, issuedAt, expiredAt, usedAt);

    // then
    assertThat(ownedCoupon.getState()).isEqualTo(OwnedCouponState.USED);
    assertThat(ownedCoupon.getUsedAt()).isEqualTo(usedAt);
  }

  @Test
  @DisplayName("만료된 쿠폰 생성")
  void createExpiredOwnedCoupon() {
    // given
    Long userId = 3L;
    Coupon coupon = createTestCoupon();
    OwnedCouponState state = OwnedCouponState.EXPIRED;
    LocalDateTime issuedAt = LocalDateTime.now().minusDays(40);
    LocalDateTime expiredAt = LocalDateTime.now().minusDays(5);
    LocalDateTime usedAt = null;

    OwnedCoupon ownedCoupon = new OwnedCoupon(userId, coupon, state, issuedAt, expiredAt, usedAt);

    // then
    assertThat(ownedCoupon.getState()).isEqualTo(OwnedCouponState.EXPIRED);
  }

  private Coupon createTestCoupon() {
    return new Coupon(
        "테스트 쿠폰",
        CouponType.PERCENTAGE,
        new java.math.BigDecimal("10"),
        LocalDateTime.now().plusDays(30),
        ApplyTargetType.PRODUCT,
        1L
    );
  }
}