package com.commerce.domain.point.repository.impl;

import com.commerce.domain.point.domain.Point;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPointRepository extends JpaRepository<Point, Long> {
  Optional<Point> findByUserId(Long userId);
}
