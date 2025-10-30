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

  public PointBalanceService(PointRepository pointRepository, PointHistoryRepository pointHistoryRepository) {
    this.pointRepository = pointRepository;
    this.pointHistoryRepository = pointHistoryRepository;
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

    point.use(amount);

    pointHistoryRepository.save(new PointHistory(
        userId, amount, PointTransactionType.USE, point.getBalance()
    ));
  }
}
