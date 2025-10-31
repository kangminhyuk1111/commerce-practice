package com.commerce.domain.point.repository.impl;

import com.commerce.domain.point.domain.PointHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPointHistoryRepository extends JpaRepository<PointHistory, Long> {
  List<PointHistory> findByUserIdOrderByTransactionAtDesc(Long userId);
}
