package com.commerce.domain.favorite.repository.impl;

import com.commerce.domain.favorite.domain.Favorite;
import com.commerce.domain.favorite.repository.FavoriteRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

  private final JpaFavoriteRepository favoriteRepository;

  public FavoriteRepositoryImpl(JpaFavoriteRepository favoriteRepository) {
    this.favoriteRepository = favoriteRepository;
  }

  @Override
  public Page<Favorite> findByUserId(Long userId, Pageable pageable) {
    return favoriteRepository.findByUserId(userId, pageable);
  }

  @Override
  public Favorite save(Favorite favorite) {
    return favoriteRepository.save(favorite);
  }

  @Override
  public Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId) {
    return favoriteRepository.findByUserIdAndProductId(userId, productId);
  }

  @Override
  public void deleteByUserIdAndProductId(Long userId, Long productId) {
    favoriteRepository.deleteByUserIdAndProductId(userId, productId);
  }
}
