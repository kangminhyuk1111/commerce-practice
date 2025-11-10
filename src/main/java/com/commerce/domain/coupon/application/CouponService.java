package com.commerce.domain.coupon.application;

import com.commerce.domain.coupon.domain.Coupon;
import com.commerce.domain.coupon.domain.OwnedCoupon;
import com.commerce.domain.coupon.domain.OwnedCouponState;
import com.commerce.domain.coupon.repository.CouponRepository;
import com.commerce.domain.coupon.repository.OwnedCouponRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

  private final CouponRepository couponRepository;
  private final OwnedCouponRepository ownedCouponRepository;

  public CouponService(CouponRepository couponRepository, OwnedCouponRepository ownedCouponRepository) {
    this.couponRepository = couponRepository;
    this.ownedCouponRepository = ownedCouponRepository;
  }

  /**
   * 쿠폰 다운로드
   */
  public void download(Long userId, Long couponId) {
    Coupon coupon = couponRepository.findById(couponId)
        .orElseThrow(() -> new CoreException(ErrorType.COUPON_NOT_FOUND));

    List<OwnedCoupon> ownedCoupons = ownedCouponRepository.findByUserIdAndCouponId(userId,
        couponId);

    if (!ownedCoupons.isEmpty()) {
      throw new CoreException(ErrorType.COUPON_ALREADY_DOWNLOADED);
    }

    OwnedCoupon ownedCoupon = new OwnedCoupon(
        userId,
        coupon,
        OwnedCouponState.DOWNLOADED,
        LocalDateTime.now(),
        LocalDateTime.now().plusDays(30),
        null);

    ownedCouponRepository.save(ownedCoupon);
  }

  /**
   * 유저가 가지고 있는 쿠폰 조회
   */
  public List<OwnedCoupon> getOwnedCoupons(Long userId) {
    return ownedCouponRepository.findOwnedCouponByUserId(userId);
  }

  /**
   * 상품에 적용 가능한 쿠폰 리스트 조회
   */
  public List<Coupon> getApplicableCouponsForProduct(Long productId) {
    List<Long> categoryIds = couponRepository.findCategoryIdsByProductId(productId);

    return couponRepository.findCouponsForProduct(productId, categoryIds);
  }
}
