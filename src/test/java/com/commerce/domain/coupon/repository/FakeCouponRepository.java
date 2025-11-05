package com.commerce.domain.coupon.repository;

import com.commerce.domain.coupon.domain.Coupon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeCouponRepository implements CouponRepository {

  private final Map<Long, Coupon> store = new HashMap<>();
  private final Map<Long, List<Long>> productCategoryMap = new HashMap<>();
  private final Map<Long, List<Coupon>> categoryProductCouponMap = new HashMap<>();

  @Override
  public Optional<Coupon> findById(Long couponId) {
    return Optional.ofNullable(store.get(couponId));
  }

  @Override
  public List<Long> findCategoryIdsByProductId(Long productId) {
    return productCategoryMap.getOrDefault(productId, List.of());
  }

  @Override
  public List<Coupon> findCouponsForProduct(Long productId, List<Long> categoryIds) {
    return categoryIds.stream()
        .flatMap(
            categoryId -> categoryProductCouponMap.getOrDefault(categoryId, List.of()).stream())
        .distinct()
        .toList();
  }
}
