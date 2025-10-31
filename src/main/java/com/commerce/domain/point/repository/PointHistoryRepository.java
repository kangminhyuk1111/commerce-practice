package com.commerce.domain.point.repository;

import com.commerce.domain.point.domain.PointHistory;
import java.util.List;

public interface PointHistoryRepository {
  List<PointHistory> findByUserIdOrderByTransactionAtDesc(Long userId);
  PointHistory save(PointHistory pointHistory);
}
