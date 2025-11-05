package com.commerce.domain.coupon.repository;

import com.commerce.domain.coupon.domain.Coupon;
import java.util.List;
import java.util.Optional;

public interface CouponRepository {
  Optional<Coupon> findById(Long couponId);
  List<Long> findCategoryIdsByProductId(Long productId);
  List<Coupon> findCouponsForProduct(Long productId, List<Long> categoryIds);
}
