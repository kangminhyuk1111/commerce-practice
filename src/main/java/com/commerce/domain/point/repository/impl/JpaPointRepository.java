package com.commerce.domain.point.repository.impl;

import com.commerce.domain.point.domain.Point;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPointRepository extends JpaRepository<Point, Long> {
  Optional<Point> findByUserId(Long userId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p FROM Point p WHERE p.userId = :userId")
  Point findByUserIdWithLock(Long userId);
}
