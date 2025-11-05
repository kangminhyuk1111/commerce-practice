package com.commerce.domain.coupon.repository.impl;

import com.commerce.domain.coupon.domain.Coupon;
import com.commerce.domain.coupon.repository.CouponRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

  private final JpaCouponRepository jpaCouponRepository;

  public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository) {
    this.jpaCouponRepository = jpaCouponRepository;
  }

  @Override
  public Optional<Coupon> findById(Long couponId) {
    return jpaCouponRepository.findById(couponId);
  }

  @Override
  public List<Long> findCategoryIdsByProductId(Long productId) {
    return jpaCouponRepository.findCategoryIdsByApplyTargetId(productId);
  }

  @Override
  public List<Coupon> findCouponsForProduct(Long productId, List<Long> categoryIds) {
    return jpaCouponRepository.findCouponsForProduct(productId, categoryIds);
  }
}
