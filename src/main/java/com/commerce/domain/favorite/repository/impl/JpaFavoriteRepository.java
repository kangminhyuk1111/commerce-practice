package com.commerce.domain.favorite.repository.impl;

import com.commerce.domain.favorite.domain.Favorite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFavoriteRepository extends JpaRepository<Favorite, Long> {
  Page<Favorite> findByUserId(Long userId, Pageable pageable);
  Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);
  void deleteByUserIdAndProductId(Long userId, Long productId);
}
