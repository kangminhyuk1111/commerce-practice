package com.commerce.domain.point.application;

import com.commerce.domain.point.domain.Point;
import com.commerce.domain.point.domain.PointHistory;
import com.commerce.domain.point.domain.PointTransactionType;
import com.commerce.domain.point.repository.PointHistoryRepository;
import com.commerce.domain.point.repository.PointRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointBalanceService {

  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;
  private final PointPolicyValidator pointPolicyValidator;

  public PointBalanceService(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository, PointPolicyValidator pointPolicyValidator) {
    this.pointRepository = pointRepository;
    this.pointHistoryRepository = pointHistoryRepository;
    this.pointPolicyValidator = pointPolicyValidator;
  }

  // 포인트 추가
  @Transactional
  public void chargeBalance(Long userId, BigDecimal amount) {
    final Point point = pointRepository.findByUserIdWithLock(userId);

    point.charge(amount);

    pointHistoryRepository.save(new PointHistory(
        userId, amount, PointTransactionType.CHARGE, point.getBalance()
    ));
  }

  // 포인트 사용, 차감
  @Transactional
  public void useBalance(Long userId, BigDecimal amount) {
    final Point point = pointRepository.findByUserIdWithLock(userId);

    // 포인트가 기준점 이하로 남았을 시 사용이 불가능
    pointPolicyValidator.isUsable(point);

    point.use(amount);

    pointHistoryRepository.save(new PointHistory(
        userId, amount, PointTransactionType.USE, point.getBalance()
    ));
  }
}
