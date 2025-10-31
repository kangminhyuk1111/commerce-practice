package com.commerce.domain.point.repository;

import com.commerce.domain.point.domain.PointHistory;
import com.commerce.utils.BaseReflectionUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakePointHistoryRepository implements PointHistoryRepository {

  private final Map<Long, PointHistory> store = new HashMap<>();
  private final AtomicLong idSequence = new AtomicLong(1);

  @Override
  public List<PointHistory> findByUserIdOrderByTransactionAtDesc(Long userId) {
    return store.values().stream()
        .filter(history -> history.getUserId().equals(userId))
        .sorted(Comparator.comparing(PointHistory::getTransactionAt).reversed())
        .toList();
  }

  @Override
  public PointHistory save(PointHistory pointHistory) {
    if (pointHistory.getId() == null) {
      BaseReflectionUtils.setId(pointHistory, idSequence.getAndIncrement());
      BaseReflectionUtils.setTransactionAt(pointHistory, LocalDateTime.now());
    }
    store.put(pointHistory.getId(), pointHistory);
    return pointHistory;
  }

  public void clear() {
    store.clear();
    idSequence.set(1);
  }

  public int size() {
    return store.size();
  }

  public PointHistory findById(Long id) {
    return store.get(id);
  }

  public List<PointHistory> findAll() {
    return new ArrayList<>(store.values());
  }
}