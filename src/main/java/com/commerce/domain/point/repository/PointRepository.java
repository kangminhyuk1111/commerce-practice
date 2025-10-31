package com.commerce.domain.point.repository;

import com.commerce.domain.point.domain.Point;

public interface PointRepository {
  Point findByUserId(Long userId);
  Point save(Point point);
  Point findByUserIdWithLock(Long userId);
}
