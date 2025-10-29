package com.commerce.domain.point.repository.impl;

import com.commerce.domain.point.domain.PointHistory;
import com.commerce.domain.point.repository.PointHistoryRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

  private final JpaPointHistoryRepository jpaPointHistoryRepository;

  public PointHistoryRepositoryImpl(JpaPointHistoryRepository jpaPointHistoryRepository) {
    this.jpaPointHistoryRepository = jpaPointHistoryRepository;
  }

  @Override
  public List<PointHistory> findByUserIdOrderByTransactionAtDesc(Long userId) {
    return jpaPointHistoryRepository.findByUserIdOrderByTransactionAtDesc(userId);
  }

  @Override
  public PointHistory save(PointHistory pointHistory) {
    return jpaPointHistoryRepository.save(pointHistory);
  }
}
