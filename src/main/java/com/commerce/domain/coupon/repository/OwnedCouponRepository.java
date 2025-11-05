package com.commerce.domain.coupon.repository;

import com.commerce.domain.coupon.domain.OwnedCoupon;
import java.util.List;

public interface OwnedCouponRepository {
  List<OwnedCoupon> findByUserIdAndCouponId(Long userId, Long couponId);
  OwnedCoupon save(OwnedCoupon ownedCoupon);
  List<OwnedCoupon> findOwnedCouponByUserId(Long userId);
}
