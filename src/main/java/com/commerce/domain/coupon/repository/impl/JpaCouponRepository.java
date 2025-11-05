package com.commerce.domain.coupon.repository.impl;

import com.commerce.domain.coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCouponRepository extends JpaRepository<Coupon, Long> {
  List<Long> findCategoryIdsByApplyTargetId(Long applyTargetId);

  @Query("""
    SELECT c FROM Coupon c
    WHERE (c.applyTargetType = 'PRODUCT' AND c.applyTargetId = :productId)
       OR (c.applyTargetType = 'CATEGORY' AND c.applyTargetId IN :categoryIds)
    """)
  List<Coupon> findCouponsForProduct(@Param("productId") Long productId, @Param("categoryIds") List<Long> categoryIds);
}
