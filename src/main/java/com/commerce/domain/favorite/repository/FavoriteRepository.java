package com.commerce.domain.favorite.repository;

import com.commerce.domain.favorite.domain.Favorite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteRepository {
  Page<Favorite> findByUserId(Long userId, Pageable pageable);
  Favorite save(Favorite favorite);
  Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);
  void deleteByUserIdAndProductId(Long userId, Long productId);
}
