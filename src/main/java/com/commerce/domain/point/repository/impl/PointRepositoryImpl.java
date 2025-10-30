package com.commerce.domain.point.repository.impl;

import com.commerce.domain.point.domain.Point;
import com.commerce.domain.point.repository.PointRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {

  private final JpaPointRepository jpaPointRepository;

  public PointRepositoryImpl(JpaPointRepository jpaPointRepository) {
    this.jpaPointRepository = jpaPointRepository;
  }

  @Override
  public Point findByUserId(Long userId) {
    return jpaPointRepository.findByUserId(userId)
        .orElseThrow(() -> new CoreException(ErrorType.POINT_NOT_FOUND));
  }

  @Override
  public Point save(Point point) {
    return jpaPointRepository.save(point);
  }

  @Override
  public Point findByUserIdWithLock(Long userId) {
    return jpaPointRepository.findByUserIdWithLock(userId);
  }
}
